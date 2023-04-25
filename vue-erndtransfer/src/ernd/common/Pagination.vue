<template>

  <div style="text-align: center;">
    <el-pagination
      :current-page="pager.currentPage"
      :page-size="pager.limit"
      :pager-count="10"
      layout="prev, pager, next"
      :total="1000"
    />
  </div>
</template>

<script>
export default {
  name: 'Pagination',
  props: {
    pager: {
      type: Object,
      required: true,
      default: () => {
        return {
          currentPage: 1,
          limit: 10,
          total: 1
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
