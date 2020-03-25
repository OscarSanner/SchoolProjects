/*
 * 	startup.c
 *
 */
__attribute__((naked)) __attribute__((section (".start_section")) )
void startup ( void )
{
__asm__ volatile(" LDR R0,=0x2001C000\n");		/* set stack */
__asm__ volatile(" MOV SP,R0\n");
__asm__ volatile(" BL main\n");					/* call main */
__asm__ volatile(".L1: B .L1\n");				/* never return */
}

unsigned int bitconvert(int method, unsigned int value){

	method = (unsigned int) (method & 0x3);
	int pNumb = 0x19940522;
	
	if(method == 0){
		return (value & pNumb);
	}
	if(method == 1){
		return ~(value);
	}
	if(method == 2){
		return (value & 0xFFFF0000);
	}
	if(method == 3){
		value = (value & 0x00FFFF00);
		value = (value >> 2);
		signed short retval = value;
		return (signed int) retval;
	}
}




void main(void)
{
}

