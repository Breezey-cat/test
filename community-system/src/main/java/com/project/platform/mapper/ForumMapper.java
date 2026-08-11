package com.project.platform.mapper;

import com.project.platform.entity.Forum;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ForumMapper {
    List<Forum> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM forum WHERE id = #{id}")
    Forum selectById(Integer id);

    @Select("SELECT * FROM forum")
    List<Forum> list();

    int insert(Forum entity);

    int updateById(Forum entity);

    boolean removeByIds(List<Integer> ids);

}