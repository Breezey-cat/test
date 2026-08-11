package com.project.platform.service.impl;

import com.project.platform.entity.Comments;
import com.project.platform.entity.User;
import com.project.platform.exception.CustomException;
import com.project.platform.mapper.CommentsMapper;
import com.project.platform.service.CommentsService;
import com.project.platform.service.UserService;
import com.project.platform.utils.CurrentUserThreadLocal;
import com.project.platform.vo.PageVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 评论记录
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    @Resource
    private CommentsMapper commentsMapper;
    @Resource
    private UserService userService;

    @Override
    public PageVO<Comments> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<Comments> page = new PageVO();
        if (CurrentUserThreadLocal.getCurrentUser().getType().equals("USER")) {
            query.put("userId", CurrentUserThreadLocal.getCurrentUser().getId());
        }
        List<Comments> list = commentsMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(commentsMapper.queryCount(query));
        return page;
    }

    @Override
    public Comments selectById(Integer id) {
        Comments comments = commentsMapper.selectById(id);
        return comments;
    }

    @Override
    public List<Comments> list() {
        return commentsMapper.list();
    }

    @Override
    public void insert(Comments entity) {
        if (!CurrentUserThreadLocal.getCurrentUser().getType().equals("USER")) {
            throw new CustomException("普通用户才允许添加");
        }
        entity.setUserId(CurrentUserThreadLocal.getCurrentUser().getId());
        check(entity);
        commentsMapper.insert(entity);
    }

    @Override
    public void updateById(Comments entity) {
        check(entity);
        commentsMapper.updateById(entity);
    }

    private void check(Comments entity) {

    }

    @Override
    public void removeByIds(List<Integer> ids) {
        commentsMapper.removeByIds(ids);
    }
    @Override
    public List<Comments> queryCommentsList(Integer diaryId){
        List<Comments> commentsList=  commentsMapper.queryCommentsListByTop(diaryId);
        //简单嵌套循环查询层级数据
        for (Comments comments : commentsList) {
            User user = userService.selectById(comments.getUserId());
            comments.setUser(user);
            List<Comments> commentsChildList = commentsMapper.queryCommentsListByChild(diaryId,comments.getId());
            if(commentsChildList.size()>0){
                for (Comments commentsChild : commentsChildList) {
                    User userChild = userService.selectById(commentsChild.getUserId());
                    commentsChild.setUser(userChild);
                }
                comments.setChildList(commentsChildList);
            }
        }
        return commentsList;
    }

    @Override
    public List<Comments> queryCommentsListByHelp(Integer helpId){
        List<Comments> commentsList=  commentsMapper.queryHelpCommentsListByTop(helpId);
        //简单嵌套循环查询层级数据
        for (Comments comments : commentsList) {
            User user = userService.selectById(comments.getUserId());
            comments.setUser(user);
            List<Comments> commentsChildList = commentsMapper.queryHelpCommentsListByChild(helpId,comments.getId());
            if(commentsChildList.size()>0){
                for (Comments commentsChild : commentsChildList) {
                    User userChild = userService.selectById(commentsChild.getUserId());
                    commentsChild.setUser(userChild);
                }
                comments.setChildList(commentsChildList);
            }
        }
        return commentsList;
    }
}
