import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * Created by jofisaes on 22/06/2022
 */
class CoroutinesIntro {
    companion object {
        @DelicateCoroutinesApi
        @JvmStatic
        fun main(args: Array<String>) = runBlocking {
            println(
                measureTimeMillis {
                    (1..1000).map {
                        async {
                            delay(2000)
                        }
                    }.awaitAll()
                }
            )
        }
    }
}