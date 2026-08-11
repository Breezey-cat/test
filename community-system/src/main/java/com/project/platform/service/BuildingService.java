package com.project.platform.service;

import com.project.platform.entity.Building;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 楼宇信息
 */
public interface BuildingService {

    PageVO<Building> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    Building selectById(Integer id);

    List<Building> list();


    List<Integer> queryLikelist(String name);

    void insert(Building entity);

    void updateById(Building entity);

    void removeByIds(List<Integer> id);

    Integer getCapacity(Integer id);
}
