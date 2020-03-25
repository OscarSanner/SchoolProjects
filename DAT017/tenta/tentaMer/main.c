#include <stdio.h>

int main(int argc, char **argv)
{
	short a = 0x000F;
	short b = 0x00F0;
	short c = 0x0F00;
	a |= (b&c);
	
	printf("%i", a);
	return 0;
}
