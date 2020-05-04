#include <stdio.h> 
int 
a = 0; /* variable global */  

void inc_a(void) { 
    int a; 
a++; 
} 

int valor_anterior(int v) { 
int temp; 
int s; 
temp = s; 
      s = v; 
      return temp; 
}
5 
main() 
{ // Esta función incrementa 
en 1 el 
valor de la variable global a 
int b = 2; /* variable local */ 
inc_a();
valor_anterior(b);
printf("a= %d, b= %d\
n", a, b); 
a++; 
b++; 
inc_a(); 
      b = valor_anterior(b);
printf("a= %d, b= %d\
n", a, b); 
} 

