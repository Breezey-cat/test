package com.project.platform.entity;

import java.time.LocalDateTime;
/**
 * 用户
 */
public class User  {
   /**
    * id
    */
   private Integer id;
   /**
   * 用户名
   */
   private String username;
   /**
   * 昵称
   */
   private String nickname;
   /**
   * 密码
   */
   private String password;
   /**
   * 头像
   */
   private String avatarUrl;
   /**
   *  电话
   */
   private String tel;
   /**
   * 邮箱
   */
   private String email;
   /**
   * 余额
   */
   private Float balance;
   /**
   * 状态
   */
   private String status;
   /**
   * 创建时间
   */
   private LocalDateTime createdTime;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }
   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getNickname() {
      return nickname;
   }

   public void setNickname(String nickname) {
      this.nickname = nickname;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getAvatarUrl() {
      return avatarUrl;
   }

   public void setAvatarUrl(String avatarUrl) {
      this.avatarUrl = avatarUrl;
   }

   public String getTel() {
      return tel;
   }

   public void setTel(String tel) {
      this.tel = tel;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Float getBalance() {
      return balance;
   }

   public void setBalance(Float balance) {
      this.balance = balance;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public LocalDateTime getCreatedTime() {
      return createdTime;
   }

   public void setCreatedTime(LocalDateTime createdTime) {
      this.createdTime = createdTime;
   }


}
