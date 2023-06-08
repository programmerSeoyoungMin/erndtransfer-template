<template>
  <div class="app-container">
    <page-info :parent-name="'데이터 이관'" :child-name="'전체 이관'" :parent-link="'/ernd/trns/all'" :child-link="'/ernd/trns/all'" />
    <el-container>
      <el-header style="height: 200px;">
        <el-row :gutter="20" :align="'middle'" style="border: 1px solid lightgray;" type="flex">
          <el-col :span="7">
            <!-- 화면 좌측상단 진행상태 표시 field -->
            <!-- Case1 진행중인 프로시저 존재하는 경우-->
            <div v-show="prgrsPrcdExist" class="header-content">
              <h4>[이관ID : {{ trnsfInfo.trnsfId }}]</h4>
              <h5>진행단계 : {{ prgrsPrcdInfo.prcdNm }} ({{ prgrsPrcdInfo.prcdId }})<br>
                Error Occurred : {{ numberFormatter(prgrsPrcdInfo.errOcrnNocs) }} <br>
                Time taken : {{ prgrsPrcdInfo.reqTm }} <br>
                Inserted Rows : {{ numberFormatter(prgrsPrcdInfo.trnsfDataNocs) }}
              </h5>
            </div>
            <!-- Case2 진행중인 프로시저 존재하지 않는 경우 -->
            <div v-show="!prgrsPrcdExist" class="header-content">
              <!-- Case2-1 이관이 진행중인 경우 -->
              <h4 v-show="!transferEnd">
                실행중인 프로시저가 존재하지 않습니다.
              </h4>
              <!-- Case2-2 종료된 프로시저 존재-->
              <h4 v-show="transferEnd && lastTrnsfId !== ''">
                [이관ID : {{ trnsfInfo.trnsfId }}]
              </h4>
              <h5 v-show="transferEnd && lastTrnsfId !== ''">
                이관 시작 : {{ trnsfInfo.dmndDt }} <br>
                이관 종료 : {{ trnsfInfo.endDt }} <br>
                소요 시간 : {{ trnsfInfo.reqTm }} <br>
                이관 과제 : {{ numberFormatter(trnsfInfo.sbjtNocs) }} 건
              </h5>
              <!-- Case2-3 종료된 프로시저가 존재하지 않는경우 (최초이관화면진입시)-->
              <h4 v-show="transferEnd && (lastTrnsfId === '' || lastTrnsfId === undefined || lastTrnsfId === null)">
                이관 이력이 없습니다. <br>
                데이터 이관버튼을 클릭하여, 이관을 시작해주세요.
              </h4>
            </div>
          </el-col>
          <el-col :span="11">
            <div class="header-content">
              <h4>진행률</h4>
              <el-progress :text-inside="true" :stroke-width="18" :percentage="prgrsPrcdInfo.trnsfPrgrsRt" status="success" />
              <h5>이관데이터 구분별</h5>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="header-content" style="height:150px; display: flex;justify-content: flex-end;align-items: center;">
              <el-button ref="trnsfBtn" :type="trnsferBtnType" @click="trnsfBtnClick">
                <i class="fa-solid fa-link" />
                데이터 이관
              </el-button>
            </div>
          </el-col>
        </el-row>
        <!-- 그리드 상단 정보표시 field -->
        <!-- Case1 이관 진행 중 -->
        <el-row v-if="!transferEnd" :align="'middle'" :justify="'center'" type="flex">
          <el-col :span="1">
            <div style="margin-top:3px;">
              <el-button size="small" @click="retriveTransfer"><i class="fa-solid fa-refresh" /></el-button>
            </div>
          </el-col>
          <el-col :span="11">
            <div>
              <h4>이관 진행 현황<span v-show="prgrsPrcdInfo.trnsfPrgrsTm !== undefined && prgrsPrcdInfo.trnsfPrgrsTm !== ''">(진행시간 : {{ prgrsPrcdInfo.trnsfPrgrsTm }})</span></h4>
            </div>
          </el-col>
          <el-col :span="12">
            <div style="display: flex;justify-content: flex-end;align-items: center;">
              <h5 style="color:red;">데이터 이관은 중복실행이 불가능하며, 실행중인 이관이 종료될 때까지 이관 버튼은 비활성화 됩니다.</h5>
            </div>
          </el-col>
        </el-row>
        <!-- Case2 이관 종료 -->
        <el-row v-show="transferEnd" :align="'middle'" :justify="'center'" type="flex">
          <el-col :span="5">
            <div style="padding-left: 10px;">
              <h4>이관 진행 결과</h4>
            </div>
          </el-col>
          <el-col :span="19">
            <div style="display: flex;justify-content: flex-end;align-items: center;">
              <h5 style="color:red;">진행중인 이관이 없습니다. 가장 최근에 완료된 이관 정보가 표시됩니다.</h5>
            </div>
          </el-col>
        </el-row>
      </el-header>
      <el-main style="border:1px solid lightgray; margin-top: 10px;">
        <el-row>
          <el-table :data="dataList" :header-row-class-name="'tableHeader'" border>
            <el-table-column prop="prcdNm" label="이관 데이터 구분" :align="'left'" width="200px" />
            <el-table-column prop="prcdPrgrsSttus" label="진행상태" :align="'center'">
              <template slot-scope="scope">
                <div v-html="prgrsSttsColoring(scope.row.prcdPrgrsSttus)" />
              </template>
            </el-table-column>
            <el-table-column prop="allNocs" label="전체" :align="'right'">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.allNocs)" />
              </template>
            </el-table-column>
            <el-table-column prop="scsNocs" label="성공" :align="'right'">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.scsNocs)" />
              </template>
            </el-table-column>
            <el-table-column prop="exclNocs" label="미이관" :align="'right'">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.etcNocs)" />
              </template>
            </el-table-column>
            <el-table-column prop="errNocs" label="오류" :align="'right'">
              <template slot-scope="scope">
                <div :style="scope.row.errNocs === '0' ? 'color:black' : 'color:red'" v-html="numberFormatter(scope.row.errNocs)" />
              </template>
            </el-table-column>
            <el-table-column prop="trnsfDataNocs" label="이관데이터 count" :align="'right'">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.trnsfDataNocs)" />
              </template>
            </el-table-column>
            <el-table-column prop="reqTm" label="소요시간" :align="'center'" />
          </el-table>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import '@fortawesome/fontawesome-free/css/all.css'
import PageInfo from '../common/PageInfo.vue'

import Axios from 'axios'

export default {
  components: {
    PageInfo
  },
  data() {
    return {
      // 진행 프로시저 존재여부(T/F)
      prgrsPrcdExist: false,
      // 이관 종료 여부(T/F)
      transferEnd: false,
      // 진행 이관 존재여부(Y/N)
      prgrsTrnsfExistYn: 'N',
      // 진행 중 이관 아이디
      currentTrnsfId: '',
      // 최종 이관 아이디
      lastTrnsfId: '',
      // 이관 진행현황 리스트
      dataList: [],
      // 진행 프로시저 정보
      prgrsPrcdInfo: {
        // 프로시저ID
        prcdId: '',
        // 프로시저명
        prcdNm: '',
        // 오류 발생 cnt
        errOcrnNocs: 0,
        // 소요시간
        reqTm: '',
        // inserted Rows
        trnsfDataNocs: '',
        // 진행률
        trnsfPrgrsRt: 0,
        // 진행시간
        trnsfPrgrsTm: ''
      },
      // 이관 정보
      trnsfInfo: {
        // 이관 ID
        trnsfId: '',
        // 요청 일시
        dmndDt: '',
        // 종료 일시
        endDt: '',
        // 과제 수
        sbjtNocs: 0,
        // 소요시간
        reqTm: ''
      },
      // 이관 버튼 타입
      trnsferBtnType: 'primary'
    }
  },
  async mounted() {
    const trnsfParam = {
      trnsfSe: 'TA', // trnsfSe : TA (전체이관), TE (개별이관)
      trnsfId: ''
    }
    // 진행중인 프로시저 존재여부 확인
    const prgrsTrnsfExistYnRes = await Axios.post('http://localhost:8080/trnsf/retrivePrgrsTrnsfExistYn', trnsfParam)
    this.prgrsTrnsfExistYn = prgrsTrnsfExistYnRes.data
    // 진행중인 이관이 존재하는 경우, 이관 ID 및 이관 정보 조회
    if (this.prgrsTrnsfExistYn === 'Y') {
      // 현재 진행중인 이관 아이디 조회
      const currentTrnsfIdRes = await Axios.post('http://localhost:8080/trnsf/retriveCurrentTrnsfId', trnsfParam)
      this.currentTrnsfId = currentTrnsfIdRes.data
      this.trnsferBtnType = 'default'
      this.transferEnd = false
      this.retriveTransfer()
    } else { // 진행중인 이관이 존재하지 않는 경우, 마지막으로 진행된 이관 ID 및 이관정보 조회
      // 마지막으로 진행된 이관 아이디 조회
      const lastTrnsfIdRes = await Axios.post('http://localhost:8080/trnsf/retriveLastTrnsfId', trnsfParam)
      this.lastTrnsfId = lastTrnsfIdRes.data
      this.trnsferBtnType = 'primary'
      // 마지막으로 진행된 이관 존재여부에 상관없이 진행중인 이관이 없을경우 종료 True
      this.transferEnd = true
      this.retriveTransfer()
    }

    // 병합 헤더 설정
    this.$nextTick(() => {
      this.setHeaderStyle()
    })
  },
  methods: {
    setHeaderStyle() {
      const header = {
        mergeHeader: '',
        mergeHeader2: '',
        tr: document.querySelector('.tableHeader'),
        thList: document.querySelectorAll('.tableHeader th')
      }

      header.mergeHeader += '<tr class="tableHeader">'
      for (let i = 0; i < header.thList.length; i++) {
        header.thList[i].setAttribute('style', 'text-align:center;')
        if (i === 0 || i === 1 || i === 6 || i === 7) {
          header.thList[i].setAttribute('rowspan', 2)
          header.mergeHeader += header.thList[i].outerHTML
        }
        if (i === 2 || i === 3 || i === 4 || i === 5) {
          header.mergeHeader2 += header.thList[i].outerHTML
        }
        if (i === 2) {
          header.thList[i].setAttribute('colspan', 4)
          header.thList[i].classList.remove('is-leaf')
          header.mergeHeader += header.thList[i].outerHTML.replace('전체', 'e-R&D과제건수기준')
        }
      }
      header.mergeHeader += '</tr>' +
                  '<tr class="tableHeader">' + header.mergeHeader2 + '</tr>'

      document.querySelector('.el-table__header thead').innerHTML = header.mergeHeader
      // document.querySelector('.el-table__header thead').innerHTML = header.html
    },
    prgrsSttsColoring(prgrsStts) {
      if (prgrsStts === '완료') {
        return '<span style="color:#00B050;">' + prgrsStts + '</span>'
      } else if (prgrsStts === '진행중') {
        return '<span style="color:blue;">' + prgrsStts + '</span>'
      } else {
        return '<span style="color:black;">' + prgrsStts + '</span>'
      }
    },
    numberFormatter(value) {
      if (value === 0) return 0
      if (!value) return ''

      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    },
    async trnsfBtnClick() {
      const trnsfParam = {
        trnsfSe: 'TA',
        trnsfId: this.currentTrnsfId
      }
      if (this.prgrsTrnsfExistYn === 'Y' && this.currentTrnsfId !== '' && !this.transferEnd) {
        this.$alert('진행중인 이관이 존재합니다.', '알림', {
          confirmButtonText: '확인',
          type: 'error'
        })
        return
      } else {
        const h = this.$createElement
        const confirm = await this.$confirm('', '', {
          confirmButtonText: '확인',
          cancelButtonText: '취소',
          customClass: 'confirm-model',
          message: h('div', null, [
            h('i', { class: 'el-icon-check', style: 'color:#f90;font-size:30px;' }),
            h('span', { style: 'margin-left:10px;font-size:16px;line-height:30px;font-weight:600;vertical-align:top;' }, '확인'),
            h('p', { style: 'margin:10px 0 0 40px;' }, '데이터 이관을 시작할 경우, 전체 이관이 완료되기 전까지 절대 중지할 수 없습니다.'),
            h('p', { style: 'margin:10px 0 0 40px;' }, '진행하시겠습니까?')
          ])
        }).catch((action) => {
          if (action === 'cancel') {
            this.$message({
              type: 'info',
              message: '취소되었습니다.'
            })
            return false
          }
        })
        if (confirm) {
          Axios.post('http://localhost:8080/trnsf/registTrnsfMaster', trnsfParam)
          window.location.reload()
        }
      }
    },
    async retriveTransfer() {
      const trnsfParam = {
        trnsfSe: 'TA',
        trnsfId: this.currentTrnsfId === '' ? this.lastTrnsfId : this.currentTrnsfId
      }
      if (trnsfParam.trnsfId === '') {
        return
      }

      const currentTrnsfInfoRes = await Axios.post('http://localhost:8080/trnsf/retriveCurrentTrnsfInfo', trnsfParam)

      // 종료확인 후 종료여부 및 이관 정보설정
      if (currentTrnsfInfoRes.data.endDt !== null && currentTrnsfInfoRes.data.endDt !== undefined && currentTrnsfInfoRes.data.endDt !== '') {
        // 종료 처리
        this.currentTrnsfId = ''
        this.trnsferBtnType = 'primary'
        this.transferEnd = true
        this.prgrsPrcdExist = false
        this.lastTrnsfId = currentTrnsfInfoRes.data.trnsfId
        // 이관 정보 조회
        this.trnsfInfo.trnsfId = currentTrnsfInfoRes.data.trnsfId
        this.trnsfInfo.dmndDt = currentTrnsfInfoRes.data.dmndDt
        this.trnsfInfo.endDt = currentTrnsfInfoRes.data.endDt
        this.trnsfInfo.sbjtNocs = currentTrnsfInfoRes.data.sbjtNocs
        this.trnsfInfo.reqTm = currentTrnsfInfoRes.data.reqTm
      } else {
        this.trnsfInfo.trnsfId = currentTrnsfInfoRes.data.trnsfId
        this.transferEnd = false
      }
      // 진행중인 이관 프로시저 정보 조회
      const prgrsPrcdInfoRes = await Axios.post('http://localhost:8080/trnsf/retrivePrgrsPrcdInfo', trnsfParam)
      this.prgrsPrcdInfo = prgrsPrcdInfoRes.data
      if (prgrsPrcdInfoRes.data.prcdId !== null && prgrsPrcdInfoRes.data.prcdId !== undefined && prgrsPrcdInfoRes.data.prcdId !== '') {
        this.prgrsPrcdExist = true
      } else {
        this.prgrsPrcdExist = false
      }
      if ((this.transferEnd || !this.prgrsPrcdExist) && (this.lastTrnsfId !== '' && this.lastTrnsfId !== undefined && this.lastTrnsfId !== null)) {
        this.prgrsPrcdInfo = { trnsfPrgrsRt: 100 }
      }

      // 이관 프로시저 수행 목록 조회
      const prgrsPrcdListRes = await Axios.post('http://localhost:8080/trnsf/retrivePrgrsPrcdList', trnsfParam)
      this.dataList = prgrsPrcdListRes.data
    }
  }
}
</script>

<style>
.confirm-model .el-message-box__btns .el-button:nth-child(1) {
  float:right;
}
.confirm-model .el-message-box__btns .el-button:nth-child(2) {
  margin-right:10px;
  background-color:#2d8cf0;
  border-color:#2d8cf0;
}
</style>
