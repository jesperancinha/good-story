#include<stdio.h>
#include <inttypes.h>
#include <time.h>
#include<sys/wait.h>
#include<unistd.h>
#include <pthread.h>

int64_t currentMillis()
{
    struct timespec now;
    timespec_get(&now, TIME_UTC);
    return ((int64_t) now.tv_sec) * 1000 + ((int64_t) now.tv_nsec) / 1000000;
}

int processes(int startIndex, int endIndex)
{
    static long long int i;
    static int state = 0;
    switch (state)
    {
        case 0: state = 1;
                for (i = startIndex; i < endIndex; i++)
                {
                    sleep(1);
                    printf("👍 Ordering at %lld. This suspends the next run (coroutine)\n",currentMillis());
                    return i;
                    case 1:
                        sleep(1);
                        printf("✅ Ending step. This is the callback (start of the coroutine): %lld at %lld with id %d\n",i, currentMillis(),pthread_self());
                 }
    }
    puts("┌──────────────┐");
    puts("│ 🛑 Finished! │");
    puts("└──────────────┘");
    state = 0;
    return 0;
}

int main()
{
    printf("╔════════════════════════════════════════════════╗\n");
    printf("║ Welcome to the Epoxy Table manufacture factory ║\n");
    printf("╚════════════════════════════════════════════════╝\n");
    printf("--- Tables are made in parallel steps ---\n");
    int i;
    for (; i=processes(1, 11);){
        printf("---> ▶️ Execution (%d)starts here with id %d!\n",i,pthread_self());
        switch(i){
            case 1: printf("==>Collect Epoxy and Resin\n"); break;
            case 2: printf("==>Mix ingredients\n"); break;
            case 3: printf("==>Polish wooden plank\n"); break;
            case 4: printf("==>Water seal container\n"); break;
            case 5: printf("==>Pour epoxy mix\n"); break;
            case 6: printf("==>Wait for it to dry\n"); break;
            case 7: printf("==>Polish final edges and result\n"); break;
            case 8: printf("==>Fine polish\n"); break;
            case 9: printf("==>Paint\n"); break;
            case 10: printf("==>Slow dry surface\n");
        }
        }
    return 0;
}