package com.project.platform.mapper;

import com.project.platform.entity.ForumComment;
import com.project.platform.entity.NewsComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ForumCommentMapper {
    List<ForumComment> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM forum_comment WHERE id = #{id}")
    ForumComment selectById(Integer id);

    @Select("SELECT * FROM forum_comment")
    List<ForumComment> list();

    int insert(ForumComment entity);

    int updateById(ForumComment entity);

    boolean removeByIds(List<Integer> ids);

    @Select("SELECT * FROM forum_comment WHERE forum_id = #{forumId} and parent_id is null order by create_time desc")
    List<ForumComment> queryCommentsListByTop(Integer forumId);

    @Select("SELECT * FROM forum_comment WHERE forum_id = #{forumId} and parent_id = #{parentId} order by create_time desc")
    List<ForumComment> queryCommentsListByChild(Integer forumId,Integer parentId);

}