package com.project.platform.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class EchartsDataVO {
    private List<String> xData;
    private List<Object> yData;
    private List<HashMap> seriesData;
    private List<String> legendData;


    public EchartsDataVO() {
        this.xData = new ArrayList<>();
        this.yData = new ArrayList<>();
        this.legendData = new ArrayList<>();
        this.seriesData = new ArrayList<>();
    }
}
