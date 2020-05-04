#include <stdio.h>

int main(int argc, char *argv[]){
    int i;
    printf("Numero de argumentos = %d \n", argc);
    for(i = 0;i < argc; i ++){
       printf("Argumento %d es %s \n", i, argv[i]);
    }
}

