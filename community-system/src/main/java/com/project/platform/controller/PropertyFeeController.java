package com.project.platform.controller;

import com.project.platform.dto.CurrentUserDTO;
import com.project.platform.entity.Building;
import com.project.platform.entity.House;
import com.project.platform.entity.PropertyFee;
import com.project.platform.service.BuildingService;
import com.project.platform.service.HouseService;
import com.project.platform.service.PropertyFeeService;
import com.project.platform.service.UserService;
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
@RequestMapping("/propertyFee")
public class PropertyFeeController {
    @Resource
    private PropertyFeeService propertyFeeService;
    @Resource
    private HouseService houseService;
    @Resource
    private BuildingService buildingService;
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
    public ResponseVO<PageVO<PropertyFee>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        CurrentUserDTO currentUser = CurrentUserThreadLocal.getCurrentUser();
        if (currentUser != null&&currentUser.getType().equals("USER")) {
            query.put("userId",currentUser.getId());
        }
        PageVO<PropertyFee> page = propertyFeeService.page(query, pageNum, pageSize);
        page.getList().forEach(propertyFee -> {
            House house  = houseService.selectById(propertyFee.getHouseId());
            if (house != null) {
                propertyFee.setHouseName(house.getHouseNumber());
                propertyFee.setBuildingId(house.getBuildingId());
                Building building = buildingService.selectById(house.getBuildingId());
                if (building != null) {
                    propertyFee.setBuildingName(building.getCode());
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
    public ResponseVO<PropertyFee> selectById(@PathVariable("id") Integer id) {
        PropertyFee entity = propertyFeeService.selectById(id);
        return ResponseVO.ok(entity);
    }


    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public ResponseVO<List<PropertyFee>> list() {
        return ResponseVO.ok(propertyFeeService.list());
    }


    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("add")
    public ResponseVO add(@RequestBody PropertyFee entity) {
        propertyFeeService.insert(entity);
        return ResponseVO.ok();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @PutMapping("update")
    public ResponseVO update(@RequestBody PropertyFee entity) {
        propertyFeeService.updateById(entity);
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
        propertyFeeService.removeByIds(ids);
        return ResponseVO.ok();
    }

    /**
     * 支付
     *
     * @param id
     */
    @PostMapping("pay/{id}")
    public ResponseVO pay(@PathVariable("id") Integer id) {
        propertyFeeService.pay(id);
        return ResponseVO.ok();
    }
}
