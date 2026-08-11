package com.project.platform.mapper;

import com.project.platform.entity.ParkingLot;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ParkingLotMapper {
    List<ParkingLot> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM parking_lot WHERE id = #{id}")
    ParkingLot selectById(Integer id);

    @Select("SELECT * FROM parking_lot")
    List<ParkingLot> list();

    @Select("SELECT id FROM parking_lot where space_number LIKE CONCAT('%', #{parkingLotName}, '%')")
    List<Integer> queryLikelist(String parkingLotName);

    int insert(ParkingLot entity);

    int updateById(ParkingLot entity);

    boolean removeByIds(List<Integer> ids);

    @Select("select parking_num from parking_lot where id = #{parkingLotId}")
    Integer getCapacity(Integer parkingLotId);

}