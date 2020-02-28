/*
 * 	startup.c
 *
 */
 #define USBDM
//#define SIMULATOR
#define GPIOD_BASE 		0x40020C00
#define GPIOD_MODER 	(volatile unsigned long*) GPIOD_BASE
#define GPIOD_ODR_LOW 	(volatile unsigned char*) (GPIOD_BASE + 0x14)
#define GPIOD_ODR_HIGH 	(volatile unsigned char*) (GPIOD_BASE + 0x15)

#define SYSTICK_BASE	0xE000E010
#define	STK_CTRL		(volatile unsigned char*) SYSTICK_BASE
#define STK_COUNTFLAG	(volatile unsigned char*) (SYSTICK_BASE + 2)
#define STK_LOAD		(volatile unsigned long*) (SYSTICK_BASE + 4)
#define STK_VAL 		(volatile unsigned long*) (SYSTICK_BASE + 8) 
#define SCB_VTOR		(volatile unsigned long*) 0xE000ED08

#ifdef SIMULATOR
#define DELAY_COUNT	100
#else  
#define DELAY_COUNT	1000000
#endif

static volatile int systic_flag;
static volatile int delay_count;


__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}


void systic_irq_handler(){
	* STK_CTRL = 0;
	delay_count = delay_count - 1;
	if(delay_count > 0){
		delay_1micro();
	}else{
		systic_flag = 1;
	}
}

void init_app(){
	#ifdef USBDM
	* ((unsigned long *) 0x40023830) = 0x18;
	* ((unsigned long *) 0x40023844) |= 0x4000;
	* ((unsigned long *) 0xE000ED08) = 0x2001C000;
	#endif
	
	
	* GPIOD_MODER = 0x55555555;
	* SCB_VTOR = 0x2001C000;
	* ((void (**) (void)) 0x2001C03C) = systic_irq_handler;
}

void delay_1micro(){
	* STK_CTRL = 0;
	* STK_LOAD = (168 - 1);
	* STK_VAL = 0;
	* STK_CTRL = 7;
}

void delay(unsigned int count){
	if(count == 0){
		return;
	}
	delay_count = count;
	systic_flag = 0;
	delay_1micro();
}
	
															   
void main(void){
	init_app();
	* GPIOD_ODR_LOW = 0;
	delay(DELAY_COUNT);
	* GPIOD_ODR_LOW = 0x0F;
	while(1){
		if(systic_flag == 1){
			break;
		}
	}
	* GPIOD_ODR_LOW = 0; 
}


