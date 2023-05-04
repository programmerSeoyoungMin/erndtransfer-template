<template>
  <div class="app-container">
    <!-- show modal btn for ExcelModal.vue -->
    <el-button type="primary" @click="showModal = true">Show Modal</el-button>
    <!-- modal component -->
    <ExcelUploadModal
      v-if="showModal"
      :excel-title="'사업 정제데이터 엑셀업로드'"
      :task-se-tbl-nm="'IRIS_BSNS_TEMP_CD_MAP'"
      :excel-data-list="excelDataList"
      :list-loading="listLoading"
      @onUploadBtnClick="uploadBtnClick"
      @onDownloadBtnClick="downloadBtnClick"
      @onSaveBtnClick="saveBtnClick"
      @close="closeBtnClick"
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
      // fileName 파라미터에 다운로드 될 파일명을 입력함.
      const downloadUrl = 'http://localhost:8080/file/download?fileName=BSNS_CD.xlsx'
      Axios.get(downloadUrl, {
        responseType: 'blob' // 응답 데이터 타입
      }).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', '사업코드서식파일.xlsx') // 다운로드될 파일이름
        document.body.appendChild(link)
        link.click()
      })
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
      const result = true
      Axios.post('http://localhost:8080/bsns/saveBsnsList', this.excelDataList)
        .then(response => {
          console.log(response.data)
        })
        .catch(error => {
          console.log(error)
        })
        .finally(() => {
          this.listLoading = false
        }
        )
      return result
    },
    // modal close시, excelDataList 초기화
    closeBtnClick() {
      this.excelDataList = []
      this.showModal = false
    }
  }
}
</script>

<style>
</style>
