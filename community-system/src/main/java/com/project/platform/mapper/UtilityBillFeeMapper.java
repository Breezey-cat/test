package com.project.platform.mapper;

import com.project.platform.entity.UtilityBillFee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface UtilityBillFeeMapper {
    List<UtilityBillFee> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM utility_bill_fee WHERE id = #{id}")
    UtilityBillFee selectById(Integer id);

    @Select("SELECT * FROM utility_bill_fee")
    List<UtilityBillFee> list();

    int insert(UtilityBillFee entity);

    int updateById(UtilityBillFee entity);

    boolean removeByIds(List<Integer> ids);

    boolean removeByHouseIds(List<Integer> ids);
}