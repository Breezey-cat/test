<template>
  <div style="width: 75%;margin: 0 auto" :key="activeName">

    <el-card>
      <el-tabs v-model="activeName" tab-position="left">
        <el-tab-pane label="车位费管理" name="parkingFee">
          <ParkingFee v-if="activeName==='parkingFee'"></ParkingFee>
        </el-tab-pane>
        <el-tab-pane label="物业费管理" name="propertyFee">
          <PropertyFee v-if="activeName==='propertyFee'"></PropertyFee>
        </el-tab-pane>
        <el-tab-pane label="水电费管理" name="utilityBillFee">
          <UtilityBillFee v-if="activeName==='utilityBillFee'"></UtilityBillFee>
        </el-tab-pane>
        <el-tab-pane label="我的投诉列表" name="complaint">
          <Complaint v-if="activeName==='complaint'"></Complaint>
        </el-tab-pane>
        <el-tab-pane label="我的报修列表" name="repair">
          <Repair v-if="activeName==='repair'"></Repair>
        </el-tab-pane>
<!--        <el-tab-pane label="关注列表" name="followsItem">-->
<!--          <FollowsItem v-if="activeName==='followsItem'"></FollowsItem>-->
<!--        </el-tab-pane>-->
<!--        <el-tab-pane label="收藏记录" name="favoritesRecord">-->
<!--          <FavoritesRecord v-if="activeName==='favoritesRecord'"></FavoritesRecord>-->
<!--        </el-tab-pane>-->
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {useRoute} from "vue-router";
import ParkingFee from "@/views/front/personalCenter/ParkingFee.vue";
import PropertyFee from "@/views/front/personalCenter/PropertyFee.vue";
import Complaint from "@/views/front/personalCenter/Complaint.vue";
import Repair from "@/views/front/personalCenter/Repair.vue";
import UtilityBillFee from "@/views/front/personalCenter/UtilityBillFee.vue";

const activeName = ref('parkingFee')
const route = useRoute()
if (route.query.type) {
  activeName.value = route.query.type
}
</script>

<style>

</style>
