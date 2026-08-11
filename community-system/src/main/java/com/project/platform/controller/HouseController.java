package com.project.platform.controller;

import com.project.platform.entity.Building;
import com.project.platform.entity.House;
import com.project.platform.entity.User;
import com.project.platform.service.BuildingService;
import com.project.platform.service.HouseService;
import com.project.platform.service.UserService;
import com.project.platform.vo.PageVO;
import com.project.platform.vo.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 房屋信息
 */
@RestController
@RequestMapping("/house")
public class HouseController {
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
    public ResponseVO<PageVO<House>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (query.get("buildingName") != null&& !query.get("buildingName").toString().equals("")) {
            String buildingName = query.get("buildingName").toString();
            List<Integer> buildingIds = buildingService.queryLikelist(buildingName);
            query.put("buildingIds",buildingIds);
        }
        PageVO<House> page = houseService.page(query, pageNum, pageSize);
        page.getList().forEach(house -> {
            Building building = buildingService.selectById(house.getBuildingId());
            if (building != null) {
                house.setBuildingName(building.getCode());
            }
            User user = userService.selectById(house.getUserId());
            if (user != null) {
                house.setUserName(user.getUsername());
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
    public ResponseVO<House> selectById(@PathVariable("id") Integer id) {
        House entity = houseService.selectById(id);
        return ResponseVO.ok(entity);
    }


    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public ResponseVO<List<House>> list() {
        return ResponseVO.ok(houseService.list());
    }

    /**
     * 我的列表
     *
     * @return
     */
    @GetMapping("mylist")
    public ResponseVO<List<House>> mylist() {
        return ResponseVO.ok(houseService.mylist());
    }


    /**
     * 树列表
     *
     * @return
     */
    @GetMapping("treelist")
    public ResponseVO<List<House>> treelist() {
        return ResponseVO.ok(houseService.treelist());
    }


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("getById/{id}")
    public ResponseVO<House> getById(@PathVariable("id") Integer id) {
        List<House> list = houseService.getListByBuildingId(id);
        return ResponseVO.ok(list);
    }
    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("add")
    public ResponseVO add(@RequestBody House entity) {
        int number = buildingService.getCapacity(entity.getBuildingId());
        int CountSpaceNumber = houseService.getCountSpace(entity.getBuildingId());
        if (number <= CountSpaceNumber) {
            return  ResponseVO.fail(400,"房屋已满");
        }
        houseService.insert(entity);
        return ResponseVO.ok();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @PutMapping("update")
    public ResponseVO update(@RequestBody House entity) {
        houseService.updateById(entity);
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
        houseService.removeByIds(ids);
        return ResponseVO.ok();
    }
}
