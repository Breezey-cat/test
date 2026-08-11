package com.project.platform.mapper;

import com.project.platform.vo.ValueNameVO;

import java.util.HashMap;
import java.util.List;


public interface EchartsMapper {
    List<HashMap> residentOccupancyRate();
    List<HashMap> parkingCondition();
    List<HashMap> collectionOfPropertyFee();
    List<HashMap> collectionOfWaterCharge();
    List<HashMap> collectionOfElectricityCharge();
    List<HashMap> maintenanceProcessingEfficiency();
}