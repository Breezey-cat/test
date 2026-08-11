package com.project.platform.service.impl;

import com.project.platform.entity.House;
import com.project.platform.entity.PropertyFee;
import com.project.platform.entity.UtilityBillFee;
import com.project.platform.exception.CustomException;
import com.project.platform.mapper.PropertyFeeMapper;
import com.project.platform.mapper.UtilityBillFeeMapper;
import com.project.platform.service.HouseService;
import com.project.platform.service.PropertyFeeService;
import com.project.platform.service.UserService;
import com.project.platform.service.UtilityBillFeeService;
import com.project.platform.vo.PageVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 物业收费信息
 */
@Service
public class UtilityBillFeeServiceImpl implements UtilityBillFeeService {
    @Resource
    private UtilityBillFeeMapper utilityBillFeeMapper;
    @Resource
    private UserService userService;

    @Resource
    private HouseService houseService;

    @Override
    public PageVO<UtilityBillFee> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<UtilityBillFee> page = new PageVO();
        List<UtilityBillFee> list = utilityBillFeeMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(utilityBillFeeMapper.queryCount(query));
        return page;
    }

    @Override
    public UtilityBillFee selectById(Integer id) {
        UtilityBillFee utilityBillFee = utilityBillFeeMapper.selectById(id);
        return utilityBillFee;
    }

    @Override
    public List<UtilityBillFee> list() {
        return utilityBillFeeMapper.list();
    }
    @Override
    public void insert(UtilityBillFee entity) {
        House house = houseService.selectById(entity.getHouseId());
        entity.setUserId(house.getUserId());
        entity.setPaymentStatus("未支付");
        check(entity);
        utilityBillFeeMapper.insert(entity);
    }
    @Override
    public void updateById(UtilityBillFee entity) {
        check(entity);
        utilityBillFeeMapper.updateById(entity);
    }
    private void check(UtilityBillFee entity) {

    }
    @Override
    public void removeByIds(List<Integer> ids) {
        utilityBillFeeMapper.removeByIds(ids);
    }

    /**
     * 付款
     *
     * @param id
     */
    public void pay(Integer id) {
        UtilityBillFee utilityBillFee = selectById(id);
        if (!utilityBillFee.getPaymentStatus().equals("未支付")) {
            throw new CustomException("数据已过期，请先刷新页面");
        }
        //扣除用户余额
        userService.consumption(utilityBillFee.getUserId(), utilityBillFee.getFee());
        utilityBillFee.setPaymentStatus("已支付");
        utilityBillFeeMapper.updateById(utilityBillFee);
    }
}
