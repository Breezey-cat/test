package com.project.platform.entity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 新闻信息
 */
public class News  {
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
    * 封面图
    */
   private String image;

   /**
   * 创建时间
   */
   private LocalDateTime createTime;

   /**
    * 评论列表
    */
   private List<NewsComment> CommentsList;

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


   public LocalDateTime getCreateTime() {
      return createTime;
   }

   public void setCreateTime(LocalDateTime createTime) {
      this.createTime = createTime;
   }

   public List<NewsComment> getCommentsList() {
      return CommentsList;
   }

   public void setCommentsList(List<NewsComment> commentsList) {
      CommentsList = commentsList;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }
}
