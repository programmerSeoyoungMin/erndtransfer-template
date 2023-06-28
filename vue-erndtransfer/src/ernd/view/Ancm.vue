<template>
  <div class="app-container">
    <!-- parent name : 대메뉴명 -->
    <!-- child name(required) : 소메뉴명 -->
    <!-- parent link : 대메뉴링크 -->
    <!-- child link : 소메뉴링크 -->
    <page-info :parent-name="'데이터 정제'" :child-name="'공고'" :parent-link="'/ernd/bsns'" :child-link="'/ernd/ancm'" />

    <!-- 검색 영역, 박스 및 검색 버튼 자동 적용. 검색 버튼 작동 함수는 @onSearch에 적용 -->
    <search-table :model="ancmSearchForm" @onSearch="search">
      <!-- 아래 slot 안에 검색 조건 추가 -->
      <template v-slot:body>
        <el-col :span="9">
          <el-form-item label-width="180px" label="e-R&D 접수환경번호">
            <el-input v-model="ancmSearchForm.erndAncmNo" placeholder="텍스트입력" />
          </el-form-item>
        </el-col>

        <el-col :span="9">
          <el-form-item label-width="180px" label="IRIS 공고ID">
            <el-input v-model="ancmSearchForm.irisAncmNo" placeholder="텍스트입력" />
          </el-form-item>
        </el-col>

        <el-col>
          <el-form-item label-width="180px" label="e-R&D 사업년도">
            <el-date-picker v-model="ancmSearchForm.erndBizYr" type="year" placeholder="년도선택" />
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
import Axios from 'axios'
export default {
  name: 'Ancm',
  components: { PageInfo, SearchTable, Grid, Pagination },
  data() {
    return {
      pager: {
        currentPage: 1,
        limit: 10,
        total: 1
      },
      ancmSearchForm: {
        erndAncmNo: '',
        irisAncmNo: '',
        erndBizYr: ''
      },
      listLoading: false,
      headers: [{ key: 'no', name: '순번', width: '50', align: 'center' },
        { key: 'erndAncmNo', name: 'ERND 접수환경번호', width: '150', align: 'center' },
        { key: 'erndBizYr', name: 'ERND 사업년도', width: '150', align: 'center' },
        { key: 'erndBizCd', name: 'ERND 사업코드', width: '150', align: 'center' },
        { key: 'ancmNm', name: '접수환경명', width: '500', align: 'right' },
        { key: 'irisAncmNo', name: 'IRIS공고ID', width: '200', align: 'center' },
        { key: 'totStepCycl', name: '총단계차수', width: '200', align: 'center' },
        { key: 'wholAnuCycl', name: '전체연차차수', width: '200', align: 'center' },
        { key: 'prsStep', name: '현재단계', width: '200', align: 'center' },
        { key: 'prsAnu', name: '현재차수', width: '200', align: 'center' }
      ],
      dataList: [],
      selectedRow: null
    }
  },
  mounted() {
    const today = new Date()
    this.ancmSearchForm.erndBizYr = today.getFullYear() + ''
    this.search()
  },
  methods: {
    search() {
      this.listLoading = true
      const searchParams = this.ancmSearchForm
      // pagingInfo 검색조건 설정
      searchParams.erndBizYr = new Date(this.ancmSearchForm.erndBizYr).getFullYear() + ''
      searchParams.currentPage = this.pager.currentPage
      searchParams.limit = this.pager.limit

      Axios.post('http://localhost:8080/ancm/retriveAncmList', searchParams)
        .then(response => {
          this.dataList = response.data.ancmList
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
    dataMapBtnClick() {
      const confirm = this.$confirm('데이터 정제 하시겠습니까?')
      if (confirm) {
        Axios.post('http://localhost:8080/ancm/insertAncmMappingData')
          .catch(error => {
            console.log(error)
          })
      }
    },
    excelDownloadClick() {
      const downloadUrl = 'http://localhost:8080/excel/download'
      const downloadParam = {
        'paramObj': this.ancmSearchForm,
        'divCd': 'ANCM'
      }
      Axios.post(downloadUrl, downloadParam,
        {
          'responseType': 'arraybuffer' // 응답 데이터를 byte 배열로 받기 위해 responseType을 설정합니다.
        }).then(response => {
        const downloadInfo = {
          blob: new Blob([response.data], { type: 'application/octet-stream' }),
          link: document.createElement('a')
        }
        downloadInfo.link.href = window.URL.createObjectURL(downloadInfo.blob)
        downloadInfo.link.setAttribute('download', '공고.xlsx') // 다운로드될 파일이름
        document.body.appendChild(downloadInfo.link)
        downloadInfo.link.click()
      })
    },
    handleCurrentChange() {
      this.search()
    }
  }
}
</script>

<style scoped>

</style>
