package com.project.platform.controller;

import com.project.platform.dto.CurrentUserDTO;
import com.project.platform.entity.*;
import com.project.platform.service.*;
import com.project.platform.utils.CurrentUserThreadLocal;
import com.project.platform.vo.PageVO;
import com.project.platform.vo.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 车位收费信息
 */
@RestController
@RequestMapping("/parkingFee")
public class ParkingFeeController {
    @Resource
    private ParkingFeeService parkingFeeService;
    @Resource
    private ParkingSpaceService  parkingSpaceService;
    @Resource
    private UserService userService;

    /**
     * 分页查询
     *
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("page")
    public ResponseVO<PageVO<ParkingFee>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        CurrentUserDTO currentUser = CurrentUserThreadLocal.getCurrentUser();
        if (currentUser != null&&currentUser.getType().equals("USER")) {
            query.put("userId",currentUser.getId());
        }
        PageVO<ParkingFee> page = parkingFeeService.page(query, pageNum, pageSize);
        page.getList().forEach(parkingFee -> {
           ParkingSpace space =  parkingSpaceService.selectById(parkingFee.getParkingSpaceId());
           User user = userService.selectById(space.getUserId());
           parkingFee.setParkingSpaceName(space.getSpaceNumber());
           parkingFee.setUserName(user.getUsername());
        });
        return ResponseVO.ok(page);

    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("selectById/{id}")
    public ResponseVO<ParkingFee> selectById(@PathVariable("id") Integer id) {
        ParkingFee entity = parkingFeeService.selectById(id);
        return ResponseVO.ok(entity);
    }


    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public ResponseVO<List<ParkingFee>> list() {
        return ResponseVO.ok(parkingFeeService.list());
    }


    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("add")
    public ResponseVO add(@RequestBody ParkingFee entity) {
        parkingFeeService.insert(entity);
        return ResponseVO.ok();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @PutMapping("update")
    public ResponseVO update(@RequestBody ParkingFee entity) {
        parkingFeeService.updateById(entity);
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
        parkingFeeService.removeByIds(ids);
        return ResponseVO.ok();
    }

    /**
     * 支付
     *
     * @param id
     */
    @PostMapping("pay/{id}")
    public ResponseVO pay(@PathVariable("id") Integer id) {
        parkingFeeService.pay(id);
        return ResponseVO.ok();
    }

}
