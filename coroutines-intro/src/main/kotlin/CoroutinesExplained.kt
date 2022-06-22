import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import java.lang.Thread.sleep
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

/**
 * Created by jofisaes on 22/06/2022
 */
class CoroutinesExplained {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) = runBlocking {
            println(measureTimeMillis {
                runSleepExample()
            })
            println(measureTimeMillis {
                runDelayExample()
            })
        }

        private suspend fun runSleepExample() {
            withContext(Default) {
                println(
                    "3 - This is the parent coroutine, it will not be suspended by launch ==> (${Thread.currentThread().name}) - ${
                        Thread.currentThread().threadId()
                    }"
                )
                launch {
                    sleep(2000)
                    println(
                        "1 - (launch) - This launches a coroutine in parallel ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                    println(
                        "1 - (launch) - The coroutine should remain pinned to the original thread up until the end ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                }
                val deferred = async {
                    println(
                        "4 - (async) - This coroutine is asynchronous and therefore it's thread has to be another ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                }
                println(
                    "3 - The parent coroutine will get suspended with a withContext ==> (${Thread.currentThread().name}) - ${
                        Thread.currentThread().threadId()
                    }"
                )
                withContext(Dispatchers.IO) {
                    println(
                        "2 - (IO) This couroutine has suspended the caller coroutine and now it will be parked or scheduled to run later ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                    sleep(500)
                    println(
                        "2 - (IO) Although the coroutine has been parked, it is now unparked and it remains on the same thread ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                }
                withContext(Dispatchers.Unconfined) {
                    println(
                        "6 - (Unconfined) This coroutine has suspended the caller coroutine and now it will be parked or scheduled to run later ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                    sleep(500)
                    println(
                        "6 - (Unconfined) Although the coroutine has been parked, it is now unparked and it remains on the same thread ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                }
                withContext(Dispatchers.Default) {
                    println(
                        "7 - (Default) This couroutine has suspended the caller coroutine and now it will be parked or scheduled to run later ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                    sleep(500)
                    println(
                        "7 - (Default) Although the coroutine has been parked, it is now unparked and it remains on the same thread ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                }
                println(
                    "3 - This coroutine has been suspended but now running and still on the same thread ==> (${Thread.currentThread().name}) - ${
                        Thread.currentThread().threadId()
                    }"
                )
                deferred.await()
                sleep(2000)
            }
        }

        private suspend fun runDelayExample() {
            withContext(Default)  {
                println(
                    "3 - This is the parent coroutine, it will not be suspended by launch ==> (${Thread.currentThread().name}) - ${
                        Thread.currentThread().threadId()
                    }"
                )
                launch {
                    delay(2000)
                    println(
                        "1 - (launch) - This launches a coroutine in parallel ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                    println(
                        "1 - (launch) - The coroutine should remain pinned to the original thread up until the end ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                }
                val deferred = async {
                    println(
                        "4 - (async) - This coroutine is asynchronous and therefore it's thread has to be another ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                }
                println(
                    "3 - The parent coroutine will get suspended with a withContext ==> (${Thread.currentThread().name}) - ${
                        Thread.currentThread().threadId()
                    }"
                )
                withContext(Dispatchers.IO) {
                    println(
                        "2 - (IO) This couroutine has suspended the caller coroutine and now it will be parked or scheduled to run later ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                    delay(500)
                    println(
                        "2 - (IO) Although the coroutine has been parked, it is now unparked and it remains on the same thread ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                }
                withContext(Dispatchers.Unconfined) {
                    println(
                        "6 - (Unconfined) This coroutine has suspended the caller coroutine and now it will be parked or scheduled to run later ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                    delay(500)
                    println(
                        "6 - (Unconfined) Although the coroutine has been parked, it is now unparked and it remains on the same thread ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                }
                withContext(Dispatchers.Default) {
                    println(
                        "7 - (Default) This couroutine has suspended the caller coroutine and now it will be parked or scheduled to run later ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                    delay(500)
                    println(
                        "7 - (Default) Although the coroutine has been parked, it is now unparked and it remains on the same thread ==> (${Thread.currentThread().name}) - ${
                            Thread.currentThread().threadId()
                        }"
                    )
                }
                println(
                    "3 - This coroutine has been suspended but now running and still on the same thread ==> (${Thread.currentThread().name}) - ${
                        Thread.currentThread().threadId()
                    }"
                )
                deferred.await()
                delay(2000)
            }
        }
    }
}