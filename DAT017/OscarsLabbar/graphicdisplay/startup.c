/*
 * 	startup.c
 *
 */
#define		STK_BAS 		0xE000E010
#define		STK_CTRL		((volatile unsigned char *) (STK_BAS))
#define		STK_COUNTFLAG	((volatile unsigned char *) (STK_BAS + 0x2))
#define		STK_LOAD		((volatile unsigned int *) (STK_BAS + 0x4))
#define		STK_VAL			((volatile unsigned int *) (STK_BAS + 0x8))

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
#define B_CS1_AND_B_CS2 0x18

#define LCD_ON 0x3F // Display on
#define LCD_OFF 0x3E // Display off
#define LCD_SET_ADD 0x40 // Set horizontal coordinate
#define LCD_SET_PAGE 0xB8 // Set vertical coordinate
#define LCD_DISP_START 0xC0 // Start address
#define LCD_BUSY 0x80 // Read busy status

typedef unsigned char uint8_t;

__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}


void init_app(void){
	* portModer = 0x55555555;
}
	// 					DELAYS					//
void delay_250ns(void){
	*STK_CTRL = 0;
	*STK_LOAD = 168/4 -1;
	*STK_VAL = 0;
	*STK_CTRL = 5;
	while( (*STK_COUNTFLAG & 0x01) != 0){
	}
	*STK_CTRL = 0;
}

void delay_mikro(unsigned int us){	
	#ifdef SIMULATOR 
	us = us/1000;
	us ++;
	#endif
	for(int i = 0; i < 4*us; i++){
		delay_250ns();
	}
}

void delay_milli(unsigned int ms){
	#ifdef SIMULATOR
		ms = ms/1000;
		ms ++;
	#endif
	for(int i = 0; i < ms; i++){
		delay_mikro(1000);
	}
}

void delay_500ns(){
	delay_250ns();
	delay_250ns();
}

//						DISPLAY						//

void graphic_ctrl_bit_set(uint8_t x){
	* portOdrLow |= (x & ~B_SELECT);
	* portOdrLow &= B_SELECT;
}
void graphic_ctrl_bit_clear(uint8_t x){
	* portOdrLow &= ~(x | B_SELECT);
}

void select_controller(uint8_t controller){
	uint8_t both = (int)(B_CS1 & B_CS2);
	switch(controller){
		case 0: graphic_ctrl_bit_clear(B_CS1 | B_CS2); break;
		case B_CS1: graphic_ctrl_bit_clear(B_CS2); 
					graphic_ctrl_bit_set(B_CS1); break;
		case B_CS2: graphic_ctrl_bit_clear(B_CS1); 
					graphic_ctrl_bit_set(B_CS2); break;
		case (B_CS1_AND_B_CS2): graphic_ctrl_bit_set(B_CS1 | B_CS2); break;
		default: return;
	}
}

void graphic_wait_ready(void){
	graphic_ctrl_bit_clear(B_E);
	*portModer = 0x00005555;
	graphic_ctrl_bit_set(B_RW);
	graphic_ctrl_bit_clear(B_RS);
	delay_500ns();
	while(1){
		graphic_ctrl_bit_set(B_E);
		delay_500ns();
		graphic_ctrl_bit_clear(B_E);
		delay_500ns();
		if((* portIdrHigh & LCD_BUSY) == 0){
			break;
		}
	}
	graphic_ctrl_bit_set(B_E);
	* portModer = 0x55555555;
}

uint8_t graphic_read(uint8_t controller){
	graphic_ctrl_bit_clear(B_E);
	*portModer = 0x00005555;
	graphic_ctrl_bit_set(B_RS | B_RW);
	select_controller(controller);
	delay_500ns();
	graphic_ctrl_bit_set(B_E);
	delay_500ns();
	uint8_t rv = * portIdrHigh;
	graphic_ctrl_bit_clear(B_E);
	* portModer = 0x55555555;				//VARFÖR ÄR DETTA VIKTIGT???
	if(controller == B_CS1){
		select_controller(B_CS1);
		graphic_wait_ready();
	}
	if(controller == B_CS2){
		select_controller(B_CS2);
		graphic_wait_ready();
	}
	return rv;
}

void graphic_write(uint8_t value, uint8_t controller){
	* portOdrHigh = value;
	select_controller(controller);
	delay_500ns();
	graphic_ctrl_bit_set(B_E);
	delay_500ns();
	graphic_ctrl_bit_clear(B_E);
	
	if(controller & B_CS1){
		select_controller(B_CS1);
		graphic_wait_ready();
	}
	if(controller & B_CS2){
		select_controller(B_CS2);
		graphic_wait_ready();
	}
	*portOdrHigh = 0;
	graphic_ctrl_bit_set(B_E);
	select_controller(0);
}

void graphic_write_command(uint8_t command, uint8_t controller){
	graphic_ctrl_bit_clear(B_E);
	select_controller(controller);
	graphic_ctrl_bit_clear(B_RS | B_RW);
	graphic_write(command, controller);
}

void graphic_write_data(uint8_t data, uint8_t controller){
	graphic_ctrl_bit_clear(B_E);
	select_controller(controller);
	graphic_ctrl_bit_set(B_RS);
	graphic_ctrl_bit_clear(B_RW);
	graphic_write(data, controller);
}

uint8_t graphic_read_data(uint8_t controller){
	graphic_read(controller);
	return graphic_read(controller);
}

void graphic_initialize(){
	graphic_ctrl_bit_set(B_E);
	delay_mikro(10);
	graphic_ctrl_bit_clear(B_CS1 | B_CS2 | B_RST | B_E);
	delay_milli(30);
	graphic_ctrl_bit_set(B_RST);
	graphic_write_command(LCD_OFF, B_CS1 | B_CS2);
	graphic_write_command(LCD_ON, B_CS1 | B_CS2);
	graphic_write_command(LCD_DISP_START, B_CS1 | B_CS2);
	graphic_write_command(LCD_SET_ADD, B_CS1 | B_CS2);
	graphic_write_command(LCD_SET_PAGE, B_CS1 | B_CS2);
	select_controller(0);
}

void main(void){
	
}

