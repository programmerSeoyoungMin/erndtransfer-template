<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from '../common/resize'
import Axios from 'axios'

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
      chart: null,
      noMapping: [],
      nonTransfer: [],
      noPreData: []
    }
  },
  async mounted() {
    // 메인패널 정보 표기
    const noMappingRes = await Axios.post('http://localhost:8080/dashBoard/retriveNonTransferablePanelNoMapping', '')
    const arrNoMapping = []
    Object.keys(noMappingRes.data).forEach(function(v) {
      arrNoMapping.push(noMappingRes.data[v])
    })
    this.noMapping = arrNoMapping

    const nonTransferRes = await Axios.post('http://localhost:8080/dashBoard/retriveNonTransferablePanelNonTransfer', '')
    const arrNonTransfer = []
    Object.keys(nonTransferRes.data).forEach(function(v) {
      arrNonTransfer.push(nonTransferRes.data[v])
    })
    this.nonTransfer = arrNonTransfer

    const noPreDataRes = await Axios.post('http://localhost:8080/dashBoard/retriveNonTransferablePanelNoPreData', '')
    const arrNoPreData = []
    Object.keys(noPreDataRes.data).forEach(function(v) {
      arrNoPreData.push(noPreDataRes.data[v])
    })
    this.noPreData = arrNoPreData

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
        title: {
          text: '정제데이터 이관불가 현황',
          x: '0',
          top: '0',
          left: '15',
          textStyle: {
            color: '#0C0C0CFF',
            fontSize: '15'
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 축 표시기, 축 트리거 활성
            type: 'shadow' // 기본값은 직선이며 선택 사항입니다
          }
        },
        grid: {
          top: 40,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          data: ['사업', '공고', '과제', '비목'],
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
          barWidth: '60%',
          data: this.noMapping,
          animationDuration
        }, {
          name: '미이관',
          type: 'bar',
          stack: 'vistors',
          barWidth: '60%',
          data: this.nonTransfer,
          animationDuration
        }, {
          name: '선행데이터 미존재',
          type: 'bar',
          stack: 'vistors',
          barWidth: '60%',
          data: this.noPreData,
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
