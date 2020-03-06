/*
 * 	startup.c
 *
 */
 
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
	delay_mikro(8);
	ascii_write_cmd(command);
	delay_milli(2);
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
	while((ascii_read_status() & 0x80) == 0x80){
		
	}
	delay_mikro(8);
	ascii_write_data(c);
	delay_mikro(50);
}

void ascii_playerscore_init(void){
	char * s;
	char test1[] = "Player Left: 0";	//plats 14
	char test2[] = "Player Right: 0";	//plats 15
	
	ascii_init();
	ascii_gotoxy(1,1);
	s = test1;
	while(*s){
		ascii_write_char(*s);
		*s++;
	}
	ascii_gotoxy(1,2);
	s=test2;
	while(*s){
		ascii_write_char(*s);
		*s++;
	}
}


