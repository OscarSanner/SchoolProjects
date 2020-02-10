/*
 * 	startup.c
 *
 */
 
#define GPIOD_BASE 0x40020C00
#define GPIOD_MODER ((volatile unsigned long*) GPIOD_BASE)
#define GPIOD_PUPDR ((volatile unsigned long*) GPIOD_BASE + 0x0C)
#define GPIOD_OTYPER ((volatile unsigned short*) GPIOD_BASE + 4)
#define Keypad ((volatile unsigned char*) GPIOD_BASE + 0x11)
#define SevenDigitDisplay ((volatile unsigned long*) GPIOD_BASE + 0x10)

 
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

void app_init(){
	* GPIOD_MODER = 0x55005555;
	* GPIOD_PUPDR = 0xAAAAAAAA;
	* GPIOD_OTYPER = 0x0;
}

unsigned char keyb(){
	char column;
	char key;
	for(int row = 0; row < 4; row++){
		activateRow(row);
		column = readColumn();
		if (column != 0){
			return getKeyValue(row);
		}
		key = 0xFF;
	}
}

void activateRow(int row){
	char c = * Keypad;
	switch(row){
		case 0: * Keypad = c | 0x10;
		case 1: * Keypad = c | 0x20;
		case 2: * Keypad = c | 0x40;
		case 3: * Keypad = c | 0x80;
		default: return;
	}
}

int getKeyValue(int row){
	
}

int readColumn(){
	return (char)(Keypad && 0xF);
}

void main(void){
	
}

