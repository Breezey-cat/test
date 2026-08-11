package com.project.platform.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 论坛评论信息
 */
public class ForumComment  {
   /**
    * id
    */
   private Integer id;
   /**
   * 论坛信息ID
   */
   private Integer forumId;

   private  String forumName;
   /**
   * 用户id
   */
   private Integer userId;
   /**
   * 内容
   */
   private String content;

   private String userName;
   /**
   * 父级id
   */
   private Integer parentId;
   /**
   * 修改时间
   */
   private LocalDateTime createTime;

   private User user;

   private List<ForumComment> childList;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }
   public Integer getForumId() {
      return forumId;
   }

   public void setForumId(Integer forumId) {
      this.forumId = forumId;
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

   public List<ForumComment> getChildList() {
      return childList;
   }

   public void setChildList(List<ForumComment> childList) {
      this.childList = childList;
   }

   public String getForumName() {
      return forumName;
   }

   public void setForumName(String forumName) {
      this.forumName = forumName;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }
}
