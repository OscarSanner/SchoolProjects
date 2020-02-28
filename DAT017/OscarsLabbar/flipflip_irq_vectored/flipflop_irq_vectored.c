/*
 * 	startup.c
 *
 */
 

//#define SIMULATOR
#define USBDM

#define GPIOD_BASE 		0x40020C00
#define GPIOD_MODER 	(volatile unsigned long *) GPIOD_BASE
#define GPIOD_ODR_LOW 	(volatile unsigned char *) (GPIOD_BASE + 0x14)
#define GPIOD_ODR_HIGH 	(volatile unsigned char *) (GPIOD_BASE + 0x15)

#define GPIOE_BASE 		0x40021000
#define GPIOE_MODER 	(volatile unsigned long *) GPIOE_BASE
#define GPIOE_IDR_LOW 	(volatile unsigned char *) (GPIOE_BASE + 0x10)
#define GPIOE_IDR_HIGH 	(volatile unsigned char *) (GPIOE_BASE + 0x11)
#define GPIOE_ODR_LOW 	(volatile unsigned char *) (GPIOE_BASE + 0x14)
#define GPIOE_ODR_HIGH 	(volatile unsigned char *) (GPIOE_BASE + 0x15)

#define SYSCFG	0x40013800
#define SYSCFG_EXTICR1 (volatile unsigned short *) (SYSCFG + 0x8)
#define SYSCFG_EXTICR2 (volatile unsigned short *) (SYSCFG + 0xC)
#define SYSCFG_EXTICR3 (volatile unsigned short *) (SYSCFG + 0x10)
#define SYSCFG_EXTICR4 (volatile unsigned short *) (SYSCFG + 0x14)

#define EXTI			0x40013C00
#define EXTI_IMR		(volatile unsigned long *) EXTI
#define EXTI_EMR		(volatile unsigned long *) (EXTI + 0x4)
#define EXTI_RTSR		(volatile unsigned long *) (EXTI + 0x8)
#define EXTI_FTSR		(volatile unsigned long *) (EXTI + 0xC)
#define EXTI_SWIER		(volatile unsigned long *) (EXTI + 0x10)
#define EXTI_PR			(volatile unsigned long *) (EXTI + 0x14)

#define VTOR			0x2001C000
#define EXTI3_IRQVEC	((void (**) (void)) (VTOR + 0x64))	
#define EXTI2_IRQVEC	((void (**) (void)) (VTOR + 0x60))	
#define EXTI1_IRQVEC	((void (**) (void)) (VTOR + 0x5C))	
#define EXTI0_IRQVEC	((void (**) (void)) (VTOR + 0x58))	

#define NVIC_EXTI3_IRQ_BPOS	(1<<9)
#define NVIC_EXTI2_IRQ_BPOS (1<<8)
#define NVIC_EXTI1_IRQ_BPOS (1<<7)
#define NVIC_EXTI0_IRQ_BPOS (1<<6)

#define EXTI3_IRQ_BPOS (1<<3)
#define EXTI2_IRQ_BPOS (1<<2)
#define EXTI1_IRQ_BPOS (1<<1)
#define EXTI0_IRQ_BPOS (1<<0)

#define NVIC			0xE000E100
#define NVIC_ISER0		(volatile unsigned long *) NVIC
#define NVIC_ISER1		(volatile unsigned long *) (NVIC + 0x4)
#define NVIC_ISER2		(volatile unsigned long *) (NVIC + 0x8)

#define NVIC_CER0		(volatile unsigned long *) (NVIC + 0x80)
#define NVIC_CER1		(volatile unsigned long *) (NVIC + 0x84)
#define NVIC_CER2		(volatile unsigned long *) (NVIC + 0x88)

#define IRQ0	1
#define IRQ1	2
#define IRQ2	4

#define RST0	0x10
#define RST1	0x20
#define RST2	0x40

 
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

static volatile unsigned char counter;

void irq0_handler(){
	*((volatile unsigned long *) 0x40013C14) |= (1<<0);
	
	* GPIOE_ODR_LOW |= RST0;
	* GPIOE_ODR_LOW &= ~RST0;
		
	counter++;
}

void irq1_handler(){
	*((volatile unsigned long *) 0x40013C14) |= (1<<1);
	
	* GPIOE_ODR_LOW |= RST1;
	* GPIOE_ODR_LOW &= ~RST1;	
		
	counter = 0;
}

void irq2_handler(){
	*((volatile unsigned long *) 0x40013C14) |= (1<<2);
	
	* GPIOE_ODR_LOW |= RST2;
	* GPIOE_ODR_LOW &= ~RST2;
		
	if(counter){
		counter = 0;
	}else{
		counter = 0xFF;
	}
}

void app_init(){
	
#ifdef USBDM
	* ((unsigned long *) 0x40023830) = 0x18;
	* ((unsigned long *) 0x40023844) = 0x4000;
	* ((unsigned long *) 0xE000ED08) = 0x2001C000;
#endif
	
	* GPIOD_MODER &= 0xFFFF0000;
	* GPIOD_MODER |= 0x00005555;
	
	* GPIOE_MODER &= 0xFFFF0000;
	* GPIOE_MODER |= 0x00005500;
	
	* SYSCFG_EXTICR1 &= 0xF000;
	* SYSCFG_EXTICR1 |= 0x0444;
	
	* EXTI_IMR |= (EXTI0_IRQ_BPOS | EXTI1_IRQ_BPOS | EXTI2_IRQ_BPOS);
	* EXTI_RTSR |= (EXTI0_IRQ_BPOS | EXTI1_IRQ_BPOS | EXTI2_IRQ_BPOS);
	* EXTI_FTSR &= ~(EXTI0_IRQ_BPOS | EXTI1_IRQ_BPOS | EXTI2_IRQ_BPOS);
	
	* EXTI0_IRQVEC = irq0_handler;
	* EXTI1_IRQVEC = irq1_handler;
	* EXTI2_IRQVEC = irq2_handler;
	
	* NVIC_ISER0 |= (NVIC_EXTI0_IRQ_BPOS | NVIC_EXTI1_IRQ_BPOS | NVIC_EXTI2_IRQ_BPOS);
}

void main(void){
	app_init();
	while(1){
		* GPIOD_ODR_LOW = counter;
	}
}

