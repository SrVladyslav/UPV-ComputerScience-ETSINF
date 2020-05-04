#include<stdio.h>

int main(int argc, char *argv[]){
    int i;
    for(i = 0; i < argc; i ++){
        if(argv[i][0] == '-'){
            if(argv[i][1] == 'c'){
                printf("Argumento %d es Compilar \n", i);
            }else if(argv[i][1] == 'E'){
                printf("Argumento %d es Preprocesar \n", i);
            }else if(argv[i][1] == 'i'){
                printf("Argumento %d es Incluir %s \n", i, argv[i]+2);
            }
        }
    }
}

