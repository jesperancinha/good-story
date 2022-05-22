package org.jesperancinha.good.story

import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val log: Logger = LoggerFactory.getLogger(App::class.java)

/**
 * Created by jofisaes on 18/05/2022
 */
fun getSystemRunningData(): String? {
    return try {
        "Prototype Phase"
    } catch (e: Exception) {
        log.error("An error has occurred", e)
        null
    }
}