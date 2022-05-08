/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package org.jesperancinha.kotlin.good.story

import kotlinx.coroutines.*
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger

val aiVirtualThread = AtomicInteger(0)

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

@DelicateCoroutinesApi
suspend fun main() {
    println(App().greeting)
    val startTime = LocalDateTime.now()
    GlobalScope.launch {
        repeat(10000000) {
          launch {
                aiVirtualThread.incrementAndGet()
            }
        }
    }.join()
    val endTime = LocalDateTime.now()
    println("Imma be the main Thread")
    println(aiVirtualThread.get())
    println("It took me ${Duration.between(startTime, endTime).seconds} to finish")


}
