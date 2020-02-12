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
 
__attribute__((naked) ) __attribute__((sectionvolatile (".start_section")) )
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

void main(void){
	init_app();
	while(1){
		*Bargraph = 0xFF;
		*Bargraph = 0;
		delayMilli(500);	//DOES SOME REALLY FUCKING COOL SHIT
		*Bargraph = 0xFF;
		delayMilli(500);
	}
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

