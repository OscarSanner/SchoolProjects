#define		STK_BAS 		0xE000E010
#define		STK_CTRL		((volatile unsigned char *) (STK_BAS))
#define		STK_COUNTFLAG	((volatile unsigned char *) (STK_BAS + 0x2))
#define		STK_LOAD		((volatile unsigned int *) (STK_BAS + 0x4))
#define		STK_VAL			((volatile unsigned int *) (STK_BAS + 0x8))

#define 	GPIO_BAS		0x40021000
#define	GPIO_MODER		((volatile unsigned int *) (GPIO_BAS))
#define 	GPIO_ODR_LOW	((volatile unsigned short *) (GPIO_BAS+0x14))
 
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

void init_app(void){
	* GPIO_MODER &= 0xFFFF0000;
	* GPIO_MODER |= 0x5555;
}

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

void main(void){
	init_app();
	while(1){
		*GPIO_ODR_LOW = 0xFF;
		delay_milli(500);
		*GPIO_ODR_LOW = 0x0;
		delay_milli(500);
	}
}

