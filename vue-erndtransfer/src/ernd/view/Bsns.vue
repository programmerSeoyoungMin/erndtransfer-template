<template>
  <div class="app-container">
    <!-- parent name : 대메뉴명 -->
    <!-- child name(required) : 소메뉴명 -->
    <!-- parent link : 대메뉴링크 -->
    <!-- child link : 소메뉴링크 -->
    <page-info :parent-name="'데이터 정제'" :child-name="'사업'" :parent-link="'/ernd/bsns'" :child-link="'/ernd/bsns'" />

    <!-- 검색 영역, 박스 및 검색 버튼 자동 적용. 검색 버튼 작동 함수는 @onSearch에 적용 -->
    <div ref="bsnsSearchBox">
      <search-table :model="bsnsSearchForm" @onSearch="search">
        <!-- 아래 slot 안에 검색 조건 추가 -->
        <template v-slot:body>
          <el-col :span="9">
            <el-form-item label-width="130px" label="e-R&D 사업코드">
              <el-input v-model="bsnsSearchForm.erndBsnsCd" placeholder="" />
            </el-form-item>
          </el-col>

          <el-col :span="9">
            <el-form-item label-width="180px" label="IRIS 사업코드">
              <el-input ref="irisBsnsCd" v-model="bsnsSearchForm.irisBsnsCd" placeholder="" />
            </el-form-item>
          </el-col>

          <el-col :span="9">
            <el-form-item label-width="130px" label="e-R&D 사업년도">
              <el-date-picker v-model="bsnsSearchForm.seleYy" type="year" format="yyyy" placeholder="선택" />
            </el-form-item>
          </el-col>

          <el-col :span="9">
            <el-form-item label-width="180px" label="e-R&D 매핑여부">
              <el-radio v-model="bsnsSearchForm.mappingYn" label="Y" aria-checked="checked">Y</el-radio>
              <el-radio v-model="bsnsSearchForm.mappingYn" label="N">N</el-radio>
            </el-form-item>
          </el-col>
        </template>
      </search-table>
    </div>
    <!-- modal component -->
    <ExcelUploadModal
      v-if="showModal"
      ref="excelUploadModal"
      :excel-title="'사업 정제데이터 엑셀업로드'"
      :task-se-tbl-nm="'IRIS_BSNS_CD_MAP_TEMP'"
      :excel-data-list="excelDataList"
      :list-loading="listLoadingModal"
      @onUploadBtnClick="uploadBtnClick"
      @onDownloadBtnClick="downloadBtnClick"
      @onSaveBtnClick="saveBtnClick"
      @close="closeBtnClick"
    />
    <div class="gridContainer">
      <grid
        :grid-name="'사업 목록'"
        :headers="headers"
        :data-list="dataList"
        :list-loading="listLoading"
        :use-checkbox="false"
        :use-common="true"
        :excel-upload="true"
        :excel-download="true"
        :row-limit="pager.limit"
        :grid-height="'400'"
        :total-cnt="pager.total"
        @onRowSelect="rowSelect"
        @onRowLimitSelect="setRowLimit"
        @onExcelUploadClick="excelUploadClick"
        @onExcelDownloadClick="excelDownloadClick"
      >
        <template v-slot:rows />
      </grid>
    <!-- selected row 확인용 -->
    <!--selected row : {{ selectedRow }}-->
    </div>
    <pagination v-show="dataList.length > 0" :pager="pager" @onhandleCurrentChange="handleCurrentChange" />
  </div>

</template>

<script>
import PageInfo from '@/ernd/common/PageInfo'
import SearchTable from '@/ernd/common/SearchTable'
import Grid from '@/ernd/common/Grid'
import Pagination from '@/ernd/common/Pagination.vue'
import ExcelUploadModal from '@/ernd/common/ExcelUploadModal'

import Axios from 'axios'

export default {
  name: 'Demo',
  components: { PageInfo, SearchTable, Grid, Pagination, ExcelUploadModal },
  data() {
    return {
      pager: {
        currentPage: 1,
        limit: 10,
        total: 1
      },
      listLoading: false,
      headers: [],
      dataList: [],
      selectedRow: null,
      bsnsSearchForm: {
        erndBsnsCd: '',
        irisBsnsCd: '',
        seleYy: '',
        mappingYn: 'Y'
      },
      // modal data
      showModal: false,
      listLoadingModal: false,
      excelDataList: []
    }
  },
  watch: {
    'bsnsSearchForm.mappingYn': function(val) {
      const irisBsnsCd = this.$refs['irisBsnsCd'].$el.querySelector('input')
      if (val === 'N') {
        irisBsnsCd.value = ''
        irisBsnsCd.disabled = true
        irisBsnsCd.style.backgroundColor = 'lightgray'
      } else {
        irisBsnsCd.disabled = false
        irisBsnsCd.style.backgroundColor = '#FFFFFF'
      }
    }
  },
  mounted() {
    const today = new Date()
    this.bsnsSearchForm.seleYy = today.getFullYear() + ''
    this.search()
  },
  methods: {
    search() {
      this.listLoading = true

      const searchParams = this.bsnsSearchForm
      // pagingInfo 검색조건 설정
      searchParams.seleYy = new Date(this.bsnsSearchForm.seleYy).getFullYear() + ''
      searchParams.currentPage = this.pager.currentPage
      searchParams.limit = this.pager.limit

      if (searchParams.mappingYn === 'N') {
        const headerDto = {
          taskSeTblNm: 'IRIS_BSNS_CD_MAP_N'
        }
        Axios.post('http://localhost:8080/common/retriveHeaderList', headerDto)
          .then(response => {
            this.headers = response.data
          })
          .catch(error => {
            console.log(error)
          })
      } else {
        const headerDto = {
          taskSeTblNm: 'IRIS_BSNS_CD_MAP'
        }
        Axios.post('http://localhost:8080/common/retriveHeaderList', headerDto)
          .then(response => {
            this.headers = response.data
          })
          .catch(error => {
            console.log(error)
          })
      }

      Axios.post('http://localhost:8080/bsns/retriveBsnsList', searchParams)
        .then(response => {
          this.dataList = response.data.bsnsList
          this.pager.total = response.data.totalCnt
        })
        .catch(error => {
          console.log(error)
        })
        .finally(() => {
          this.listLoading = false
        })
    },
    setRowLimit(val) {
      if (val !== undefined) this.pager.limit = val
      this.search()
    },
    rowSelect(row) {
      console.log(row)
      this.selectedRow = row
    },
    excelUploadClick() {
      this.showModal = true
    },
    excelDownloadClick() {
      const downloadUrl = 'http://localhost:8080/excel/download'
      const downloadParam = {
        'paramObj': this.bsnsSearchForm,
        'divCd': 'BSNS_GRID'
      }
      confirm('화면에 표시된 항목만 다운로드 하시겠습니까?\n취소버튼을 누르시면 사업정보 전체 데이터가 다운로드 됩니다.') ? downloadParam.divCd = 'BSNS_GRID' : downloadParam.divCd = 'BSNS_ALL'
      Axios.post(downloadUrl, downloadParam,
        {
          'responseType': 'arraybuffer' // 응답 데이터를 byte 배열로 받기 위해 responseType을 설정합니다.
        }).then(response => {
        const downloadInfo = {
          blob: new Blob([response.data], { type: 'application/octet-stream' }),
          link: document.createElement('a'),
          fileNm: '사업_' + this.bsnsSearchForm.seleYy
        }
        if (this.bsnsSearchForm.mappingYn === 'N') {
          downloadInfo.fileNm = downloadInfo.fileNm + '_미매핑'
        } else {
          downloadInfo.fileNm = downloadInfo.fileNm + '_매핑'
        }
        downloadInfo.link.href = window.URL.createObjectURL(downloadInfo.blob)
        downloadInfo.link.setAttribute('download', downloadInfo.fileNm + '.xlsx') // 다운로드될 파일이름
        document.body.appendChild(downloadInfo.link)
        downloadInfo.link.click()
      })
    },
    handleCurrentChange(value) {
      this.search()
    },
    // modal methods
    downloadBtnClick() {
      // fileName 파라미터에 다운로드 될 파일명을 입력함.
      const downloadUrl = 'http://localhost:8080/file/templates/download?fileName=BSNS_CD.xlsx'
      Axios.get(downloadUrl, {
        responseType: 'blob' // 응답 데이터 타입
      }).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', '사업_엑셀업로드서식.xlsx') // 다운로드될 파일이름
        document.body.appendChild(link)
        link.click()
      })
    },
    uploadBtnClick(file) {
      if (file === undefined) {
        alert('업로드할 엑셀 파일을 선택해주세요.')
        return
      }
      this.listLoadingModal = true
      // upload excel file with axios
      const result = true
      // file
      // excelUploadModal.vue의 file 가져오기
      const formData = new FormData()
      formData.append('file', file) // 파일 데이터 추가
      formData.append('category', 'bsns')
      const config = {
        headers: {
          'Content-Type': 'multipart/form-data' // 헤더 설정
        }
      }
      Axios.post('http://localhost:8080/excel/upload', formData, config)
        .then(response => {
          const bsnsTempConfig = {
            uploadDe: response.data.uploadDe,
            uploadFileNm: response.data.uploadFileNm
          }
          this.retriveBsnsTempList(bsnsTempConfig.uploadDe, bsnsTempConfig.uploadFileNm)
        })
        .catch(error => {
          console.log(error)
          return result
        }).finally(() => {
          this.listLoadingModal = false
        })
    },
    saveBtnClick() {
      const result = true
      this.listLoadingModal = true
      Axios.post('http://localhost:8080/bsns/saveBsnsList', this.excelDataList)
        .then(response => {

        })
        .catch(error => {
          console.log(error)
        })
        .finally(() => {
          this.listLoadingModal = false
        })
      return result
    },
    // modal close시, excelDataList 초기화
    closeBtnClick() {
      this.excelDataList = []
      this.showModal = false
    },
    async retriveBsnsTempList(uploadDe, uploadFileNm) {
      const retriveBsnsTempList = await Axios.get('http://localhost:8080/bsns/retriveBsnsTempList?uploadDe=' + uploadDe + '&uploadFileNm=' + uploadFileNm)
      this.excelDataList = retriveBsnsTempList.data
    }
  }
}
</script>

<style scoped>

</style>
