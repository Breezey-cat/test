<template>
<!--  小区居民入住率-->
  <el-space direction="vertical" style="width: 100%" size="large">
    <div ref="chart" style="width: 600px; height: 345px;"></div>
  </el-space>
</template>

<script setup>
import {ref, onMounted, onBeforeUnmount} from 'vue';
import * as echarts from 'echarts';
import http from "@/utils/http.js";

const chart = ref(null);

onBeforeUnmount(() => {
  // 清理图表实例
  if (chart.value) {
    chart.value.dispose();
  }
});

// 指定图表的配置项和数据
var option = {
  title: {
    text: '居民入住情况',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ["已入住",'空置','出租'],
    right: 30,
    top: 30,
    textStyle: {
      color: '#666'
    }
  },
  xAxis: {
    type: 'category',
    data: [],
    axisLabel: {
      rotate: 45,  // 旋转45度
      interval: 0, // 强制显示所有标签
      width: 70,   // 设置标签宽度
      overflow: 'break' // 超出自动换行
    },
    axisTick: {
      alignWithLabel: true
    }
  },
  yAxis: [
    {
      type: 'value',
      name: '数量（户）',
      position: 'left',
      axisLine: {
        lineStyle: {
          color: '#5793f3'
        }
      },
      splitLine: {
        lineStyle: {
          type: 'dashed'
        }
      }
    },
    {
      type: 'value',
      position: 'right',
      axisLine: {
        lineStyle: {
          color: '#d14a61'
        }
      },
      splitLine: {
        show: false
      }
    }
  ],
  series: [
  ]
};

const initChart = () => {
  // 基于准备好的dom，初始化echarts实例
  const chartInstance = echarts.init(chart.value);


  // 使用刚指定的配置项和数据显示图表
  chartInstance.setOption(option);

  // 将 chart 实例绑定到 ref 上，以便在卸载时清理
  chart.value = chartInstance;
};

const day = ref(7)

getData()

function getData() {
  http.get("/echarts/residentOccupancyRate").then(res => {
    option.xAxis.data = res.data.xdata
    res.data.seriesData.forEach((item) => {
      item.type = 'bar'
      option.series.push(item)
    })
    option.title.text = '居民入住情况'

    initChart();
  })
}


</script>

<style scoped>
/* 可以添加样式 */
</style>