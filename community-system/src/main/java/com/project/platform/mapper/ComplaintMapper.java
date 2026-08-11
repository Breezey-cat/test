package com.project.platform.mapper;

import com.project.platform.entity.Complaint;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ComplaintMapper {
    List<Complaint> queryPage(Integer offset, Integer pageSize, @Param("query") Map<String, Object> query);

    int queryCount(@Param("query") Map<String, Object> query);

    @Select("SELECT * FROM complaint WHERE id = #{id}")
    Complaint selectById(Integer id);

    @Select("SELECT * FROM complaint")
    List<Complaint> list();

    int insert(Complaint entity);

    int updateById(Complaint entity);

    boolean removeByIds(List<Integer> ids);

}