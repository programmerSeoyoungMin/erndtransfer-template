<template>
  <div class="app-container">
    <page-info :parent-name="'데이터 이관'" :child-name="'개별 이관'" :parent-link="'/ernd/trns/all'" :child-link="'/ernd/trns/each'" />
    <el-container>
      <el-header style="height: 200px;">
        <el-row :gutter="20" :align="'middle'" style="height:150px; border: 1px solid lightgray;">
          <el-col :span="6">
            <div v-show="prgrsPrcdExist" class="header-content">
              <h4>[이관ID : {{ trnsfInfo.trnsfId }}]</h4>
              <h5>진행단계 : {{ prgrsPrcdInfo.prcdNm }} ({{ prgrsPrcdInfo.prcdId }})<br>
                Error Occurred : {{ numberFormatter(prgrsPrcdInfo.errOcrnNocs) }} <br>
                Time taken : {{ prgrsPrcdInfo.reqTm }} <br>
                Inserted Rows : {{ numberFormatter(prgrsPrcdInfo.trnsfDataNocs) }}
              </h5>
            </div>
            <div v-show="!prgrsPrcdExist" class="header-content">
              <h4 v-show="transferEnd && lastTrnsfId !== ''">
                [이관ID : {{ trnsfInfo.trnsfId }}]
              </h4>
              <h5 v-show="transferEnd && lastTrnsfId !== ''">
                이관 시작 : {{ trnsfInfo.dmndDt }} <br>
                이관 종료 : {{ trnsfInfo.endDt }} <br>
                소요 시간 : {{ trnsfInfo.reqTm }} <br>
                이관 과제 : {{ numberFormatter(trnsfInfo.sbjtNocs) }} 건
              </h5>
              <h4 v-show="!transferEnd">
                파일 업로드 후 대기중입니다.
              </h4>
              <h5 v-show="!transferEnd && fileUpload">
                데이터이관버튼을 클릭하여, 진행하십시오.
              </h5>
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
            <el-row :gutter="20" style="padding:5px">
              <el-col>
                <div class="uploadzone">
                  <div class="dropzone" @drop="handleDrop">
                    <span>{{ fileName }}</span>
                    <input ref="fileInput" type="file" style="display:none" @change="handleFileChange">
                    <el-button type="default" size="mini" @click="openFileSelect">파일선택</el-button>
                  </div>
                </div>
              </el-col>
            </el-row>
            <el-row>
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
        <!-- 이관 진행 중 -->
        <el-row v-show="!transferEnd" h4 :align="'middle'" :justify="'center'" style="height:50px;">
          <el-col :span="1">
            <div style="margin-top:6px;">
              <el-button @click="retriveTransfer"><i class="fa-solid fa-refresh" /></el-button>
            </div>
          </el-col>
          <el-col :span="11">
            <div style="padding-left: 10px;">
              <h4>이관 진행 현황<span v-show="prgrsPrcdInfo.trnsfPrgrsTm !== undefined && prgrsPrcdInfo.trnsfPrgrsTm !== ''">(진행시간 : {{ prgrsPrcdInfo.trnsfPrgrsTm }})</span></h4>
            </div>
          </el-col>
          <el-col :span="12">
            <div style="display: flex;justify-content: flex-end;align-items: center;">
              <h5 style="color:red;">데이터 이관은 중복실행이 불가능하며, 실행중인 이관이 종료될 때까지 이관 버튼은 비활성화 됩니다.</h5>
            </div>
          </el-col>
        </el-row>
        <!-- 이관 종료 -->
        <el-row v-show="transferEnd" h4 :align="'middle'" :justify="'center'" style="height:50px;">
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
      fileName: '',
      // 이관 종료 여부
      transferEnd: false,
      // 이관 파일 업로드 여부
      fileUpload: false,
      // 진행 프로시저 존재
      prgrsPrcdExist: false,
      // 진행 이관 존재여부
      prgrsTrnsfExistYn: 'N',
      // 진행 중 이관 아이디 조회
      currentTrnsfId: '',
      lastTrnsfId: '',
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
        trnsfId: '',
        dmndDt: '',
        endDt: '',
        sbjtNocs: 0,
        reqTm: ''
      },
      trnsferBtnType: 'primary'
    }
  },
  async mounted() {
    const trnsfParam = {
      trnsfSe: 'TE',
      trnsfId: ''
    }
    // trnsfSe : TA (전체이관), TE (개별이관)
    const prgrsTrnsfExistYnRes = await Axios.post('http://localhost:8080/trnsf/retrivePrgrsTrnsfExistYn', trnsfParam)
    this.prgrsTrnsfExistYn = prgrsTrnsfExistYnRes.data
    // 진행중인 이관이 존재하는 경우, 이관 ID 및 이관 정보 조회
    if (this.prgrsTrnsfExistYn === 'Y') {
      const currentTrnsfIdRes = await Axios.post('http://localhost:8080/trnsf/retriveCurrentTrnsfId', trnsfParam)
      this.currentTrnsfId = currentTrnsfIdRes.data
      await this.retriveTransfer()
    } else { // 진행중인 이관이 존재하지 않는 경우, 마지막으로 진행된 이관 ID 및 이관정보 조회
      const lastTrnsfIdRes = await Axios.post('http://localhost:8080/trnsf/retriveLastTrnsfId', trnsfParam)
      this.lastTrnsfId = lastTrnsfIdRes.data
      this.transferEnd = true
      this.retriveTransfer()
    }
    // 병합 헤더 설정
    this.$nextTick(() => {
      this.setHeaderStyle()
    })
  },
  methods: {
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
    handleFileChange(event) {
      const files = event.target.files
      console.log(files[0].name)
      if (files.length > 0) {
        this.fileName = files[0].name
      }
    },
    handleDrop(event) {

    },
    async handleUpload() {
      const file = this.$refs.fileInput.files[0]

      // 파일 업로드 또는 이관 버튼 클릭 후 event 발생 방지
      if (this.prgrsTrnsfExistYn === 'Y' && !this.transferEnd) {
        this.$alert('진행중인 이관이 존재합니다.', '알림', {
          confirmButtonText: '확인',
          type: 'error'
        })
        return
      } else if (this.fileUpload && !this.transferEnd) {
        const result = await this.checkUploadFile()
        if (result) {
          this.$refs.fileInput.click()
        } else {
          return false
        }
      } else {
        // upload를 위해, file 객체 부모 컴포넌트로 전달.
        if (file === undefined) {
          this.$alert('업로드할 엑셀 파일을 선택해주세요.', '알림', {
            confirmButtonText: '확인',
            type: 'error'
          })
          return
        }
      }

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

      if (confirm) {
        const formData = new FormData()
        formData.append('file', file) // 파일 데이터 추가
        formData.append('category', 'trnsfSbjt')
        const config = {
          headers: {
            'Content-Type': 'multipart/form-data' // 헤더 설정
          }
        }
        await Axios.post('http://localhost:8080/excel/upload', formData, config)
        // 이관 시작 및 파일업로드
        this.prgrsTrnsfExistYn = 'Y'
        this.fileUpload = true
        this.transferEnd = false
        const trnsfParam = {
          trnsfSe: 'TE',
          trnsfId: ''
        }
        // 진행중인 이관ID 조회
        const currentTrnsfIdRes = await Axios.post('http://localhost:8080/trnsf/retriveCurrentTrnsfId', trnsfParam)
        this.currentTrnsfId = currentTrnsfIdRes.data
        this.lastTrnsfId = ''
        this.retriveTransfer()
      } else {
        this.$message({
          type: 'info',
          message: '취소되었습니다.'
        })
      }
    },
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
        return '<span style="color:#00B050;font-weight: bold;">' + prgrsStts + '</span>'
      } else if (prgrsStts === '진행중') {
        return '<span style="color:blue;font-weight: bold;">' + prgrsStts + '</span>'
      } else {
        return '<span style="color:black;font-weight: bold;">' + prgrsStts + '</span>'
      }
    },
    numberFormatter(value) {
      if (value === 0) return 0
      if (!value) return ''

      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    },
    async trnsfBtnClick() {
      // 업로드된 파일 존재여부 확인
      if (!this.fileUpload) {
        this.$alert('업로드한 파일이 존재하지 않습니다. 파일먼저 업로드해주십시오.', '알림', {
          confirmButtonText: '확인',
          type: 'error'
        })
        return
      }
      if (this.prgrsTrnsfExistYn === 'Y' && !this.transferEnd) {
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
        }).catch(() => {
          return false
        })

        if (confirm) {
          const trnsfParam = {
            trnsfSe: 'TE',
            trnsfId: this.currentTrnsfId
          }
          const registTrnsfMasterRes = await Axios.post('http://localhost:8080/trnsf/registTrnsfMaster', trnsfParam).finally(() => {
            this.$message({
              type: 'success',
              message: '이관이 시작되었습니다.'
            })
            window.location.reload()
          })
          this.currentTrnsfId = registTrnsfMasterRes.data
        } else {
          this.$message({
            type: 'info',
            message: '취소되었습니다.'
          })
        }
      }
    },
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
        // 이관정보 조회
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
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '취소되었습니다.'
        })
      })

      if (confirm) {
        this.fileName = ''
        const trnsfParam = {
          trnsfSe: 'TE',
          trnsfId: this.currentTrnsfId
        }
        await Axios.post('http://localhost:8080/trnsf/deleteTrnsfUploadFile', trnsfParam)
        const lastTrnsfIdRes = await Axios.post('http://localhost:8080/trnsf/retriveLastTrnsfId', trnsfParam)
        this.currentTrnsfId = ''
        this.lastTrnsfId = lastTrnsfIdRes.data
        this.$refs.fileInput.value = ''
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
