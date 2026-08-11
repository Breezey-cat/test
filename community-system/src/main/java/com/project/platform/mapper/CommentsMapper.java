package com.project.platform.mapper;

import com.project.platform.entity.Comments;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface CommentsMapper {
    List<Comments> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM comments WHERE id = #{id}")
    Comments selectById(Integer id);

    @Select("SELECT * FROM comments")
    List<Comments> list();

    int insert(Comments entity);

    int updateById(Comments entity);

    boolean removeByIds(List<Integer> ids);

    @Select("SELECT * FROM comments WHERE diary_id = #{diaryId} and parent_id is null order by create_time desc")
    List<Comments> queryCommentsListByTop(Integer diaryId);

    @Select("SELECT * FROM comments WHERE diary_id = #{diaryId} and parent_id = #{parentId} order by create_time desc")
    List<Comments> queryCommentsListByChild(Integer diaryId,Integer parentId);

    @Select("SELECT * FROM comments WHERE help_id = #{help} and parent_id is null order by create_time desc")
    List<Comments> queryHelpCommentsListByTop(Integer help);

    @Select("SELECT * FROM comments WHERE help_id = #{help} and parent_id = #{parentId} order by create_time desc")
    List<Comments> queryHelpCommentsListByChild(Integer help,Integer parentId);
}