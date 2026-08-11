package com.project.platform.service;

import com.project.platform.entity.Complaint;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 投诉信息
 */
public interface ComplaintService {

    PageVO<Complaint> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    Complaint selectById(Integer id);

    List<Complaint> list();

    void insert(Complaint entity);

    void updateById(Complaint entity);

    void removeByIds(List<Integer> id);

    void handle(Integer id);
}
