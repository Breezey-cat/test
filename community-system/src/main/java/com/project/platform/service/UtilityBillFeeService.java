package com.project.platform.service;

import com.project.platform.entity.PropertyFee;
import com.project.platform.entity.UtilityBillFee;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 物业收费信息
 */
public interface UtilityBillFeeService {

    PageVO<UtilityBillFee> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    UtilityBillFee selectById(Integer id);

    List<UtilityBillFee> list();

    void insert(UtilityBillFee entity);

    void updateById(UtilityBillFee entity);

    void removeByIds(List<Integer> id);

    void pay(Integer id);
}
