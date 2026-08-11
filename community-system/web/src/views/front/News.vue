<template>
  <div style="width: 70%;margin: 0 auto">
    <el-space direction="vertical" alignment="left" style="width: 100%">
      <el-card shadow="hover">
        <el-space style="flex-wrap: wrap; gap: 15px;">
          <el-input
              v-model="searchForm.title"
              placeholder="请输入关键词搜索"
              style="width: 300px;"
              clearable
          />
          <el-button type="primary" :icon="Search" @click="search">搜索</el-button>
        </el-space>
      </el-card>
      <el-card
          v-for="(item,index) in listData"
          shadow="hover"
      >
        <template #header>
          <H3>{{ item.title }}</H3>
          <br>
          <div class="user-details">
            <p class="created-at">{{ item.createTime }}</p>
          </div>

        </template>
        <div class="content" :style="{ height: item.showFullContent ? 'auto' : '150px' }">
          <span v-html="item.content"></span>
          <div v-if="item.image">
            <img :src="item.image" alt="Content Image" style="max-width:100%;height:auto;">
          </div>
        </div>
        <template #footer>
          <el-button
              v-if="!item.showFullContent"
              @click="item.showFullContent = true"
              type="info"
              link
          >
            展开
          </el-button>
          <el-button link type="plan" @click="router.push('/newsDetails/'+item.id)">查看详情</el-button>
          <!--评论-->
          <el-button size="mini" type="info" :icon="ChatDotSquare" circle
                     @click="toggleChat(item)" class="small-chat-left"/>
          <!-- 评论输入框和列表的容器 -->
          <div v-if="item.isCommentsVisible" class="comments-container">
            <!-- 评论输入框 -->
            <el-input
                v-model="newComment"
                placeholder="输入评论..."
                @keyup.enter="addComment(item)"
                class="comment-input"></el-input>
            <div class="comment-actions">
              <!--评论发送按钮-->
              <el-button
                  type="primary"
                  @click="addComment(item)"
                  class="comment-send-button"
              >
                发送
              </el-button>
            </div>
            <!-- 评论列表 -->
            <ul class="comment-list">
              <li v-for="comment in item.commentsList" :key="comment.id" class="comment-item">
                <!-- 评论内容 -->
                <div class="comment-content-pl">
                  <div class="comment-avatar">
                    <el-avatar :src="comment.user.avatarUrl" class="user-avatar comment-user-avatar"></el-avatar>
                  </div>
                  <div class="comment-info">
                    <div class="comment-username-wrapper">
                      <h3 class="comment-username">{{ comment.user.username }}</h3>
                    </div>
                    <p class="comment-text">{{ comment.content }}</p>
                    <div class="comment-metadata">
                      <span class="comment-timestamp">{{ comment.createTime }}</span>
                      <!-- 回复按钮 -->
                      <el-button v-if="comment.id" @click="toggleReply(comment,item)" class="reply-button">
                        {{ comment.showReply ? '取消回复' : '回复' }}
                      </el-button>
                      <!-- 展开/收起按钮 -->
                      <el-button
                          v-if="comment.childList && comment.childList.length"
                          @click="toggleReplies(comment)"
                          class="toggle-replies-button"
                          type="info"
                          link
                      >
                        {{ comment.expanded ? '收起' : '展开' }}
                      </el-button>
                    </div>
                  </div>
                </div>
                <!-- 回复输入框 -->
                <div v-if="comment.showReply" class="reply-input">
                  <el-input
                      v-model="comment.replyText"
                      placeholder="输入回复..."
                      @keyup.enter="addReply(comment,item)"
                      class="reply-input-field"
                  ></el-input>
                  <el-button type="primary" @click="addReply(comment,item)" class="comment-sendReply-button">发送
                  </el-button>
                </div>
                <!-- 子评论列表 -->
                <ul v-if="comment.showReplies&&comment.childList && comment.childList.length" class="replies-list">
                  <li v-for="reply in comment.childList" :key="reply.id" class="reply-item">
                    <div class="reply-content-pl">
                      <div class="reply-line"></div>
                      <div class="reply-info">
                        <div class="reply-username-wrapper">
                          <h4 class="reply-username">{{ reply.user.username }}</h4>
                        </div>
                        <p class="reply-text">{{ reply.content }}</p>
                        <div class="reply-metadata">
                          <span class="reply-timestamp">{{ reply.createTime }}</span>
                        </div>
                      </div>
                    </div>
                  </li>
                </ul>
              </li>
            </ul>
          </div>
        </template>
      </el-card>
      <el-card>
        <el-pagination
            @current-change="currentChange"
            @size-change="sizeChange"
            :page-size="pageInfo.pageSize"
            :current-page="pageInfo.currentPage"
            background
            layout="total,sizes, prev, pager, next"
            :total="pageInfo.total">
        </el-pagination>
      </el-card>
    </el-space>
  </div>
</template>

<script setup>
import request from "@/utils/http.js";
import {Check, Close, Delete, Edit, Refresh, Plus, Search, ChatDotSquare} from '@element-plus/icons-vue'
import {ref, toRaw} from "vue";
import tools from "@/utils/tools.js";
import router from "@/router/index.js";
import {ElMessage, ElMessageBox} from "element-plus";

const searchFormComponents = ref();
const tableComponents = ref();
const listData = ref([]);
const commentsData = ref([]);
const pageInfo = ref({
  //当前页
  pageNum: 1,
  //分页大小
  pageSize: 10,
  //总条数
  total: 0
});
const searchForm = ref({
  title: undefined,
  content: undefined,
  
});

const userInfo = tools.getCurrentUser()
const newComment = ref("");


getPageList()

/**
 * 获取分页数据
 */
function getPageList() {
  let data = Object.assign(toRaw(searchForm.value), toRaw(pageInfo.value))
  request.get("/news/page", {
    params: data
  }).then(res => {
    listData.value = res.data.list
    pageInfo.value.total = res.data.total
  })
}

/**
 * 选择分页
 * @param e
 */
function currentChange(e) {
  pageInfo.value.pageNum = e
  getPageList()
}

/**
 * 改变分页数量
 * @param e
 */
function sizeChange(e) {
  pageInfo.value.pageSize = e
  getPageList()
  console.log(e)
}

/**
 * 搜索
 */
function search() {
  pageInfo.value.pageNum = 1
  getPageList()
}

/**
 * 重置搜索框
 */
function resetSearch() {
  searchFormComponents.value.resetFields();
  getPageList()
}

const dialogOpen = ref(false);
const formData = ref({});
const formRef = ref();

/**
 * 新增
 */
function add() {
  formData.value = {}
  dialogOpen.value = true
}
/**
 * 编辑
 * @param index
 * @param row
 */
function edit(index, row) {
  formData.value = Object.assign({}, row)
  dialogOpen.value = true
}
/**
 * 关闭弹框
 */
function closeDialog() {
  dialogOpen.value = false
}

/**
 * 提交数据
 */
function submit() {
  formRef.value.validate((valid) => {
    if (!valid){
      ElMessage({
        message: "验证失败，请检查表单!",
        type: 'warning'
      });
      return
    }
    //新增
    if (!formData.value.id) {
      request.post("/news/add", formData.value).then(res => {
        if (!res) {
          return
        }
        dialogOpen.value = false
        ElMessage({
          message: "操作成功",
          type: 'success'
        });
        getPageList()
      })
    } else {
      //更新
      request.put("/news/update", formData.value).then(res => {
        if (!res) {
          return
        }
        dialogOpen.value = false
        ElMessage({
          message: "操作成功",
          type: 'success'
        });
        getPageList()
      })
    }
  })
}
const selectionRows = ref([]);

/**
 * 多选
 * @param rows
 */
function selectionChange(rows) {
  selectionRows.value = rows
}

/**
 * 单个删除
 * @param index
 * @param row
 */
function deleteOne(index, row) {
  batchDelete([row])
}

/**
 * 批量删除
 * @param rows
 */
function batchDelete(rows) {
  if (!rows) {
    rows = selectionRows.value;
  }
  let ids = rows.map(item => item.id);
  ElMessageBox.confirm(`此操作将永久删除ID为[${ids}]的数据, 是否继续?`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
    center: true
  }).then(() => {
    request.delete("/news/delBatch", {data: ids}).then(res => {
      if (!res) {
        return
      }
      ElMessage({
        message: "操作成功",
        type: 'success'
      });
      getPageList()
    })
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '已取消删除'
    });
    tableComponents.value.clearSelection();
  });
}
/**
 * 评论点击
 * @param rows
 */
function toggleChat(item) {
  if (item.isCommentsVisible) {
    item.isCommentsVisible = !item.isCommentsVisible;
  } else {
    item.isCommentsVisible = true;
  }
  request.get("/newsComment/selectByNewsId/" + item.id).then(res => {
    if (res && res.data.length > 0) {
      commentsData.value = res.data;
    }
  })
}

/**
 * 评论发送
 * @param rows
 */
function addComment(item) {
  const user = tools.getCurrentUser();
  const newCommentContent = newComment.value;
  formData.value = {
    newsId: item.id,
    content: newComment
  }
  request.post("/newsComment/add", formData.value).then(res => {
    if (!res) {
      return
    }
    // 更新评论区列表
    listData.value = listData.value.map(i => {
      if (i.id === item.id) {
        let comments = {
          newsId: item.id,
          userId: user.userId,
          content: newCommentContent,
          createTime: new Date().toLocaleString().replace(/\//g, '-'),
          user: user,
        }
        if (i.commentsList === null) {
          i.commentsList = [comments];
        } else {
          i.commentsList.unshift(comments);
        }
        return {
          ...i
        };
      }
      return i;
    });
    dialogOpen.value = false
    ElMessage({
      type: 'success'
    });
  })
  // 清空输入框
  newComment.value = "";
}

/**
 * 回复取消回复事件
 * @param rows
 */
function toggleReply(comment, item) {
  if (comment.showReply) {
    comment.showReply = !comment.showReply;
  } else {
    comment.showReply = true;
  }
  // 清空输入框
  comment.replyText = "";
}

/**
 * 回复发送
 * @param rows
 */
function addReply(comment, item) {
  const user = tools.getCurrentUser();
  const newReplyText = comment.replyText;
  formData.value = {
    newsId: item.id,
    content: newReplyText,
    parentId: comment.id
  }
  request.post("/newsComment/add", formData.value).then(res => {
    if (!res) {
      return
    }
    // 更新评论区列表
    listData.value = listData.value.map(i => {
      if (i.id === item.id) {
        i.commentsList.map(j => {
          if (j.id === comment.id) {
            let child = {
              newsId: item.id,
              userId: user.userId,
              content: newReplyText,
              parentId: comment.id,
              createTime: new Date().toLocaleString().replace(/\//g, '-'),
              user: user,
            }
            if (j.childList == null) {
              j.childList = [child];
            } else {
              j.childList.unshift(child);
            }
            return {
              ...j
            };
          }
        })
        return {
          ...i
        };
      }
      return i;
    });
    dialogOpen.value = false
    ElMessage({
      type: 'success'
    });
  })
  // 清空输入框
  comment.replyText = "";
}

/**
 * 子评论展开收起
 * @param rows
 */
function toggleReplies(comment) {
  if (comment.showReplies) {
    comment.showReplies = !comment.showReplies;
  } else {
    comment.showReplies = true;
  }
}
</script>

<style scoped>

.user-details {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0;
}

.user-left {
  display: flex;
  align-items: center;
}

.user-avatar {
  margin-right: 10px;
}

.username {
  font-size: 16px;
  font-weight: bold;
  margin: 0;
}

.created-at {
  margin: 0;
  font-size: 14px;
  color: #888;
  white-space: nowrap;
}

.content {
  overflow: hidden;
  transition: height 0.3s;
  padding: 0 16px 16px;
}

.small-margin-left {
  margin-left: 20px;
  padding: 5px 20px;
  font-size: 12px;
}

.small-message-left {
  margin-left: 20px;
}

.small-chat-left {
  margin-left: 20px;
}

.small-liked-right {
  margin-right: 5px;
}

.small-starred-right {
  margin-left: 20px;
  margin-right: 5px;
}

.pointer-count, .star-count {
  font-size: 12px;
  color: #666;
  margin-left: 5px;
}

.comments-container {
  margin-top: 20px;
}

.comment-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 5px;
}

/* 设置评论区容器高度，超出进行滑动条查看*/
.comment-list {
  list-style-type: none;
  max-height: 300px; /* 设置你想要的最大高度 */
  overflow-y: auto; /* 当内容超出时显示垂直滚动条 */
  padding-right: 15px; /* 可选：为滚动条预留空间，防止内容被遮挡 */
  box-sizing: border-box; /* 确保padding不会增加元素的总高度 */
}

/* 一级评论渲染相关布局设置*/
.comment-content-pl {
  display: flex;
  align-items: flex-start; /* 确保内容从顶部开始对齐 */
  margin-bottom: 8px; /* 每个评论之间的间距 */
}

.comment-avatar {
  margin-right: 8px; /* 头像和评论信息之间的间距 */
}

.comment-user-avatar {
  width: 30px; /* 头像大小 */
  height: 30px; /* 头像大小 */
}

.comment-info {
  flex: 1; /* 占据剩余空间 */
}

.comment-username-wrapper {
  margin-bottom: 4px; /* 用户名和内容之间的间距 */
}

.comment-username {
  font-size: 14px; /* 用户名字体大小 */
  font-weight: bold; /* 用户名加粗 */
  margin: 0; /* 去除默认 margin */
}

.comment-text {
  font-size: 10px; /* 内容字体大小 */
  line-height: 1.5; /* 行高 */
  margin: 0 0 8px 0; /* 内容和元数据之间的间距 */
  word-wrap: break-word; /* 自动换行 */
  white-space: pre-wrap; /* 保留空白符，同时自动换行 */
}

.comment-metadata {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
  gap: 8px; /* 时间戳和回复按钮之间的间距 */
}

.comment-timestamp {
  font-size: 12px; /* 时间戳字体大小 */
  color: #999; /* 时间戳颜色 */
}

/* 二级评论回复按钮、输入框相关布局设置*/
.reply-button {
  background: none;
  border: none;
  color: #1da1f2; /* 回复按钮颜色 */
  cursor: pointer;
  font-size: 12px; /* 回复按钮字体大小 */
  padding: 0; /* 去除默认 padding */
}

.replies-list {
  list-style-type: none;
  max-height: 150px; /* 设置你想要的最大高度 */
  overflow-y: auto; /* 当内容超出时显示垂直滚动条 */
  padding-right: 15px; /* 可选：为滚动条预留空间，防止内容被遮挡 */
  box-sizing: border-box; /* 确保padding不会增加元素的总高度 */
}

.reply-input {
  display: flex; /* 启用Flexbox布局 */
  align-items: center; /* 垂直方向居中对齐 */
  margin-bottom: 5px;
  margin-left: 50px;
}

.reply-input-field {
  flex: 1; /* 输入框占据剩余空间 */
  margin-right: 10px; /* 输入框与按钮之间的间距 */
}

/* 二级评论渲染相关布局设置*/
replies-list {
  list-style: none;
}

.reply-item {
  margin-bottom: 12px;
}

.reply-content-pl {
  display: flex;
  align-items: flex-start;
  position: relative;
}

/* 二级评论左侧的竖线 */
.reply-line {
  position: absolute;
  left: 50px; /* 调整竖线位置 */
  top: 0;
  bottom: 0;
  width: 2px;
  background-color: #ddd; /* 竖线颜色 */
}

.reply-info {
  flex: 1;
  margin-left: 55px;
}

.reply-username {
  font-size: 13px;
  font-weight: bold;
  margin: 0 0 4px 0;
}

.reply-text {
  font-size: 13px;
  line-height: 1.5;
  margin: 0 0 8px 0;
  word-wrap: break-word;
  white-space: pre-wrap;
}

.reply-metadata {
  display: flex;
  align-items: center;
  gap: 8px;
}

.reply-timestamp {
  font-size: 12px;
  color: #999;
}

.comment-sendReply-button {
  flex-shrink: 0;
}
</style>
