package com.project.platform.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.project.platform.dto.CurrentUserDTO;
import com.project.platform.dto.RetrievePasswordDTO;
import com.project.platform.dto.UpdatePasswordDTO;
import com.project.platform.entity.Admin;
import com.project.platform.entity.User;
import com.project.platform.exception.CustomException;
import com.project.platform.mapper.UserMapper;
import com.project.platform.service.UserService;
import com.project.platform.utils.CurrentUserThreadLocal;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.project.platform.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * 用户
 */
@Service
public class  UserServiceImpl  implements UserService  {
    @Resource
    private UserMapper userMapper;
    @Value("${resetPassword}")
    private String resetPassword;
    @Override
    public PageVO<User> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
        PageVO<User> page = new PageVO();
        List<User> list = userMapper.queryPage((pageNum - 1) * pageSize, pageSize, query);
        page.setList(list);
        page.setTotal(userMapper.queryCount(query));
        return page;
    }
    @Override
    public User selectById(Integer id) {
        User user = userMapper.selectById(id);
        return user;
    }
    @Override
    public List<User> list() {
        return userMapper.list();
    }
    @Override
    public void insert(User entity) {
        check(entity);
        if (entity.getPassword() == null) {
            entity.setPassword(resetPassword);
        }
        userMapper.insert(entity);
    }
    @Override
    public void updateById(User entity) {
        check(entity);
        userMapper.updateById(entity);
    }
    private void check(User entity) {
        User user = userMapper.selectByUsername(entity.getUsername());
        if (user != null && user.getId() != entity.getId()) {
            throw new CustomException("用户名已存在");
        }
    }
    @Override
    public void removeByIds(List<Integer> ids) {
        userMapper.removeByIds(ids);
    }

    @Override
    public CurrentUserDTO login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new CustomException("用户名或密码错误");
        }
        if (user.getStatus().equals("禁用")) {
            throw new CustomException("用户已禁用");
        }
        CurrentUserDTO currentUserDTO = new CurrentUserDTO();
        BeanUtils.copyProperties(user, currentUserDTO);
        return currentUserDTO;
    }

    @Override
    public void register(JSONObject data) {
        User user = new User();
        user.setUsername(data.getString("username"));
        user.setNickname(data.getString("nickname"));
        user.setAvatarUrl(data.getString("avatarUrl"));
        user.setPassword(data.getString("password"));
        user.setBalance(100F);//新用户赠送 100余额
        user.setStatus("启用");
        insert(user);
    }


    @Override
    public void updateCurrentUserInfo(CurrentUserDTO currentUserDTO) {
        User user = userMapper.selectById(currentUserDTO.getId());
        user.setId(currentUserDTO.getId());
        user.setNickname(currentUserDTO.getNickname());
        user.setAvatarUrl(currentUserDTO.getAvatarUrl());
        user.setTel(currentUserDTO.getTel());
        user.setEmail(currentUserDTO.getEmail());
        userMapper.updateById(user);
    }

    @Override
    public void updateCurrentUserPassword(UpdatePasswordDTO updatePassword) {
        User user = userMapper.selectById(CurrentUserThreadLocal.getCurrentUser().getId());
        if (!user.getPassword().equals(updatePassword.getOldPassword())) {
            throw new CustomException("旧密码不正确");
        }
        user.setPassword(updatePassword.getNewPassword());
        userMapper.updateById(user);
    }

    @Override
    public void resetPassword(Integer id) {
        User user = userMapper.selectById(id);
        user.setPassword(resetPassword);
        userMapper.updateById(user);
    }

    @Override
    public void retrievePassword(RetrievePasswordDTO retrievePasswordDTO) {
        User user = userMapper.selectByUsername(retrievePasswordDTO.getUsername());
        if (user == null) {
            throw new CustomException("用户名不存在");
        }
        //TODO 校验验证码
        user.setPassword(retrievePasswordDTO.getPassword());
        userMapper.updateById(user);
    }


    /**
     * 充值
     *
     * @param userId
     * @param amount
     */

    public void topUp(Integer userId, Float amount) {
        // 修复3：校验金额必须正数
        if (amount == null || amount <= 0) {
            throw new CustomException("充值金额必须大于0");
        }

        // 修复1：判空
        User user = selectById(userId);
        if (user == null) {
            throw new CustomException("用户不存在");
        }

        // 修复2：balance 为 null 时默认为 0
        Float currentBalance = user.getBalance() != null ? user.getBalance() : 0F;
        user.setBalance(currentBalance + amount);
        userMapper.updateById(user);


    }
    /**
     * 消费
     *
     * @param userId
     * @param amount
     */
    public void consumption(Integer userId, Float amount) {
        User user = selectById(userId);
        user.setBalance(user.getBalance() - amount);
        if (user.getBalance() < 0) {
            throw new CustomException("余额不足");
        }
        userMapper.updateById(user);
    }
}
