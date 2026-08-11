package com.project.platform.entity;

import java.time.LocalDateTime;
/**
 * 物业收费信息
 */
public class PropertyFee  {
   /**
    * id
    */
   private Integer id;
   /**
   * 房屋Id
   */
   private Integer houseId;

   private Integer buildingId;
   private String buildingName;
   private String houseName;
   /**
   * 费用
   */
   private Float fee;
   /**
   * 支付状态
   */
   private String paymentStatus;

   private String code;

   /**
   * 创建时间
   */
   private LocalDateTime createTime;

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

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public Integer getBuildingId() {
      return buildingId;
   }

   public void setBuildingId(Integer buildingId) {
      this.buildingId = buildingId;
   }
}
