package com.project.platform.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 论坛信息
 */
public class Forum  {
   /**
    * id
    */
   private Integer id;
   /**
   * 标题
   */
   private String title;
   /**
   * 内容
   */
   private String content;
   /**
   * 用户id
   */
   private Integer userId;
   /**
   * 创建时间
   */
   private LocalDateTime createTime;
   /**
   * 状态
   */
   private String status;

   /**
    * 用户名称
    */
   private String username;

   /**
    * 用户头像
    */
   private String userAvatarUrl;

   /**
    * 评论列表
    */
   private List<ForumComment> CommentsList;


   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }
   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public Integer getUserId() {
      return userId;
   }

   public void setUserId(Integer userId) {
      this.userId = userId;
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

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getUserAvatarUrl() {
      return userAvatarUrl;
   }

   public void setUserAvatarUrl(String userAvatarUrl) {
      this.userAvatarUrl = userAvatarUrl;
   }

   public List<ForumComment> getCommentsList() {
      return CommentsList;
   }

   public void setCommentsList(List<ForumComment> commentsList) {
      CommentsList = commentsList;
   }
}
