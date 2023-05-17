<template>
  <div class="app-container">
    <!-- parent name : 대메뉴명 -->
    <!-- child name(required) : 소메뉴명 -->
    <!-- parent link : 대메뉴링크 -->
    <!-- child link : 소메뉴링크 -->
    <page-info :parent-name="'데이터 정제'" :child-name="'공고'" :parent-link="'/ernd/bsns'" :child-link="'/ernd/ancm'" />

    <!-- 검색 영역, 박스 및 검색 버튼 자동 적용. 검색 버튼 작동 함수는 @onSearch에 적용 -->
    <search-table @onSearch="search">
      <!-- 아래 slot 안에 검색 조건 추가 -->
      <template v-slot:body>
        <el-col :span="9">
          <el-form-item label-width="180px" label="e-R&D 접수환경번호">
            <el-input v-model="erndAncmNo" placeholder="텍스트입력" />
          </el-form-item>
        </el-col>

        <el-col :span="9">
          <el-form-item label-width="180px" label="IRIS 공고ID">
            <el-input v-model="irisSbjtNo" placeholder="텍스트입력" />
          </el-form-item>
        </el-col>

        <el-col>
          <el-form-item label-width="180px" label="e-R&D 사업년도">
            <el-date-picker v-model="bizYr" type="year" placeholder="년도선택" />
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
      :grid-name="'공고목록'"
      :headers="headers"
      :data-list="dataList"
      :list-loading="listLoading"
      :use-checkbox="false"
      :delete-btn="false"
      :data-map-btn="true"
      :excel-upload="false"
      :excel-download="true"
      :row-limit="pager.limit"
      @onRowLimitSelect="setRowLimit"
      @onDataMapBtnClick="dataMapBtnClick"
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
import Grid from '@/ernd/common/Grid.vue'
import Pagination from '@/ernd/common/Pagination.vue'
export default {
  name: 'Sbjt',
  components: { PageInfo, SearchTable, Grid, Pagination },
  data() {
    return {
      pager: {
        currentPage: 1,
        limit: 10,
        total: 1
      },
      listLoading: false,
      headers: [{ key: 'no', name: '순번', width: '80' },
        { key: 'sbjtStts', name: '접수환경번호', width: '100' },
        { key: 'erndSbjtNo', name: '사업년도', width: '100' },
        { key: 'erndsbjtNm', name: 'ERND사업코드', width: '200' },
        { key: 'irisFlfmtYy', name: '접수환경명', width: '550' },
        { key: 'irisSbjtNo', name: 'IRIS공고ID', width: '200' }
      ],
      dataList: [],
      selectedRow: null
    }
  },
  methods: {
    search() {
      this.listLoading = true

      this.$alert('current page = ' + this.pager.currentPage + ', row limit = ' + this.pager.limit, '검색')
    },
    setRowLimit(val) {
      if (val !== undefined) this.pager.limit = val
      console.log(
        'setRowLimit : ' + this.pager.limit + ', currentPage : ' + this.pager.currentPage
      )
      this.search()
    },
    dataMapBtnClick(row) {
      this.selectedRow = row
      console.log(this.selectedRow)
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
