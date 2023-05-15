<template>
  <div class="app-container">
    <page-info :parent-name="'데이터 이관'" :child-name="'개별 이관'" :parent-link="'/ernd/trns/all'" :child-link="'/ernd/trns/each'" />
    <el-container>
      <el-header style="height: 200px;">
        <el-row :gutter="20" :align="'middle'" style="height:150px; border: 1px solid lightgray;">
          <el-col :span="6">
            <div class="header-content">
              <h4>진행단계 - 과제키워드</h4>
              <h5>Procedure : PS_SBJT_KWD_DATA <br>
                Error Occurred : 1 <br>
                Time taken : 2m 30s <br>
                Inserted Rows : 1,500
              </h5>
            </div>
          </el-col>
          <el-col :span="10">
            <div class="header-content">
              <h4>진행률</h4>
              <el-progress :text-inside="true" :stroke-width="18" :percentage="100" status="success" />
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
                <el-button type="primary"><i class="fa-solid fa-link" />&nbsp;&nbsp;데이터 이관</el-button>
              </div>
            </el-row>
          </el-col>
        </el-row>
        <el-row :align="'middle'" :justify="'center'" style="height:50px;">
          <el-col :span="1">
            <div style="margin-top:6px;">
              <el-button type="primary"><i class="fa-solid fa-refresh" /></el-button>
            </div>
          </el-col>
          <el-col :span="11">
            <div style="padding-left: 10px;">
              <h4>이관 진행 현황<span>(진행시간 : 8h 1m 10s)</span></h4>
            </div>
          </el-col>
          <el-col :span="12">
            <div style="display: flex;justify-content: flex-end;align-items: center;">
              <h5 style="color:red;">데이터 이관은 중복실행이 불가능하며, 실행중인 이관이 종료될 때까지 이관 버튼은 비활성화 됩니다.</h5>
            </div>
          </el-col>
        </el-row>
      </el-header>
      <el-main style="border:1px solid lightgray">
        <el-row>
          <el-table :data="dataList" :header-row-class-name="'tableHeader'" border>
            <el-table-column prop="trnsfSeNm" label="이관 데이터 구분" :align="'left'">
              <template slot-scope="scope">
                <span style="font-weight:bold;">{{ scope.row.trnsfSeNm }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="prgrsStts" label="진행상태" :align="'center'">
              <template slot-scope="scope">
                <div v-html="prgrsSttsColoring(scope.row.prgrsStts)" />
              </template>
            </el-table-column>
            <el-table-column prop="allCnt" label="전체" :align="'right'">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.allCnt)" />
              </template>
            </el-table-column>
            <el-table-column prop="scsCnt" label="성공" :align="'right'">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.scsCnt)" />
              </template>
            </el-table-column>
            <el-table-column prop="exclCnt" label="미이관" :align="'right'">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.exclCnt)" />
              </template>
            </el-table-column>
            <el-table-column prop="errCnt" label="오류" :align="'right'">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.errCnt)" />
              </template>
            </el-table-column>
            <el-table-column prop="trnsfDataCnt" label="이관데이터 count" :align="'right'">
              <template slot-scope="scope">
                <div v-html="numberFormatter(scope.row.trnsfDataCnt)" />
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
export default {
  components: {
    PageInfo
  },
  data() {
    return {
      fileName: '',
      dataList: [
        { trnsfSeNm: '과제', prgrsStts: '완료', allCnt: '4687', scsCnt: '4670', exclCnt: '17', errCnt: '0', trnsfDataCnt: '4687', reqTm: '1h 5m 5s' },
        { trnsfSeNm: '과제진행', prgrsStts: '완료', allCnt: '4687', scsCnt: '4670', exclCnt: '17', errCnt: '0', trnsfDataCnt: '90548', reqTm: '1h 2m 1s' },
        { trnsfSeNm: '과제세부진행', prgrsStts: '완료', allCnt: '4687', scsCnt: '4670', exclCnt: '17', errCnt: '0', trnsfDataCnt: '252439', reqTm: '1h 2m 8s' },
        { trnsfSeNm: '과제기술분류', prgrsStts: '완료', allCnt: '4687', scsCnt: '3893', exclCnt: '794', errCnt: '0', trnsfDataCnt: '85805', reqTm: '1h 2m 7s' },
        { trnsfSeNm: '과제보안', prgrsStts: '완료', allCnt: '4687', scsCnt: '3893', exclCnt: '794', errCnt: '0', trnsfDataCnt: '15244', reqTm: '1h 3m 0s' },
        { trnsfSeNm: '과제계획서', prgrsStts: '완료', allCnt: '4687', scsCnt: '4670', exclCnt: '17', errCnt: '0', trnsfDataCnt: '7618', reqTm: '1h 3m 2s' },
        { trnsfSeNm: '과제키워드', prgrsStts: '진행중', allCnt: '4687', scsCnt: '4657', exclCnt: '29', errCnt: '1', trnsfDataCnt: '455569', reqTm: '' },
        { trnsfSeNm: '과제총연차', prgrsStts: '대기', allCnt: '4687', scsCnt: '0', exclCnt: '4670', errCnt: '', trnsfDataCnt: '', reqTm: '' },
        { trnsfSeNm: '과제연차', prgrsStts: '대기', allCnt: '4687', scsCnt: '0', exclCnt: '4670', errCnt: '', trnsfDataCnt: '', reqTm: '' },
        { trnsfSeNm: '과제비목연구개발비', prgrsStts: '대기', allCnt: '4687', scsCnt: '0', exclCnt: '4513', errCnt: '', trnsfDataCnt: '', reqTm: '' },
        { trnsfSeNm: '협약', prgrsStts: '대기', allCnt: '4687', scsCnt: '0', exclCnt: '4513', errCnt: '', trnsfDataCnt: '', reqTm: '' },
        { trnsfSeNm: '기관', prgrsStts: '대기', allCnt: '4687', scsCnt: '0', exclCnt: '4670', errCnt: '', trnsfDataCnt: '', reqTm: '' },
        { trnsfSeNm: '연구인력', prgrsStts: '대기', allCnt: '4687', scsCnt: '0', exclCnt: '4670', errCnt: '', trnsfDataCnt: '', reqTm: '' },
        { trnsfSeNm: '연구자참여기간', prgrsStts: '대기', allCnt: '4687', scsCnt: '0', exclCnt: '4660', errCnt: '', trnsfDataCnt: '', reqTm: '' }
      ]
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.setHeaderStyle()
    })
  },
  methods: {
    openFileSelect() {
      this.$refs.fileInput.click()
    },
    handleFileChange(event) {
      const files = event.target.files
      if (files.length > 0) {
        this.fileName = files[0].name
      }
    },
    handleDrop(event) {

    },
    handleUpload() {
      const file = this.$refs.fileInput.files[0]
      // upload를 위해, file 객체 부모 컴포넌트로 전달.
      console.log(file)
    },
    handleDownload() {

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
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
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
  margin-right: 15px;
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

</style>
