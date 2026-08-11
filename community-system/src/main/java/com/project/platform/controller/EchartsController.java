package com.project.platform.controller;

import com.project.platform.service.EchartsService;
import com.project.platform.vo.EchartsDataVO;
import com.project.platform.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private EchartsService echartsService;


    //    小区居民入住率Ò
    @GetMapping("residentOccupancyRate")
    public ResponseVO<List<EchartsDataVO>> residentOccupancyRate() {

        return ResponseVO.ok(echartsService.residentOccupancyRate());
    }

    //    小区停车状况 parkingCondition
    @GetMapping("parkingCondition")
    public ResponseVO<List<HashMap>> parkingCondition() {
        return ResponseVO.ok(echartsService.parkingCondition());
    }

    //    物业费收缴情况 CollectionOfPropertyFee
    @GetMapping("collectionOfPropertyFee")
    public ResponseVO<List<HashMap>> CollectionOfPropertyFee() {
        return ResponseVO.ok(echartsService.CollectionOfPropertyFee());
    }

    //    水费收费情况 CollectionOfWaterCharge
    @GetMapping("collectionOfWaterCharge")
    public ResponseVO<List<HashMap>> collectionOfWaterCharge() {
        return ResponseVO.ok(echartsService.collectionOfWaterCharge());
    }

    //    电费收费情况 CollectionOfElectricityCharge
    @GetMapping("collectionOfElectricityCharge")
    public ResponseVO<List<HashMap>> collectionOfElectricityCharge() {
        return ResponseVO.ok(echartsService.collectionOfElectricityCharge());
    }

    //    报修处理效率情况 maintenanceProcessingEfficiency
    @GetMapping("maintenanceProcessingEfficiency")
    public ResponseVO<List<HashMap>> maintenanceProcessingEfficiency() {
        return ResponseVO.ok(echartsService.maintenanceProcessingEfficiency());
    }
}
