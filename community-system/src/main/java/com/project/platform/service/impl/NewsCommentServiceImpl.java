package com.project.platform.service.impl;

import com.project.platform.entity.*;
import com.project.platform.exception.CustomException;
import com.project.platform.mapper.NewsCommentMapper;
import com.project.platform.service.NewsCommentService;
import com.project.platform.service.NewsService;
import com.project.platform.service.UserService;
import com.project.platform.utils.CurrentUserThreadLocal;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 新闻评论信息
 */
@Service
public class NewsCommentServiceImpl  implements NewsCommentService {
    @Resource
    private NewsCommentMapper newsCommentMapper;

    @Resource
    private UserService userService;
    @Resource
    private NewsService newsService;
    
    @Override
    public PageVO<NewsComment> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<NewsComment> page = new PageVO();
        List<NewsComment> list = newsCommentMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        list.forEach(newsComment -> {
            User user = userService.selectById(newsComment.getUserId());
            newsComment.setUser(user);
            newsComment.setUserName(user.getUsername());
            News news = newsService.selectById(newsComment.getNewsId());
            newsComment.setNewsTitle(news.getTitle());
        });
        page.setList(list);
        page.setTotal(newsCommentMapper.queryCount(query));
        return page;
    }

    @Override
    public NewsComment selectById(Integer id) {
        NewsComment newsComment = newsCommentMapper.selectById(id);
        return newsComment;
    }

    @Override
    public List<NewsComment> list() {
        return newsCommentMapper.list();
    }
    @Override
    public void insert(NewsComment entity) {
        if (!CurrentUserThreadLocal.getCurrentUser().getType().equals("USER")) {
            throw new CustomException("普通用户才允许添加");
        }
        entity.setUserId(CurrentUserThreadLocal.getCurrentUser().getId());
        check(entity);
        newsCommentMapper.insert(entity);
    }
    @Override
    public void updateById(NewsComment entity) {
        check(entity);
        newsCommentMapper.updateById(entity);
    }
    private void check(NewsComment entity) {

    }
    @Override
    public void removeByIds(List<Integer> ids) {
        newsCommentMapper.removeByIds(ids);
    }

    @Override
    public List<NewsComment> queryCommentsList(Integer id){
        List<NewsComment> commentsList=  newsCommentMapper.queryCommentsListByTop(id);
        //简单嵌套循环查询层级数据
        for (NewsComment comments : commentsList) {
            User user = userService.selectById(comments.getUserId());
            comments.setUser(user);
            List<NewsComment> commentsChildList = newsCommentMapper.queryCommentsListByChild(id,comments.getId());
            if(commentsChildList.size()>0){
                for (NewsComment commentsChild : commentsChildList) {
                    User userChild = userService.selectById(commentsChild.getUserId());
                    commentsChild.setUser(userChild);
                }
                comments.setChildList(commentsChildList);
            }
        }
        return commentsList;
    }
}
