<template>
  <div class="app-container">
    <div class="filter-container">
      <aside>
        <el-form>
          <div class="createPost-main-container">
            <el-row style="margin-top: 20px">
              <div class="postInfo-container">
                <slot name="body">
                  slot default
                </slot>
              </div>

              <el-button style="float: right;" type="primary" @click="$emit('onSearch')">
                <i class="el-icon-search" /> 검색
              </el-button>
            </el-row>
          </div>
        </el-form>
      </aside>
    </div>
  </div>
</template>

<script>

export default {
  name: 'SearchTable',
  components: {}
}
</script>

<style scoped>

</style>
