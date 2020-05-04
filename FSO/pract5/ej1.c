#include <stdio.h>
#include <pthread.h>
#include <string.h>
#include <unistd.h>



void *Imprime(void *ptr){
    char *men;
    men = (char*)ptr;
    
    usleep(2);
    write(1, men, strlen(men));
}
int main(){
    pthread_attr_t atrib;
    pthread_t hilo1, hilo2;
    
    pthread_attr_init(&atrib);
 
    pthread_create(&hilo1, &atrib, Imprime, "Hola\n");
    pthread_create(&hilo2, &atrib, Imprime, "mundo\n");
    
    //pthread_join(hilo1, NULL);
    //pthread_join(hilo2, NULL);
    usleep(15);
    //pthread_exit(0);
}

