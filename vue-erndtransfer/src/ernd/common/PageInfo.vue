<template>
  <div>
    <div style="float: right; margin-right: 20px">
      <i class="el-icon-s-home" />
      <span v-show="parentName">
        <el-link :href="parentLink"> {{ parentName }} </el-link> >
      </span>
      <el-link :href="childLink"> {{ childName }} </el-link>
    </div>

    <div class="app-container">
      <span style="font-size:x-large; font-weight: bold">{{ childName }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PageInfo',
  props: {
    parentName: {
      type: String,
      default: ''
    },
    childName: {
      type: String,
      required: true,
      default: ''
    },
    parentLink: {
      type: String,
      default: '/#'
    },
    childLink: {
      type: String,
      default: '/#'
    }
  }
}
</script>

<style scoped>

</style>
