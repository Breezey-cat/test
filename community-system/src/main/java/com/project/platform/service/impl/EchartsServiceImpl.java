package com.project.platform.service.impl;

import com.project.platform.entity.Building;
import com.project.platform.mapper.EchartsMapper;
import com.project.platform.mapper.HouseMapper;
import com.project.platform.service.BuildingService;
import com.project.platform.service.EchartsService;
import com.project.platform.service.HouseService;
import com.project.platform.vo.EchartsDataVO;
import com.project.platform.vo.ValueNameVO;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EchartsServiceImpl implements EchartsService {
    @Resource
    private EchartsMapper echartsMapper;
    @Resource
    private BuildingService buildingService;
    @Resource
    private HouseMapper houseMapper;

    @Override
    public EchartsDataVO residentOccupancyRate() {
        List<Building> buildingList = buildingService.list();
        EchartsDataVO echartsDataVO = new EchartsDataVO();
        List<HashMap> seriesData =new ArrayList<>();
        List<String> strings = houseMapper.selectByGroup();
        List<String> xData = new ArrayList<>();
        strings.forEach(string -> {
            HashMap map = new HashMap();
            map.put("name", string);
            List<String> list = new ArrayList<>();
            buildingList.forEach(building -> {
                String count =  houseMapper.countByStatus(string, building.getId());
                list.add(count);
           });
            map.put("data", list);
            seriesData.add(map);
        });
        buildingList.forEach(building -> {
            xData.add(building.getCode());
        });
        echartsDataVO.setLegendData(strings);
        echartsDataVO.setXData(xData);
        echartsDataVO.setSeriesData(seriesData);
        return echartsDataVO;
    }

    @Override
    public List<HashMap> parkingCondition() {
        return echartsMapper.parkingCondition();
    }

    @Override
    public List<HashMap> CollectionOfPropertyFee() {
        return echartsMapper.collectionOfPropertyFee();
    }

    @Override
    public List<HashMap> collectionOfWaterCharge() {
        return echartsMapper.collectionOfWaterCharge();
    }

    @Override
    public List<HashMap> collectionOfElectricityCharge() {
        return echartsMapper.collectionOfElectricityCharge();
    }

    @Override
    public List<HashMap> maintenanceProcessingEfficiency() {
        return echartsMapper.maintenanceProcessingEfficiency();
    }
}
