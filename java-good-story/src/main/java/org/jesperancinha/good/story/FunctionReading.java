package org.jesperancinha.good.story;

import com.opencsv.bean.CsvBindByName;

/**
 * Created by jofisaes on 28/05/2022
 */
public class FunctionReading{
    @CsvBindByName
    private String method;
    @CsvBindByName
    private String timeComplexity;
    @CsvBindByName
    private String spaceComplexity;
    @CsvBindByName
    private Long repetition;
    @CsvBindByName
    private Long javaDuration;
    @CsvBindByName
    private Long kotlinDuration;
    @CsvBindByName
    private String machine;

    public FunctionReading(){

    }
    public FunctionReading(
        String method,
        String timeComplexity,
        String spaceComplexity,
        Long repetition,
        Long javaDuration,
        Long kotlinDuration,
        String machine){

        this.method = method;
        this.timeComplexity = timeComplexity;
        this.spaceComplexity = spaceComplexity;
        this.repetition = repetition;
        this.javaDuration = javaDuration;
        this.kotlinDuration = kotlinDuration;
        this.machine = machine;
    }

    public String getMethod() {
        return method;
    }

    public void setJavaDuration(Long javaDuration) {
        this.javaDuration = javaDuration;
    }

    public String getTimeComplexity() {
        return timeComplexity;
    }

    public String getSpaceComplexity() {
        return spaceComplexity;
    }

    public Long getJavaDuration() {
        return javaDuration;
    }

    public Long getKotlinDuration() {
        return kotlinDuration;
    }

    public Long getRepetition() {
        return repetition;
    }

    public String getMachine() {
        return machine;
    }
}