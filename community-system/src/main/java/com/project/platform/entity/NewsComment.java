package com.project.platform.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 新闻评论信息
 */
public class NewsComment  {
   /**
    * id
    */
   private Integer id;
   /**
   * 新闻id
   */
   private Integer newsId;

   private  String newsTitle;
   /**
   * 用户id
   */
   private Integer userId;
   /**
   * 内容
   */
   private String content;
   /**
   * 父级id
   */
   private Integer parentId;
   /**
   * 修改时间
   */
   private LocalDateTime createTime;

   private User user;

   private String userName;

   private List<NewsComment> childList;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }
   public Integer getNewsId() {
      return newsId;
   }

   public void setNewsId(Integer newsId) {
      this.newsId = newsId;
   }

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public Integer getParentId() {
      return parentId;
   }

   public void setParentId(Integer parentId) {
      this.parentId = parentId;
   }

   public LocalDateTime getCreateTime() {
      return createTime;
   }

   public void setCreateTime(LocalDateTime createTime) {
      this.createTime = createTime;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public List<NewsComment> getChildList() {
      return childList;
   }

   public void setChildList(List<NewsComment> childList) {
      this.childList = childList;
   }

   public String getNewsTitle() {
      return newsTitle;
   }

   public void setNewsTitle(String newsTitle) {
      this.newsTitle = newsTitle;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }
}
