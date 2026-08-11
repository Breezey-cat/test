package com.project.platform.service.impl;

import com.project.platform.entity.ParkingSpace;
import com.project.platform.mapper.ParkingSpaceMapper;
import com.project.platform.service.ParkingLotService;
import com.project.platform.service.ParkingSpaceService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 车位信息
 */
@Service
public class ParkingSpaceServiceImpl  implements ParkingSpaceService {
    @Resource
    private ParkingSpaceMapper parkingSpaceMapper;
    @Resource
    private ParkingLotService parkingLotService;
    
    @Override
    public PageVO<ParkingSpace> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<ParkingSpace> page = new PageVO();
        List<ParkingSpace> list = parkingSpaceMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(parkingSpaceMapper.queryCount(query));
        return page;
    }

    @Override
    public ParkingSpace selectById(Integer id) {
        ParkingSpace parkingSpace = parkingSpaceMapper.selectById(id);
        return parkingSpace;
    }

    @Override
    public List<ParkingSpace> list() {
        return parkingSpaceMapper.list();
    }

    @Override
    public void insert(ParkingSpace entity) {
        check(entity);
        parkingSpaceMapper.insert(entity);
    }
    @Override
    public void updateById(ParkingSpace entity) {
        check(entity);
        parkingSpaceMapper.updateById(entity);
    }
    private void check(ParkingSpace entity) {

    }
    @Override
    public void removeByIds(List<Integer> ids) {
        parkingSpaceMapper.removeByIds(ids);
    }

    @Override
    public int getCountSpace(Integer parkingLotId){
        return parkingSpaceMapper.getCountSpace(parkingLotId);
    }

    @Override
    public List<Integer> queryLikelist(String parkingSpaceName) {
        return parkingSpaceMapper.queryLikelist(parkingSpaceName);
    }
}
