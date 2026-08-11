package com.project.platform.service;

import com.project.platform.entity.Comments;
import com.project.platform.entity.ForumComment;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 论坛评论信息
 */
public interface ForumCommentService {

    PageVO<ForumComment> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    ForumComment selectById(Integer id);

    List<ForumComment> list();

    void insert(ForumComment entity);

    void updateById(ForumComment entity);

    void removeByIds(List<Integer> id);

    List<ForumComment> queryCommentsList(Integer id);
}
