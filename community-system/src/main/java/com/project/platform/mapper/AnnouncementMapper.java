package com.project.platform.mapper;

import com.project.platform.entity.Announcement;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface AnnouncementMapper {
    List<Announcement> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM announcement WHERE id = #{id}")
    Announcement selectById(Integer id);

    @Select("SELECT * FROM announcement")
    List<Announcement> list();

    int insert(Announcement entity);

    int updateById(Announcement entity);

    boolean removeByIds(List<Integer> ids);

}