<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from '../common/resize'
import Axios from 'axios'

export default {
  name: 'TrnsfPrcdTime',
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
      default: '350px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      chart: null,
      trnsfPrcdTimeData: []
    }
  },
  async mounted() {
    const selectData = await Axios.get('http://localhost:8080/dashBoard/retriveAvgTime')
    this.trnsfPrcdTimeData = selectData.data
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
      this.setOptions(this.chartData)
    },
    setOptions({ trnsfPrcdTimeData } = {}) {
      this.chart.setOption({
        title: {
          text: '이관프로시저별 평균 소요시간',
          x: '0',
          top: '0',
          left: '15',
          textStyle: {
            color: '#0C0C0CFF',
            fontSize: '15'
          }
        },
        xAxis: {
          data: ['과제', '연차과제', '기술', '보안', '계획서', '키워드', '비목', '협약', '기관', '연구인력'],
          boundaryGap: false,
          axisTick: {
            show: false
          }
        },
        grid: {
          top: 40,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          padding: [5, 5]
        },
        yAxis: {
          axisTick: {
            show: false
          }
        },
        legend: {
          data: ['이관시간(분)']
        },
        series: [{
          name: '이관시간(h)', itemStyle: {
            normal: {
              color: '#2688F5FF',
              lineStyle: {
                color: '#2688F5FF',
                width: 2
              }
            }
          },
          smooth: true,
          type: 'line',
          data: this.trnsfPrcdTimeData,
          animationDuration: 2600,
          animationEasing: 'cubicInOut'
        }]
      })
    }
  }
}
</script>

<style scoped>

</style>
