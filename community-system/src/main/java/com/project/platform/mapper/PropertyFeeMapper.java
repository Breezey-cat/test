package com.project.platform.mapper;

import com.project.platform.entity.PropertyFee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface PropertyFeeMapper {
    List<PropertyFee> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM property_fee WHERE id = #{id}")
    PropertyFee selectById(Integer id);

    @Select("SELECT * FROM property_fee")
    List<PropertyFee> list();

    int insert(PropertyFee entity);

    int updateById(PropertyFee entity);

    boolean removeByIds(List<Integer> ids);

    boolean removeByHouseIds(List<Integer> ids);
}