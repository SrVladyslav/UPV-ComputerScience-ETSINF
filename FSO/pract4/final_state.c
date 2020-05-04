/* my_child.c */
#include <stdio.h>
#include <stdlib.h>
#include<sys/types.h>
#include<unistd.h>
int main(int argc, char *argv[])
{
    int val, x;
    int status;
    int i = 0;
    printf("Start\n");
    for(i = 0; i < 4; i ++){
        val = fork();
        if(val == 0){
            printf("Hijo iteracion %d PID: %d > PPID: %d\n",i,getpid(),getppid());
            sleep(10);
            exit(i);
        }
    }
    sleep(10);
    
   while(wait(&status) != -1){
     printf("Este es el valor de mi hijo: %d", status);
   }
   exit(0);
}

