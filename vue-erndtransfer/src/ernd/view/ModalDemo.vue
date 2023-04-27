<template>
  <div class="app-container">
    <!-- show modal btn for ExcelModal.vue -->
    <el-button type="primary" @click="showModal = true">Show Modal</el-button>
    <!-- modal component -->
    <ExcelUploadModal
      v-if="showModal"
      :excel-title="'사업 정제데이터 엑셀업로드'"
      :gbn="'BSNS_CD'"
      :excel-data-list="excelDataList"
      :list-loading="listLoading"
      :exclude="'NO'"
      :include-error-field-yn="'Y'"
      @onUploadBtnClick="uploadBtnClick"
      @onDownloadBtnClick="downloadBtnClick"
      @onSaveBtnClick="saveBtnClick"
      @close="showModal = false"
    />
  </div>
</template>

<script>
import ExcelUploadModal from '@/ernd/common/ExcelUploadModal.vue'
import Axios from 'axios'

export default {
  components: {
    ExcelUploadModal
  },
  data() {
    return {
      showModal: false,
      excelDataList: [],
      listLoading: false
    }
  },
  methods: {
    downloadBtnClick() {

    },
    retriveBsnsTempList() {

    },
    uploadBtnClick(file) {
      this.listLoading = true
      // upload excel file with axios
      const result = true
      // file
      // excelUploadModal.vue의 file 가져오기
      const formData = new FormData()
      formData.append('file', file) // 파일 데이터 추가
      formData.append('gbn', 'bsns')
      const config = {
        headers: {
          'Content-Type': 'multipart/form-data' // 헤더 설정
        }
      }
      Axios.post('http://localhost:8080/excel/upload', formData, config)
        .then(response => {
          // Axios config setting
          Axios.get('http://localhost:8080/bsns/retriveBsnsTempList')
            .then(response => {
              console.log(response.data)
              this.excelDataList = response.data
            })
            .catch(error => {
              console.log(error)
            })
            .finally(() => {
              this.listLoading = false
            })
        })
        .catch(error => {
          console.log(error)
          return result
        })
        .finally(() => {
          this.listLoading = false
        })
    },
    saveBtnClick() {
      this.$alert('저장 버튼 클릭2222')
    }
  }
}
</script>

<style>
</style>
