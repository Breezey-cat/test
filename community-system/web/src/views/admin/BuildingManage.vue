<template>
  <div>
    <el-space direction="vertical" alignment="left" style="width: 100%">
      <el-card>
        <el-form ref="searchFormComponents" :model="searchForm" inline>
          <el-form-item label="楼宇编号" prop="name">
            <el-input v-model="searchForm.code" clearable></el-input>
          </el-form-item>
          <el-form-item label="分类" prop="type">
            <el-select v-model="searchForm.type" placeholder="请选择" clearable filterable style="width: 150px">
              <el-option :label="item"  :value="item" :key="item" v-for="item in typeList"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="searchForm.status" placeholder="请选择" clearable filterable style="width: 150px">
              <el-option :label="item"  :value="item" :key="item" v-for="item in statusList"></el-option>
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
          <el-table-column show-overflow-tooltip  prop="code" label="楼宇编码"></el-table-column>
          <el-table-column show-overflow-tooltip  prop="type" label="分类"></el-table-column>
          <el-table-column show-overflow-tooltip  prop="address" label="楼宇地址"></el-table-column>
          <el-table-column show-overflow-tooltip  prop="number" label="楼层数"></el-table-column>
          <el-table-column show-overflow-tooltip  prop="elementNumber" label="单元数"></el-table-column>
          <el-table-column show-overflow-tooltip prop="houseNumber" label="户数"></el-table-column>typeList\statusList
          <el-table-column show-overflow-tooltip  prop="status" label="状态">
            <template #default="scope">
              <el-tag type="success" v-if="scope.row.status==='正常'">正常</el-tag>
              <el-tag type="danger" v-if="scope.row.status==='维修中'">维修中</el-tag>
              <el-tag type="danger" v-if="scope.row.status==='停用'">停用</el-tag>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip  prop="remark" label="备注"></el-table-column>
          <el-table-column show-overflow-tooltip width="180px" prop="createTime" label="创建时间"></el-table-column>
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
          <el-form-item label="楼宇编号" prop="code"  :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-input v-model="formData.code"></el-input>
          </el-form-item>
          <el-form-item label="楼宇地址" prop="address"  :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-input v-model="formData.address"></el-input>
          </el-form-item>
          <el-form-item label="楼层数" prop="number"  :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-input v-model="formData.number"></el-input>
          </el-form-item>
          <el-form-item label="单元数" prop="elementNumber"  :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-input v-model="formData.elementNumber"></el-input>
          </el-form-item>
          <el-form-item label="户数" prop="houseNumber"  :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-input type="number" v-model="formData.houseNumber"></el-input>
          </el-form-item>
          <el-form-item label="分类" prop="type" :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-radio-group v-model="formData.type">
              <el-radio :label="item" :key="item" v-for="item in typeList">{{ item }}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="formData.remark"></el-input>
          </el-form-item>
          <el-form-item label="状态" prop="status" :rules="[{required:true,message:'不能为空',trigger:[ 'blur','change']}]">
            <el-radio-group v-model="formData.status">
              <el-radio :label="item" :key="item" v-for="item in statusList">{{ item }}</el-radio>
            </el-radio-group>
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
  code:undefined,
  type:undefined,
  status:undefined
});

const  typeList=ref(['住宅楼','商业楼','商住两用楼'])
const  statusList=ref(['正常','维修中','停用'])

getPageList()

/**
 * 获取分页数据
 */
function getPageList() {
  let data = Object.assign(toRaw(searchForm.value), toRaw(pageInfo.value))
  request.get("/building/page", {
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
      request.post("/building/add", formData.value).then(res => {
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
      request.put("/building/update", formData.value).then(res => {
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
    request.delete("/building/delBatch", {data: ids}).then(res => {
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
