package com.project.platform.service.impl;

import com.project.platform.entity.Announcement;
import com.project.platform.mapper.AnnouncementMapper;
import com.project.platform.service.AnnouncementService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 公告
 */
@Service
public class AnnouncementServiceImpl  implements AnnouncementService {
    @Resource
    private AnnouncementMapper announcementMapper;

    @Override
    public PageVO<Announcement> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<Announcement> page = new PageVO();
        List<Announcement> list = announcementMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(announcementMapper.queryCount(query));
        return page;
    }

    @Override
    public Announcement selectById(Integer id) {
        Announcement announcement = announcementMapper.selectById(id);
        return announcement;
    }

    @Override
    public List<Announcement> list() {
        return announcementMapper.list();
    }
    @Override
    public void insert(Announcement entity) {
        check(entity);
        announcementMapper.insert(entity);
    }
    @Override
    public void updateById(Announcement entity) {
        check(entity);
        announcementMapper.updateById(entity);
    }
    private void check(Announcement entity) {

    }
    @Override
    public void removeByIds(List<Integer> ids) {
        announcementMapper.removeByIds(ids);
    }

    @Override
    public void publish(Integer id) {
        Announcement entity = announcementMapper.selectById(id);
        announcementMapper.updateById(entity);
    }

    @Override
    public void delist(Integer id) {
        Announcement entity = announcementMapper.selectById(id);
        announcementMapper.updateById(entity);
    }
}
