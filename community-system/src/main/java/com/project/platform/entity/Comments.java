package com.project.platform.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论记录
 */
@Data
public class Comments {
   /**
    * id
    */
   private Integer id;
   /**
   * 用户ID
   */
   private Integer userId;
   /**
    * 评论类型：1 新闻评论 2 论坛评论
    */
   private Integer type;
   /**
   * 新闻Id/论坛Id
   */
   private Integer sourceId;
   /**
   * 评论内容
   */
   private String content;
   /**
   * 父评论ID (用于回复评论)
   */
   private Integer parentId;
   /**
   * 评论时间
   */
   private LocalDateTime createTime;

   private User user;

   private List<Comments> childList;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   public Integer getType() {
      return type;
   }

   public void setType(Integer type) {
      this.type = type;
   }

   public Integer getSourceId() {
      return sourceId;
   }

   public void setSourceId(Integer sourceId) {
      this.sourceId = sourceId;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public Integer getParentId() {
      return parentId;
   }

   public void setParentId(Integer parentId) {
      this.parentId = parentId;
   }

   public LocalDateTime getCreateTime() {
      return createTime;
   }

   public void setCreateTime(LocalDateTime createTime) {
      this.createTime = createTime;
   }

   public List<Comments> getChildList() {
      return childList;
   }

   public void setChildList(List<Comments> childList) {
      this.childList = childList;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

}
