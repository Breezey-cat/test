package com.project.platform.entity;

import java.time.LocalDateTime;
/**
 * 房屋信息
 */
public class House  {
   /**
    * id
    */
   private Integer id;
   /**
   * 楼宇Id
   */
   private Integer buildingId;
   /**
   * 户主id
   */
   private Integer userId;
   /**
   * 房屋编号
   */
   private String houseNumber;
   /**
   * 房屋面积
   */
   private String area;
   /**
   * 户型
   */
   private String houseType;
   /**
   * 房屋状态
   */
   private String status;
   /**
   * 创建时间
   */
   private LocalDateTime createTime;

   private String buildingName;

   private String userName;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }
   public Integer getBuildingId() {
      return buildingId;
   }

   public void setBuildingId(Integer buildingId) {
      this.buildingId = buildingId;
   }

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   public String getHouseNumber() {
      return houseNumber;
   }

   public void setHouseNumber(String houseNumber) {
      this.houseNumber = houseNumber;
   }

   public String getArea() {
      return area;
   }

   public void setArea(String area) {
      this.area = area;
   }

   public String getHouseType() {
      return houseType;
   }

   public void setHouseType(String houseType) {
      this.houseType = houseType;
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

   public String getBuildingName() {
      return buildingName;
   }

   public void setBuildingName(String buildingName) {
      this.buildingName = buildingName;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }
}
