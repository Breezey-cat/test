<template>
  <div style="width: 60%;margin: 0 auto">
    <el-card shadow="always">
      <el-space direction="vertical" alignment="left" style="width: 100%">
        <div style="text-align: center"><h2 style="font-size: 25px">{{ info.title }}</h2></div>
        <div style="text-align: center">
          <el-space style="color: #999999;font-size: 14px">
            <span>发布时间：{{ info.createTime }}</span>
          </el-space>
        </div>
        <div style="padding: 0px 15px" class="content-container">
          <div v-html="info.content" class="content-html"></div>
        </div>
      </el-space>
    </el-card>
  </div>
</template>
<script setup>
import http from "@/utils/http.js";
import {ref, watch} from "vue";
import {useRoute} from "vue-router";
import {View, User} from "@element-plus/icons-vue";
import {FieldTimeOutlined} from "@ant-design/icons-vue";
import router from "@/router/index.js";

const route = useRoute()
const id = ref(route.params.id)
const info = ref({});
getInfo()

function getInfo() {
  http.get("/announcement/selectById/" + id.value,).then(res => {
    info.value = res.data;
    // 处理内容中的图片
    setTimeout(fixImages, 100); // 延迟执行确保DOM已渲染
  })
}
// 修复图片显示问题
function fixImages() {
  const container = document.querySelector('.content-container');
  if (!container) return;

  const images = container.querySelectorAll('img');
  images.forEach(img => {
    // 添加图片样式
    img.style.maxWidth = '100%';
    img.style.height = 'auto';
    img.style.display = 'block';
    img.style.margin = '10px auto';

    // 处理点击图片预览
    img.style.cursor = 'pointer';
    img.addEventListener('click', () => {
      previewImage(img.src);
    });
  });
}

// 图片预览功能
function previewImage(src) {
  const image = new Image();
  image.src = src;

  const viewer = document.createElement('div');
  viewer.style.position = 'fixed';
  viewer.style.top = '0';
  viewer.style.left = '0';
  viewer.style.width = '100%';
  viewer.style.height = '100%';
  viewer.style.backgroundColor = 'rgba(0,0,0,0.8)';
  viewer.style.display = 'flex';
  viewer.style.justifyContent = 'center';
  viewer.style.alignItems = 'center';
  viewer.style.zIndex = '9999';
  viewer.style.cursor = 'zoom-out';

  const img = new Image();
  img.src = src;
  img.style.maxWidth = '90%';
  img.style.maxHeight = '90%';
  img.style.objectFit = 'contain';

  viewer.appendChild(img);
  document.body.appendChild(viewer);

  viewer.addEventListener('click', () => {
    document.body.removeChild(viewer);
  });
}

// 监听内容变化
watch(() => info.value.content, () => {
  setTimeout(fixImages, 100);
});
</script>
<style scoped>
.content-container {
  width: 100%;
  overflow: hidden;
}

.content-html {
  word-break: break-word;
}

.content-html >>> img {
  max-width: 100%;
  height: auto;
  display: block;
  margin: 10px auto;
  cursor: pointer;
}

/* 响应式调整 */
@media screen and (max-width: 768px) {
  .content-container {
    width: 100%;
    padding: 0 5px;
  }

  .content-html >>> img {
    max-width: 100%;
  }
}
</style>