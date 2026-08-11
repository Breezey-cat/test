package com.project.platform.mapper;

import com.project.platform.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface UserMapper {
    List<User> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(Integer id);

    @Select("SELECT * FROM user")
    List<User> list();

    int insert(User entity);

    int updateById(User entity);

    boolean removeByIds(List<Integer> ids);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    @Select("SELECT * FROM user WHERE tel = #{tel}")
    User selectByTel(String tel);
}
