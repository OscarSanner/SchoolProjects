#include <stdio.h>

void f(float x, float* xsquared, float* xcubed){
	*xsquared = x*x;
	*xcubed = x*x*x;
}

int main(){
  float x = 5.0f;
  float xsquared,xcubed;
  f(x, &xsquared, &xcubed);
  printf("x = %f, x^2 = %f, x^3 = %f\n", x, xsquared, xcubed);
}

