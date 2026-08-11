package com.project.platform.entity;

import java.time.LocalDateTime;
/**
 * 车位信息
 */
public class ParkingSpace  {
   /**
    * id
    */
   private Integer id;
   /**
   * 停车场Id
   */
   private Integer parkingLotId;
   /**
   * 车位编号
   */
   private String spaceNumber;
   /**
    * 车位地址
    */
   private String address;
   /**
   * 车位类型
   */
   private String type;
   /**
   * 用户id
   */
   private Integer userId;
   /**
   * 收费价格/小时
   */
   private String price;

   /**
   * 月租价格
   */
   private String monthPrice;
   /**
   * 创建时间
   */
   private LocalDateTime createTime;
   /**
   * 状态
   */
   private String status;

   private String remake;

   private  String parkingLotName;

   private  String userName;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }
   public Integer getParkingLotId() {
      return parkingLotId;
   }

   public void setParkingLotId(Integer parkingLotId) {
      this.parkingLotId = parkingLotId;
   }

   public String getSpaceNumber() {
      return spaceNumber;
   }

   public void setSpaceNumber(String spaceNumber) {
      this.spaceNumber = spaceNumber;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   public String getPrice() {
      return price;
   }

   public void setPrice(String price) {
      this.price = price;
   }


   public String getMonthPrice() {
      return monthPrice;
   }

   public void setMonthPrice(String monthPrice) {
      this.monthPrice = monthPrice;
   }

   public LocalDateTime getCreateTime() {
      return createTime;
   }

   public void setCreateTime(LocalDateTime createTime) {
      this.createTime = createTime;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getParkingLotName() {
      return parkingLotName;
   }

   public void setParkingLotName(String parkingLotName) {
      this.parkingLotName = parkingLotName;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getRemake() {
      return remake;
   }

   public void setRemake(String remake) {
      this.remake = remake;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }
}
