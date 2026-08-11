package com.project.platform.mapper;

import com.project.platform.entity.Building;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface BuildingMapper {
    List<Building> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM building WHERE id = #{id}")
    Building selectById(Integer id);

    @Select("SELECT * FROM building")
    List<Building> list();

    @Select("SELECT * FROM building where user_id = #{id}")
    List<Building> mylist(int id);

    @Select("SELECT id FROM building WHERE code LIKE CONCAT('%', #{name}, '%')")
    List<Integer> queryLikelist(String name);

    int insert(Building entity);

    int updateById(Building entity);

    boolean removeByIds(List<Integer> ids);
    @Select("select house_number from building where id = #{buildingId}")
    Integer getCapacity(Integer buildingId);
}