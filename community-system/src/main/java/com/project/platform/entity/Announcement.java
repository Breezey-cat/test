package com.project.platform.entity;

import java.time.LocalDateTime;
/**
 * 公告
 */
public class Announcement  {
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
   * 创建时间
   */
   private LocalDateTime createTime;

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




}
