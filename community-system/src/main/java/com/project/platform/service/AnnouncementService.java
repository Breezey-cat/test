package com.project.platform.service;

import com.project.platform.entity.Announcement;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 公告
 */
public interface AnnouncementService {

    PageVO<Announcement> page(Map<String, Object> query, Integer pageNum, Integer pageSize);

    Announcement selectById(Integer id);

    List<Announcement> list();

    void insert(Announcement entity);

    void updateById(Announcement entity);

    void removeByIds(List<Integer> id);

    void publish(Integer id);

    void delist(Integer id);
}
