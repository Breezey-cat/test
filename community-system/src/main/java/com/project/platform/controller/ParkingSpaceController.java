package com.project.platform.controller;

import com.project.platform.entity.*;
import com.project.platform.service.ParkingLotService;
import com.project.platform.service.ParkingSpaceService;
import com.project.platform.service.UserService;
import com.project.platform.vo.PageVO;
import com.project.platform.vo.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 车位信息
 */
@RestController
@RequestMapping("/parkingSpace")
public class ParkingSpaceController {
    @Resource
    private ParkingSpaceService parkingSpaceService;

    @Resource
    private ParkingLotService parkingLotService;

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
    public ResponseVO<PageVO<ParkingSpace>> page(@RequestParam Map<String, Object> query, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        if (query.get("parkingLotName") != null&&query.get("parkingLotName")!= "") {
            String parkingLotName = query.get("parkingLotName").toString();
            List<Integer> parkingLotIds = parkingLotService.queryLikelist(parkingLotName);
            query.put("parkingLotIds",parkingLotIds);
        }
        PageVO<ParkingSpace> page = parkingSpaceService.page(query, pageNum, pageSize);
        page.getList().forEach(parkingSpace -> {
            ParkingLot parkingLot = parkingLotService.selectById(parkingSpace.getParkingLotId());
            if (parkingLot != null) {
                parkingSpace.setParkingLotName(parkingLot.getName());
            }

            User user = userService.selectById(parkingSpace.getUserId());
            if(user!=null){
                parkingSpace.setUserName(user.getUsername());
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
    public ResponseVO<ParkingSpace> selectById(@PathVariable("id") Integer id) {
        ParkingSpace entity = parkingSpaceService.selectById(id);
        return ResponseVO.ok(entity);
    }


    /**
     * 列表
     *
     * @return
     */
    @GetMapping("list")
    public ResponseVO<List<ParkingSpace>> list() {
        return ResponseVO.ok(parkingSpaceService.list());
    }


    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("add")
    public ResponseVO add(@RequestBody ParkingSpace entity) {
        int number = parkingLotService.getCapacity(entity.getParkingLotId());
        int CountSpaceNumber = parkingSpaceService.getCountSpace(entity.getParkingLotId());
        if (number <= CountSpaceNumber) {
            return ResponseVO.fail(400,"车位已满");
        }
        parkingSpaceService.insert(entity);
        return ResponseVO.ok();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    @PutMapping("update")
    public ResponseVO update(@RequestBody ParkingSpace entity) {
        parkingSpaceService.updateById(entity);
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
        parkingSpaceService.removeByIds(ids);
        return ResponseVO.ok();
    }
}
