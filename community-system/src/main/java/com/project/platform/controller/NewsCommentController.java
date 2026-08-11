package com.project.platform.controller;

import com.project.platform.entity.ForumComment;
import com.project.platform.entity.NewsComment;
import com.project.platform.service.NewsCommentService;
import com.project.platform.utils.CurrentUserThreadLocal;
import com.project.platform.vo.PageVO;
import com.project.platform.vo.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 新闻评论信息
 */
@RestController
@RequestMapping("/newsComment")
public class NewsCommentController {
    @Resource
    private NewsCommentService newsCommentService;

    /**
     * 分页查询
     *
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("page")
    public ResponseVO<PageVO<NewsComment>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageVO<NewsComment> page = newsCommentService.page(query, pageNum, pageSize);
        return ResponseVO.ok(page);

    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("selectById/{id}")
    public ResponseVO<NewsComment> selectById(@PathVariable("id") Integer id) {
        NewsComment entity = newsCommentService.selectById(id);
        return ResponseVO.ok(entity);
    }


    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public ResponseVO<List<NewsComment>> list() {
        return ResponseVO.ok(newsCommentService.list());
    }


    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("add")
    public ResponseVO add(@RequestBody NewsComment entity) {
        newsCommentService.insert(entity);
        return ResponseVO.ok();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @PutMapping("update")
    public ResponseVO update(@RequestBody NewsComment entity) {
        newsCommentService.updateById(entity);
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
        newsCommentService.removeByIds(ids);
        return ResponseVO.ok();
    }

    /**
     * 根据newsId查询
     *
     * @param newsId
     * @return
     */
    @GetMapping("selectByNewsId/{newsId}")
    public ResponseVO<List<NewsComment>> selectByNewsId(@PathVariable("newsId") Integer newsId) {
        List<NewsComment> list = newsCommentService.queryCommentsList(newsId);
        return ResponseVO.ok(list);
    }
}
