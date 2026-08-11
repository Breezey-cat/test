package com.project.platform.service.impl;

import com.project.platform.entity.Announcement;
import com.project.platform.entity.News;
import com.project.platform.mapper.NewsMapper;
import com.project.platform.service.NewsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 新闻信息
 */
@Service
public class NewsServiceImpl  implements NewsService {
    @Resource
    private NewsMapper newsMapper;

    @Override
    public PageVO<News> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<News> page = new PageVO();
        List<News> list = newsMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(newsMapper.queryCount(query));
        return page;
    }

    @Override
    public News selectById(Integer id) {
        News news = newsMapper.selectById(id);
        return news;
    }

    @Override
    public List<News> list() {
        return newsMapper.list();
    }
    @Override
    public void insert(News entity) {
        check(entity);
        newsMapper.insert(entity);
    }
    @Override
    public void updateById(News entity) {
        check(entity);
        newsMapper.updateById(entity);
    }
    private void check(News entity) {

    }
    @Override
    public void removeByIds(List<Integer> ids) {
        newsMapper.removeByIds(ids);
    }


}
