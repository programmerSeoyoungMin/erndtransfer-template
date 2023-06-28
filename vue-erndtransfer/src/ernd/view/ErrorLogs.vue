<template>
  <div class="app-container">
    Error Logs
    <p>이관진행중 발생한 로그가 표시됩니다(최신순)</p>
    <el-table
      v-loading="false"
      header-align="center"
      :data="dataList"
    >
      <el-table-column v-for="header in headers" :key="header.key" :prop="header.key" :label="header.name" :width="header.width" :align="header.align" header-align="center" />
    </el-table>
  </div>

</template>

<script>
import Axios from 'axios'
export default {
  name: 'ErrorLogsVue',
  data() {
    return {
      headers: [{ key: 'trnsfSe', name: '이관구분', width: '100' },
        { key: 'trnsfId', name: '이관ID', width: '100' },
        { key: 'prcdNm', name: '발생업무', width: '200' },
        { key: 'regDtlDt', name: '발생일시', width: '200' }
      ],
      dataList: []
    }
  },
  mounted() {
    this.search()
  },
  methods: {
    search() {
      Axios.get('http://localhost:8080/dashBoard/retriveErrLogs')
        .then(response => {
          this.dataList = response.data
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>

</style>
