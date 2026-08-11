package com.project.platform.service.impl;

import com.project.platform.entity.Complaint;
import com.project.platform.mapper.ComplaintMapper;
import com.project.platform.service.ComplaintService;
import com.project.platform.utils.CurrentUserThreadLocal;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 投诉信息
 */
@Service
public class ComplaintServiceImpl  implements ComplaintService {
    @Resource
    private ComplaintMapper complaintMapper;
    @Override
    public PageVO<Complaint> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<Complaint> page = new PageVO();
        List<Complaint> list = complaintMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(complaintMapper.queryCount(query));
        return page;
    }
    @Override
    public Complaint selectById(Integer id) {
        Complaint complaint = complaintMapper.selectById(id);
        return complaint;
    }
    @Override
    public List<Complaint> list() {
        return complaintMapper.list();
    }
    @Override
    public void insert(Complaint entity) {
        check(entity);
        Integer userId = CurrentUserThreadLocal.getCurrentUser().getId();
        entity.setUserId(userId);
        entity.setStatus("未处理");
        complaintMapper.insert(entity);
    }
    @Override
    public void updateById(Complaint entity) {
        check(entity);
        complaintMapper.updateById(entity);
    }
    private void check(Complaint entity) {

    }
    @Override
    public void removeByIds(List<Integer> ids) {
        complaintMapper.removeByIds(ids);
    }

    @Override
    public void handle(Integer id) {
        Complaint complaint = complaintMapper.selectById(id);
        if (complaint != null&&complaint.getStatus().equals("未处理")) {
            complaint.setStatus("已处理");
            complaintMapper.updateById(complaint);
        }
    }
}
