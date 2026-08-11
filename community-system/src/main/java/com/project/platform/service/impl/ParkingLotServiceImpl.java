package com.project.platform.service.impl;

import com.project.platform.entity.ParkingLot;
import com.project.platform.mapper.ParkingLotMapper;
import com.project.platform.service.ParkingLotService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 停车场信息
 */
@Service
public class ParkingLotServiceImpl  implements ParkingLotService {
    @Resource
    private ParkingLotMapper parkingLotMapper;
    
    @Override
    public PageVO<ParkingLot> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<ParkingLot> page = new PageVO();
        List<ParkingLot> list = parkingLotMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(parkingLotMapper.queryCount(query));
        return page;
    }

    @Override
    public ParkingLot selectById(Integer id) {
        ParkingLot parkingLot = parkingLotMapper.selectById(id);
        return parkingLot;
    }

    @Override
    public List<ParkingLot> list() {
        return parkingLotMapper.list();
    }

    @Override
    public List<Integer> queryLikelist(String parkingLotName){
        return parkingLotMapper.queryLikelist(parkingLotName);
    }

    @Override
    public void insert(ParkingLot entity) {
        check(entity);
        parkingLotMapper.insert(entity);
    }
    @Override
    public void updateById(ParkingLot entity) {
        check(entity);
        parkingLotMapper.updateById(entity);
    }
    private void check(ParkingLot entity) {

    }
    @Override
    public void removeByIds(List<Integer> ids) {
        parkingLotMapper.removeByIds(ids);
    }

    @Override
    public Integer getCapacity(Integer parkingLotId) {
        return  parkingLotMapper.getCapacity(parkingLotId);
    }

}
