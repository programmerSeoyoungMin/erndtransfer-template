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

    <div class="gridContainer">
      <!-- 조회 그리드 -->
      <!-- headers(required) : 헤더 목록. 적용 방법은 아래 headers 참조 -->
      <!-- data-list(required) : 데이터 목록. 적용 방법은 아래 dataList 참조 -->
      <!-- list-loading : (테이블) 로딩중 true, 로딩완료 false -->
      <!-- use checkbox : 첫번째 열에 checkbox row 생성 -->
      <!-- delete btn : row 삭제 버튼 생성 -->
      <!-- data map btn : 데이터 정제 버튼 생성 -->
      <!-- excel upload : 엑셀 업로드 버튼 생성 -->
      <!-- excel download : 엑셀 다운로드 버튼 생성 -->
      <!-- row limit : 한번에 조회할 row 개수 설정 -->
      <!-- onRowLimitSelect : row limit select 선택 후 작동할 함수 설정 -->
      <!-- onRowSelect : 선택한 row object 리턴. use checkbox 사용 시 배열로 리턴함 -->
      <grid
        :grid-name="'사업 목록'"
        :headers="headers"
        :data-list="dataList"
        :list-loading="listLoading"
        :use-checkbox="false"
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
        <!-- el-table-column 수동 추가를 원하면 아래 slot 안에 적용 -->
        <template v-slot:rows />
      </grid>
    <!-- selected row 확인용 -->
    <!--selected row : {{ selectedRow }}-->
    </div>
    <!-- pagination -->
    <!-- pager(required) : 페이지 정보 -->
    <pagination v-show="dataList.length > 0" :pager="pager" @onhandleCurrentChange="handleCurrentChange" />
  </div>

</template>

<script>
import PageInfo from '@/ernd/common/PageInfo'
import SearchTable from '@/ernd/common/SearchTable'
import Grid from '@/ernd/common/Grid'
import Pagination from '@/ernd/common/Pagination.vue'

import Axios from 'axios'

export default {
  name: 'Demo',
  components: { PageInfo, SearchTable, Grid, Pagination },
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
      }
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

    const headerDto = {
      taskSeTblNm: 'IRIS_BSNS_CD_MAP'
    }
    Axios.post('http://localhost:8080/common/retriveHeaderList', headerDto)
      .then(response => {
        this.headers = response.data
      })
      .catch(error => {
        console.log(error)
      }).finally(() => {
        this.search()
      })
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
        this.headers = [
          { 'key': 'seq', 'name': '번호(Q1)', 'width': '176' },
          { 'key': 'seleYy', 'name': '선정년도(Q2)', 'width': '176' },
          { 'key': 'erndBsnsCd', 'name': 'ERND사업코드(Q3)', 'width': '176' }
        ]
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
      console.log(
        'setRowLimit : ' + this.pager.limit + ', currentPage : ' + this.pager.currentPage
      )
      this.search()
    },
    rowSelect(row) {
      this.selectedRow = row
      console.log(this.selectedRow)
    },
    excelUploadClick() {
      this.$alert('엑셀 업로드 버튼 클릭', '엑셀 업로드')
    },
    excelDownloadClick() {
      this.$alert('엑셀 다운로드 버튼 클릭', '엑셀 다운로드')
    },
    handleCurrentChange(value) {
      this.search()
    }
  }
}
</script>

<style scoped>

</style>
