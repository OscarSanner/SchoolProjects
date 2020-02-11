#include <stdio.h>
#include <string.h>
#include <math.h>

void cPrinter(){
	for (int i = 0; i < 10; i++){
		printf("I am an awesome C programmer \n");
	}
}

void printBackwards(char word[]){
	int length = strlen(word) - 1;
	for (int i = length; i >= 0; i--){
		printf("%c", word[i]);
	}
	printf("\n");
}

void print8bit(char number){
	int temp;
	for (int i = 7; i >= 0; i--){
		temp = (number >> i);
		printf("%i", (temp & 1));
	}
	printf("\n");
}

void print16bit(short number){
	int temp;
	for (int i = 15; i >= 0; i--){
		temp = (number >> i);
		printf("%i", (temp & 1));
	}
	printf("\n");
}

void print32bit(int number){
	int temp;
	for (int i = 31; i >= 0; i--){
		temp = (number >> i);
		printf("%i", (temp & 1));
	}
	printf("\n");
}

void printByteToDec(char binary[]){
	int powerCounter = 0;
	int sum = 0;
	int length = strlen(binary) - 1;
	for(int i = length; i >= 0; i--){
		if(binary[i] == '1'){
			sum += pow(2,powerCounter);
		}
		powerCounter++;
	}
	printf("%i \n", sum);
}

int main(int argc, char ** argv){
	
	cPrinter();
	printBackwards("olof");
	print8bit(7);
	print16bit(7);
	print32bit(7);
	printByteToDec("10000000");
	
	
	//unsigned char c = 128;
	//c |= 0b00101010; // eller 0x2A
	//print8bit(c);
	
	
	//signed char x = 27;
	//x = ~x;
	//x++;
	
	//printf("%d \n", x);
	
	return 0;
}