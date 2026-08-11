package com.project.platform.controller;

import com.project.platform.entity.Forum;
import com.project.platform.entity.News;
import com.project.platform.service.ForumCommentService;
import com.project.platform.service.ForumService;
import com.project.platform.utils.CurrentUserThreadLocal;
import com.project.platform.vo.PageVO;
import com.project.platform.vo.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 论坛信息
 */
@RestController
@RequestMapping("/forum")
public class ForumController {
    @Resource
    private ForumService forumService;
    @Resource
    private ForumCommentService forumCommentService;

    /**
     * 分页查询
     *
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("page")
    public ResponseVO<PageVO<Forum>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (query.containsKey("onlyYou") && Boolean.valueOf(query.get("onlyYou").toString())) {
            Integer userId = -1;
            if (CurrentUserThreadLocal.getCurrentUser().getType().equals("USER")) {
                //普通用户看自己才有效果
                userId = CurrentUserThreadLocal.getCurrentUser().getId();
            }
            query.put("userId", userId);
        }
        PageVO<Forum> page = forumService.page(query, pageNum, pageSize);
        List<Forum> list = page.getList();
        if (CurrentUserThreadLocal.getCurrentUser().getType().equals("USER")){
            list.forEach(newsComment -> {
                //评论内容
                newsComment.setCommentsList(forumCommentService.queryCommentsList(newsComment.getId()));
            });
        }
        return ResponseVO.ok(page);

    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("selectById/{id}")
    public ResponseVO<Forum> selectById(@PathVariable("id") Integer id) {
        Forum entity = forumService.selectById(id);
        return ResponseVO.ok(entity);
    }


    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public ResponseVO<List<Forum>> list() {
        return ResponseVO.ok(forumService.list());
    }


    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("add")
    public ResponseVO add(@RequestBody Forum entity) {
        forumService.insert(entity);
        return ResponseVO.ok();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @PutMapping("update")
    public ResponseVO update(@RequestBody Forum entity) {
        forumService.updateById(entity);
        return ResponseVO.ok();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("delBatch")
    public ResponseVO delBatch(@RequestBody List<Integer> ids) {
        forumService.removeByIds(ids);
        return ResponseVO.ok();
    }
}
