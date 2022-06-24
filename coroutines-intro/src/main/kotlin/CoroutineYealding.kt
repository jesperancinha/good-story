import kotlinx.coroutines.*

/**
 * Created by jofisaes on 24/06/2022
 */
class CoroutineYealding {
    companion object {
        @JvmStatic
        fun main(args: Array<String>)  {
                println("The beginning on Thread ${Thread.currentThread()}")

                runBlocking {
                    launch { mainChapter() }
                    launch { insideChapter() }
                    println("Started Book on Thread ${Thread.currentThread()}")
                }

                println("The end main on Thread ${Thread.currentThread()}")
                println("Check the order. Yield gives a hint for another thread to be used ${Thread.currentThread()}")
        }

        private suspend fun insideChapter() {
            println("Inside chapter Thread ${Thread.currentThread()}")
            yield()
            println("End Inside chapter Thread ${Thread.currentThread()}")
        }

        private suspend fun mainChapter() {
            println("Main chapter Thread ${Thread.currentThread()}")
            yield()
            println("End main chapter Thread ${Thread.currentThread()}")
        }
    }
}