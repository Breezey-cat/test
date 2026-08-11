package com.project.platform.service;

import com.project.platform.entity.Repair;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 报修信息
 */
public interface RepairService {

    PageVO<Repair> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    Repair selectById(Integer id);

    List<Repair> list();

    void insert(Repair entity);

    void updateById(Repair entity);

    void removeByIds(List<Integer> id);

    void handle(Integer id);
}
