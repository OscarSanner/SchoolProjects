
//	FÖRELÄSNING 2
//-------------------------------//	UPPGIFT 1

/*
#include <stdio.h>

void f(float x, float* ptr_xsquared, float* ptr_xcubed){
	*ptr_xsquared = x * x ;
	*ptr_xcubed = x * x * x ;
}

int main() {
  float x = 5.0f;
  float xsquared, xcubed;
  f(x, &xsquared, &xcubed);
  printf("x = %f, x^2 = %f, x^3 = %f\n", x, xsquared, xcubed);
}
 */

//-------------------------------//	UPPGIFT 2

/*
#include <stdio.h>
#include <string.h>

char * GetSecondString(char * ptr_string) {
	char * ptr_new;
	int counter = 0;
	while(1){
		if (*(ptr_string + counter) == '|') {
			ptr_new = (ptr_string + counter);
			return ptr_new;
		}
		counter++;
	}
	return ptr_new;
	
	
	//while(*ptr_string != '|') ptr_string++;
	//ptr_string++;
	//return ptr_string;
	
}

int main() {
  char string[] = "This is the first string | This is the second string.";
  printf("The second string is: %s", GetSecondString(string));
}
	*/

//-------------------------------//	UPPGIFT 3

#include <stdio.h>
#include <string.h>

char * GetLongestName(char ** ptr_long_names, int number_of_names){
	char * current_longest_name = 0;
	int most_characters_so_far = 0;
	
	for (int i = 0; i < number_of_names; i++){
		int counter = 0;
		while(ptr_long_names[i][counter] != '\0'){
			counter++;
		}
		if (counter > most_characters_so_far){
			most_characters_so_far = counter;
			current_longest_name = ptr_long_names[i];
		}
	}
	return current_longest_name;
	
	//
	//printf("%s", *ptr_long_names);
	//char * current_longest_name = ptr_long_names[0];

	//int length = strlen(ptr_long_names[0][0]);
	//for (int i = 1; i < number_of_names; i++){
	//	if (strlen(ptr_long_names[i][0]) > strlen(*current_longest_name)) {
	//		current_longest_name = ptr_long_names[i][0];
	//	}
	//}
	//return current_longest_name;
	
	//for (int i = 0; i < number_of_names; i++){
	//	if (strlen(*current_longest_name) < strlen(*(ptr_long_names + i*4))){
	//		current_longest_name = (ptr_long_names + i*4);
	//	}
	//}
	//return current_longest_name;
}

int main() {
  char *long_names[] = {
    "Alexandrovitj Bulgakowski",
    "Constantine Plumberbatcllllh",
    "Bret",
    "Nathaniel Prescott",
    "Jacqueline mmmmmmmmmBratwurst"
  };
  
  int number_of_names = sizeof(long_names)/sizeof(char *);
  char * longest_name = GetLongestName(long_names, number_of_names);
  printf("Longest Name: %s\n", longest_name);
}

