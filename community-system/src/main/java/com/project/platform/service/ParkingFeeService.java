package com.project.platform.service;

import com.project.platform.entity.ParkingFee;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 车位收费信息
 */
public interface ParkingFeeService {

    PageVO<ParkingFee> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    ParkingFee selectById(Integer id);

    List<ParkingFee> list();

    void insert(ParkingFee entity);

    void updateById(ParkingFee entity);

    void removeByIds(List<Integer> id);

    void pay(Integer id);
}
