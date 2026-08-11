package com.project.platform.service;

import com.project.platform.vo.EchartsDataVO;
import com.project.platform.vo.ValueNameVO;

import java.util.HashMap;
import java.util.List;

public interface EchartsService {

    EchartsDataVO residentOccupancyRate();
    List<HashMap> parkingCondition();
    List<HashMap> CollectionOfPropertyFee();
    List<HashMap> collectionOfWaterCharge();
    List<HashMap> collectionOfElectricityCharge();
    List<HashMap> maintenanceProcessingEfficiency();
}
