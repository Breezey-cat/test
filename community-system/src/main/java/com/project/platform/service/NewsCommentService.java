package com.project.platform.service;

import com.project.platform.entity.Comments;
import com.project.platform.entity.NewsComment;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 新闻评论信息
 */
public interface NewsCommentService {

    PageVO<NewsComment> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    NewsComment selectById(Integer id);

    List<NewsComment> list();

    void insert(NewsComment entity);

    void updateById(NewsComment entity);

    void removeByIds(List<Integer> id);

    List<NewsComment> queryCommentsList(Integer id);
}
