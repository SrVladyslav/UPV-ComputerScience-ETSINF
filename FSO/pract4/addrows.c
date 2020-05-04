#include <stdio.h> 
#include <math.h> 
#define DIMROW 100 
#define NUMROWS 20 
 
typedef struct row 
{    int vector[DIMROW]; 
     long add; 
} row;
 
row matrix[NUMROWS];
int main() 
{
  int i, j, k; 
  long total_add = 0 ;
  // Initializing to 1 all the elements of the vector
  for (i =0; i < NUMROWS; i ++) {
    for (j =0; j < DIMROW; j ++) { 
      matrix[i].vector[j] = 1 ;
      matrix[i].add = 0 ;
    }
  }
  for (i =0; i < NUMROWS; i ++) { 
    for (k =0; k < DIMROW; k ++) {     

      matrix[i].add += matrix[i].vector[k];
    }
  }
  for (i =0; i < NUMROWS; i ++) 
    total_add += matrix[i].add;
  printf ("The total addition is %ld\n", total_add); 
}

