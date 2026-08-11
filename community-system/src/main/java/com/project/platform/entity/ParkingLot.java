package com.project.platform.entity;

import java.time.LocalDateTime;
/**
 * 停车场信息
 */
public class ParkingLot  {
   /**
    * id
    */
   private Integer id;
   /**
   * 停车场名称
   */
   private String name;
   /**
   * 停车场地址
   */
   private String address;
   /**
   * 车位数量
   */
   private Integer parkingNum;
   /**
   * 停车场状态
   */
   private String status;
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
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public Integer getParkingNum() {
      return parkingNum;
   }

   public void setParkingNum(Integer parkingNum) {
      this.parkingNum = parkingNum;
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
