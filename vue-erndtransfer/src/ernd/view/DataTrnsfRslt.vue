<template>
  <div class="app-container">
    <!-- parent name : 대메뉴명 -->
    <!-- child name(required) : 소메뉴명 -->
    <!-- parent link : 대메뉴링크 -->
    <!-- child link : 소메뉴링크 -->
    <!--    <page-info :parent-name="'데이터 이관 결과'" :parent-link="'/ernd/dataTrnsfRslt'" />-->

    <!-- 검색 영역, 박스 및 검색 버튼 자동 적용. 검색 버튼 작동 함수는 @onSearch에 적용 -->
    <search-table :model="dataTrnsfRsltSearchForm" @onSearch="search">
      <!-- 아래 slot 안에 검색 조건 추가 -->
      <template v-slot:body>
        <el-col :span="9">
          <el-form-item label-width="180px" label="이관구분">
            <el-select v-model="dataTrnsfRsltSearchForm.trnsfSe" placeholder="-- 선택 --">
              <el-option
                v-for="item in options"
                :key="item.trnsfSe"
                :label="item.label"
                :value="item.trnsfSe"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="9">
          <el-form-item label-width="130px" label="이관일자">
            <el-date-picker
              v-model="dataTrnsfRsltSearchForm.trnsfDt"
              type="daterange"
              value-format="yyyyMMdd"
              format="yyyy-MM-dd"
              start-placeholder="Start Date"
              end-placeholder="End Date"
              placeholder="일자선택"
            />
          </el-form-item>
        </el-col>
      </template>
    </search-table>
    <!-- 이관목록 그리드 -->
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
      :grid-name="'이관목록'"
      :headers="headers"
      :data-list="dataList"
      :grid-height="300"
      :list-loading="listLoading"
      :use-common="true"
      :use-checkbox="false"
      :delete-btn="false"
      :data-map-btn="false"
      :excel-upload="false"
      :excel-download="false"
      :row-limit="pager.limit"
      @onRowLimitSelect="setRowLimit"
      @onRowSelect="getDetailList"
    />
    <pagination v-show="dataList.length > 0" :pager="pager" />
    <!--  이관목록 상세 그리드   -->
    <grid
      v-show="detailTable"
      :grid-name="'이관목록상세'"
      :headers="detailHeaders"
      :data-list="detailDataList"
      :grid-height="300"
      :list-loading="listLoading"
      :use-common="true"
      :use-checkbox="false"
      :delete-btn="false"
      :data-map-btn="false"
      :excel-upload="false"
      :excel-download="false"
      @onCellDblClick="cellDblClick"
    >
      <template v-slot:rows>
        <el-table-column label="오류상세" align="center" width="100" header-align="center">
          <el-button type="info">
            <i class="el-icon-search" />
          </el-button>
        </el-table-column>
      </template>
    </grid>
    <TrnsResultModal
      v-if="showModal"
      ref="trnsfResultModal"
      :prcd-id="prcdId"
      :trnsf-id="trnsfId"
      :list-loading="listLoading"
      @close="closeBtnClick"
    />
  </div>

</template>

<script>
import SearchTable from '@/ernd/common/SearchTable'
import Grid from '@/ernd/common/Grid.vue'
import Pagination from '@/ernd/common/Pagination.vue'
import TrnsResultModal from '@/ernd/common/TrnsResultModal.vue'
import Axios from 'axios'
import row from 'element-ui/packages/row'
export default {
  name: 'DataTrnsfRslt',
  components: { SearchTable, Grid, Pagination, TrnsResultModal },
  data() {
    return {
      showModal: false,
      errDtlBtn: false,
      trnsfId: '',
      prcdId: '',
      pager: {
        currentPage: 1,
        limit: 10,
        total: 1
      },
      dataTrnsfRsltSearchForm: {
        trnsfStrtDt: '',
        trnsfEndDt: '',
        trnsfSe: '',
        trnsfId: '',
        prcdId: '',
        trnsfDt: []
      },
      listLoading: false,
      headers: [
        { key: 'trnsfSe', name: '이관구분', width: '130' },
        { key: 'uldFileNm', name: '파일명', width: '450' },
        { key: 'trnsfYmd', name: '이관일시', width: '200' },
        { key: 'sbjtNocs', name: '과제건수', width: '150' },
        { key: 'errNocs', name: '오류건수', width: '150' }
      ],
      detailHeaders: [
        { key: 'prcdNm', name: '업무구분', width: '200' },
        { key: 'trnsfTbl', name: 'SOURCE', width: '200' },
        { key: 'prcdId', name: 'TARGET', width: '200' },
        { key: 'trnsfDataNocs', name: '이관데이터건수', width: '150' },
        { key: 'errNocs', name: '오류건수', width: '150' }
      ],
      dataList: [],
      detailDataList: [],
      selectedRow: null,
      detailTable: false,
      options: [{
        trnsfSe: 'TA',
        label: '전체'
      }, {
        trnsfSe: 'TE',
        label: '개별'
      }]
    }
  },
  computed: {
    row() {
      return row
    }
  },
  mounted() {
    const today = new Date()
    this.dataTrnsfRsltSearchForm.trnsfDt = [this.dateToString(today), this.dateToString(today)]
    this.search()
  },
  methods: {
    search() {
      this.detailTable = false
      this.listLoading = true
      const searchParams = this.dataTrnsfRsltSearchForm
      const strtDt = this.dataTrnsfRsltSearchForm.trnsfDt[0]
      const endDt = this.dataTrnsfRsltSearchForm.trnsfDt[1]
      searchParams.currentPage = this.pager.currentPage
      searchParams.limit = this.pager.limit
      searchParams.trnsfStrtDt = strtDt
      searchParams.trnsfEndDt = endDt

      Axios.post('http://localhost:8080/trnsfRslt/retriveTrnsfRsltList', searchParams)
        .then(response => {
          this.dataList = response.data.trnsRsltList
          this.pager.total = response.data.totalCnt
          console.log(this.dataList)
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
    getDetailList(val) {
      this.detailTable = true
      const searchParams = this.dataTrnsfRsltSearchForm
      // pagingInfo 검색조건 설정
      searchParams.trnsfId = val.trnsfId
      this.dataTrnsfRsltSearchForm.trnsfId = val.trnsfId
      Axios.post('http://localhost:8080/trnsfRslt/retriveTrnsfRsltDtl', searchParams)
        .then(response => {
          this.detailDataList = response.data.trnsfRsltDtl
        })
        .catch(error => {
          console.log(error)
        })
        .finally(() => {
          this.listLoading = false
        })
    },
    cellDblClick(row, column, cell, event) {
      this.trnsfId = row.trnsfId
      this.prcdId = row.prcdId
      if (column.label === '오류상세') {
        this.showModal = true
        this.$refs.trnsfResultModal.errSearch()
      }
    },
    closeBtnClick() {
      this.showModal = false
    },
    dateToString(date) {
      const newDate = new Date(date)
      const newDateYear = newDate.getFullYear() + ''
      const newMonth = '0' + (newDate.getMonth() + 1)
      const newDay = newDate.getDate() + ''
      return newDateYear + newMonth + newDay
    }
  }
}
</script>

<style scoped>

</style>
