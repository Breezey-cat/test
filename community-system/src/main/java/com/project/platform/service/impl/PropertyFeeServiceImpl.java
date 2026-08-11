package com.project.platform.service.impl;

import com.project.platform.entity.House;
import com.project.platform.entity.ParkingFee;
import com.project.platform.entity.PropertyFee;
import com.project.platform.exception.CustomException;
import com.project.platform.mapper.PropertyFeeMapper;
import com.project.platform.service.HouseService;
import com.project.platform.service.PropertyFeeService;
import com.project.platform.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 物业收费信息
 */
@Service
public class PropertyFeeServiceImpl  implements PropertyFeeService {
    @Resource
    private PropertyFeeMapper propertyFeeMapper;
    @Resource
    private UserService userService;
    @Resource
    private HouseService houseService;
    @Override
    public PageVO<PropertyFee> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<PropertyFee> page = new PageVO();
        List<PropertyFee> list = propertyFeeMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(propertyFeeMapper.queryCount(query));
        return page;
    }
    @Override
    public PropertyFee selectById(Integer id) {
        PropertyFee propertyFee = propertyFeeMapper.selectById(id);
        return propertyFee;
    }
    @Override
    public List<PropertyFee> list() {
        return propertyFeeMapper.list();
    }
    @Override
    public void insert(PropertyFee entity) {
        House house = houseService.selectById(entity.getHouseId());
        if (house == null)
        {                                    // ← 新增
            throw new CustomException("房屋不存在");
        }
        entity.setUserId(house.getUserId());// house 为 null → NPE！
        entity.setPaymentStatus("未支付");
        check(entity);
        propertyFeeMapper.insert(entity);
    }
    @Override
    public void updateById(PropertyFee entity) {
        check(entity);
        propertyFeeMapper.updateById(entity);
    }
    private void check(PropertyFee entity) {
        if (entity.getFee() == null || entity.getFee() <= 0) {
            throw new CustomException("金额必须大于0");
        }
        if (entity.getFee() > 999999) {
            throw new CustomException("金额不能超过9999");
        }
    }
    @Override
    public void removeByIds(List<Integer> ids) {
        propertyFeeMapper.removeByIds(ids);
    }

    /**
     * 付款
     *
     * @param id
     */
    public void pay(Integer id) {
        PropertyFee propertyFee = selectById(id);
        if (!propertyFee.getPaymentStatus().equals("未支付")) {
            throw new CustomException("数据已过期，请先刷新页面");
        }
        //扣除用户余额
        userService.consumption(propertyFee.getUserId(), propertyFee.getFee());
        propertyFee.setPaymentStatus("已支付");
        propertyFeeMapper.updateById(propertyFee);
    }
}
