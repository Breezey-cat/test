package com.project.platform.controller;

import com.project.platform.entity.Comments;
import com.project.platform.entity.ForumComment;
import com.project.platform.service.ForumCommentService;
import com.project.platform.vo.PageVO;
import com.project.platform.vo.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 论坛评论信息
 */
@RestController
@RequestMapping("/forumComment")
public class ForumCommentController {
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
    public ResponseVO<PageVO<ForumComment>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageVO<ForumComment> page = forumCommentService.page(query, pageNum, pageSize);
        return ResponseVO.ok(page);

    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("selectById/{id}")
    public ResponseVO<ForumComment> selectById(@PathVariable("id") Integer id) {
        ForumComment entity = forumCommentService.selectById(id);
        return ResponseVO.ok(entity);
    }


    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public ResponseVO<List<ForumComment>> list() {
        return ResponseVO.ok(forumCommentService.list());
    }


    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("add")
    public ResponseVO add(@RequestBody ForumComment entity) {
        forumCommentService.insert(entity);
        return ResponseVO.ok();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @PutMapping("update")
    public ResponseVO update(@RequestBody ForumComment entity) {
        forumCommentService.updateById(entity);
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
        forumCommentService.removeByIds(ids);
        return ResponseVO.ok();
    }

    /**
     * 根据forumId查询
     *
     * @param forumId
     * @return
     */
    @GetMapping("selectByForumId/{forumId}")
    public ResponseVO<List<ForumComment>> selectByForumId(@PathVariable("forumId") Integer forumId) {
        List<ForumComment> list = forumCommentService.queryCommentsList(forumId);
        return ResponseVO.ok(list);
    }
}
