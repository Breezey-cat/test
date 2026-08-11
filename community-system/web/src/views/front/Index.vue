<template>
  <div class="home-container">
    <!-- 顶部公告栏 -->
    <el-row :gutter="20" class="top-row">
      <!-- 主公告区域 -->
      <el-col :span="16">
        <el-card class="main-announcement" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="header-title">小区公告</span>
              <el-button type="primary" link @click="router.push('/announcement/')">
                更多公告 <el-icon><ArrowRight /></el-icon>
              </el-button>
            </div>
          </template>
          <div class="announcement-content">
            <div class="main-notice" @click="viewDetail(currentAnnouncement.id)">
              <h3 class="notice-title">{{ currentAnnouncement.title }}</h3>
              <div class="notice-info">
                <span class="time">{{ currentAnnouncement.createTime}}</span>
              </div>
              <div class="notice-summary-container">
                <div class="notice-summary" v-html="currentAnnouncement.content"></div>
              </div>
            </div>
            <el-scrollbar class="notice-scrollbar">
            <div class="notice-list">
              <div v-for="(item,index) in announcementList"
                   :key="item.id"
                   class="notice-item"
                   v-show="index>0"
                   :class="{active: index < 3}"
                   @click="viewDetail(item.id)">
                <div class="item-index">0{{ index + 1 }}</div>
                <div class="item-content">
                  <span class="item-title">{{ item.title }}</span>
                  <span class="item-time">{{ item.createTime }}</span>
                </div>
              </div>
            </div>
            </el-scrollbar>
          </div>
        </el-card>
      </el-col>

      <!-- 论坛信息侧边栏 -->
      <el-col :span="8">
        <el-card class="forum-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="header-title">小区论坛</span>
              <el-button type="primary" link @click="router.push('/forum')">
                更多帖子 <el-icon><ArrowRight /></el-icon>
              </el-button>
            </div>
          </template>
          <el-scrollbar height="380px">
            <div class="forum-list">
              <div v-for="item in forumList"
                   :key="item.id"
                   class="forum-item"
                   @click="router.push('/forumDetails/'+item.id)">
                <div class="forum-content">
                  <div class="forum-header">
                    <span class="forum-title">{{ item.title }}</span>
                  </div>
                  <div class="forum-info">
                    <span class="time">{{ item.createTime }}</span>
                  </div>
                </div>
              </div>
            </div>
          </el-scrollbar>
        </el-card>
      </el-col>
    </el-row>

    <!-- 社区新闻板块 -->
    <el-row class="news-section">
      <el-card shadow="hover">
        <template #header>
          <div class="card-header">
            <span class="header-title">小区新闻</span>
            <el-button type="primary" link @click="router.push('/news')">
              更多新闻 <el-icon><ArrowRight /></el-icon>
            </el-button>
          </div>
        </template>
        <div class="news-grid">
          <div v-for="item in newsList"
               :key="item.id"
               class="news-card"
               @click="router.push('/newsDetails/'+item.id)">
            <el-image :src="item.image" class="news-cover" fit="cover">
              <template #error>
                <div class="image-error">图片加载失败</div>
              </template>
            </el-image>
            <div class="news-info">
              <h4 class="news-title">{{ item.title }}</h4>
              <div class="news-desc"  v-html="item.content"></div>
              <div class="news-footer">
                <span class="news-time">{{  item.createTime }}</span>
              </div>
            </div>
          </div>
        </div>
      </el-card>
    </el-row>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import request from "@/utils/http.js";
import router from "@/router/index.js";
import {ArrowRight} from "@element-plus/icons-vue";

// 公告数据
const announcementList = ref([])
const currentAnnouncement = computed(() => announcementList.value[0] || {})

// 论坛数据
const forumList = ref([])

// 新闻数据
const newsList = ref([])

// 查看详情方法
const viewDetail = (id) => {
  router.push(`/announcementDetails/${id}`)
}

const viewForum = (id) => {
  router.push(`/forumDetails/${id}`)
}
const viewNews = (id) => {
  router.push('/newsDetails/'+id)
}

// 数据获取方法
const fetchData =  () => {
  try {
    let data = {
      pageNum: 1,
      pageSize: 8
    }
    let announcementData = {
      pageNum: 1,
      pageSize: 3
    }
    // 获取公告数据
    const annRes = request.get('/announcement/page', {
      announcementData
    }).then(res => {
      announcementList.value = res.data.list
    })
    // 获取论坛数据
    const forumRes =  request.get('/forum/page', {
      data
    }).then(res => {
      forumList.value = res.data.list
    })
    // 获取新闻数据
    const newsRes =  request.get('/news/page', {
      data
    }).then(res => {
      newsList.value = res.data.list
    })
  } catch (error) {
    console.error('数据加载失败:', error)
  }
}
fetchData()
</script>

<style scoped>
.home-container {
  width: 85%;
  margin: 20px auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 20px;
}

.header-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--el-color-primary);
}

/* 主公告样式 */
.main-announcement {
  height: 480px;
  display: flex;
  flex-direction: column;
}

.announcement-content-wrapper {
  flex: 1;
  overflow: hidden;
  padding: 0 20px 20px 20px;
}

.announcement-content {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 30px;
  height: 100%;
}

.main-notice {
  background: linear-gradient(45deg, #409eff, #79bbff);
  color: white;
  padding: 30px;
  border-radius: 15px;
  cursor: pointer;
  transition: transform 0.3s;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.main-notice:hover {
  transform: translateY(-3px);
}

.notice-title {
  font-size: 22px;
  margin-bottom: 15px;
  word-break: break-word;
}

.notice-info {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 20px;
}
.notice-scrollbar {
  height: calc(100% - 10px);
}
.notice-summary-container {
  flex: 1;
  overflow: hidden;
  position: relative;
}
.notice-summary {
  font-size: 14px;
  line-height: 1.6;
  opacity: 0.9;
  max-height: 100%;
  overflow-y: auto;
  word-break: break-word;
  padding-right: 5px;
}

.notice-summary::-webkit-scrollbar {
  width: 4px;
}

.notice-summary::-webkit-scrollbar-thumb {
  background: rgba(255,255,255,0.3);
  border-radius: 2px;
}

.notice-summary >>> img {
  max-width: 100% !important;
  height: auto !important;
  display: block;
  margin: 10px auto;
}
.notice-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
  padding-right: 10px;
}

.notice-item {
  display: flex;
  align-items: center;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s;
}

.notice-item:hover {
  background: var(--el-color-primary-light-9);
}

.item-index {
  font-size: 24px;
  font-weight: bold;
  color: var(--el-color-primary);
  margin-right: 15px;
}

.item-content {
  flex: 1;
}

.item-title {
  display: block;
  font-size: 14px;
  margin-bottom: 5px;
}

.item-time {
  font-size: 12px;
  color: #666;
}

/* 论坛样式 */
.forum-card {
  height: 480px;
}

.forum-list {
  padding-right: 10px;
}

.forum-item {
  display: flex;
  gap: 15px;
  padding: 15px 0;
  border-bottom: 1px solid #eee;
  cursor: pointer;
  transition: background 0.3s;
}

.forum-item:hover {
  background: #f8f9fa;
}

.forum-content {
  flex: 1;
}

.forum-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 8px;
}

.forum-title {
  font-weight: 500;
  flex: 1;
}

.forum-info {
  display: flex;
  gap: 15px;
  font-size: 12px;
  color: #666;
}

.stats {
  display: flex;
  align-items: center;
  gap: 3px;
}

/* 新闻板块样式 */
.news-section {
  margin-top: 25px;
}

.news-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 25px;
}

.news-card {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0,0,0,0.08);
  cursor: pointer;
  transition: transform 0.3s;
}

.news-card:hover {
  transform: translateY(-5px);
}

.news-cover {
  width: 100%;
  height: 180px;
}

.news-info {
  padding: 15px;
}

.news-title {
  font-size: 16px;
  margin-bottom: 10px;
  line-height: 1.3;
}

.news-desc {
  font-size: 13px;
  color: #666;
  line-height: 1.5;
  margin-bottom: 12px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
  flex: 1;
  word-break: break-word;
}
.news-desc >>> img {
  max-width: 100% !important;
  height: auto !important;
  display: none;
}
.news-footer {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #999;
}

.image-error {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  color: #909399;
}
/* 响应式设计 */
@media (max-width: 1200px) {
  .announcement-content {
    grid-template-columns: 1fr;
  }

  .news-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 992px) {
  .news-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .home-container {
    width: 95%;
  }

  .news-grid {
    grid-template-columns: 1fr;
  }

  .main-notice {
    padding: 20px;
  }

  .notice-title {
    font-size: 18px;
  }
}
</style>
