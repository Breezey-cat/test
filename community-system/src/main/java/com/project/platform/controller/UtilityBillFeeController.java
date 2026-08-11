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
 * 物业收费信息
 */
@RestController
@RequestMapping("/utilityBillFee")
public class UtilityBillFeeController {
    @Resource
    private UtilityBillFeeService utilityBillFeeService;
    @Resource
    private HouseService houseService;
    @Resource
    private BuildingService buildingService;
    @Resource
    private UserService userService;
    /**    /**
     * 分页查询
     *
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("page")
    public ResponseVO<PageVO<UtilityBillFee>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        CurrentUserDTO currentUser = CurrentUserThreadLocal.getCurrentUser();
        if (currentUser != null&&currentUser.getType().equals("USER")) {
            query.put("userId",currentUser.getId());
        }
        PageVO<UtilityBillFee> page = utilityBillFeeService.page(query, pageNum, pageSize);
        page.getList().forEach(utilityBillFee -> {
            House house  = houseService.selectById(utilityBillFee.getHouseId());
            if (house != null) {
                utilityBillFee.setHouseName(house.getHouseNumber());
                utilityBillFee.setBuildingId(house.getBuildingId());
                Building building = buildingService.selectById(house.getBuildingId());
                User user = userService.selectById(house.getUserId());
                if (building != null) {
                    utilityBillFee.setBuildingName(building.getCode());
                }
                if (user != null) {
                    utilityBillFee.setUserName(user.getUsername());
                }
            }
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
    public ResponseVO<UtilityBillFee> selectById(@PathVariable("id") Integer id) {
        UtilityBillFee entity = utilityBillFeeService.selectById(id);
        return ResponseVO.ok(entity);
    }


    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public ResponseVO<List<UtilityBillFee>> list() {
        return ResponseVO.ok(utilityBillFeeService.list());
    }


    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("add")
    public ResponseVO add(@RequestBody UtilityBillFee entity) {
        utilityBillFeeService.insert(entity);
        return ResponseVO.ok();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @PutMapping("update")
    public ResponseVO update(@RequestBody UtilityBillFee entity) {
        utilityBillFeeService.updateById(entity);
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
        utilityBillFeeService.removeByIds(ids);
        return ResponseVO.ok();
    }

    /**
     * 支付
     *
     * @param id
     */
    @PostMapping("pay/{id}")
    public ResponseVO pay(@PathVariable("id") Integer id) {
        utilityBillFeeService.pay(id);
        return ResponseVO.ok();
    }
}
