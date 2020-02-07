#include <stdio.h>
#include <string.h>
#include <math.h>

void cPrinter(){
	for(int i = 0; i < 10; i++){
		printf("I am an awesome C progrmmer\n");
	}
}

void reversePrint(char word[]){
	int lenght = strlen(word) - 1;
	for(int i = lenght; i >=0; i--){
		printf("%c", word[i]);
	}
	printf("\n");
}

void charToBinary(char number){
	for (int i = 7; i >= 0; i--){
		int binaryToPrint = number >> i;
		binaryToPrint = binaryToPrint & 1;
		printf("%i", binaryToPrint);
	}
	printf("\n");
}

void shortToBinary(short number){
	for (int i = 15; i >= 0; i--){
		int binaryToPrint = number >> i;
		binaryToPrint = binaryToPrint & 1;
		printf("%i", binaryToPrint);
	}
	printf("\n");
}

void intToBinary(int number){
	for (int i = 31; i >= 0; i--){
		int binaryToPrint = number >> i;
		binaryToPrint = binaryToPrint & 1;
		printf("%i", binaryToPrint);
	}
	printf("\n");
}

void binaryToDec(char bin[]){
	int powerCounter = 0;
	int sum = 0;
	for(int i = strlen(bin) - 1; i >= 0; i--){
		if(bin[i] == '1'){
			sum = sum + pow(2,i);
			powerCounter++;
		}
	}
	printf("%i\n", sum);
}



int main(int argc, char **argv){
	//cPrinter();
	//reversePrint("Olof");
	//intToBinary((int)0xFAC6B389);
	//binaryToDec("11110111");
	//unsigned char c = 128;
	//c |= (char)0x2A;
	//charToBinary(128);
	//charToBinary(c);
	//return 0;
	signed char x = 97;
	x = ~x;
	x = x + 1;
	printf("%d\n", x);
}
