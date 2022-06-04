package org.jesperancinha.good.story.flows

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.math.log

/**
 * Created by jofisaes on 04/06/2022
 */
class FlowManager {

    private fun wordsFlow(words: List<String>): Flow<String> = flow {
        words.forEach {
            emit(it)
        }
    }

    suspend fun showAllWordsInFlow(words: List<String>) =
        wordsFlow(words).collect { logger.info("Read word $it") }

    suspend fun readWordFlowBack(words: List<String>) = wordsFlow(words).toList().joinToString(" ")

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(FlowManager::class.java)
    }
}