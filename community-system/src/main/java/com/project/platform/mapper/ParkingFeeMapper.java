package com.project.platform.mapper;

import com.project.platform.entity.ParkingFee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ParkingFeeMapper {
    List<ParkingFee> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM parking_fee WHERE id = #{id}")
    ParkingFee selectById(Integer id);

    @Select("SELECT * FROM parking_fee")
    List<ParkingFee> list();

    int insert(ParkingFee entity);

    int updateById(ParkingFee entity);

    boolean removeByIds(List<Integer> ids);

}