package com.project.platform.service.impl;

import com.project.platform.dto.CurrentUserDTO;
import com.project.platform.entity.*;
import com.project.platform.mapper.*;
import com.project.platform.service.BuildingService;
import com.project.platform.utils.CurrentUserThreadLocal;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 楼宇信息
 */
@Service
public class BuildingServiceImpl  implements BuildingService {
    @Resource
    private BuildingMapper buildingMapper;
    @Resource
    private HouseMapper houseMapper;
    @Resource
    private PropertyFeeMapper propertyFeeMapper;
    @Resource
    private RepairMapper repairMapper;
    @Resource
    private UtilityBillFeeMapper utilityBillFeeMapper;

    @Override
    public PageVO<Building> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<Building> page = new PageVO();
        List<Building> list = buildingMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(buildingMapper.queryCount(query));
        return page;
    }

    @Override
    public Building selectById(Integer id) {
        Building building = buildingMapper.selectById(id);
        return building;
    }

    @Override
    public List<Building> list() {
        return buildingMapper.list();
    }
    @Override
    public List<Integer> queryLikelist(String name) {
        return buildingMapper.queryLikelist(name);
    }
    @Override
    public void insert(Building entity) {
        check(entity);
        buildingMapper.insert(entity);
    }
    @Override
    public void updateById(Building entity) {
        check(entity);
        buildingMapper.updateById(entity);
    }
    private void check(Building entity) {

    }
    @Override
    @Transactional
    public void removeByIds(List<Integer> ids) {
        List<Integer> houseIds = new ArrayList<>();
        ids.forEach(id -> {
            List<House> houses = houseMapper.queryByBuildingId(id);
            houses.forEach(house -> {
                if (house != null) {
                    houseIds.add(house.getId());
                }
            });
        });
        buildingMapper.removeByIds(ids);
        if (houseIds!=null&&houseIds.size() > 0) {
            //删除房屋
            houseMapper.removeByBuildingId(ids);
            //删除物业费、报修、水电费
            propertyFeeMapper.removeByHouseIds(houseIds);
            repairMapper.removeByHouseIds(houseIds);
            utilityBillFeeMapper.removeByHouseIds(houseIds);
        }
    }

    @Override
    public Integer getCapacity(Integer id) {
        return  buildingMapper.getCapacity(id);
    }
}
