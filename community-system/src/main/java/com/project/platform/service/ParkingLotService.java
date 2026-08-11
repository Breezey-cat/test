package com.project.platform.service;

import com.project.platform.entity.ParkingLot;
import com.project.platform.vo.PageVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 停车场信息
 */
public interface ParkingLotService {

    PageVO<ParkingLot> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    ParkingLot selectById(Integer id);

    List<ParkingLot> list();

    List<Integer> queryLikelist(String parkingLotName);

    void insert(ParkingLot entity);

    void updateById(ParkingLot entity);

    void removeByIds(List<Integer> id);

    Integer getCapacity(Integer parkingLotId);
}
