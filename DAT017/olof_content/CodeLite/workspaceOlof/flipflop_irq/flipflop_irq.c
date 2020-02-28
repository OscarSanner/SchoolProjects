/*
 * 	startup.c
 *
 */
#define USBDM
//#define SIMULATOR

#define GPIOD_BASE 				0x40020C00
#define GPIOD_MODER		 	(volatile unsigned long*) GPIOD_BASE
#define GPIOD_ODR_LOW 		(volatile unsigned char*) (GPIOD_BASE + 0x14)
#define GPIOD_ODR_HIGH 	(volatile unsigned char*) (GPIOD_BASE + 0x15)

#define GPIOE_BASE 				0x40021000
#define GPIOE_MODER		 	(volatile unsigned long*) GPIOE_BASE
#define GPIOE_IDR_LOW 		(volatile unsigned char*) (GPIOE_BASE + 0x10)
#define GPIOE_IDR_HIGH	 	(volatile unsigned char*) (GPIOE_BASE + 0x11)

#define SYSCFG						0x40013800
#define SYSCFG_EXTICR1		(volatile unsigned short*) (SYSCFG + 0x8)
#define SYSCFG_EXTICR2		(volatile unsigned short*) (SYSCFG + 0xC)
#define SYSCFG_EXTICR3		(volatile unsigned short*) (SYSCFG + 0x10)
#define SYSCFG_EXTICR4		(volatile unsigned short*) (SYSCFG + 0x14)

#define EXTI							0x40013C00
#define EXTI_IMR					(volatile unsigned long*) EXTI
#define EXTI_EMR					(volatile unsigned long*) (EXTI + 0x4)
#define EXTI_RTSR					(volatile unsigned long*) (EXTI + 0x8)
#define EXTI_FTSR					(volatile unsigned long*) (EXTI + 0xC)
#define EXTI_SWEIR				(volatile unsigned long*) (EXTI + 0x10)
#define EXTI_PR						(volatile unsigned long*) (EXTI + 0x14)
 
 #define VTOR							0x2001C000
 
 #define NVIC							0xE000E100
 #define NVIC_ISER0				(volatile unsigned long*) NVIC
 #define NVIC_ISER1				(volatile unsigned long*) (NVIC + 0x4)
 #define NVIC_ISER2				(volatile unsigned long*) (NVIC + 0x8)
 #define NVIC_CER0				(volatile unsigned long*) (NVIC + 0x80)
 #define NVIC_CER1				(volatile unsigned long*) (NVIC + 0x84)
 #define NVIC_CER2				(volatile unsigned long*) (NVIC + 0x88)
 
 
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

static unsigned char counter;

void irq_handler(){
	counter++;
	*((unsigned long *) 0x40013C14) |= (1<<3);
}

void app_init(void){
	#ifdef USBDM
	* ((unsigned long *) 0x40023830) = 0x18;
	* ((unsigned long *) 0x40023844) |= 0x4000;
	* ((unsigned long *) 0xE000ED08) = 0x2001C000;
	#endif
	
	* GPIOD_MODER &= 0xFFFF0000;
	* GPIOD_MODER |= 0x00005555;
	
	* GPIOE_MODER &= 0xFFFF0000;
	* GPIOE_MODER |= 0x00000000;
	
	* SYSCFG_EXTICR1 &= 0x0FFF;
	* SYSCFG_EXTICR1 |= 0x4000;
	
	* EXTI_IMR |= (1<<3);
	* EXTI_RTSR |= (1<<3);
	* EXTI_FTSR &= ~(1<<3);
	
	* ((void (**) (void)) (VTOR + 0x64)) = irq_handler;
	
	* NVIC_ISER0 |= (1<<9);
}

void main(void)
{
	app_init();
	while(1) {
		* GPIOD_ODR_LOW = counter;
	}
}

