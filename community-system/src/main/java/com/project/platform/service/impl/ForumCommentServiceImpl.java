package com.project.platform.service.impl;

import com.project.platform.entity.Forum;
import com.project.platform.entity.ForumComment;
import com.project.platform.entity.NewsComment;
import com.project.platform.entity.User;
import com.project.platform.exception.CustomException;
import com.project.platform.mapper.ForumCommentMapper;
import com.project.platform.service.ForumCommentService;
import com.project.platform.service.ForumService;
import com.project.platform.service.UserService;
import com.project.platform.utils.CurrentUserThreadLocal;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 论坛评论信息
 */
@Service
public class ForumCommentServiceImpl  implements ForumCommentService {
    @Resource
    private ForumCommentMapper forumCommentMapper;
    @Resource
    private UserService userService;
    @Resource
    private ForumService forumService;

    @Override
    public PageVO<ForumComment> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<ForumComment> page = new PageVO();
        List<ForumComment> list = forumCommentMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        list.forEach(forumComment -> {
            User user = userService.selectById(forumComment.getUserId());
            forumComment.setUser(user);
            forumComment.setUserName(user.getUsername());
            Forum forum = forumService.selectById(forumComment.getForumId());

            if(forum!=null){
                forumComment.setForumName(forum.getTitle());
            }

        });
        page.setList(list);
        page.setTotal(forumCommentMapper.queryCount(query));
        return page;
    }

    @Override
    public ForumComment selectById(Integer id) {
        ForumComment forumComment = forumCommentMapper.selectById(id);
        return forumComment;
    }

    @Override
    public List<ForumComment> list() {
        return forumCommentMapper.list();
    }
    @Override
    public void insert(ForumComment entity) {
        if (!CurrentUserThreadLocal.getCurrentUser().getType().equals("USER")) {
            throw new CustomException("普通用户才允许添加");
        }
        entity.setUserId(CurrentUserThreadLocal.getCurrentUser().getId());
        check(entity);
        forumCommentMapper.insert(entity);
    }
    @Override
    public void updateById(ForumComment entity) {
        check(entity);
        forumCommentMapper.updateById(entity);
    }
    private void check(ForumComment entity) {

    }
    @Override
    public void removeByIds(List<Integer> ids) {
        forumCommentMapper.removeByIds(ids);
    }

    @Override
    public List<ForumComment> queryCommentsList(Integer id){
        List<ForumComment> commentsList=  forumCommentMapper.queryCommentsListByTop(id);
        //简单嵌套循环查询层级数据
        for (ForumComment comments : commentsList) {
            User user = userService.selectById(comments.getUserId());
            comments.setUser(user);
            List<ForumComment> commentsChildList = forumCommentMapper.queryCommentsListByChild(id,comments.getId());
            if(commentsChildList.size()>0){
                for (ForumComment commentsChild : commentsChildList) {
                    User userChild = userService.selectById(commentsChild.getUserId());
                    commentsChild.setUser(userChild);
                }
                comments.setChildList(commentsChildList);
            }
        }
        return commentsList;
    }
}
