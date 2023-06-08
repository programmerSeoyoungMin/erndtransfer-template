<template>
  <div class="app-container">
    <page-info :parent-name="'데이터 이관'" :child-name="'개별 이관'" :parent-link="'/ernd/trns/all'" :child-link="'/ernd/trns/each'" />
    <el-container>
      <el-header style="height: 200px;">
        <el-row :gutter="20" :align="'middle'" style="height:150px; border: 1px solid lightgray;" type="flex">
          <el-col :span="6">
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
              <!-- Case2-1 파일업로드만 한 경우 -->
              <h4 v-show="!transferEnd && fileUpload">
                [이관ID : {{ trnsfInfo.trnsfId }}] <br>
                <span v-show="!transferEnd && fileUpload && trnsfInfo.uldRowCnt != '' && trnsfInfo.uldRowCnt != null">
                  이관 대상 과제 : {{ trnsfInfo.uldRowCnt }} 건
                </span>
              </h4>
              <h5 v-show="!transferEnd && fileUpload">
                파일 업로드 후 대기중입니다. <br>
                데이터이관버튼을 클릭하여, 진행하십시오.
              </h5>
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
                파일 업로드 후 데이터 이관버튼을 클릭하여 <br>
                이관을 시작해주세요.
              </h4>
            </div>
          </el-col>
          <el-col :span="10">
            <div class="header-content">
              <h4>진행률</h4>
              <el-progress :text-inside="true" :stroke-width="18" :percentage="prgrsPrcdInfo.trnsfPrgrsRt" status="success" />
              <h5>이관데이터 구분별</h5>
            </div>
          </el-col>
          <el-col :span="8" style="height:150px;">
            <el-row :gutter="20" style="padding:5px" type="flex">
              <el-col>
                <div class="uploadzone">
                  <div class="dropzone">
                    <span>{{ fileName }}</span>
                    <!-- 파일 업로드 후, 이관을 진행하지 않은 경우만 삭제버튼표시 -->
                    <el-button v-show="fileUpload && prgrsTrnsfExistYn === 'N'" type="default" size="mini" icon="el-icon-delete" @click="handleFileClear" />
                    <input ref="fileInput" type="file" style="display:none" @change="handleFileChange">
                    <el-button type="default" size="mini" @click="openFileSelect">파일선택</el-button>
                  </div>
                </div>
              </el-col>
            </el-row>
            <el-row type="flex">
              <el-col>
                <div style="display:flex; justify-content: flex-end; align-items: center; padding:5px;">
                  <el-button type="info" class="download-btn" @click="handleDownload"><i class="fa-solid fa-download" />&nbsp;&nbsp;서식 다운로드</el-button>
                  <el-button type="primary" class="upload-btn" @click="handleUpload"><i class="fa-solid fa-floppy-disk" />&nbsp;&nbsp;파일 업로드</el-button>
                </div>
              </el-col>
            </el-row>
            <el-row>
              <div style="display:flex; justify-content: flex-end; align-items: center; padding:5px;">
                <el-button :type="trnsferBtnType" @click="trnsfBtnClick"><i class="fa-solid fa-link" />&nbsp;&nbsp;데이터 이관</el-button>
              </div>
            </el-row>
          </el-col>
        </el-row>
        <!-- 그리드 상단 정보표시 field -->
        <!-- Case1 이관 진행 중 -->
        <el-row v-show="!transferEnd" h4 :align="'middle'" :justify="'center'" style="height:50px;" type="flex">
          <el-col :span="1">
            <div>
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
        <el-row v-show="transferEnd" h4 :align="'middle'" :justify="'center'" style="height:50px;" type="flex">
          <el-col :span="12">
            <div style="padding-left: 10px;">
              <h4>이관 진행 결과</h4>
            </div>
          </el-col>
          <el-col :span="12">
            <div style="display: flex;justify-content: flex-end;align-items: center;">
              <h5 style="color:red;">진행중인 이관이 없습니다. 가장 최근에 완료된 이관 정보가 표시됩니다.</h5>
            </div>
          </el-col>
        </el-row>
      </el-header>
      <el-main style="border:1px solid lightgray">
        <el-row type="flex">
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
      // 이관 파일명
      fileName: '',
      // 이관 종료 여부(T/F)
      transferEnd: false,
      // 이관 파일 업로드 여부(T/F)
      fileUpload: false,
      // 진행 프로시저 존재 여부(T/F)
      prgrsPrcdExist: false,
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
        reqTm: '',
        // 이관 파일 업로드 row count
        uldRowCnt: ''
      },
      // 이관 버튼 타입
      trnsferBtnType: 'primary'
    }
  },
  async mounted() {
    const trnsfParam = {
      trnsfSe: 'TE', // trnsfSe : TA (전체이관), TE (개별이관)
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
      await this.retriveTransfer()
    } else { // 진행중인 이관이 존재하지 않는 경우, 마지막으로 진행된 이관 ID 및 이관정보 조회
      // 마지막으로 진행된 이관 아이디 조회
      const lastTrnsfIdRes = await Axios.post('http://localhost:8080/trnsf/retriveLastTrnsfId', trnsfParam)
      this.lastTrnsfId = lastTrnsfIdRes.data
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
    // 파일 선택 open
    async openFileSelect() {
      if (this.prgrsTrnsfExistYn === 'Y' && !this.transferEnd) {
        this.$alert('진행중인 이관이 존재합니다.', '알림', {
          confirmButtonText: '확인',
          type: 'error'
        })
        return false
      } else if (this.fileUpload && !this.transferEnd) {
        const result = await this.checkUploadFile()
        if (result) {
          this.$refs.fileInput.click()
        } else {
          return false
        }
      } else {
        this.$refs.fileInput.click()
      }
    },
    // 파일변경 시, 파일명 field에 표시
    handleFileChange(event) {
      const files = event.target.files
      if (files.length > 0) {
        this.fileName = files[0].name
      }
    },
    // 파일 삭제
    async handleFileClear() {
      const h = this.$createElement
      const confirm = await this.$confirm('', '', {
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        customClass: 'confirm-model',
        message: h('div', null, [
          h('i', { class: 'el-icon-check', style: 'color:#f90;font-size:30px;' }),
          h('span', { style: 'margin-left:10px;font-size:16px;line-height:30px;font-weight:600;vertical-align:top;' }, '확인'),
          h('p', { style: 'margin:10px 0 0 40px;' }, '업로드 된 파일을 삭제하시겠습니까?')
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
        const trnsfParam = {
          trnsfSe: 'TE',
          trnsfId: this.currentTrnsfId
        }
        // 파일 삭제
        await Axios.post('http://localhost:8080/trnsf/deleteTrnsfUploadFile', trnsfParam)
        this.fileName = ''
        this.fileUpload = false
        this.$refs.fileInput.value = ''
        // 파일 삭제 후, last 이관 ID 조회
        const lastTrnsfIdRes = await Axios.post('http://localhost:8080/trnsf/retriveLastTrnsfId', trnsfParam)
        this.transferEnd = true
        this.lastTrnsfId = lastTrnsfIdRes.data
        // 진행중 이관 ID 초기화
        this.currentTrnsfId = ''
        this.$message({
          type: 'info',
          message: '삭제되었습니다.'
        })
        this.retriveTransfer()
      }
    },
    // 파일 업로드
    async handleUpload() {
      const file = this.$refs.fileInput.files[0]
      if (this.prgrsTrnsfExistYn === 'Y' && !this.transferEnd) { // 이관이 진행중인 경우
        this.$alert('진행중인 이관이 존재합니다.', '알림', {
          confirmButtonText: '확인',
          type: 'error'
        })
        return
      } else if (this.fileUpload && !this.transferEnd) { // 이미 파일을 업로드한 경우
        const result = await this.checkUploadFile()
        if (result) {
          this.$refs.fileInput.click()
        } else {
          return false
        }
      } else {
        // 파일 존재여부 확인
        if (file === undefined || file === null || file === '') {
          this.$alert('업로드할 엑셀 파일을 선택해주세요.', '알림', {
            confirmButtonText: '확인',
            type: 'error'
          })
          return
        }
      }
      // 파일업로드 시작 전 confirm
      const h = this.$createElement
      const confirm = await this.$confirm('', '', {
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        customClass: 'confirm-model',
        message: h('div', null, [
          h('i', { class: 'el-icon-check', style: 'color:#f90;font-size:30px;' }),
          h('span', { style: 'margin-left:10px;font-size:16px;line-height:30px;font-weight:600;vertical-align:top;' }, '확인'),
          h('p', { style: 'margin:10px 0 0 40px;' }, '파일 업로드를 진행하시겠습니까?'),
          h('p', { style: 'margin:10px 0 0 40px;' }, '파일 업로드 후, 데이터 이관버튼을 클릭하십시오.')
        ])
      }).catch(() => {
        return false
      })
      // 파일업로드 시작
      if (confirm) {
        const formData = new FormData()
        formData.append('file', file) // 파일 데이터 추가
        formData.append('category', 'trnsfSbjt')
        const config = {
          headers: {
            'Content-Type': 'multipart/form-data' // 헤더 설정
          }
        }
        const uploadRes = await Axios.post('http://localhost:8080/excel/upload', formData, config)
        if (uploadRes.data.result === 'templateError') {
          this.$refs.fileInput.value = ''
          this.fileName = ''
          this.$alert('서식파일이 맞지 않습니다. 파일을 다시 선택해 주십시오.', '알림', {
            confirmButtonText: '확인',
            type: 'error'
          })
        } else if (uploadRes.data.result === 'sizeError') {
          this.$refs.fileInput.value = ''
          this.fileName = ''
          this.$alert('저장할 데이터가 존재하지 않습니다. 파일을 다시 선택해 주십시오.', '알림', {
            confirmButtonText: '확인',
            type: 'error'
          })
        } else if (uploadRes.data.result === 'success') {
          // 성공인 경우만, fileUpload flag를 true로 변경
          this.fileUpload = true
          this.transferEnd = false
          this.prgrsTrnsfExistYn = 'Y'
          this.uldRowCnt = uploadRes.data.uldRowCnt
          const trnsfParam = {
            trnsfSe: 'TE',
            trnsfId: ''
          }
          // 진행중인 이관ID 조회 > 파일 업로드 시, 이관ID 생성하므로 조회
          const currentTrnsfIdRes = await Axios.post('http://localhost:8080/trnsf/retriveCurrentTrnsfId', trnsfParam)
          this.currentTrnsfId = currentTrnsfIdRes.data
          this.lastTrnsfId = ''
          this.retriveTransfer()
        }
      } else {
        this.$message({
          type: 'info',
          message: '취소되었습니다.'
        })
      }
    },
    // 서식 다운로드
    handleDownload() {
      // fileName 파라미터에 다운로드 될 파일명을 입력함.
      const downloadUrl = 'http://localhost:8080/file/templates/download?fileName=TRNSF_SBJT.xlsx'
      Axios.get(downloadUrl, {
        responseType: 'blob' // 응답 데이터 타입
      }).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', '이관_엑셀업로드서식.xlsx') // 다운로드될 파일이름
        document.body.appendChild(link)
        link.click()
      })
    },
    /*
      Grid Function (setHeaderStyle, prgrsSttsColoring, numberFormatter)
    */
    // 테이블 Header Style 변경
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
    // 진행상태에 따른 항목 색상 변경
    prgrsSttsColoring(prgrsStts) {
      if (prgrsStts === '완료') {
        return '<span style="color:#00B050;font-weight: bold;">' + prgrsStts + '</span>'
      } else if (prgrsStts === '진행중') {
        return '<span style="color:blue;font-weight: bold;">' + prgrsStts + '</span>'
      } else {
        return '<span style="color:black;font-weight: bold;">' + prgrsStts + '</span>'
      }
    },
    // 숫자 3자리마다 콤마(,) 입력
    numberFormatter(value) {
      if (value === 0) return 0
      if (!value) return ''

      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    },
    // 이관버튼 클릭
    async trnsfBtnClick() {
      // 파일 업로드 여부 확인
      if (!this.fileUpload) {
        this.$alert('업로드한 파일이 존재하지 않습니다. 파일 먼저 업로드해주십시오.', '알림', {
          confirmButtonText: '확인',
          type: 'error'
        })
        return
      }
      // 진행중인 이관이 존재하는지 확인
      if (this.prgrsTrnsfExistYn === 'Y' && !this.transferEnd) {
        this.$alert('진행중인 이관이 존재합니다.', '알림', {
          confirmButtonText: '확인',
          type: 'error'
        })
        return
      } else {
        // 이관 시작 전 Confirm
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
        // 이관 시작
        if (confirm) {
          const trnsfParam = {
            trnsfSe: 'TE',
            trnsfId: this.currentTrnsfId
          }
          Axios.post('http://localhost:8080/trnsf/registTrnsfMaster', trnsfParam)
          window.location.reload()
        }
      }
    },
    // 이관 조회
    async retriveTransfer() {
      const trnsfParam = {
        trnsfSe: 'TE',
        trnsfId: this.currentTrnsfId === '' ? this.lastTrnsfId : this.currentTrnsfId
      }
      if (trnsfParam.trnsfId === '') {
        return
      }
      const currentTrnsfInfoRes = await Axios.post('http://localhost:8080/trnsf/retriveCurrentTrnsfInfo', trnsfParam)
      // 시작 여부 조회
      // 업로드 후, 이관버튼 클릭한 상태만 dmnd_dt가 존재함
      if (currentTrnsfInfoRes.data.dmndDt !== null && currentTrnsfInfoRes.data.dmndDt !== undefined && currentTrnsfInfoRes.data.dmndDt !== '') {
        this.prgrsTrnsfExistYn = 'Y'
        this.trnsferBtnType = 'defulat'
      } else {
        this.prgrsTrnsfExistYn = 'N'
        this.trnsferBtnType = 'primary'
      }

      // 종료확인 후 종료여부 설정 (진행중이던 과제 정보 초기화 및 완료정보 표시)
      if (currentTrnsfInfoRes.data.endDt !== null && currentTrnsfInfoRes.data.endDt !== undefined && currentTrnsfInfoRes.data.endDt !== '') {
        // 종료 처리
        this.trnsferBtnType = 'primary'
        this.prgrsPrcdExist = false
        this.transferEnd = true
        this.lastTrnsfId = currentTrnsfInfoRes.data.trnsfId
        this.currentTrnsfId = ''
        this.fileName = ''
        this.fileUpload = false
        // 이관정보 조회결과 표시 (이관ID, 요청일시, 종료일시, 과제명, 소요시간)
        this.trnsfInfo.trnsfId = currentTrnsfInfoRes.data.trnsfId
        this.trnsfInfo.dmndDt = currentTrnsfInfoRes.data.dmndDt
        this.trnsfInfo.endDt = currentTrnsfInfoRes.data.endDt
        this.trnsfInfo.sbjtNocs = currentTrnsfInfoRes.data.sbjtNocs
        this.trnsfInfo.reqTm = currentTrnsfInfoRes.data.reqTm
      } else {
        this.trnsfInfo.trnsfId = currentTrnsfInfoRes.data.trnsfId
        this.transferEnd = false
      }

      // 파일 업로드 여부 판단
      if (!this.transferEnd && currentTrnsfInfoRes.data.uldFileNm !== undefined && currentTrnsfInfoRes.data.uldFileNm !== '') {
        this.fileUpload = true
        this.fileName = currentTrnsfInfoRes.data.uldFileNm
        this.trnsfInfo.uldRowCnt = currentTrnsfInfoRes.data.uldRowCnt
      } else {
        this.fileUpload = false
        this.fileName = ''
      }

      // 진행중인 이관 프로시저 정보 조회
      const prgrsPrcdInfoRes = await Axios.post('http://localhost:8080/trnsf/retrivePrgrsPrcdInfo', trnsfParam)
      this.prgrsPrcdInfo = prgrsPrcdInfoRes.data

      if (prgrsPrcdInfoRes.data.prcdId !== null && prgrsPrcdInfoRes.data.prcdId !== '' && prgrsPrcdInfoRes.data.prcdId !== undefined) {
        this.prgrsPrcdExist = true
      } else {
        this.prgrsPrcdExist = false
      }
      // 이관 프로시저 수행 목록 조회
      const prgrsPrcdListRes = await Axios.post('http://localhost:8080/trnsf/retrivePrgrsPrcdList', trnsfParam)
      this.dataList = prgrsPrcdListRes.data

      if ((this.transferEnd || !this.prgrsPrcdExist) && (this.lastTrnsfId !== '' && this.lastTrnsfId !== undefined && this.lastTrnsfId !== null)) {
        this.prgrsPrcdInfo = { trnsfPrgrsRt: 100 }
      }
    },
    async checkUploadFile() {
      // 파일 업로드가 되어있는 경우, 삭제 후 다시 업로드할 것인지 확인
      const h = this.$createElement
      const confirm = await this.$confirm('', '', {
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        customClass: 'confirm-model',
        message: h('div', null, [
          h('i', { class: 'el-icon-check', style: 'color:#f90;font-size:30px;' }),
          h('span', { style: 'margin-left:10px;font-size:16px;line-height:30px;font-weight:600;vertical-align:top;' }, '확인'),
          h('p', { style: 'margin:10px 0 0 40px;' }, '업로드한 파일이 존재합니다.'),
          h('p', { style: 'margin:10px 0 0 40px;' }, '재업로드를 위해 삭제하시겠습니까?')
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
      // 파일 삭제
      if (confirm) {
        const trnsfParam = {
          trnsfSe: 'TE',
          trnsfId: this.currentTrnsfId
        }
        await Axios.post('http://localhost:8080/trnsf/deleteTrnsfUploadFile', trnsfParam)
        const lastTrnsfIdRes = await Axios.post('http://localhost:8080/trnsf/retriveLastTrnsfId', trnsfParam)
        this.fileName = ''
        this.fileUpload = false
        this.currentTrnsfId = ''
        this.lastTrnsfId = lastTrnsfIdRes.data
        this.$refs.fileInput.value = ''
        // 삭제 후 가장 최근에 종료된 이관정보 조회 위해서 transferEnd flag true로 변경
        this.transferEnd = true
        this.$message({
          type: 'info',
          message: '삭제되었습니다.'
        })
        await this.retriveTransfer()
        return false
      }
    }

  }
}
</script>

<style>
.uploadzone {
  border: 1px dashed #aaa;
  border-radius: 5px;
  text-align: center;
  flex: 1;
}

.uploadzone p {
  font-size: 16px;
  margin: 0;
}

.uploadzone .dropzone {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 100%;
  color:#5cb85c;
  font-weight: bold;
}
.uploadzone .dropzone span{
  font-size: 15px;
  padding-right: 5px;
}

.upload-btn, .upload-btn:hover{
  background-color: #203D91;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.download-btn, .download-btn:hover {
  background-color: #E7E6E6;
  color: black;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
.confirm-model .el-message-box__btns .el-button:nth-child(1) {
  float:right;
}
.confirm-model .el-message-box__btns .el-button:nth-child(2) {
  margin-right:10px;
  background-color:#2d8cf0;
  border-color:#2d8cf0;
}
</style>
