/*
 * 	startup.c
 *
 */
 
#define GPIOD_BASE 0x40020C00
#define GPIOD_MODER ((volatile unsigned long*) GPIOD_BASE)
#define GPIOD_PUPDR ((volatile unsigned long*) GPIOD_BASE + 0x0C)
#define GPIOD_OTYPER ((volatile unsigned short*) GPIOD_BASE + 4)
#define KeypadIn ((volatile unsigned char*) GPIOD_BASE + 0x11)
#define KeypadOut ((volatile unsigned char*) GPIOD_BASE + 0x15)
#define SevenDigitDisplay ((volatile unsigned char*) GPIOD_BASE + 0x14)

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
	* GPIOD_PUPDR = 0x00AA0000;
	* GPIOD_OTYPER = 0x0;
}

unsigned char keyb(){
	char column;
	char key;
	for(int row = 0; row < 4; row++){
		activateRow(row);
		column = readColumn();
		
		if (column != 4){
			key = getKeyValue(row);
			return key;
		}
	}
	return -1;
}

void activateRow(int row){
	switch(row){
		case 0: * KeypadOut = 0x10; break;
		case 1: * KeypadOut = 0x20; break;
		case 2: * KeypadOut = 0x40; break;
		case 3: * KeypadOut = 0x80; break;
		default: * KeypadOut = 0x0;
	}
}

int getKeyValue(int row){
	unsigned char keys[4][4] = { {1, 2, 3, 0xA}, {4, 5, 6, 0xB}, {7, 8, 9, 0xC}, {0xF, 0, 0xE, 0xD} };
	return keys[row][readColumn()];
}

int readColumn(){
	unsigned char c = * KeypadIn;
	if(c & 0x8){return 3;}
	if(c & 0x4){return 2;}
	if(c & 0x2){return 1;}
	if(c & 0x1){return 0;}
	return 4;
}

void out7seg(unsigned char key){
	char display_7seg_hex_to_decimal_array[] ={0x3F,0x06,0x5B,0x4F,0x66,0x6D,0x7D,0x07,0x7F,0x6F,0x77,0x7C,0x39,0x5E,0x79,0x71};
	unsigned char dv;
	if(key < 16){
		dv = display_7seg_hex_to_decimal_array[key];
	}else{
		dv = 0x00;
	}
	* SevenDigitDisplay = dv;
}

void main(void){
	app_init();
	while(1){
		out7seg(keyb());
	}
}

