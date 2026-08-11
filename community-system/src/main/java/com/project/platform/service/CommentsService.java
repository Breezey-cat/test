package com.project.platform.service;

import com.project.platform.entity.Comments;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 评论记录
 */
public interface CommentsService {

    PageVO<Comments> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    Comments selectById(Integer id);

    List<Comments> list();

    void insert(Comments entity);

    void updateById(Comments entity);

    void removeByIds(List<Integer> id);

    List<Comments> queryCommentsList(Integer diaryId);

    List<Comments> queryCommentsListByHelp(Integer helpId);
}
