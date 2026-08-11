<template>
  <el-container class="admin-wrapper" style="height: 100%;">
    <el-aside width="200px" class="my-aside">
      <h3 class="title">智慧小区物业管理系统</h3>
      <el-menu
          style="width: 100%; overflow: hidden;"
          active-text-color="#409EFF"
          background-color="#000000"
          text-color="white"
          :default-active="useRoute().path"
          @select="handleMenuSelect"
          router>
        <el-menu-item index="/admin">
          <el-icon>
            <HomeOutlined/>
          </el-icon>
          <span>首页</span>
        </el-menu-item>

          <el-menu-item index="/admin/admin">
              <el-icon>
                  <Lock/>
              </el-icon>
              <span>管理员管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/user">
              <el-icon>
                  <User/>
              </el-icon>
              <span>用户管理</span>
          </el-menu-item>


        <el-menu-item index="/admin/announcement">
          <el-icon><Document /></el-icon>
          <span>公告管理</span>
        </el-menu-item>
        <el-sub-menu index="1">
          <template #title>
            <el-icon><Reading /></el-icon>
            <span>新闻管理</span>
          </template>
          <el-menu-item index="/admin/news">
            <el-icon><Memo /></el-icon>
            <span>新闻管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/newsComment">
            <el-icon><ChatLineSquare /></el-icon>
            <span>新闻评论管理</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <el-icon><Promotion /></el-icon>
            <span>论坛管理</span>
          </template>
          <el-menu-item index="/admin/forum">
            <el-icon><ChatLineSquare /></el-icon>
            <span>论坛管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/forumComment">
            <el-icon>
              <el-icon><ChatDotRound /></el-icon>
            </el-icon>
            <span>论坛评论管理</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title>
            <el-icon><OfficeBuilding /></el-icon>
            <span>房屋资产管理</span>
          </template>
          <el-menu-item index="/admin/building">
            <el-icon><School /></el-icon>
            <span>楼宇管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/house">
            <el-icon><HomeFilled /></el-icon>
            <span>房屋管理</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="4">
          <template #title>
            <el-icon><Van /></el-icon>
            <span>停车场管理</span>
          </template>
          <el-menu-item index="/admin/parkingLot">
            <el-icon><CaretRight /></el-icon>
            <span>停车场管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/parkingSpace">
            <el-icon><Location /></el-icon>
            <span>车位管理</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="5">
          <template #title>
            <el-icon><Menu /></el-icon>
            <span>收费管理</span>
          </template>
          <el-menu-item index="/admin/propertyFee">
            <el-icon><House /></el-icon>
            <span>物业费管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/parkingFee">
            <el-icon><Van /></el-icon>
            <span>车位费管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/utilityBillFee">
            <el-icon><Clock /></el-icon>
            <span>水电费管理</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="6">
          <template #title>
            <el-icon><Tools /></el-icon>
            <span>报修及投诉管理</span>
          </template>
          <el-menu-item index="/admin/repair">
            <el-icon><Setting /></el-icon>
            <span>报修管理</span>
          </el-menu-item>
          <el-menu-item index="/admin/complaint">
            <el-icon><QuestionFilled /></el-icon>
            <span>投诉管理</span>
          </el-menu-item>
        </el-sub-menu>

      </el-menu>
    </el-aside>
    <el-container>

      <el-header height="60px">
        <el-row :gutter="5">
          <el-col :span="6" style="margin-top: 20px;">
            <el-space>
              <router-link to="/admin">
                <HomeOutlined/>
              </router-link>
            </el-space>
          </el-col>
          <el-col :span="6">
          </el-col>
          <el-col :span="9">
          </el-col>
          <el-col :span="3">
            <div style="text-align: right;">
              <el-space style="margin-top: 15px;">
                <el-dropdown v-if="isUserLogin">
                  <div>
                    <el-space>
                      <el-avatar style="width: 30px;height: 30px;border-radius: 50%"
                                 shape="square" :size="30" :src="currentUser.avatarUrl"></el-avatar>
                      <span style="font-size: 16px">  {{ currentUser.username }}</span></el-space>
                  </div>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item><span @click="editCurrentUser">个人信息</span></el-dropdown-item>
                      <el-dropdown-item><span @click="editPassword">修改密码</span></el-dropdown-item>
                      <el-dropdown-item divided><span @click="logout">退出登录</span></el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>

              </el-space>
            </div>
          </el-col>
        </el-row>
      </el-header>
      <el-main style="background-color: RGB(245,245,245)" class="my-main">
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import tools from "@/utils/tools.js";
import {ref} from "vue";
import router from "@/router/index.js";
import {ElMessage} from "element-plus";
import {useRoute} from 'vue-router';
import {HomeOutlined} from "@ant-design/icons-vue";
import {User} from '@element-plus/icons-vue'
import "@/styles/admin.css";

const isUserLogin = ref(tools.isLogin())
const currentUser = ref(tools.getCurrentUser())
const activeIndex = ref(useRoute().path)

if (currentUser.value === null) {
  window.location.href = "/login"
}
if (currentUser.value && currentUser.value.type === 'USER') {
  router.push({path: "/"})
}

function handleMenuSelect(key, keyPath) {
  activeIndex.value = key
}


function logout() {
  ElMessage({
    message: '退出登录成功，正在跳转',
    type: 'success'
  });
  localStorage.clear()
  router.push({path: "/login"})
}

function editCurrentUser() {
  router.push({path: "/admin/editCurrentUser"})
}

function editPassword() {
  router.push({path: "/admin/editPassword"})
}


const isCollapse = ref(true)

</script>

<style scoped>
.title {
  color: white;
  width: 100%;
  text-align: center;
  margin: 15px 5px;
}
.my-main::-webkit-scrollbar {
  display: none; /* 隐藏滚动条 */
}
.my-aside {
  background-color: #000000;
  overflow-x: hidden;
}

.el-menu {
  border-right: 0px;
}
</style>

