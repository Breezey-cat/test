package com.project.platform.controller;

import com.project.platform.entity.News;
import com.project.platform.entity.NewsComment;
import com.project.platform.service.NewsCommentService;
import com.project.platform.service.NewsService;
import com.project.platform.utils.CurrentUserThreadLocal;
import com.project.platform.vo.PageVO;
import com.project.platform.vo.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 新闻信息
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Resource
    private NewsService newsService;
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
    public ResponseVO<PageVO<News>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageVO<News> page = newsService.page(query, pageNum, pageSize);
        List<News> list = page.getList();
        if (CurrentUserThreadLocal.getCurrentUser().getType().equals("USER")){
            list.forEach(newsComment -> {
                //评论内容
                newsComment.setCommentsList(newsCommentService.queryCommentsList(newsComment.getId()));
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
    public ResponseVO<News> selectById(@PathVariable("id") Integer id) {
        News entity = newsService.selectById(id);
        return ResponseVO.ok(entity);
    }


    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public ResponseVO<List<News>> list() {
        return ResponseVO.ok(newsService.list());
    }


    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("add")
    public ResponseVO add(@RequestBody News entity) {
        newsService.insert(entity);
        return ResponseVO.ok();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @PutMapping("update")
    public ResponseVO update(@RequestBody News entity) {
        newsService.updateById(entity);
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
        newsService.removeByIds(ids);
        return ResponseVO.ok();
    }

}
