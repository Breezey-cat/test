package com.project.platform.mapper;

import com.project.platform.entity.ParkingSpace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ParkingSpaceMapper {
    List<ParkingSpace> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM parking_space WHERE id = #{id}")
    ParkingSpace selectById(Integer id);

    @Select("SELECT * FROM parking_space")
    List<ParkingSpace> list();

    int insert(ParkingSpace entity);

    int updateById(ParkingSpace entity);

    boolean removeByIds(List<Integer> ids);

    @Select("SELECT COUNT(*) FROM parking_space WHERE parking_lot_id = #{id}")
    Integer getCountSpace(Integer id);

    @Select("SELECT id FROM parking_space WHERE house_number LIKE CONCAT('%', #{name}, '%')")
    List<Integer> queryLikelist(String name);

}