/*
 * 	startup.c
 *
 */
 
#define GPIO_E_BAS 0x40021000
#define GPIO_E_MODER ((volatile unsigned long*) GPIO_E_BAS)
#define GPIO_E_OTYPER ((volatile unsigned short*) GPIO_E_BAS + 0x4)
#define GPIO_E_PUPDR ((volatile unsigned long*) GPIO_E_BAS + 0xC)
#define GPIO_E_IDR_LOW ((volatile unsigned char*) GPIO_E_BAS + 0x10)
#define GPIO_E_ODR_LOW ((volatile unsigned char*) GPIO_E_BAS + 0x14)
#define GPIO_E_ODR_HIGH ((volatile unsigned char*) GPIO_E_BAS + 0x15)

#define SYSCFG_EXTICR1 ((unsigned short*) 0x40013808)

#define EXTI_BAS 0x40013C00
#define EXTI_IMR ((unsigned long*) EXTI_BAS)
#define EXTI_FTSR ((unsigned long*) EXTI_BAS + 0x8)
#define EXTI_RTSR ((unsigned long*) EXTI_BAS + 0xC)
#define EXTI_PR ((unsigned long*) EXTI_BAS + 0x14)

#define NVIC_ISER0 ((unsigned long*) 0xE000E100)


#define VTOR 0x2001C000
#define EXTI0_IRQVEC ((volatile unsigned long*) (VTOR + 0x58))
#define EXTI1_IRQVEC ((volatile unsigned long*) (VTOR + 0x5C))
#define EXTI2_IRQVEC ((volatile unsigned long*) (VTOR + 0x60))

static char lastKey;
static char keybLocked;
 
void gpio_init(){
	 * GPIO_E_MODER = 0x55555400; //sätter b15-b5 till utportar och övriga som in.
	 * GPIO_E_OTYPER = 0x0; // Sätter ALLT till pushpull, detta är ok
	 * GPIO_E_PUPDR = 0x294; //Sätter inportar till pullDown/Up enligt specs, sista pinnen b0 lämnas orörd
}

void init_exti(){
	
	* SYSCFG_EXTICR1 &= 0xF000; //Sparar undan de bitar vi inte använder
	* SYSCFG_EXTICR1 |= 0x0444; //Kopplar b0,b1,b2 i E till avbrott EXTI0,1,2;
	
	* EXTI_IMR |= 7; //Tänder de tre första bitarna i IMR, -> avbrott för EXTI 0,1,2 aktiveras.
	
	* EXTI_RTSR |= (1<<2); //Vi vill ha rising trigger på S2, denna är aktiv hög. Känns passande.
	* EXTI_FTSR &= ~(1<<2); //Stänger av falling trigger på S2
	
	* EXTI_FTSR |= 3; // Vi vill ha falling trigger på S1 och för b0, dessa är aktivt låga.
	* EXTI_RTSR &= ~3; //Stänger av rising trigger då vi inte vill ha dubbla triggers.
	
	* NVIC_ISER0 |= (1 << 6); //Aktiverar IRQ i NVIC för EXTI0;
	* NVIC_ISER0 |= (1 << 7); //Aktiverar IRQ i NVIC för EXTI1;
	* NVIC_ISER0 |= (1 << 8); //Aktiverar IRQ i NVIC för EXTI2;
	
	* EXTI0_IRQVEC = KeybIRQ; //placerar adressen för keybirq på vektorn för EXTI0 avbrott
	* EXTI1_IRQVEC = S1IRQ; //placerar adressen för keybirq på vektorn för EXTI1 avbrott
	* EXTI2_IRQVEC = S2IRQ; //placerar adressen för keybirq på vektorn för EXTI2 avbrott
}

unsigned char get(){
	if(keybLocked){ //Om den globala variabeln keybLocked är aktiv returnera 0xF
		return 0xF
	}
	char mat[3][2] = {{1,0},{2,3},{4,5}}; //Skapar en matris med returvärden
	
	for (int i = 0; i < 3; i++){ //För varje rad i returmatrisen
		* GPIO_E_ODR_LOW = (1<<(5+i); // Aktiverar raderna genom att skicka ström
		char c = (* GPIO_E_IDR_LOW >> 3) & 0xFF; // Skapar en char av b0,b1;
		switch(c){
			case 1: * GPIO_E_ODR_LOW | = 0xE0; return mat[i][0]; // returnerar rätt värde i matrisen och sätter tillbaks strömmen för avbrott
			case 2: * GPIO_E_ODR_LOW | = 0xE0; return mat[i][1]; // returnerar rätt värde i matrisen och sätter tillbaks strömmen för avbrott
			default: *GPIO_E_ODR_LOW | = 0xE0; return lastKey; //returnerar senaste keyn, något måste gått fel.
		}
	}
}

void KeybIRQ(){
	EXTI_PR |= 1; //Kvitterar avbrottet
	char c = get(); //Hämtar den nedtryckta knappen som genererat avborttet
	lastKey = c; //Sparar denna i lastKey, Kanske inte förstått uppgiften.
}

void S1IRQ(){
	EXTI_PR |= 2; //Kvitterar avbrottet
	keybLocked = 0; // Låser upp tangentbordet.
}

void S2IRQ(){
	EXTI_PR |= 4; //Kvitterar avbrottet
	keybLocked = 1; //Låser tangentbordet så get() inte returnerar rätt.
}

int main(){
	
}


 
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

void main(void){
	gpio_init(); // aktiverar gpio_e
	init_exti(); // aktiverar avbrott
	* GPIO_E_ODR_LOW |= 0xE0 //Sätter b7-b5 till 1 för att vänta på avbrott.
	
	while(1){ //Sätts i evighetsloop
		*GPIO_E_ODR_HIGH = lastKey; //Kontrollerar konstant lastkey som ändras av interupts från tangentbordet.
	}
}


void circularShiftRight(char* p1, char * p2, char * p3){
	p11=*p1
	p22=*p2
	p33=*p3
	
	*p1 = p22
	*P2 = p33
	*p3 = p11
}
