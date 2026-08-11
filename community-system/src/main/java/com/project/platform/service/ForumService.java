package com.project.platform.service;

import com.project.platform.entity.Forum;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 论坛信息
 */
public interface ForumService {

    PageVO<Forum> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    Forum selectById(Integer id);

    List<Forum> list();

    void insert(Forum entity);

    void updateById(Forum entity);

    void removeByIds(List<Integer> id);
}
