package com.project.platform.service.impl;

import com.project.platform.entity.Complaint;
import com.project.platform.entity.Repair;
import com.project.platform.mapper.RepairMapper;
import com.project.platform.service.RepairService;
import com.project.platform.utils.CurrentUserThreadLocal;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 报修信息
 */
@Service
public class RepairServiceImpl  implements RepairService {
    @Resource
    private RepairMapper repairMapper;
    @Override
    public PageVO<Repair> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<Repair> page = new PageVO();
        List<Repair> list = repairMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(repairMapper.queryCount(query));
        return page;
    }
    @Override
    public Repair selectById(Integer id) {
        Repair repair = repairMapper.selectById(id);
        return repair;
    }
    @Override
    public List<Repair> list() {
        return repairMapper.list();
    }
    @Override
    public void insert(Repair entity) {
        check(entity);
        Integer userId = CurrentUserThreadLocal.getCurrentUser().getId();
        entity.setUserId(userId);
        entity.setStatus("未处理");
        repairMapper.insert(entity);
    }
    @Override
    public void updateById(Repair entity) {
        check(entity);
        repairMapper.updateById(entity);
    }
    private void check(Repair entity) {

    }
    @Override
    public void removeByIds(List<Integer> ids) {
        repairMapper.removeByIds(ids);
    }

    @Override
    public void handle(Integer id) {
        Repair repair = repairMapper.selectById(id);
        if (repair != null&&repair.getStatus().equals("未处理")) {
            repair.setStatus("已处理");
            repairMapper.updateById(repair);
        }
    }
}
