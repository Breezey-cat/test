package com.project.platform.service;

import com.project.platform.entity.ParkingSpace;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 车位信息
 */
public interface ParkingSpaceService {

    PageVO<ParkingSpace> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    ParkingSpace selectById(Integer id);

    List<ParkingSpace> list();

    void insert(ParkingSpace entity);

    void updateById(ParkingSpace entity);

    void removeByIds(List<Integer> id);

    int getCountSpace(Integer parkingLotId);

    List<Integer> queryLikelist(String parkingSpaceName);
}
