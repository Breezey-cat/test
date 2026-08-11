package com.project.platform.mapper;

import com.project.platform.entity.House;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface HouseMapper {
    List<House> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM house WHERE id = #{id}")
    House selectById(Integer id);

    @Select("SELECT * FROM house")
    List<House> list();

    @Select("SELECT * FROM house where user_id = #{id}")
    List<House> mylist(Integer id);

    int insert(House entity);

    int updateById(House entity);

    boolean removeByIds(List<Integer> ids);

    @Select("SELECT COUNT(*) FROM house WHERE building_id = #{id}")
    Integer getCountSpace(Integer id);

    @Select("SELECT * FROM house WHERE building_id = #{id}")
    List<House>  queryByBuildingId(Integer id);

    @Select("SELECT id FROM house WHERE house_number LIKE CONCAT('%', #{name}, '%')")
    List<Integer> queryLikelist(String name);

    @Select("SELECT status FROM house WHERE 1=1 group by status")
    List<String> selectByGroup();

    @Select("SELECT count(*) FROM house WHERE status = #{name} and building_id = #{id}")
    String countByStatus(String name, Integer id);

    boolean removeByBuildingId(List<Integer> ids);
}