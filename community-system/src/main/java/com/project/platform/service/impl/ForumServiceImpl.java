package com.project.platform.service.impl;

import com.project.platform.entity.Forum;
import com.project.platform.entity.User;
import com.project.platform.mapper.ForumMapper;
import com.project.platform.service.ForumService;
import com.project.platform.utils.CurrentUserThreadLocal;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 论坛信息
 */
@Service
public class ForumServiceImpl  implements ForumService {
    @Resource
    private ForumMapper forumMapper;
    
    @Override
    public PageVO<Forum> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<Forum> page = new PageVO();
        if (query.containsKey("onlyYou") && Boolean.valueOf(query.get("onlyYou").toString())) {
            Integer userId = -1;
            if (CurrentUserThreadLocal.getCurrentUser().getType().equals("USER")) {
                //普通用户看自己才有效果
                userId = CurrentUserThreadLocal.getCurrentUser().getId();
            }
            query.put("userId", userId);
        }
        List<Forum> list = forumMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(forumMapper.queryCount(query));
        return page;
    }

    @Override
    public Forum selectById(Integer id) {
        Forum forum = forumMapper.selectById(id);
        return forum;
    }

    @Override
    public List<Forum> list() {
        return forumMapper.list();
    }
    @Override
    public void insert(Forum entity) {
        Integer userId = CurrentUserThreadLocal.getCurrentUser().getId();
        entity.setUserId(userId);
        entity.setStatus("正常");
        check(entity);
        forumMapper.insert(entity);
    }
    @Override
    public void updateById(Forum entity) {
        check(entity);
        forumMapper.updateById(entity);
    }
    private void check(Forum entity) {

    }
    @Override
    public void removeByIds(List<Integer> ids) {
        forumMapper.removeByIds(ids);
    }
}
