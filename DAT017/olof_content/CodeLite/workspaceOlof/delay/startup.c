/*
 * 	startup.c
 *
 */
 
 #define STK_CTRL ( (volatile unsigned char *) 0xE000E010)
 #define STK_LOAD ( (volatile unsigned long *) 0xE000E014)
 #define STK_VAL ( (volatile unsigned long *) 0xE000E018)
 #define COUNTFLAG ( (volatile unsigned char *) 0xE000E012)
 #define Bargraph ((volatile unsigned long *) 0x40021014)
 
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

void init_app() {
	* ((volatile unsigned long *) 0x40021000) &= 0xFFFF0000;
	* ((volatile unsigned long *) 0x40021000) |= 0x00005555;
}

void delay_250ns(void) {
	* STK_CTRL = 0;
	* STK_LOAD = (168/4) - 1;
	* STK_VAL = 0;
	* STK_CTRL = 5;
	while (  ( *COUNTFLAG & 0x01 ) != 0 ) {}
	* STK_CTRL = 0;
}

void delay_mikro(unsigned int us) {
	#ifdef	SIMULATOR
		us = us / 1000;
	#endif
	for (int i = 0; i < 4*us; i++) {
		delay_250ns();
	}
}

void delay_milli(unsigned int ms) {
	#ifdef	SIMULATOR
		ms = ms / 1000;
		ms++;
	#endif
	for (int i = 0; i < 4000*ms;  i++) {
		delay_250ns();
	}
}

void main(void) {
	init_app();
	while(1) {
		* Bargraph = 0;
		delay_milli(500);
		* Bargraph = 0xFF;
		delay_milli(500);
	}
}

