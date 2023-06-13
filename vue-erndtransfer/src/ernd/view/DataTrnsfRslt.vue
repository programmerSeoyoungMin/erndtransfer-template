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
            <el-select v-model="trnsfSe" placeholder="Select">
              <el-option
                v-for="item in options"
                :key="item.trnsfSe"
                :label="item.label"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="9">
          <el-form-item label-width="130px" label="이관일자">
            <el-date-picker
              v-model="dataTrnsfRsltSearchForm.trnsfDt"
              type="daterange"
              start-placeholder="Start Date"
              end-placeholder="End Date"
              placeholder="일자선택"
              :picker-options="pickerOptions"
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
      v-if="detailTable"
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
    >
      <template v-slot:rows>
        <el-table-column label="오류상세" align="center" width="100" header-align="center">
          <el-button type="info" @click="detailModal">
            <i class="el-icon-search" />
          </el-button>
        </el-table-column>
      </template>
    </grid>
    <TrnsResultModal
      v-if="showModal"
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
export default {
  name: 'DataTrnsfRslt',
  components: { SearchTable, Grid, Pagination, TrnsResultModal },
  data() {
    return {
      showModal: false,
      pager: {
        currentPage: 1,
        limit: 10,
        total: 1
      },
      dataTrnsfRsltSearchForm: {
        trnsfDt: ''
      },
      listLoading: false,
      headers: [
        { key: 'trnsfSe', name: '이관구분', width: '130' },
        { key: 'uldFileNm', name: '파일명', width: '450' },
        { key: 'dmndDt', name: '이관일시', width: '200' },
        { key: 'trnsfDataTnocs', name: '총건수', width: '150' },
        { key: 'sbjtNocs', name: '과제건수', width: '150' },
        { key: 'errNocs', name: '오류건수', width: '150' }
      ],
      detailHeaders: [
        { key: 'trnsfSe', name: '업무구분', width: '130' },
        { key: 'uldFileNm', name: 'SOURCE', width: '200' },
        { key: 'dmndDt', name: 'TARGET', width: '200' },
        { key: 'trnsfDataTnocs', name: '이관데이터건수', width: '150' },
        { key: 'sbjtNocs', name: '오류건수', width: '150' }
      ],
      dataList: [{ trnsfSe: 'trnsfSe', uldFileNm: '이관구분', dmndDt: '130', trnsfDataTnocs: '', sbjtNocs: '', errNocs: '' },
        { trnsfSe: 'dddddd', uldFileNm: '이관구분', dmndDt: '130', trnsfDataTnocs: '', sbjtNocs: '', errNocs: '' }],
      detailDataList: [{ trnsfSe: 'trnsfSe', uldFileNm: '이관구분', dmndDt: '130', trnsfDataTnocs: '', sbjtNocs: '' }],
      selectedRow: null,
      detailTable: false,
      options: [{
        trnsfSe: 'B',
        label: '전체'
      }, {
        trnsfSe: 'C',
        label: '개별'
      }],
      trnsfSe: '-- 선택 --',
      pickerOptions: {
        shortcuts: [{
          text: 'Last week',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: 'Last month',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: 'Last 3 months',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      }
    }
  },
  mounted() {
    const start = new Date()
    this.dataTrnsfRsltSearchForm.trnsfDt = [start.getTime() - 3600 * 1000 * 24 * 7, start]
    this.search()
  },
  methods: {
    search() {
      this.$alert('current page = ' + this.dataTrnsfRsltSearchForm.trnsfDt + ', row limit = ' + this.pager.limit, '검색')
    },
    setRowLimit(val) {
      if (val !== undefined) this.pager.limit = val
      console.log(
        'setRowLimit : ' + this.pager.limit + ', currentPage : ' + this.pager.currentPage
      )
      this.search()
    },
    getDetailList(row) {
      this.detailTable = true
      console.log(
        this.$alert('getDetail ', '검색')
      )
    },
    handleCurrentChange(val) {
      this.search()
    },
    detailModal() {
      this.showModal = true
    },
    filterHtml(row, key, type) {
      const html = {
        openTag: '<span>',
        value: '',
        closeTag: '</span>'
      }
      if (row[key] != null && row[key] !== '') {
        // type이 number인 경우, 3자리마다 콤마 추가
        if (type != null && type === 'number') {
          html.value = row[key].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
          // type이 popup인 경우, decoration 추가
        } else if (type != null && type === 'popup') {
          html.openTag = '<i class="fa-solid fa-caret-right"></i> <span style="text-decoration:underline;cursor:pointer">'
          html.value = row[key]
        } else {
          html.value = row[key]
        }
      }

      return html.openTag + html.value + html.closeTag
    },
    closeBtnClick() {
      this.showModal = false
    }
  }
}
</script>

<style scoped>

</style>
