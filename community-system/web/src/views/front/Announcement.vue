<template>
  <div class="announcement-container">
    <el-space direction="vertical" alignment="left" style="width: 100%">
      <el-card shadow="hover" class="search-card">
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
          :key="item.id"
          shadow="hover"
          class="announcement-card"
      >
        <template #header>
          <h3 class="announcement-title">{{ item.title }}</h3>
          <div class="user-details">
            <p class="created-at">{{ item.createTime }}</p>
          </div>
        </template>

        <div class="content-container">
          <div class="content" :class="{ 'collapsed': !item.showFullContent }" v-html="item.content"></div>
        </div>

        <template #footer>
          <div class="footer-actions">
            <el-button
                v-if="!item.showFullContent"
                @click="item.showFullContent = true"
                type="info"
                link
            >
              展开
            </el-button>
            <el-button link type="primary" @click="router.push('/announcementDetails/'+item.id)">查看详情</el-button>
          </div>
        </template>
      </el-card>

      <el-card class="pagination-card">
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
import {ElMessage, ElMessageBox} from "element-plus";
import router from "@/router/index.js";

const searchFormComponents = ref();
const tableComponents = ref();
const listData = ref([]);
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


getPageList()

/**
 * 获取分页数据
 */
function getPageList() {
  let data = Object.assign(toRaw(searchForm.value), toRaw(pageInfo.value))
  request.get("/announcement/page", {
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
    if (!valid) {
      ElMessage({
        message: "验证失败，请检查表单!",
        type: 'warning'
      });
      return
    }
    //新增
    if (!formData.value.id) {
      request.post("/announcement/add", formData.value).then(res => {
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
      request.put("/announcement/update", formData.value).then(res => {
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
    request.delete("/announcement/delBatch", {data: ids}).then(res => {
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
</script>

<style scoped>
.announcement-container {
  width: 70%;
  margin: 0 auto;
  padding: 20px 0;
}

.search-card {
  margin-bottom: 20px;
}

.announcement-card {
  margin-bottom: 20px;
  width: 100%;
}

.announcement-title {
  margin: 0;
  padding: 0;
  word-break: break-word;
}

.user-details {
  margin-top: 8px;
  color: #888;
  font-size: 14px;
}

.content-container {
  position: relative;
  overflow: hidden;
  padding-right: 10px; /* 为滚动条留出空间 */
}

.content {
  line-height: 1.6;
  word-break: break-word;
  overflow: hidden;
}

.content.collapsed {
  max-height: 150px;
  mask-image: linear-gradient(to bottom, black 60%, transparent 100%);
  -webkit-mask-image: linear-gradient(to bottom, black 60%, transparent 100%);
}

.content >>> img {
  max-width: 100%;
  height: auto;
  display: block;
  margin: 10px auto;
  border-radius: 4px;
}

.content >>> table {
  width: 100% !important;
  max-width: 100% !important;
  border-collapse: collapse;
}

.content >>> iframe {
  max-width: 100%;
}

.footer-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 10px;
  border-top: 1px solid #f0f0f0;
}

.pagination-card {
  margin-top: 20px;
}

/* 响应式设计 */
@media (max-width: 992px) {
  .announcement-container {
    width: 85%;
  }
}

@media (max-width: 768px) {
  .announcement-container {
    width: 95%;
    padding: 10px;
  }

  .content.collapsed {
    max-height: 120px;
  }
}
</style>
