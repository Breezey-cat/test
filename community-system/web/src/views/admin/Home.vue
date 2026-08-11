<template>
  <div>
      <el-row :gutter="20" v-if="currentUser.type==='ADMIN'">
        <!--    小区入住情况-->
        <el-col :span="24">
          <el-card>
            <ResidentOccupancyRate/>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="10" v-if="currentUser.type==='ADMIN'" style="margin-top: 10px">
        <!--      停车情况占比-->
        <el-col :span="12">
          <el-card>
            <ParkingCondition/>
          </el-card>
        </el-col>
        <!--      物业费缴费占比-->
        <el-col :span="12">
          <el-card>
            <CollectionOfPropertyFee/>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="10" v-if="currentUser.type==='ADMIN'" style="margin-top: 10px">
        <!--      水电缴费占比-->
        <el-col :span="12">
          <el-card>
            <CollectionOfWaterCharge/>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card>
            <CollectionOfElectricityCharge/>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="20" v-if="currentUser.type==='ADMIN'" style="margin-top: 10px">
        <!--    维修情况占比-->
        <el-col :span="24">
          <el-card>
            <MaintenanceProcessingEfficiency/>
          </el-card>
        </el-col>
      </el-row>
  </div>
</template>
<script setup>
import {ref} from "vue";
import tools from "@/utils/tools.js";
import ResidentOccupancyRate from "@/views/admin/chat/ResidentOccupancyRate.vue";
import ParkingCondition from "@/views/admin/chat/ParkingCondition.vue";
import MaintenanceProcessingEfficiency from "@/views/admin/chat/MaintenanceProcessingEfficiency.vue";
import CollectionOfPropertyFee from "@/views/admin/chat/CollectionOfPropertyFee.vue";
import CollectionOfElectricityCharge from "@/views/admin/chat/CollectionOfElectricityCharge.vue";
import CollectionOfWaterCharge from "@/views/admin/chat/CollectionOfWaterCharge.vue";

const currentUser = ref(tools.getCurrentUser())
console.log(currentUser.value)
</script>
