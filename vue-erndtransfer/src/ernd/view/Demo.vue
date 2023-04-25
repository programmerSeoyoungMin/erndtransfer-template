<template>
  <div class="app-container">
    <!-- parent name : 대메뉴명 -->
    <!-- child name(required) : 소메뉴명 -->
    <!-- parent link : 대메뉴링크 -->
    <!-- child link : 소메뉴링크 -->
    <page-info :parent-name="'데이터 정제'" :child-name="'사업'" />

    <!-- 검색 영역, 박스 및 검색 버튼 자동 적용. 검색 버튼 작동 함수는 @onSearch에 적용 -->
    <search-table @onSearch="search">
      <!-- 아래 slot 안에 검색 조건 추가 -->
      <template v-slot:body>
        <el-col :span="9">
          <el-form-item label-width="130px" label="e-R&D 사업코드">
            <el-input placeholder="텍스트입력" />
          </el-form-item>
        </el-col>

        <el-col :span="9">
          <el-form-item label-width="180px" label="IRIS 사업코드">
            <el-input placeholder="텍스트입력" />
          </el-form-item>
        </el-col>

        <el-col :span="9">
          <el-form-item label-width="130px" label="e-R&D 사업날짜">
            <el-date-picker format="yyyy-MM-dd HH:mm:ss" placeholder="날짜선택" />
          </el-form-item>
        </el-col>

        <el-col :span="9">
          <el-form-item label-width="180px" label="e-R&D 매핑여부">
            <el-radio-group>
              <el-radio :label="1">Y</el-radio>
              <el-radio :label="2">N</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </template>
    </search-table>

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
      :delete-btn="true"
      :data-map-btn="true"
      :excel-upload="true"
      :excel-download="true"
      :row-limit="pager.limit"
      @onRowSelect="rowSelect"
      @onRowLimitSelect="setRowLimit"
      @onDeleteBtnClick="deleteBtnClick"
      @onDataMapBtnClick="dataMapBtnClick"
      @onExcelUploadClick="excelUploadClick"
      @onExcelDownloadClick="excelDownloadClick"
    >
      <!-- el-table-column 수동 추가를 원하면 아래 slot 안에 적용 -->
      <template v-slot:rows />
    </grid>
    <!-- selected row 확인용 -->
    <!--selected row : {{ selectedRow }}-->

    <!-- pagination -->
    <!-- pager(required) : 페이지 정보 -->
    <pagination v-show="dataList.length > 0" :pager="pager" />
  </div>

</template>

<script>
import PageInfo from '@/ernd/common/PageInfo'
import SearchTable from '@/ernd/common/SearchTable'
import Grid from '@/ernd/common/Grid'
import Pagination from '@/ernd/common/Pagination.vue'

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
      headers: [
        { key: 'no', name: '순번', width: '80' },
        { key: 'year', name: '사업년도', width: '100' },
        { key: 'program', name: '프로그램 사업코드' },
        { key: 'unit', name: '단위 사업코드' },
        { key: 'detail', name: '세부 사업코드' },
        { key: 'ernd', name: 'e-R&D 사업코드' },
        { key: 'iris', name: 'IRIS 사업코드' },
        { key: 'name', name: '내역사업명', width: '500' }
      ],
      dataList: [
        { no: '1', year: '2017', program: 'B001687', unit: 'B009873', detail: 'B034736', ernd: 'S2A5A2A01', iris: 'NR50081', name: '중견연구자지원사업_유형', errorYn: 'Y' },
        { no: '2', year: '2017', program: 'B001687', unit: 'B009873', detail: 'B034736', ernd: 'S2A5A2A01', iris: 'NR50081', name: '중견연구자지원사업_유형' },
        { no: '3', year: '2017', program: 'B001687', unit: 'B009873', detail: 'B034736', ernd: 'S2A5A2A01', iris: 'NR50081', name: '중견연구자지원사업_유형' },
        { no: '4', year: '2017', program: 'B001687', unit: 'B009873', detail: 'B034736', ernd: 'S2A5A2A01', iris: 'NR50081', name: '중견연구자지원사업_유형' },
        { no: '5', year: '2017', program: 'B001687', unit: 'B009873', detail: 'B034736', ernd: 'S2A5A2A01', iris: 'NR50081', name: '중견연구자지원사업_유형' },
        { no: '6', year: '2017', program: 'B001687', unit: 'B009873', detail: 'B034736', ernd: 'S2A5A2A01', iris: 'NR50081', name: '중견연구자지원사업_유형' },
        { no: '7', year: '2017', program: 'B001687', unit: 'B009873', detail: 'B034736', ernd: 'S2A5A2A01', iris: 'NR50081', name: '중견연구자지원사업_유형' },
        { no: '8', year: '2017', program: 'B001687', unit: 'B009873', detail: 'B034736', ernd: 'S2A5A2A01', iris: 'NR50081', name: '중견연구자지원사업_유형' },
        { no: '9', year: '2017', program: 'B001687', unit: 'B009873', detail: 'B034736', ernd: 'S2A5A2A01', iris: 'NR50081', name: '중견연구자지원사업_유형' },
        { no: '10', year: '2017', program: 'B001687', unit: 'B009873', detail: 'B034736', ernd: 'S2A5A2A01', iris: 'NR50081', name: '중견연구자지원사업_유형' }
      ],
      selectedRow: null
    }
  },
  methods: {
    search() {
      this.$alert('current page = ' + this.pager.currentPage + ', row limit = ' + this.pager.limit, '검색')
    },
    setRowLimit(val) {
      if (val !== undefined) this.pager.limit = val
      this.search()
    },
    rowSelect(row) {
      this.selectedRow = row
    },
    dataMapBtnClick() {
      this.$alert('데이터 정제 버튼 클릭', '데이터 정제')
    },
    excelUploadClick() {
      this.$alert('엑셀 업로드 버튼 클릭', '엑셀 업로드')
    },
    excelDownloadClick() {
      this.$alert('엑셀 다운로드 버튼 클릭', '엑셀 다운로드')
    },
    deleteBtnClick() {
      if (this.selectedRow === undefined || this.selectedRow === null) {
        this.$alert('삭제할 row를 선택하세요.', '삭제')
        return
      }

      if (Array.isArray(this.selectedRow)) { this.selectedRow.forEach((element) => this.dataList.splice(this.dataList.findIndex((value) => value.no === element.no), 1)) } else { this.dataList.splice(this.dataList.findIndex((value) => value.no === this.selectedRow.no), 1) }
    }
  }
}
</script>

<style scoped>

</style>
