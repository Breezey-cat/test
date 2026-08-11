package com.project.platform.controller;

import com.project.platform.entity.Comments;
import com.project.platform.service.CommentsService;
import com.project.platform.vo.PageVO;
import com.project.platform.vo.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 评论记录
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Resource
    private CommentsService commentsService;

    /**
     * 分页查询
     *
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("page")
    public ResponseVO<PageVO<Comments>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageVO<Comments> page = commentsService.page(query, pageNum, pageSize);
        return ResponseVO.ok(page);

    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("selectById/{id}")
    public ResponseVO<Comments> selectById(@PathVariable("id") Integer id) {
        Comments entity = commentsService.selectById(id);
        return ResponseVO.ok(entity);
    }



    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public ResponseVO<List<Comments>> list() {
        return ResponseVO.ok(commentsService.list());
    }


    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("add")
    public ResponseVO add(@RequestBody Comments entity) {
        commentsService.insert(entity);
        return ResponseVO.ok();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @PutMapping("update")
    public ResponseVO update(@RequestBody Comments entity) {
        commentsService.updateById(entity);
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
        commentsService.removeByIds(ids);
        return ResponseVO.ok();
    }

    /**
     * 根据diaryId查询
     *
     * @param diaryId
     * @return
     */
    @GetMapping("selectByDiaryId/{diaryId}")
    public ResponseVO<List<Comments>> selectByDiaryId(@PathVariable("diaryId") Integer diaryId) {
        List<Comments> list = commentsService.queryCommentsList(diaryId);
        return ResponseVO.ok(list);
    }

    /**
     * 根据helpId查询
     *
     * @param helpId
     * @return
     */
    @GetMapping("selectByHelpId/{helpId}")
    public ResponseVO<List<Comments>> selectByHelpId(@PathVariable("helpId") Integer helpId) {
        List<Comments> list = commentsService.queryCommentsListByHelp(helpId);
        return ResponseVO.ok(list);
    }
}
