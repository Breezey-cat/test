<template>
  <div>
    <el-space direction="vertical" alignment="left" style="width: 100%">
      <el-card>
        <el-form ref="searchFormComponents" :model="searchForm" inline>
          <el-form-item label="楼宇编号" prop="buildingName"  >
            <el-input v-model="searchForm.buildingName" clearable style="width: 100px"></el-input>
          </el-form-item>
          <el-form-item label="房屋编号" prop="houseNumber">
            <el-input v-model="searchForm.houseNumber" clearable style="width: 100px"></el-input>
          </el-form-item>
          <el-form-item label="抄表人" prop="readBy">
            <el-input v-model="searchForm.readBy" clearable style="width: 100px"></el-input>
          </el-form-item>
          <el-form-item label="抄表类型" prop="status">
            <el-select v-model="searchForm.type" placeholder="请选择" clearable filterable style="width: 150px">
              <el-option :label="item"  :value="item" :key="item" v-for="item in typeList"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="">
            <el-button type="primary" :icon="Search" @click="search">搜索</el-button>
            <el-button :icon="Refresh" @click="resetSearch">重置</el-button>
          </el-form-item>
        </el-form>
        <el-space>
          <el-button type="primary" @click="add" :icon="Plus">新增</el-button>
          <el-button type="danger" :icon="Delete" @click="batchDelete(null)" :disabled="selectionRows.length<=0">批量删除</el-button>
        </el-space>
      </el-card>
      <el-card>
        <el-table ref="tableComponents"
                  :data="listData"
                  tooltip-effect="dark"
                  style="width: 100%"
                  @selection-change="selectionChange"
                  border>
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="id" label="ID" width="50"></el-table-column>
          <el-table-column prop="buildingName" label="楼宇编号"></el-table-column>
          <el-table-column prop="houseName" label="房屋编号"></el-table-column>
          <el-table-column prop="type" label="抄表类型"></el-table-column>
          <el-table-column prop="reading" label="读数"></el-table-column>
          <el-table-column prop="readingDate" label="抄表日期"></el-table-column>
          <el-table-column prop="fee" label="费用"></el-table-column>
          <el-table-column prop="readBy" label="抄表人"></el-table-column>
          <el-table-column prop="createTime" label="创建时间"></el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template #default="scope">
              <el-button :icon="Edit" @click="edit(scope.$index, scope.row)">编辑</el-button>
              <el-button :icon="Delete" type="danger" @click="deleteOne(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 20px">
          <el-pagination
                  @current-change="currentChange"
                  @size-change="sizeChange"
                  :page-size="pageInfo.pageSize"
                  :current-page="pageInfo.currentPage"
                  background
                  layout="total,sizes, prev, pager, next"
                  :total="pageInfo.total">
          </el-pagination>
        </div>
      </el-card>
    </el-space>
    <el-dialog
            v-model="dialogOpen"
            v-if="dialogOpen"
            :title="formData.id?'编辑':'新增'"
            width="500"
    >
      <el-form ref="formRef" :model="formData" label-width="100px">
        <slot name="content">
          <el-form-item label="楼宇编号" prop="buildingId" :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-select v-model="formData.buildingId" placeholder="请选择" clearable filterable @change="buildingChange" style="width: 150px">
              <el-option :label="item.code"  :value="item.id" :key="item" v-for="item in treelist"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="房屋编号" prop="houseId" :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-select v-model="formData.houseId" placeholder="请选择" clearable filterable style="width: 150px">
              <el-option :label="item.houseNumber"  :value="item.id" :key="item" v-for="item in houses"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="抄表类型" prop="type" :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-radio-group v-model="formData.type" :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
              <el-radio :label="item" :key="item" v-for="item in typeList">{{ item }}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="读数" prop="reading" :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]" >
            <el-input type="number" v-model="formData.reading"></el-input>
          </el-form-item>
          <el-form-item  label="抄表日期" prop="readingDate"  :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-date-picker
                v-model="formData.readingDate"
                type="date"
                placeholder="请选择日期"
                value-format="YYYY-MM-DD"
            />
          </el-form-item>
          <el-form-item label="费用" prop="fee"  :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-input type="number" v-model="formData.fee"></el-input>
          </el-form-item>
          <el-form-item label="抄表人" prop="readBy"  :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-input v-model="formData.readBy"></el-input>
          </el-form-item>
          
        </slot>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submit" :icon="Check">提交</el-button>
          <el-button @click="closeDialog" :icon="Close">取消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import request from "@/utils/http.js";
import {Check, Close, Delete, Edit, Refresh, Plus, Search} from '@element-plus/icons-vue'
import {ref, toRaw} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";

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
  
});
const treelist = ref([]);
const houses = ref([]);
const  readByList=ref([])
const  typeList=ref(['水费','电费'])

getTreelist()
getPageList()

/**
 * 获取分页数据
 */
function getPageList() {
  let data = Object.assign(toRaw(searchForm.value), toRaw(pageInfo.value))
  request.get("/meterReading/page", {
    params: data
  }).then(res => {
    listData.value = res.data.list
    pageInfo.value.total = res.data.total
  })
}

function getTreelist() {
  request.get("/house/treelist").then(res => {
    treelist.value = res.data
  })
}

function buildingChange(value) {
  // 根据楼宇ID找到对应的房屋列表
  request.get("/house/getById/"+ value).then(res => {
    houses.value = res.data
  })
  // treelist.value.forEach(building => {
  //   if (building.id === value) {
  //     houses.value = building.houseList;
  //   }else {
  //     houses.value = [];
  //   }
  // });
  // 重置房屋编号选择
  formData.houseId = null;
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
      // formData.readingDate = new Date(formData.readingDate)
      request.post("/meterReading/add", formData.value).then(res => {
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
      request.put("/meterReading/update", formData.value).then(res => {
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
    request.delete("/meterReading/delBatch", {data: ids}).then(res => {
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

</style>
