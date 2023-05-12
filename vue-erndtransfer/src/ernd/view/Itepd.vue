<template>
  <div class="app-container">
    <!-- parent name : 대메뉴명 -->
    <!-- child name(required) : 소메뉴명 -->
    <!-- parent link : 대메뉴링크 -->
    <!-- child link : 소메뉴링크 -->
    <page-info :parent-name="'데이터 정제'" :child-name="'비목'" :parent-link="'/ernd/itepd'" :child-link="'/ernd/itepd'" />

    <!-- 검색 영역, 박스 및 검색 버튼 자동 적용. 검색 버튼 작동 함수는 @onSearch에 적용 -->
    <div ref="itepdSearchBox">
      <search-table :model="itepdSearchForm" @onSearch="search">
        <!-- 아래 slot 안에 검색 조건 추가 -->
        <template v-slot:body>

          <el-col :span="9">
            <el-form-item label-width="130px" label="e-R&D 비목코드">
              <el-select v-model="itepdSearchForm.erndIoeCd" filterable clearable placeholder="코드명을 입력하십시오.">
                <el-option v-for="item in erndIoeCdOptions" :key="item.erndIoeCd" :label="item.erndIoeNm" :value="item.erndIoeCd" />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="9">
            <el-form-item label-width="180px" label="e-R&D 비목명">
              <el-input v-model="itepdSearchForm.erndIoeNm" placeholder="" />
            </el-form-item>
          </el-col>

          <el-col :span="9">
            <el-form-item label-width="130px" label="IRIS 비목코드">
              <el-select v-model="itepdSearchForm.irisItepdCd" filterable clearable placeholder="코드명을 입력하십시오.">
                <el-option v-for="item in irisItepdCdOptions" :key="item.irisItepdCd" :label="item.irisItepdCdNm" :value="item.irisItepdCd" />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="9">
            <el-form-item label-width="180px" label="IRIS 비목명">
              <el-input v-model="itepdSearchForm.irisItepdCdNm" placeholder="" />
            </el-form-item>
          </el-col>

        </template>
      </search-table>
    </div>
    <!-- modal component -->
    <ExcelUploadModal
      v-if="showModal"
      :excel-title="'비목 정제데이터 엑셀업로드'"
      :task-se-tbl-nm="'IRIS_ITEPD_MAP_TEMP'"
      :excel-data-list="excelDataList"
      :list-loading="listLoadingModal"
      @onUploadBtnClick="uploadBtnClick"
      @onDownloadBtnClick="downloadBtnClick"
      @onSaveBtnClick="saveBtnClick"
      @close="closeBtnClick"
    />
    <div class="gridContainer">
      <grid
        :grid-name="'비목 목록'"
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
      erndIoeCdOptions: [],
      irisItepdCdOptions: [],
      itepdSearchForm: {
        erndIoeCd: '',
        erndIoeNm: '',
        irisItepdCd: '',
        irisItepdCdNm: ''
      },
      // modal data
      showModal: false,
      listLoadingModal: false,
      excelDataList: []
    }
  },
  created() {
    this.getCode()
  },
  mounted() {
    this.search()
  },
  methods: {
    async getCode() {
      const erndCdResponse = await Axios.get('http://localhost:8080/itepd/retriveErndIoeCdList')
      this.erndIoeCdOptions = erndCdResponse.data
      const irisCdResponse = await Axios.get('http://localhost:8080/itepd/retriveIrisItepdCdList')
      this.irisItepdCdOptions = irisCdResponse.data
    },
    search() {
      this.listLoading = true

      const searchParams = this.itepdSearchForm
      // pagingInfo 검색조건 설정
      searchParams.currentPage = this.pager.currentPage
      searchParams.limit = this.pager.limit

      const headerDto = {
        taskSeTblNm: 'IRIS_ITEPD_MAP'
      }
      Axios.post('http://localhost:8080/common/retriveHeaderList', headerDto)
        .then(response => {
          this.headers = response.data
        })
        .catch(error => {
          console.log(error)
        })

      Axios.post('http://localhost:8080/itepd/retriveItepdList', searchParams)
        .then(response => {
          this.dataList = response.data.itepdList
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
      console.log(this.selectedRow)
    },
    excelUploadClick() {
      this.showModal = true
    },
    excelDownloadClick() {
      const downloadUrl = 'http://localhost:8080/excel/download'
      const downloadParam = {
        'paramObj': this.itepdSearchForm,
        'divCd': 'ITEPD'
      }
      Axios.post(downloadUrl, downloadParam,
        {
          'responseType': 'arraybuffer' // 응답 데이터를 byte 배열로 받기 위해 responseType을 설정합니다.
        }).then(response => {
        const downloadInfo = {
          blob: new Blob([response.data], { type: 'application/octet-stream' }),
          link: document.createElement('a'),
          fileNm: '비목'
        }
        if (this.itepdSearchForm.mappingYn === 'N') {
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
      const downloadUrl = 'http://localhost:8080/file/templates/download?fileName=ITEPD_CD.xlsx'
      Axios.get(downloadUrl, {
        responseType: 'blob' // 응답 데이터 타입
      }).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', '비목_엑셀업로드서식.xlsx') // 다운로드될 파일이름
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
      formData.append('category', 'itepd')
      const config = {
        headers: {
          'Content-Type': 'multipart/form-data' // 헤더 설정
        }
      }
      Axios.post('http://localhost:8080/excel/upload', formData, config)
        .then(response => {
          const itepdTempConfig = {
            uploadDe: response.data.uploadDe,
            uploadFileNm: response.data.uploadFileNm
          }
          // Axios config setting
          Axios.get('http://localhost:8080/itepd/retriveItepdTempList?uploadDe=' + itepdTempConfig.uploadDe + '&uploadFileNm=' + itepdTempConfig.uploadFileNm)
            .then(response => {
              this.excelDataList = response.data
            })
            .catch(error => {
              console.log(error)
            })
            .finally(() => {
              this.listLoadingModal = false
            })
        })
        .catch(error => {
          console.log(error)
          return result
        })
        .finally(() => {
          this.listLoadingModal = false
        })
    },
    saveBtnClick() {
      const result = true
      this.listLoadingModal = true
      Axios.post('http://localhost:8080/itepd/saveItepdList', this.excelDataList)
        .then(response => {

        })
        .catch(error => {
          console.log(error)
        })
        .finally(() => {
          this.listLoadingModal = false
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

<style scoped>

</style>
