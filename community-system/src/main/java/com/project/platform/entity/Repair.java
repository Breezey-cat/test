package com.project.platform.entity;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 报修信息
 */
public class Repair  {
   /**
    * id
    */
   private Integer id;
   /**
   * 房屋id
   */
   private Integer houseId;

   /**
    * 楼宇房屋编号
    */
   private String houseName;

   /**
   * 类型
   */
   private String type;
   /**
   * 描述
   */
   private String content;
   /**
   * 紧急程度
   */
   private String urgencyLevel;
   /**
   * 状态
   */
   private String status;
   /**
   * 创建时间
   */
   private LocalDateTime createTime;

   private Date appointmentTime;

   private Integer userId;
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
   public Integer getHouseId() {
      return houseId;
   }

   public void setHouseId(Integer houseId) {
      this.houseId = houseId;
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

   public String getUrgencyLevel() {
      return urgencyLevel;
   }

   public void setUrgencyLevel(String urgencyLevel) {
      this.urgencyLevel = urgencyLevel;
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

   public String getHouseName() {
      return houseName;
   }

   public void setHouseName(String houseName) {
      this.houseName = houseName;
   }

   public Date getAppointmentTime() {
      return appointmentTime;
   }

   public void setAppointmentTime(Date appointmentTime) {
      this.appointmentTime = appointmentTime;
   }
}
