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
#define portModer ((unsigned long)* PORT_BASE)
#define portOtyper ((unsigned short)* PORT_BASE + 4)
#define portOspeedr ((unsigned long)* PORT_BASE + 8)
#define portPupdr ((unsigned long)* PORT_BASE + 0xC)

#define portportIdrLow ((unsigned char)* PORT_BASE + 0x10)
#define portIdrHigh ((unsigned char)* PORT_BASE + 0x11)
#define portOdrLow ((unsigned char)* PORT_BASE + 0x14)
#define portOdrHigh ((unsigned char)* PORT_BASE + 0x15)

#define B_E 0x40
#define B_SELECT 4
#define B_RW 2
#define B_RS 1

__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

void init_app(){
	*((volatile unsigned long*) 0x40021000) &=0xFFFF0000;
	*((volatile unsigned long*) 0x40021000) |=0x55;
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

void main(void)
{
}


