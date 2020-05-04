/* my_child.c */
#include <stdio.h>
#include <stdlib.h>
#include<sys/types.h>
#include<unistd.h>
int main(int argc, char *argv[])
{
    int val;
    int i = 0;
    printf("Start\n");
    for(i = 0; i < 5; i ++){
        val = fork();
        if(val == 0){
            printf("Hijo %d PID: %d > PPID: %d\n",i,getpid(),getppid());
            sleep(5);
            break;
        }
    }
    sleep(10);
    exit(0);
}

