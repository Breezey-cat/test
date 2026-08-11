package com.project.platform.entity;

import java.time.LocalDateTime;
/**
 * 车位收费信息
 */
public class ParkingFee  {
   /**
    * id
    */
   private Integer id;
   /**
   * 车位ID
   */
   private Integer parkingSpaceId;

   private String parkingSpaceName;
   /**
   * 用户id
   */
   private Integer userId;

   private String userName;
   /**
   * 费用
   */
   private Float fee;
   /**
   * 支付状态
   */
   private String paymentStatus;

   /**
   * 创建时间
   */
   private LocalDateTime createTime;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }
   public Integer getParkingSpaceId() {
      return parkingSpaceId;
   }

   public void setParkingSpaceId(Integer parkingSpaceId) {
      this.parkingSpaceId = parkingSpaceId;
   }

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
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

   public String getParkingSpaceName() {
      return parkingSpaceName;
   }

   public void setParkingSpaceName(String parkingSpaceName) {
      this.parkingSpaceName = parkingSpaceName;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }
}
