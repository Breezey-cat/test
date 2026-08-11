package com.project.platform.service;

import com.project.platform.entity.Building;
import com.project.platform.entity.House;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 房屋信息
 */
public interface HouseService {

    PageVO<House> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    House selectById(Integer id);

    List<House> list();

    List<House> mylist();

    List<Building>  treelist();


    List<House> getListByBuildingId(Integer buildingId);

    void insert(House entity);

    void updateById(House entity);

    void removeByIds(List<Integer> id);

    int getCountSpace(Integer id);

    List<Integer> queryLikelist(String houseName);
}
