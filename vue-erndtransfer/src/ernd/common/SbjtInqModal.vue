<template>
  <modal
    :title="'과제 상세보기'"
    :max-width="'1400px'"
    :max-height="'1000px'"
    :close-flag="closeFlag"
    @close="closeBtnClick"
  >
    <div slot="content">
      <!-- <div class="app-container"> -->
      <h3>과제 정보</h3>
      <div class="createPost-container">
        <aside>
          <el-form class="form-container">
            <el-row style="margin-top:20px">
              <el-col :span="9">
                <el-form-item label-width="180px" label="e-R&D 과제번호">
                  <span class="form-item"> {{ sbjtTrnsResult.erndSbjtNo }} </span>
                </el-form-item>
              </el-col>
              <el-col :span="9">
                <el-form-item label-width="180px" label="IRIS 과제번호">
                  <span class="form-item">{{ sbjtTrnsResult.irisSbjtId }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="18">
                <el-form-item label-width="180px" label="과제명">
                  <span class="form-item">{{ sbjtTrnsResult.hanSbjtNm }}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </aside>
      </div>
      <!-- </div> -->
      <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
        <el-tab-pane label="과제기본" name="first">
          <el-form class="sbjtTable">
            <el-row>
              <el-col :span="8">
                <el-form-item label-width="160px" label="전문기관">
                  <span>{{ sbjtTrnsResult.sorgnNm }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label-width="130px" label="연구개발과제번호">
                  <span>{{ sbjtTrnsResult.rndSbjtNo }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label-width="130px" label="총괄연구개발번호">
                  <span>{{ sbjtTrnsResult.ovrlRndSbjtNo }}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label-width="160px" label="세부사업명">
                  <span>{{ sbjtTrnsResult.ptcBsnsName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label-width="130px" label="내역사업명">
                  <span>{{ sbjtTrnsResult.brdnBsnsName }}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label-width="160px" label="통합공고명">
                  <span>{{ sbjtTrnsResult.ancmTl }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label-width="130px" label="사업공고명">
                  <span>{{ sbjtTrnsResult.bsnsAncmTl }}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label-width="160px" label="총괄연구개발명(국문)">
                  <span>{{ sbjtTrnsResult.hanOvrlRndNm }}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label-width="160px" label="총괄연구개발명(영문)">
                  <span>{{ sbjtTrnsResult.engOvrlRndNm }}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label-width="160px" label="연구개발과제명(국문)">
                  <span>{{ sbjtTrnsResult.hanSbjtNm }}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label-width="160px" label="연구개발과제명(영문)">
                  <span>{{ sbjtTrnsResult.engSbjtNm }}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label-width="160px" label="총개발기간">
                  <span>{{ sbjtTrnsResult.ttlRschPrid }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label-width="130px" label="연구개발기간구성">
                  <span>{{ sbjtTrnsResult.rschPridCpstSe }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label-width="130px" label="연구기간설정구분">
                  <span>{{ sbjtTrnsResult.rschPridStngSe }}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <el-form class="sbjtKwd" style="margin-top:20px;">
            <el-row>
              <el-col :span="24">
                <el-form-item label-width="160px" label="핵심어(국문)">
                  <span class="form-item">{{ sbjtKwd.kr1KwdNm }}</span>
                  <span class="form-item">{{ sbjtKwd.kr2KwdNm }}</span>
                  <span class="form-item">{{ sbjtKwd.kr3KwdNm }}</span>
                  <span class="form-item">{{ sbjtKwd.kr4KwdNm }}</span>
                  <span class="form-item">{{ sbjtKwd.kr5KwdNm }}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label-width="160px" label="핵심어(영문)">
                  <span class="form-item">{{ sbjtKwd.en1KwdNm }}</span>
                  <span class="form-item">{{ sbjtKwd.en2KwdNm }}</span>
                  <span class="form-item">{{ sbjtKwd.en3KwdNm }}</span>
                  <span class="form-item">{{ sbjtKwd.en4KwdNm }}</span>
                  <span class="form-item">{{ sbjtKwd.en5KwdNm }}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="연구비" name="second">
          <el-table :data="dataList" height="400px" border show-summary :summary-method="getSummaries" :span-method="onSpanMethod">
            <el-table-column prop="sbjtStep" label="과제 단계" :align="'center'" width="100px" />
            <el-table-column prop="sbjtAnnl" label="과제 연차" :align="'center'" width="100px" />
            <el-table-column prop="rschOrgnRoleSeNm" label="역할" :align="'center'" width="100px" />
            <el-table-column prop="rschOrgnNm" label="기관명" :align="'center'" width="220px" />
            <el-table-column prop="itepdCdNm" label="항목" :align="'center'" width="220px" />
            <el-table-column prop="bndsCashAm" label="현금" :align="'right'" header-align="center">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.bndsCashAm)" />
              </template>
            </el-table-column>
            <el-table-column prop="bndsSpotAm" label="현물" :align="'right'" header-align="center">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.bndsSpotAm)" />
              </template>
            </el-table-column>
            <el-table-column prop="bndsTotAm" label="합계" :align="'right'" header-align="center">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.bndsTotAm)" />
              </template>
            </el-table-column>
            <el-table-column prop="bndsNpayAm" label="미지급" :align="'right'" header-align="center">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.bndsNpayAm)" />
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div> <!-- slot end -->
  </modal>
</template>

<script>
import Modal from './Modal'

import '@fortawesome/fontawesome-free/css/all.css'

import Axios from 'axios'
import { spanRow } from 'element-ui-table-span-method'

export default {
  components: {
    Modal
  },
  props: {
    rndSbjtNo: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      showModal: false,
      sbjtTrnsResult: {
        ancmTl: '', // 공고 제목
        ancmNo: '', // 공고 번호
        bsnsYy: '', // 사업년도(W2)
        erndSbjtNo: '', // e-R&D 과제번호
        hirkSorgnBsnsCd: '', // 상위 전문기관 사업 코드
        hirkSorgnBsnsCdNm: '', // 상위 전문기관 사업 명
        irisSbjtId: '', // IRIS과제번호
        sorgnBsnsCd: '', // 전문기관 사업 코드
        sorgnBsnsNm: '', // 전문기관 사업 명
        pbofrTpSe: '', // 공모 유형 구분[AB1]
        bsnsAncmTl: '', // 사업 공고 제목
        cortiumCpstSe: '', // 컨소시엄 구성 구분[AX7]
        sbjtRcveFormSe: '', // 과제 접수 형태 구분[AT6]
        rschPridStngSe: '', // 연구 기간 설정 구분[AK3]
        rschPridCpstSe: '', // 연구 기간 구성 구분[AB5]
        ttlGvstmAm: '', // 총 정부지원금 금액
        ovrlSbjtId: '', // 총괄 과제 ID
        hanOvrlRndNm: '', // 한글 총괄 연구개발 명
        engOvrlRndNm: '', // 영문 총괄 연구개발 명
        sbjtId: '', // 과제 ID
        hanSbjtNm: '', // 한글 과제 명
        engSbjtNm: '', // 영문과제명.(데이터 이관시 자릿수 모자람 요청by 김태우 과장님)
        prgSbjtExeAnnl: '', // 진행 과제 수행 연차
        prgBsnsYy: '', // 진행 사업 년도
        prgSorgnBsnsCd: '', // 진행 전문기관 사업 코드
        scurDtlSe: '', // 보안 상세 구분[AG8]
        ttlRschPrid: '', // 총개발기간
        rschOrgnId: '', // 연구 기관 ID
        rschOrgnNm: '', // 연구 기관 명
        natSe: '', // 국가구분
        rscrNm: '', // 연구자 명
        sorgnNm: '', // 전문기관 명
        rndSbjtNo: '', // 연구개발 과제 번호
        ovrlRndSbjtNo: ''// 총괄 연구개발과제번호
      },
      sbjtKwd: {
        kr1KwdNm: '',
        kr2KwdNm: '',
        kr3KwdNm: '',
        kr4KwdNm: '',
        kr5KwdNm: '',
        en1KwdNm: '',
        en2KwdNm: '',
        en3KwdNm: '',
        en4KwdNm: '',
        en5KwdNm: ''
      },
      // 연구비 tab
      dataList: [],
      option: [
        { index: 0, field: 'sbjtStep' },
        { index: 1, field: 'sbjtAnnl' },
        { index: 2, field: 'rschOrgnRoleSeNm' },
        { index: 3, field: 'rschOrgnNm' }
      ],
      // Tab
      activeName: 'first',
      // Modal
      closeFlag: true
    }
  },
  async mounted() {
    const searchParams = {}
    searchParams.rndSbjtNo = this.rndSbjtNo

    // 과제기본정보조회
    await Axios.post('http://localhost:8080/result/retriveSbjtTrnsResultDetail', searchParams)
      .then(response => {
        this.sbjtTrnsResult = response.data
      })
      .catch(error => {
        console.log(error)
      })
    // 과제 키워드 조회
    searchParams.sbjtId = this.sbjtTrnsResult.irisSbjtId

    await Axios.post('http://localhost:8080/result/retriveSbjtTrnsResultKwd', searchParams)
      .then(response => {
        this.sbjtKwd = response.data
      })
      .catch(error => {
        console.log(error)
      })
    // 연구비조회
    await Axios.post('http://localhost:8080/result/retriveSbjtTrnsResultItepdList', searchParams)
      .then(response => {
        this.dataList = response.data
      })
      .catch(error => {
        console.log(error)
      })
    document.querySelectorAll('.sbjtTable .el-form-item__label').forEach((item) => {
      item.style.backgroundColor = '#efefef'
      item.style.borderRight = '1px solid #ddd'
      item.style.borderLeft = '1px solid #ddd'
    })
    document.querySelectorAll('.sbjtTable .el-form-item__content').forEach((item) => {
      item.style.paddingLeft = '10px'
    })
    document.querySelectorAll('.sbjtKwd .el-form-item__label').forEach((item) => {
      item.style.backgroundColor = '#efefef'
      item.style.borderRight = '1px solid #ddd'
      item.style.borderLeft = '1px solid #ddd'
    })
    document.querySelectorAll('.sbjtKwd .el-form-item__content').forEach((item) => {
      item.style.paddingLeft = '10px'
    })
  },
  methods: {
    closeBtnClick() {
      this.$emit('onClose')
    },
    handleClick(tab, event) {
      console.log(tab, event)
    },
    numberFormatter(value) {
      if (value !== null && value !== undefined && value !== '') {
        return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
      } else {
        return value
      }
    },
    getSummaries(param) {
      console.log('getSummaries')
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0 || index === 1 || index === 2 || index === 3) {
          sums[index] = ''
          return
        }
        if (index === 4) {
          sums[index] = '합계'
          return
        }
        const values = data.map(item => Number(item[column.property]))
        if (!values.every(value => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr)
            if (!isNaN(value)) {
              return prev + curr
            } else {
              return prev
            }
          }, 0)
          sums[index] = sums[index].toLocaleString()
        } else {
          sums[index] = '--'
        }
      })
      return sums
    },
    onSpanMethod({ row, column, rowIndex, columnIndex }) {
      console.log('onSpanMethod')
      return spanRow(
        { row, column, rowIndex, columnIndex },
        this.dataList,
        this.option
      )
    }
  }
}
</script>

<style scoped>
.form-item {
  background-color: white;
  padding-top: 5px;
  padding-bottom: 5px;
  padding-left: 10px;
  padding-right: 10px;
  border-radius: 4px;
  margin-right: 5px;
  border: 1px solid #ddd;
  color: #606266;
}
.el-form-item {
  margin-bottom: 0px;
}
.sbjtTable .el-row {
  border-bottom: 1px solid #ddd;
}

.sbjtKwd .el-row {
  border-bottom: 1px solid #ddd;
}

.sbjtTable .el-row:nth-child(1) {
  border-top: 1px solid #ddd;
}
.sbjtKwd .el-row:nth-child(1) {
  border-top: 1px solid #ddd;
}

</style>
