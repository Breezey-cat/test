package com.project.platform.service.impl;

import com.project.platform.entity.ParkingFee;
import com.project.platform.entity.ParkingSpace;
import com.project.platform.exception.CustomException;
import com.project.platform.mapper.ParkingFeeMapper;
import com.project.platform.mapper.ParkingSpaceMapper;
import com.project.platform.service.ParkingFeeService;
import com.project.platform.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 车位收费信息
 */
@Service
public class ParkingFeeServiceImpl  implements ParkingFeeService {
    @Resource
    private ParkingFeeMapper parkingFeeMapper;

    @Resource
    private UserService userService;

    @Resource
    private ParkingSpaceMapper parkingSpaceMapper;
    
    @Override
    public PageVO<ParkingFee> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<ParkingFee> page = new PageVO();
        List<ParkingFee> list = parkingFeeMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(parkingFeeMapper.queryCount(query));
        return page;
    }

    @Override
    public ParkingFee selectById(Integer id) {
        ParkingFee parkingFee = parkingFeeMapper.selectById(id);
        return parkingFee;
    }

    @Override
    public List<ParkingFee> list() {
        return parkingFeeMapper.list();
    }
    @Override
    public void insert(ParkingFee entity) {
        check(entity);
        ParkingSpace parkingSpace = parkingSpaceMapper.selectById(entity.getParkingSpaceId());
        parkingSpace.getUserId();
        if (parkingSpace != null) {
            entity.setUserId(parkingSpace.getUserId());
        }
        parkingFeeMapper.insert(entity);
    }
    @Override
    public void updateById(ParkingFee entity) {
        check(entity);
        parkingFeeMapper.updateById(entity);
    }
    private void check(ParkingFee entity) {

    }
    @Override
    public void removeByIds(List<Integer> ids) {
        parkingFeeMapper.removeByIds(ids);
    }

    /**
     * 付款
     *
     * @param id
     */
    public void pay(Integer id) {
        ParkingFee parkingFee = selectById(id);
        if (!parkingFee.getPaymentStatus().equals("未支付")) {
            throw new CustomException("数据已过期，请先刷新页面");
        }
        //扣除用户余额
        userService.consumption(parkingFee.getUserId(), parkingFee.getFee());
        parkingFee.setPaymentStatus("已支付");
        parkingFeeMapper.updateById(parkingFee);
    }
}
