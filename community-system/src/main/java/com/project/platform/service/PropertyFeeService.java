package com.project.platform.service;

import com.project.platform.entity.PropertyFee;
import com.project.platform.entity.UtilityBillFee;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 物业收费信息
 */
public interface PropertyFeeService {

    PageVO<PropertyFee> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    PropertyFee selectById(Integer id);

    List<PropertyFee> list();

    void insert(PropertyFee entity);

    void updateById(PropertyFee entity);

    void removeByIds(List<Integer> id);

    void pay(Integer id);
}
