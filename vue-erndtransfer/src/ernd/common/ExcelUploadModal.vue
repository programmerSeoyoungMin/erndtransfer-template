<template>
  <modal
    :title="excelTitle"
    :save-btn="true"
    :max-width="'1300px'"
    :max-height="'900px'"
    :close-flag="closeFlag"
    @onSaveBtnClick="saveBtnClick"
    @close="closeBtnClick"
  >
    <div slot="content">
      <div class="excel-upload-download">
        <div class="uploadzone">
          <div class="progressbar">
            <i :class="classObject" />
          </div>
          <div class="dropzone" @drop="handleDrop">
            <span>{{ fileName }}</span>
            <el-button type="default" @click="openFileSelect">파일선택</el-button>
          </div>
        </div>
        <input ref="fileInput" type="file" style="display:none" @change="handleFileChange">
        <el-button type="info" class="download-btn" @click="handleDownload">서식 다운로드</el-button>
        <el-button type="primary" class="upload-btn" @click="handleUpload">파일 업로드</el-button>
      </div>
      <grid
        ref="grid"
        :grid-name="'업로드 데이터 목록'"
        :headers="headers"
        :data-list="excelDataList"
        :list-loading="listLoading"
        :use-checkbox="true"
        :delete-btn="true"
        :grid-height="'379'"
        :use-vscroll="true"
        :vscroll-height="440"
        @onDeleteBtnClick="deleteBtnClick"
      />
    </div>
  </modal>
</template>

<script>
import Modal from './Modal'
import Grid from './Grid'
import '@fortawesome/fontawesome-free/css/all.css'
import Axios from 'axios'

export default {
  components: {
    Modal,
    Grid
  },
  props: {
    excelTitle: {
      type: String,
      default: ''
    },
    taskSeTblNm: {
      type: String,
      default: ''
    },
    excelDataList: {
      type: Array,
      default() {
        return []
      }
    },
    listLoading: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      fileName: '',
      showModal: false,
      animationPlayState: 'paused',
      classObject: {
        'fas': true,
        'fa-upload': true
      },
      // Modal
      closeFlag: true,
      // Grid
      headers: [],
      dataList: [],
      saveFlag: false
    }
  },
  async mounted() {
    const headerDto = {
      taskSeTblNm: this.taskSeTblNm
    }
    const response = await Axios.post('http://localhost:8080/common/retriveHeaderList', headerDto)
    this.headers = response.data
  },
  methods: {
    closeBtnClick() {
      this.closeFlag = true
      this.$emit('close')
    },
    handleFiles(files) {
      if (files.length > 0) {
        this.fileName = files[0].name
      }
    },
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
      this.classObject = {
        'fas': true,
        'fa-spinner': true,
        'fa-spin': true
      }
      const file = this.$refs.fileInput.files[0]
      // upload를 위해, file 객체 부모 컴포넌트로 전달.
      const result = this.$emit('onUploadBtnClick', file)
      if (result) {
        this.classObject = {
          'fas': true,
          'fa-check': true
        }
        this.closeFlag = false
      }
    },
    handleDownload() {
      this.$emit('onDownloadBtnClick')
    },
    saveBtnClick() {
      this.saveFlag = true

      if (this.excelDataList.length === 0) {
        this.$alert('저장할 데이터가 없습니다.')
        this.saveFlag = false
      } else {
        for (let i = 0; i < this.excelDataList.length; i++) {
          if (this.excelDataList[i].errorYn === 'Y') {
            this.$alert('저장할 수 없는 데이터가 존재합니다.')
            this.saveFlag = false
            break
          }
        }
      }

      if (this.saveFlag) {
        const result = this.$emit('onSaveBtnClick')
        if (result) {
          this.closeFlag = true
          if (confirm('저장되었습니다. 창을 닫으시겠습니까?')) {
            this.closeBtnClick()
          }
        } else {
          this.closeFlag = false
        }
      }
    },
    deleteBtnClick() {
      this.$emit('onDeleteBtnClick')
    }
  }
}

</script>

<style scoped>
.excel-upload-download {
  border: 1px solid lightgray;
  display: flex;
  margin: 2px;
  flex-direction: row;
  align-items: center;
  height: 60px;
  padding: 2px 5px;
}
.uploadzone {
  border: 1px dashed #aaa;
  border-radius: 5px;
  text-align: center;
  margin-right: 5px;
  flex: 1;
  height: 75%;
}

.uploadzone p {
  font-size: 16px;
  margin: 0;
}

.uploadzone .progressbar{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width:3%;
  color:#5cb85c;
  float:left;
}

.uploadzone .progressbar i{
  margin-left:20px;
}

.uploadzone .dropzone {
  display: flex;
  justify-content: right;
  align-items: center;
  height: 100%;
  width:97%;
  color:#5cb85c;
  float:right;
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

.rotate {
  -webkit-animation: spin 2s linear infinite;
  -moz-animation: spin 2s linear infinite;
  -o-animation: spin 2s linear infinite;
  animation: spin 2s linear infinite;
}

@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@-moz-keyframes spin {
  0% { -moz-transform: rotate(0deg); }
  100% { -moz-transform: rotate(360deg); }
}

@-o-keyframes spin {
  0% { -o-transform: rotate(0deg); }
  100% { -o-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

</style>
