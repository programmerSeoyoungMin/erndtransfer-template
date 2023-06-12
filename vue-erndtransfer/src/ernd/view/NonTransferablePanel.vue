<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from '../common/resize'

const animationDuration = 300

export default {
  name: 'NonTransferablePanel',
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '400px'
    }
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')

      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 축 표시기, 축 트리거 활성
            type: 'shadow' // 기본값은 직선이며 선택 사항입니다
          }
        },
        grid: {
          top: 10,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          data: ['사업', '과제', '비목', '기관', '인력', '기타'],
          axisTick: {
            alignWithLabel: true
          }
        }],
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          }
        }],
        series: [{
          name: '미매핑',
          type: 'bar',
          stack: 'vistors',
          barWidth: '50%',
          data: [79, 52, 200, 334, 390, 330],
          animationDuration
        }, {
          name: 'CASE1',
          type: 'bar',
          stack: 'vistors',
          barWidth: '50%',
          data: [80, 52, 200, 334, 390, 330],
          animationDuration
        }, {
          name: 'CASE2',
          type: 'bar',
          stack: 'vistors',
          barWidth: '50%',
          data: [30, 52, 200, 334, 390, 330],
          animationDuration
        }, {
          name: 'CASE3',
          type: 'bar',
          stack: 'vistors',
          barWidth: '50%',
          data: [52, 200, 334, 30, 390, 330],
          animationDuration
        }, {
          name: 'CASE4',
          type: 'bar',
          stack: 'vistors',
          barWidth: '50%',
          data: [80, 52, 200, 334, 390, 330],
          animationDuration
        }
        ]
      })
    }
  }
}
</script>

<style scoped>
</style>
