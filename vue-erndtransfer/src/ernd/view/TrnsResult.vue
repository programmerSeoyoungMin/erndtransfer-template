<template>
  <div id="main" class="app-container">
    <el-button type="primary" @click="showModal = true">Show Modal</el-button>
    <TrnsResultPopUp
      v-if="showModal"
      @close="showModal = false"
    />
  </div>
</template>

<script>
import TrnsResultPopUp from '@/ernd/common/TrnsResultModal.vue'
export default {
  name: 'TrnsResult',
  components: { TrnsResultPopUp },
  data() {
    return {
      showModal: false
    }
  }
}
</script>
<style scoped>

</style>
