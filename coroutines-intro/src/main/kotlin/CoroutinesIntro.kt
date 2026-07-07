import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.milliseconds

class CoroutinesIntro {
    companion object {
        @DelicateCoroutinesApi
        @JvmStatic
        fun main(args: Array<String> = emptyArray()) = runBlocking {
            println(
                measureTimeMillis {
                    (1..1000).map {
                        async {
                            delay(2000.milliseconds)
                        }
                    }.awaitAll()
                }
            )
        }
    }
}