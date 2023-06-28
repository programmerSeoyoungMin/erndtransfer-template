<template>
  <div class="app-container">
    <page-info :parent-name="'데이터 이관결과'" :child-name="'이관결과 미리보기'" :parent-link="'/ernd/result'" :child-link="'/ernd/result/inquiry'" />
    <search-table :model="resultSearchForm" @onSearch="search">
      <template v-slot:body>
        <el-col :span="9">
          <el-form-item label-width="180px" label="e-R&D 과제번호">
            <el-input v-model="resultSearchForm.erndSbjtNo" placeholder="" />
          </el-form-item>
        </el-col>

        <el-col :span="9">
          <el-form-item label-width="180px" label="IRIS 과제번호">
            <el-input ref="irisSbjtId" v-model="resultSearchForm.irisSbjtId" placeholder="" />
          </el-form-item>
        </el-col>

        <el-col :span="18">
          <el-form-item label-width="180px" label="과제명">
            <el-input ref="hanSbjtNm" v-model="resultSearchForm.hanSbjtNm" placeholder="" />
          </el-form-item>
        </el-col>
        <el-col :span="9">
          <el-form-item label-width="180px" label="주관기관명">
            <el-input ref="spvsAgcNm" v-model="resultSearchForm.spvsAgcNm" placeholder="" />
          </el-form-item>
        </el-col>

        <el-col :span="9">
          <el-form-item label-width="180px" label="연구책임자">
            <el-input ref="rschRpbPeRschrRegNm" v-model="resultSearchForm.rschRpbPeRschrRegNm" placeholder="" />
          </el-form-item>
        </el-col>
      </template>
    </search-table>
    <div class="gridContainer">
      <grid
        :grid-name="'이관 결과 목록'"
        :headers="headers"
        :data-list="dataList"
        :list-loading="listLoading"
        :use-checkbox="false"
        :use-common="true"
        :excel-upload="false"
        :excel-download="true"
        :row-limit="pager.limit"
        :grid-height="'400'"
        :total-cnt="pager.total"
        @onRowSelect="rowSelect"
        @onRowLimitSelect="setRowLimit"
        @onExcelDownloadClick="excelDownloadClick"
        @onCellDblClick="cellDblClick"
      >
        <template v-slot:rows />
      </grid>
    </div>
    <pagination v-show="dataList.length > 0" :pager="pager" @onhandleCurrentChange="handleCurrentChange" />

    <sbjt-inq-modal
      v-if="showModal"
      :rnd-sbjt-no="rndSbjtNo"
      @onClose="e => showModal = false"
    />
  </div>
</template>

<script>
import PageInfo from '@/ernd/common/PageInfo'
import SearchTable from '@/ernd/common/SearchTable'
import Grid from '@/ernd/common/Grid'
import Pagination from '@/ernd/common/Pagination.vue'
import SbjtInqModal from '@/ernd/common/SbjtInqModal'

import Axios from 'axios'

export default {
  components: { PageInfo, SearchTable, Grid, Pagination, SbjtInqModal },
  data() {
    return {
      dataList: [],
      headers: [],
      listLoading: false,
      pager: {
        currentPage: 1,
        limit: 10,
        total: 1
      },
      resultSearchForm: {
        erndSbjtNo: '',
        irisSbjtId: '',
        krSbjtNm: '',
        spvsAgcNm: '',
        rschRpbPeRschrRegNm: ''
      },
      showModal: false,
      rndSbjtNo: ''
    }
  },
  async mounted() {
    const headerDto = {
      taskSeTblNm: 'RESULT_INQUIRY'
    }
    const response = await Axios.post('http://localhost:8080/common/retriveHeaderList', headerDto)
    this.headers = response.data
    this.search()
  },
  methods: {
    search() {
      this.listLoading = true
      const searchParams = this.resultSearchForm
      searchParams.currentPage = this.pager.currentPage
      searchParams.limit = this.pager.limit
      // 이관과제 목록 조회
      Axios.post('http://localhost:8080/result/retriveSbjtTrnsResultList', searchParams)
        .then(response => {
          this.dataList = response.data.sbjtTrnsResultList
          this.pager.total = response.data.totalCnt
        })
        .catch(error => {
          console.log(error)
        })
        .finally(() => {
          this.listLoading = false
        })
    },
    handleCurrentChange(value) {
      this.search()
    },
    cellDblClick(row, column, cell, event) {
      this.rndSbjtNo = row.rndSbjtNo
      if (column.label === '과제명') {
        this.showModal = true
      }
    },
    setRowLimit(val) {
      if (val !== undefined) this.pager.limit = val
      this.search()
    },
    rowSelect(row) {
      this.selectedRow = row
    },
    excelDownloadClick() {
      const downloadUrl = 'http://localhost:8080/excel/download'
      const downloadParam = {
        'paramObj': this.resultSearchForm,
        'divCd': 'SBJT_TRNS_RESULT'
      }
      Axios.post(downloadUrl, downloadParam,
        {
          'responseType': 'arraybuffer' // 응답 데이터를 byte 배열로 받기 위해 responseType을 설정
        }).then(response => {
        const downloadInfo = {
          blob: new Blob([response.data], { type: 'application/octet-stream' }),
          link: document.createElement('a'),
          fileNm: '과제 이관 결과'
        }
        downloadInfo.link.href = window.URL.createObjectURL(downloadInfo.blob)
        downloadInfo.link.setAttribute('download', downloadInfo.fileNm + '.xlsx') // 다운로드될 파일이름
        document.body.appendChild(downloadInfo.link)
        downloadInfo.link.click()
      })
    },
    numberFormatter(value) {
      if (value === 0) return 0
      if (!value) return ''

      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    }
  }
}
</script>

<style>

</style>
