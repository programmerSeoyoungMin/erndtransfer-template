<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import resize from '../common/resize'
import echarts from 'echarts'
import Axios from 'axios'
require('echarts/theme/macarons') // echarts theme
export default {

  name: 'DataMappingRto',
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
    }
  },
  data() {
    return {
      dataMappingRtoData: []
    }
  },
  async mounted() {
    const selectData = await Axios.get('http://localhost:8080/dashBoard/retriveDataMappingRto')
    this.dataMappingRtoData = selectData.data
    this.initChart()
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      const xData = ['사업', '공고', '과제', '비목', '기관', '인력']
      this.chart.setOption({
        title: {
          text: '정제데이터 매핑율',
          x: '20',
          top: '40',
          textStyle: {
            color: '#0C0C0CFF',
            fontSize: '15'
          },
          subtextStyle: {
            color: '#90979c',
            fontSize: '10'
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            textStyle: {
              color: '#fff'
            }
          }
        },
        grid: {
          left: '10%',
          right: '5%',
          borderWidth: 10,
          top: 120,
          bottom: 40,
          textStyle: {
            color: '#fff'
          }
        },
        legend: {
          x: '5%',
          top: '10%',
          textStyle: {
            color: '#90979c'
          },
          data: ['Rto']
        },
        calculable: true,
        xAxis: [{
          type: 'category',
          axisLine: {
            lineStyle: {
              color: '#90979c'
            }
          },
          splitLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          splitArea: {
            show: false
          },
          axisLabel: {
            interval: 0

          },
          data: xData
        }],
        yAxis: [{
          type: 'value',
          splitLine: {
            show: false
          },
          axisLine: {
            lineStyle: {
              color: '#90979c'
            }
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            interval: 0
          },
          splitArea: {
            show: false
          }
        }],
        series: [{
          name: '비율',
          type: 'bar',
          stack: 'total',
          barMaxWidth: 30,
          barGap: '30%',
          itemStyle: {
            normal: {
              color: ['rgba(255,144,128,1)'],
              label: {
                show: true,
                textStyle: {
                  color: 'black'
                },
                position: 'insideTop',
                formatter(p) {
                  return p.value > 0 ? p.value : ''
                }
              }
            }
          },
          data: this.dataMappingRtoData
        }
        ]
      })
    }
  }
}
</script>

<style scoped>

</style>
