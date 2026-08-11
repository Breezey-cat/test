package com.project.platform.mapper;

import com.project.platform.entity.Repair;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface RepairMapper {
    List<Repair> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM repair WHERE id = #{id}")
    Repair selectById(Integer id);

    @Select("SELECT * FROM repair")
    List<Repair> list();

    int insert(Repair entity);

    int updateById(Repair entity);

    boolean removeByIds(List<Integer> ids);

    boolean removeByHouseIds(List<Integer> ids);
}