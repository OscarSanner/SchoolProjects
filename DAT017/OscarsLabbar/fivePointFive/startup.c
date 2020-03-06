/*
 * 	startup.c
 *
 */
 
#define USBDM

#define STK_CTRL (volatile unsigned long*)0xE000E010 
#define STK_LOAD (volatile unsigned long*)0xE000E014 
#define STK_VAL (volatile unsigned long*)0xE000E018 
#define STK_CALIB (volatile unsigned long*)0xE000E01C
#define COUNT_FLAG (volatile unsigned char*)0xE000E012
#define Bargraph (volatile unsigned long*) 0x40021014

#define PORT_BASE 0x40021000
#define portModer ((volatile unsigned long*) PORT_BASE)
#define portOtyper ((volatile unsigned short)* PORT_BASE + 4)
#define portOspeedr ((volatile unsigned long)* PORT_BASE + 8)
#define portPupdr ((volatile unsigned long)* PORT_BASE + 0xC)

#define portportIdrLow ((volatile unsigned char*) PORT_BASE + 0x10)
#define portIdrHigh ((volatile unsigned char*) PORT_BASE + 0x11)
#define portOdrLow ((volatile unsigned char*) PORT_BASE + 0x14)
#define portOdrHigh ((volatile unsigned char*) PORT_BASE + 0x15)

#define B_E 0x40
#define B_SELECT 4
#define B_RW 2
#define B_RS 1
#define B_RST 0x20
#define B_CS2 0x10
#define B_CS1 8

typedef unsigned char uint8_t;

__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

void delay_250ns(){
	* STK_CTRL = 0;
	* STK_LOAD = (168 / 4) - 1;
	* STK_VAL = 0;
	* STK_CTRL = 5;
	while((* COUNT_FLAG & 0x01) !=0);
	* STK_CTRL = 0;
}

void delayMikro(unsigned int us){
	#ifdef SIMULATOR
		us = us/1000;
		us++;
	#endif
	
	for(int i = 0; i < 4*us; i++){
		delay_250ns();
	}
}

void delayMilli(unsigned int ms){
	#ifdef SIMULATOR
		ms = ms/1000;
		ms++;
	#endif
	
	for(int i = 0; i < 4000*ms; i++){
		delay_250ns();
	}
}

void ascii_ctrl_bit_set(unsigned char x){
	(* portOdrLow) |=x|B_SELECT;
}

void ascii_ctrl_bit_clear(unsigned char x){
	char notX = ~x;
	* portOdrLow &=notX;
	* portOdrLow |= B_SELECT;
}

void ascii_write_cmd(unsigned char command){
	ascii_ctrl_bit_clear(B_RS);
	ascii_ctrl_bit_clear(B_RW);
	ascii_write_controller(command);
}

void ascii_write_data(unsigned char data){
	ascii_ctrl_bit_set(B_RS);
	ascii_ctrl_bit_clear(B_RW);
	ascii_write_controller(data);
}

void ascii_write_controller(unsigned char command){
		//	delay_250ns();
	ascii_ctrl_bit_set(B_E);
	* portOdrHigh = command;
		//	delay_250ns();
	ascii_ctrl_bit_clear(B_E);
	delay_250ns();
}

unsigned char ascii_read_controller(){
	unsigned char rv;
	ascii_ctrl_bit_set(B_E);
	delay_250ns();
	delay_250ns();
	rv = * portIdrHigh;
	ascii_ctrl_bit_clear(B_E);
	return rv;
}

unsigned char ascii_read_data(){
	* portModer = 0x00005555;
	unsigned char rv;
	ascii_ctrl_bit_set(B_RS);
	ascii_ctrl_bit_set(B_RW);
	rv = ascii_read_controller;
	* portModer = 0x55555555;
	return rv;
}

unsigned char ascii_read_status(){
	* portModer = 0x00005555;
	unsigned char rv;
	ascii_ctrl_bit_clear(B_RS);
	ascii_ctrl_bit_set(B_RW);
	rv = ascii_read_controller();
	* portModer = 0x55555555;
	return rv;
} 

void ascii_command(unsigned char command){
	while((ascii_read_status() & 0x80) == 0x80){}
	delayMikro(8);
	ascii_write_cmd(command);
	delayMilli(2);
}

void ascii_init(){
	ascii_ctrl_bit_clear(B_RS);
	ascii_ctrl_bit_clear(B_RW);
	ascii_command(0x38);
	ascii_command(0x0E);
	ascii_command(0x01);
	ascii_command(0x06);
}

void ascii_gotoxy(int x, int y){
	ascii_ctrl_bit_clear(B_RS);
	ascii_ctrl_bit_clear(B_RW);
	unsigned char adress = x - 1;
	if(y == 2){
		adress += 0x40;
	}
	ascii_command(0x80 | adress);
}

void ascii_write_char(unsigned char c){
	while(ascii_read_status() & 0x80 == 0x80){}
	delayMikro(8);
	ascii_write_data(c);
	delayMikro(50);
}

void init_app(){
	#ifdef USBDM
	*((unsigned long*) 0x40023830) = 0x18;
	__asm volatile(" LDR R0,=0x08000209 \n BLX R0 \n");
	#endif
	* portModer = 0x55555555;
}

void main(void){
	char * s;
	char test1[] = "Antiloper";
	char test2[] = "Display - test";
	
	init_app();
	ascii_init();
	ascii_gotoxy(1,1);
	s = test1;
	while(*s){
		ascii_write_char(*s++);
	}
	ascii_gotoxy(1,2);
	s=test2;
	while(*s){
		ascii_write_char(*s++);
	}
	return 0;
}


