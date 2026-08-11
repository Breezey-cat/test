package com.project.platform.entity;

import java.time.LocalDateTime;

/**
 * 水电收费信息
 */
public class UtilityBillFee {
   /**
    * id
    */
   private Integer id;

   private Integer userId;

   private String userName;
   /**
   * 房屋Id
   */
   private Integer houseId;

   private Integer buildingId;
   /**
    * 收费类型
    */
   private String type;
   /**
   * 费用
   */
   private Float fee;
   /**
   * 支付状态
   */
   private String paymentStatus;
   private String  buildingName;
   private String houseName;

   /**
   * 创建时间
   */
   private LocalDateTime createTime;

   private Float reading;

   private String readingDate;

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

   public Float getFee() {
      return fee;
   }

   public void setFee(Float fee) {
      this.fee = fee;
   }

   public String getPaymentStatus() {
      return paymentStatus;
   }

   public void setPaymentStatus(String paymentStatus) {
      this.paymentStatus = paymentStatus;
   }

   public LocalDateTime getCreateTime() {
      return createTime;
   }

   public void setCreateTime(LocalDateTime createTime) {
      this.createTime = createTime;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getBuildingName() {
      return buildingName;
   }

   public void setBuildingName(String buildingName) {
      this.buildingName = buildingName;
   }

   public String getHouseName() {
      return houseName;
   }

   public void setHouseName(String houseName) {
      this.houseName = houseName;
   }

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   public Integer getBuildingId() {
      return buildingId;
   }

   public void setBuildingId(Integer buildingId) {
      this.buildingId = buildingId;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public Float getReading() {
      return reading;
   }

   public void setReading(Float reading) {
      this.reading = reading;
   }

   public String getReadingDate() {
      return readingDate;
   }

   public void setReadingDate(String readingDate) {
      this.readingDate = readingDate;
   }
}
