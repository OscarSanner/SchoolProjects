#include <stdio.h>
#include <string.h>

void printbinchar(char character){
    char output[9];
    itoa(character, output, 2);
    printf("%s\n", output);
}

void toBits(char input){
		for(int i = 0; i < 8; i++){
			char bit = (char)(input & 0x01);
			printf("%c", bit);
			input = (input >> 1);
		}
	}

int main(int argc, char **argv)
{
	for (int i = 0; i < 10; i++){
		printf("I am an awesome programmer \n");
	}
	
	char a[] = "Monkey";
	printf(a);
	printf("\n");
	
	
	char aa[strlen(a)];
	int counter = 0;
	for(int i = strlen(a) - 1; i >= 0; i--){
		aa[counter] = a[i];
		counter++;
	}
	printf(aa);
	printf("\n");
	printf("\n");
	printf("\n");
	toBits('a');
	
	return 0;	
}


