package com.project.platform.service;

import com.project.platform.entity.News;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 新闻信息
 */
public interface NewsService {

    PageVO<News> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    News selectById(Integer id);

    List<News> list();

    void insert(News entity);

    void updateById(News entity);

    void removeByIds(List<Integer> id);


}
