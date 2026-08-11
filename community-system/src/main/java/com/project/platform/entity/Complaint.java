package com.project.platform.entity;

import java.time.LocalDateTime;
/**
 * 投诉信息
 */
public class Complaint  {
   /**
    * id
    */
   private Integer id;
   /**
   * 用户id
   */
   private Integer userId;
   /**
   * 投诉类型
   */
   private String type;
   /**
   * 描述
   */
   private String content;
   /**
   * 状态
   */
   private String status;
   /**
   * 创建时间
   */
   private LocalDateTime createTime;

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public LocalDateTime getCreateTime() {
      return createTime;
   }

   public void setCreateTime(LocalDateTime createTime) {
      this.createTime = createTime;
   }


}
