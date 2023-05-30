<template>
  <div class="app-container">
    <el-table v-loading="true" header-align="center">
      <el-table-column v-for="header in headers" :key="header.key" :prop="header.key" :label="header.name" :width="header.width" :align="header.align" header-align="center" show-overflow-tooltip>
        <template v-slot="{row}">
          <div v-html="filterHtml(row, header.key, header.type)" />
        </template>
      </el-table-column>
    </el-table>
  </div>

</template>

<script>
export default {
  name: 'ErrorLogsVue',
  data() {
    return {
      headers: [{ key: 'trnsfSe', name: '이관구분', width: '80' },
        { key: 'sbjtStts', name: '차수', width: '125' },
        { key: 'erndSbjtNo', name: '발생업무', width: '300' },
        { key: 'erndFlfmtYy', name: '발생일시', width: '125' }
      ]
    }
  }
}
</script>

<style scoped>

</style>
