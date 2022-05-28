package org.jesperancinha.good.story

import com.opencsv.bean.CsvBindByName

/**
 * Created by jofisaes on 28/05/2022
 */
data class FunctionReading(
    @CsvBindByName
    var method: String? = null,
    @CsvBindByName
    var timeComplexity: String? = null,
    @CsvBindByName
    var spaceComplexity: String? = null,
    @CsvBindByName
    var repetition: Long? = -1,
    @CsvBindByName
    var javaDuration: Long? = -1L,
    @CsvBindByName
    var kotlinDuration: Long? = -1L,
    @CsvBindByName
    var machine: String? = null
)