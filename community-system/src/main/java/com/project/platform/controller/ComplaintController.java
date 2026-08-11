package com.project.platform.controller;

import com.project.platform.dto.CurrentUserDTO;
import com.project.platform.entity.Complaint;
import com.project.platform.service.ComplaintService;
import com.project.platform.utils.CurrentUserThreadLocal;
import com.project.platform.vo.PageVO;
import com.project.platform.vo.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 投诉信息
 */
@RestController
@RequestMapping("/complaint")
public class ComplaintController {
    @Resource
    private ComplaintService complaintService;

    /**
     * 分页查询
     *
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("page")
    public ResponseVO<PageVO<Complaint>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        CurrentUserDTO currentUser = CurrentUserThreadLocal.getCurrentUser();
        if (currentUser != null&&currentUser.getType().equals("USER")) {
            query.put("userId",currentUser.getId());
        }
        PageVO<Complaint> page = complaintService.page(query, pageNum, pageSize);
        return ResponseVO.ok(page);

    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("selectById/{id}")
    public ResponseVO<Complaint> selectById(@PathVariable("id") Integer id) {
        Complaint entity = complaintService.selectById(id);
        return ResponseVO.ok(entity);
    }


    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public ResponseVO<List<Complaint>> list() {
        return ResponseVO.ok(complaintService.list());
    }


    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("add")
    public ResponseVO add(@RequestBody Complaint entity) {
        complaintService.insert(entity);
        return ResponseVO.ok();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @PutMapping("update")
    public ResponseVO update(@RequestBody Complaint entity) {
        complaintService.updateById(entity);
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
        complaintService.removeByIds(ids);
        return ResponseVO.ok();
    }

    /**
     * 处理
     *
     * @param id
     */
    @PostMapping("handle/{id}")
    public ResponseVO handle(@PathVariable("id") Integer id) {
        complaintService.handle(id);
        return ResponseVO.ok();
    }
}
