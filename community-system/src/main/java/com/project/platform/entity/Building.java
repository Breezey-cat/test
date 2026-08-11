package com.project.platform.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 楼宇信息
 */
public class Building  {
   /**
    * id
    */
   private Integer id;
   /**
   * 楼宇编号
   */
   private String code;
   /**
   * 楼宇名称
   */
   private String remark;
   /**
   * 楼宇地址
   */
   private String address;
   /**
   * 楼层数
   */
   private String number;
   /**
   * 单元数
   */
   private String elementNumber;
   /**
   * 户数
   */
   private Integer houseNumber;
   /**
   * 分类
   */
   private String type;
   /**
   * 状态
   */
   private String status;
   /**
   * 创建时间
   */
   private LocalDateTime createTime;

   /**
    * 状态
    */
   private List<House> houseList;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }
   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getRemark() {
      return remark;
   }

   public void setRemark(String name) {
      this.remark = name;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getNumber() {
      return number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   public String getElementNumber() {
      return elementNumber;
   }

   public void setElementNumber(String elementNumber) {
      this.elementNumber = elementNumber;
   }

   public Integer getHouseNumber() {
      return houseNumber;
   }

   public void setHouseNumber(Integer houseNumber) {
      this.houseNumber = houseNumber;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
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

   public List<House> getHouseList() {
      return houseList;
   }

   public void setHouseList(List<House> houseList) {
      this.houseList = houseList;
   }
}
