package com.project.platform.controller;

import com.project.platform.entity.Announcement;
import com.project.platform.service.AnnouncementService;
import com.project.platform.vo.PageVO;
import com.project.platform.vo.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 公告
 */
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    @Resource
    private AnnouncementService announcementService;
    @GetMapping("page")
    public ResponseVO<PageVO<Announcement>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageVO<Announcement> page = announcementService.page(query, pageNum, pageSize);
        return ResponseVO.ok(page);
    }
    @GetMapping("selectById/{id}")
    public ResponseVO<Announcement> selectById(@PathVariable("id") Integer id) {
        Announcement entity = announcementService.selectById(id);
        return ResponseVO.ok(entity);
    }
    @GetMapping("list")
    public ResponseVO<List<Announcement>> list() {
        return ResponseVO.ok(announcementService.list());
    }
    @PostMapping("add")
    public ResponseVO add(@RequestBody Announcement entity) {
        announcementService.insert(entity);
        return ResponseVO.ok();
    }
    @PutMapping("update")
    public ResponseVO update(@RequestBody Announcement entity) {
        announcementService.updateById(entity);
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
        announcementService.removeByIds(ids);
        return ResponseVO.ok();
    }
    //发布
    @PostMapping("publish/{id}")
    public ResponseVO publish(@PathVariable("id") Integer id) {
        announcementService.publish(id);
        return ResponseVO.ok();
    }
    //下架
    @PostMapping("delist/{id}")
    public ResponseVO delist(@PathVariable("id") Integer id) {
        announcementService.delist(id);
        return ResponseVO.ok();
    }
}
