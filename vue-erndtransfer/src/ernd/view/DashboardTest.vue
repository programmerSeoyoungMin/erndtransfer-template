<template>
  <div class="dashboard-container">
    <main-panel />
    <non-transferable-panel />
  </div>
</template>

<script>
import MainPanel from '@/ernd/view/MainPanel'
import NonTransferablePanel from '@/ernd/view/NonTransferablePanel'

export default {
  name: 'Dashboard',
  components: {
    MainPanel,
    NonTransferablePanel
  }
}
</script>

