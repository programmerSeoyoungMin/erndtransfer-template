<template>
  <div style="text-align: center;">
    <el-pagination
      :current-page="pager.currentPage"
      :page-size="pager.limit"
      layout="prev, pager, next"
      :total="pager.total"
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
  }
}
</script>

<style scoped>

</style>
