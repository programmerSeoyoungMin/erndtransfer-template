<template>
  <div class="dashboard-container">

    <main-panel />
    <non-transferable-panel />
    <trnsf-prcd-time />
    <el-row>
      <el-col :span="12"><data-mapping-rto /></el-col>
      <el-col :span="12"><error-logs /></el-col>
    </el-row>

  </div>
</template>

<script>
import MainPanel from '@/ernd/view/MainPanel'
import NonTransferablePanel from '@/ernd/view/NonTransferablePanel'
import TrnsfPrcdTime from '@/ernd/view/TrnsfPrcdTime.vue'
import DataMappingRto from '@/ernd/view/DataMappingRto.vue'
import ErrorLogs from '@/ernd/view/ErrorLogs.vue'

export default {
  name: 'Dashboard',
  components: {
    MainPanel,
    NonTransferablePanel,
    TrnsfPrcdTime,
    DataMappingRto,
    ErrorLogs
  }
}
</script>

