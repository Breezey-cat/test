package com.project.platform.controller;

import com.project.platform.dto.CurrentUserDTO;
import com.project.platform.entity.Building;
import com.project.platform.entity.House;
import com.project.platform.entity.Repair;
import com.project.platform.service.BuildingService;
import com.project.platform.service.HouseService;
import com.project.platform.service.RepairService;
import com.project.platform.utils.CurrentUserThreadLocal;
import com.project.platform.vo.PageVO;
import com.project.platform.vo.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 报修信息
 */
@RestController
@RequestMapping("/repair")
public class RepairController {
    @Resource
    private RepairService repairService;
    @Resource
    private HouseService houseService;
    @Resource
    private BuildingService buildingService;

    /**
     * 分页查询
     *
     * @param query
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("page")
    public ResponseVO<PageVO<Repair>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        CurrentUserDTO currentUser = CurrentUserThreadLocal.getCurrentUser();
        if (currentUser != null&&currentUser.getType().equals("USER")) {
            query.put("userId",currentUser.getId());
        }
        PageVO<Repair> page = repairService.page(query, pageNum, pageSize);
        page.getList().forEach(repair -> {
            if (repair.getHouseId()!=null) {
                House house = houseService.selectById(repair.getHouseId());
                if (house != null) {
                    Building building = buildingService.selectById(house.getBuildingId());
                    repair.setHouseName(building.getCode()+"--"+house.getHouseNumber());
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
    public ResponseVO<Repair> selectById(@PathVariable("id") Integer id) {
        Repair entity = repairService.selectById(id);
        return ResponseVO.ok(entity);
    }


    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public ResponseVO<List<Repair>> list() {
        return ResponseVO.ok(repairService.list());
    }


    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("add")
    public ResponseVO add(@RequestBody Repair entity) {
        repairService.insert(entity);
        return ResponseVO.ok();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @PutMapping("update")
    public ResponseVO update(@RequestBody Repair entity) {
        repairService.updateById(entity);
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
        repairService.removeByIds(ids);
        return ResponseVO.ok();
    }

    /**
     * 处理
     *
     * @param id
     */
    @PostMapping("handle/{id}")
    public ResponseVO handle(@PathVariable("id") Integer id) {
        repairService.handle(id);
        return ResponseVO.ok();
    }
}
