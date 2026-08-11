package com.project.platform.mapper;

import com.project.platform.entity.Comments;
import com.project.platform.entity.NewsComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface NewsCommentMapper {
    List<NewsComment> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM news_comment WHERE id = #{id}")
    NewsComment selectById(Integer id);

    @Select("SELECT * FROM news_comment")
    List<NewsComment> list();

    int insert(NewsComment entity);

    int updateById(NewsComment entity);

    boolean removeByIds(List<Integer> ids);

    @Select("SELECT * FROM news_comment WHERE news_id = #{newsId} and parent_id is null order by create_time desc")
    List<NewsComment> queryCommentsListByTop(Integer newsId);

    @Select("SELECT * FROM news_comment WHERE news_id = #{newsId} and parent_id = #{parentId} order by create_time desc")
    List<NewsComment> queryCommentsListByChild(Integer newsId,Integer parentId);


}