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
            <el-input ref="irisSbjtid" v-model="resultSearchForm.irisSbjtid" placeholder="" />
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
        irisSbjtid: '',
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
    this.dataList = [
      { ptcBsnsName: '국제연구인력교류(R&D)', brdnBsnsName: '해외우수과학자유치사업(BP+)', sbjtProplStrcSeNm: '(일반)연구개발과제', seleYy: '2020', rndSbjtNo: 'NR-2020099291', stepAnuSbjtNo: '2020H1D3A2A03099291', hanSbjtNm: '양자 물질을 이용한 초저전력 정보 처리 기술 개발', spvsAgcNm: '한국과학기술원', rschRpbPeRschrRegNm: '김세권', bizDepartNm: '과학기술정보통신부', totRschDate: '2020-07-01~2029-12-31' },
      { ptcBsnsName: '국제연구인력교류(R&D)', brdnBsnsName: '해외우수과학자유치사업(BP+)', sbjtProplStrcSeNm: '(일반)연구개발과제', seleYy: '2020', rndSbjtNo: 'NR-2020100945', stepAnuSbjtNo: '2020H1D3A2A03100945', hanSbjtNm: '전자의무기록 기반 헬스케어 인공지능 원천 기술 연구 및 적용', spvsAgcNm: '한국과학기술원', rschRpbPeRschrRegNm: '최윤재', bizDepartNm: '과학기술정보통신부', totRschDate: '2020-07-01~2029-12-31' },
      { ptcBsnsName: '국제연구인력교류(R&D)', brdnBsnsName: '해외우수과학자유치사업(BP+)', sbjtProplStrcSeNm: '(일반)연구개발과제', seleYy: '2020', rndSbjtNo: 'NR-2020100666', stepAnuSbjtNo: '2020H1D3A2A03100666', hanSbjtNm: '정밀의학을 위한 데이터 사이언스 방법 및 시스템', spvsAgcNm: '서울대학교', rschRpbPeRschrRegNm: '이승근', bizDepartNm: '과학기술정보통신부', totRschDate: '2020-07-01~2029-12-31' },
      { ptcBsnsName: '국제연구인력교류(R&D)', brdnBsnsName: '해외우수과학자유치사업(BP+)', sbjtProplStrcSeNm: '(일반)연구개발과제', seleYy: '2020', rndSbjtNo: 'NR-2020100215', stepAnuSbjtNo: '2020H1D3A2A03100215', hanSbjtNm: '희귀질환 자원, 진단 및 환자맞춤형 신약개발 허브 구축', spvsAgcNm: '한국과학기술원', rschRpbPeRschrRegNm: '김진국', bizDepartNm: '과학기술정보통신부', totRschDate: '2020-07-01~2029-12-31' },
      { ptcBsnsName: '이공학학술연구기반구축(R&D)', brdnBsnsName: '보호연구지원사업', sbjtProplStrcSeNm: '(일반)연구개발과제', seleYy: '2019', rndSbjtNo: 'NR-2019050373', stepAnuSbjtNo: '2019R1I1A2A03050373', hanSbjtNm: '탄소나노튜브 및 그래핀이 시멘트 복합체의 역학적 특성, 내구성, 및 기능성 향상에 미치는 영향 연구', spvsAgcNm: '부경대학교', rschRpbPeRschrRegNm: '정철우', bizDepartNm: '교육부', totRschDate: '2019-06-01~2029-05-31' },
      { ptcBsnsName: '이공학학술연구기반구축(R&D)', brdnBsnsName: '지역대학우수과학자지원사업', sbjtProplStrcSeNm: '(일반)연구개발과제', seleYy: '2019', rndSbjtNo: 'NR-2019059382', stepAnuSbjtNo: '2019R1I1A3A03059382', hanSbjtNm: '금융공학모델과 강화 학습을 이용한 효율적인 자산운용과 파생상품의 헤지', spvsAgcNm: '전남대학교', rschRpbPeRschrRegNm: '임현철', bizDepartNm: '교육부', totRschDate: '2019-06-01~2029-05-31' },
      { ptcBsnsName: '이공학학술연구기반구축(R&D)', brdnBsnsName: '지역대학우수과학자지원사업', sbjtProplStrcSeNm: '(일반)연구개발과제', seleYy: '2019', rndSbjtNo: 'NR-2019059103', stepAnuSbjtNo: '2019R1I1A3A03059103', hanSbjtNm: '디지털위상수학과 러프집합 이론을 융합한 국소유한 러프근사이론 연구 및 응용', spvsAgcNm: '전북대학교', rschRpbPeRschrRegNm: '한상언', bizDepartNm: '교육부', totRschDate: '2019-06-01~2029-05-31' },
      { ptcBsnsName: '이공학학술연구기반구축(R&D)', brdnBsnsName: '지역대학우수과학자지원사업', sbjtProplStrcSeNm: '(일반)연구개발과제', seleYy: '2019', rndSbjtNo: 'NR-2019050441', stepAnuSbjtNo: '2019R1I1A3A03050441', hanSbjtNm: '무인항공기 운항용 태양광 연계 일체형 재생연료전지 복합시스템 연구', spvsAgcNm: '인제대학교', rschRpbPeRschrRegNm: '김형만', bizDepartNm: '교육부', totRschDate: '2019-06-01~2029-05-31' },
      { ptcBsnsName: '이공학학술연구기반구축(R&D)', brdnBsnsName: '보호연구지원사업', sbjtProplStrcSeNm: '(일반)연구개발과제', seleYy: '2019', rndSbjtNo: 'NR-2019061067', stepAnuSbjtNo: '2019R1I1A2A03061067', hanSbjtNm: '기후변화에 따른 생태계 교란 식물 서양금혼초와 양미역취의 분포변화 모니터링-예측 및 생태적 대응방안에 대한 연구', spvsAgcNm: '제주대학교', rschRpbPeRschrRegNm: '송우람', bizDepartNm: '교육부', totRschDate: '2019-06-01~2029-05-31' },
      { ptcBsnsName: '개인기초연구(교육부)(R&D)', brdnBsnsName: '지역대학우수과학자지원사업', sbjtProplStrcSeNm: '(일반)연구개발과제', seleYy: '2018', rndSbjtNo: 'NR-2018042501', stepAnuSbjtNo: '2018R1D1A3B06042501', hanSbjtNm: '고령친화식품 개발을 위한 식품의 연화기작과 연화응용기술 연구 ', spvsAgcNm: '강원대학교', rschRpbPeRschrRegNm: '윤원병', bizDepartNm: '교육부', totRschDate: '2018-06-01~2028-05-31' }
    ]
  },
  methods: {
    search() {

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
      console.log('excelDownloadClick')
    }
  }
}
</script>

<style>

</style>
