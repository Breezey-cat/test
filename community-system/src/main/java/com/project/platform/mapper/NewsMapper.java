package com.project.platform.mapper;

import com.project.platform.entity.News;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface NewsMapper {
    List<News> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM news WHERE id = #{id}")
    News selectById(Integer id);

    @Select("SELECT * FROM news")
    List<News> list();

    int insert(News entity);

    int updateById(News entity);

    boolean removeByIds(List<Integer> ids);

}