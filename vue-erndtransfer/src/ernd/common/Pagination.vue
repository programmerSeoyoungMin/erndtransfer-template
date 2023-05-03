<template>
  <div style="text-align: center;">
    <el-pagination
      :current-page.sync="pager.currentPage"
      :page-size="pager.limit"
      layout="prev, pager, next"
      :total="pager.total"
      @current-change="$emit('onhandleCurrentChange')"
    />
  </div>
</template>

<script>
export default {
  name: 'Pagination',
  props: {
    pager: {
      type: Object,
      required: true
    }
  },
  // 2023.05.03. msy 페이지 변경 시 event binding 추가
  watch: {
    pager: {
      handler: function() {
        this.$emit('onhandleCurrentChange', this.pager.currentPage)
      },
      deep: true
    }
  }
}
</script>

<style scoped>

</style>
