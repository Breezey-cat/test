package com.project.platform.service.impl;

import com.project.platform.dto.CurrentUserDTO;
import com.project.platform.entity.Building;
import com.project.platform.entity.House;
import com.project.platform.mapper.*;
import com.project.platform.service.BuildingService;
import com.project.platform.service.HouseService;
import com.project.platform.utils.CurrentUserThreadLocal;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 房屋信息
 */
@Service
public class HouseServiceImpl  implements HouseService {
    @Resource
    private HouseMapper houseMapper;
    @Resource
    private BuildingMapper buildingMapper;
    @Resource
    private PropertyFeeMapper propertyFeeMapper;
    @Resource
    private RepairMapper repairMapper;
    @Resource
    private UtilityBillFeeMapper utilityBillFeeMapper;
    @Override
    public PageVO<House> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<House> page = new PageVO();
        List<House> list = houseMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(houseMapper.queryCount(query));
        return page;
    }

    @Override
    public House selectById(Integer id) {
        House house = houseMapper.selectById(id);
        return house;
    }

    @Override
    public List<House> list() {
        return houseMapper.list();
    }

    @Override
    public List<House> mylist() {
        CurrentUserDTO currentUserDTO = CurrentUserThreadLocal.getCurrentUser();
        if (currentUserDTO != null&&currentUserDTO.getType().equals("USER")) {
            Integer userId = currentUserDTO.getId();
            List<House> houses = houseMapper.mylist(userId);
            houses.forEach(house -> {
                Building building = buildingMapper.selectById(house.getBuildingId());
                house.setHouseNumber(building.getCode()+"--"+house.getHouseNumber());
            });
            return houses;
        }
        return null;
    }

    @Override
    public List<Building> treelist(){
        List<Building>  buildings = buildingMapper.list();
        buildings.forEach(building -> {
            List<House> houseList = houseMapper.queryByBuildingId(building.getId());
            if(houseList != null && houseList.size() > 0){
                building.setHouseList(houseList);
            }
        });
        return buildings;
    }

    @Override
    public List<House> getListByBuildingId(Integer buildingId){
        return houseMapper.queryByBuildingId(buildingId);
    }
    @Override
    public void insert(House entity) {
        check(entity);
        houseMapper.insert(entity);
    }
    @Override
    public void updateById(House entity) {
        check(entity);
        houseMapper.updateById(entity);
    }
    private void check(House entity) {

    }
    @Override
    @Transactional
    public void removeByIds(List<Integer> ids) {
        houseMapper.removeByIds(ids);
        //删除物业费、报修、水电费
        if(ids != null && ids.size() > 0){
            propertyFeeMapper.removeByHouseIds(ids);
            repairMapper.removeByHouseIds(ids);
            utilityBillFeeMapper.removeByHouseIds(ids);
        }
    }

    @Override
    public int getCountSpace(Integer id){
        return houseMapper.getCountSpace(id);
    }

    @Override
    public List<Integer> queryLikelist(String houseName) {
        return houseMapper.queryLikelist(houseName);
    }
}
