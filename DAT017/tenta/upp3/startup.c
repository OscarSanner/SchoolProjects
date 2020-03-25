/*
 * 	startup.c
 *
 */
 
#define GPIO_C_BAS 0x40020800
#define GPIO_C_MODER ((volatile unsigned long*) GPIO_C_BAS)
#define GPIO_C_ODR_LOW ((volatile unsigned char*) (GPIO_C_BAS + 0x14))

#define SYSCFG_EXTICR1 ((unsigned short*) 0x40013808)

#define EXTI_BAS 0x40013C00
#define EXTI_IMR ((unsigned long*) EXTI_BAS)
#define EXTI_FTSR ((unsigned long*) EXTI_BAS + 0x8)
#define EXTI_RTSR ((unsigned long*) EXTI_BAS + 0xC)
#define EXTI_PR ((unsigned long*) EXTI_BAS + 0x14)

#define NVIC_ISER0 ((unsigned long*) 0xE000E100)

#define VTOR 0x2001C000
 
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}


void irqHandler(){
	*GPIO_C_ODR_LOW = 0xFF;
	*EXTI_PR |= (1<<0);
	//kod
}

void app_init(){
	*GPIO_C_MODER = 0x10;
	
	*SYSCFG_EXTICR1 &= 0xFFF0;
	*SYSCFG_EXTICR1 |= 0x0002;
	
	*EXTI_IMR |= (1<<0);
	*EXTI_FTSR |= (1<<0);
	*EXTI_RTSR &= ~(1<<0);
	
	*NVIC_ISER0 |= (1<<6);
	
	*VTOR = irqHandler;
}

int oddbit(int a, unsigned int * num );	

	char ret = 0
	a=~a;
	while(a){
		ret += (a & 1);
		a = (a >> 1);
	}
	*num = ret;
	return ret % 2;
}

void main(void)
{
}

