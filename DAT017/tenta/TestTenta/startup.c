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

#define STK_BAS 0xE000E010
#define STK_CTRL ((volatile unsigned char*) STK_BAS)
#define STK_COUNTFLAG ((volatile unsigned char*) STK_BAS + 2)
#define STK_LOAD ((volatile unsigned long*) STK_BAS + 4)
#define STK_VAL ((volatile unsigned long*) STK_BAS + 8)
 
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

void init_app(){
	*GPIO_E_MODER = 0x55550500;
	*GPIO_E_OTYPER = 0;
	*GPIO_E_PUPDR = 0xAA;
}

char get_kbd_stat(){
	char retVal = 0;
	char mat[2][4] = {{0x01,0x02,0x04,0x08},{0x10,0x20,0x40,0x80}};
	
	for(int i = 0; i < 2; i++){
		*GPIO_E_ODR_LOW = (1 << (4 + i));
		char input = *GPIO_E_IDR_LOW;
		if(input & 0x08){
			retVal |= mat[i][0]; //x7x3
		}
		if(input & 0x04){
			retVal |= mat[i][1]; //x6x2
		}
		if(input & 0x02){
			retVal |= mat[i][2]; //x5x1
		}
		if(input & 0x01){
			retVal |= mat[i][3]; //x4x0
		}
	}
	*GPIO_E_ODR_LOW = 0;
	return retVal;
}

void delay10ms(){
	*STK_CTRL = 0;
	*STK_VAL = 0;
	*STK_LOAD = (168 * 10000) - 1;
	*STK_CTRL = 5;
	while(*STK_COUNTFLAG != 1);
	*STK_CTRL = 0;
}

char countOnes(char c){
	char ret = 0;
	for(int i = 0; i < 8; i++){
		char ret = (c & 1);
		c = (c >> 1);
	}
	return ret;
}

void disp_kbd(){
	while(1){
		char keys = get_kbd_stat();
		switch (keys){
			case 0: *GPIO_E_ODR_HIGH = 0xFF; break;
			case 1: *GPIO_E_ODR_HIGH = 0; break;
			case 2: *GPIO_E_ODR_HIGH = 1; break;
			case 4: *GPIO_E_ODR_HIGH = 2; break;
			case 8: *GPIO_E_ODR_HIGH = 3; break;
			case 16: *GPIO_E_ODR_HIGH = 4; break;
			case 32: *GPIO_E_ODR_HIGH = 5; break;
			case 64: *GPIO_E_ODR_HIGH = 6; break;
			case 128: *GPIO_E_ODR_HIGH = 7; break;
			default: *GPIO_E_ODR_HIGH = countOnes(keys);
		}
		delay10ms();
	}
}

void main(void)
{
	short a = 0x000F;
	short b = 0x00F0;
	short c = 0x0F00;
	a |= (b&c);
}

