/*
 * 	startup.c
 *
 */
 
 #define SIMULATOR
 
#ifdef SIMULATOR
#define DELAY_COUNT 10000
#else
#define DELAY_COUNT 1000000
#endif

#define GPIO_D_BAS 0x40020C00
#define GPIO_D_MODER ((volatile unsigned long *) GPIO_D_BAS)
#define GPIO_D_OTYPER ((volatile unsigned short *) GPIO_D_BAS + 0x4)
#define GPIO_D_OSPEEDR ((volatile unsigned long *) GPIO_D_BAS + 0x8)
#define GPIO_D_PUPDR ((volatile unsigned long *) GPIO_D_BAS + 0xC)
#define GPIO_D_ODR_LOW ((volatile unsigned char *) GPIO_D_BAS + 0x14)
#define GPIO_D_ODR_HIGH ((volatile unsigned char *) GPIO_D_BAS + 0x15)

 #define SYSTICK 0xE000E010
 #define STK_CTRL ((volatile unsigned char *) SYSTICK)
 #define STK_COUNTFLAG ((volatile unsigned char *) SYSTICK + 0x2)
 #define STK_LOAD ((volatile unsigned int *) SYSTICK + 0x4)
 #define STK_VAL ((volatile unsigned int *) SYSTICK + 0x8)
 
 #define SCB_VTOR ((volatile unsigned long *) 0xE000ED08)
 
 static volatile int systick_flag;
 static volatile int delay_count;
  
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}


void systick_irq_handler(void){
	* STK_CTRL = 0;
	delay_count--;
	if(delay_count > 0){
		delay_1mikro();
	} else {
		systick_flag = 1;
	}
}

void init_app() {
	* GPIO_D_MODER = 0x55555555;
	* SCB_VTOR = 0x2001C000;
	*((void (**)(void)) 0x2001C03C ) = systick_irq_handler;
}

void delay_1mikro(void){
	*STK_CTRL = 0;
	*STK_LOAD = (168-1);
	*STK_VAL = 0;
	*STK_CTRL = 7;
	}
	
void delay(unsigned int count){
		if (count == 0){
			return;
		}
		delay_count = count;
		systick_flag = 0;
		delay_1mikro();
	}

void main(void)
{
	init_app();
	* GPIO_D_ODR_LOW = 0x00;
	delay(DELAY_COUNT);
	* GPIO_D_ODR_LOW = 0xFF;
	while(1) {
		if (systick_flag == 1) {
			break;
		}
		*GPIO_D_ODR_HIGH = 0x55;
			//PLACERA KOD SOM KAN UTFÖRAS UNDER VÄNTETIDEN
	}
	//PLACERA KOD SOM VÄNTAR PÅ TIME-OUT
	* GPIO_D_ODR_LOW = 0x00;
}

