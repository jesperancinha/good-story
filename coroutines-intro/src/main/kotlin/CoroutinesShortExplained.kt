import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Unconfined
import java.lang.Thread.sleep
import kotlin.system.measureTimeMillis

/**
 * Created by jofisaes on 22/06/2022
 */
class CoroutinesShortExplained {

    companion object {
        @JvmStatic
        fun main(args: Array<String> = emptyArray()) {
            println(measureTimeMillis {
                runSleepExample()
            })
            println(measureTimeMillis {
                runDelayExample()
            })
        }

        /**
         * Using Thread.sleep interferes with the coroutine execution
         * This way Thread.sleep isn't advisable given that it is a blocking operation
         * We will rarely need or want to block the main Thread.
         *
         * This code should execute in ~5500 ms
         *
         * This is the sum of:
         * 1. the first launch operation which uses a blocking sleep of 2000 ms
         * 2. the last sleep operation which uses a blocking sleep of 2000 ms
         * 3. the result of whatever happens when suspending the main function with withContext 3 times with the different available ones for non-android appliations.
         */
        private fun runSleepExample() = runBlocking {
            println("3 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            launch {
                sleep(2000)
                println("1 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
                println("1 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            }
            val deferred = async {
                println("4 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            }
            println("3 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            withContext(IO) {
                println("2 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
                sleep(500)
                println("2 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            }
            withContext(Unconfined) {
                println("6 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
                sleep(500)
                println("6 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            }
            withContext(Default) {
                println("7 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
                sleep(500)
                println("7 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            }
            println("3 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            deferred.await()
            sleep(2000)
        }


        /**
         * Coroutines should not have sleep implemented in them.
         *
         * This function will take ~3500 ms to exectute.
         *
         * The first launch operation takes ~2000 ms
         * However, the first launch runs in parallel. More precisely, it will run on the main thread.
         * The delay is schedule and so nothing is blocked
         *
         * The three withContext will take ~500 ms times 3. This happens becase they suspend the main thread in order to execute themselves
         *
         * Finally, the last delay will delay the main thread in a non blocking way for ~2000 ms.
         * It will seem like it is blocking the Thread, but not really.
         * If we have another coroutine taking advantage of the main thread, it will still execute with no problem.
         */
        private fun runDelayExample() = runBlocking {
            println("3 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            launch {
                delay(2000)
                println("1 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
                println("1 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            }
            val deferred = async {
                println("4 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            }
            println("3 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            withContext(IO) {
                println("2 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
                delay(500)
                println("2 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            }
            withContext(Unconfined) {
                println("6 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
                delay(500)
                println("6 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            }
            withContext(Default) {
                println("7 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
                delay(500)
                println("7 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            }
            println("3 ==> (${Thread.currentThread().name}) - ${Thread.currentThread().threadId()}")
            deferred.await()
            delay(2000)
        }
    }
}