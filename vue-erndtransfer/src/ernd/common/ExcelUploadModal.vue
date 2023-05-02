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
        :grid-name="'업로드 데이터 목록'"
        :headers="headers"
        :data-list="excelDataList"
        :list-loading="listLoading"
        :use-checkbox="true"
        :delete-btn="true"
        :grid-height="'379'"
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
    gbn: {
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
    },
    exclude: {
      type: String,
      default: ''
    },
    includeErrorFieldYn: {
      type: String,
      default: 'N'
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
      dataList: []
    }
  },
  mounted() {
    const headerDto = {
      gbn: this.gbn,
      exclude: this.exclude,
      includeErrorFieldYn: this.includeErrorFieldYn // 오류구분 포함여부
    }
    Axios.post('http://localhost:8080/common/retriveHeaderList', headerDto)
      .then(response => {
        this.headers = response.data
      })
      .catch(error => {
        console.log(error)
      })
  },
  methods: {
    closeBtnClick() {
      this.$emit('close')
    },
    handleFiles(files) {
      if (files.length > 0) {
        this.fileName = files[0].name
        console.log(files[0])
      }
    },
    openFileSelect() {
      this.$refs.fileInput.click()
    },
    handleFileChange(event) {
      const files = event.target.files
      if (files.length > 0) {
        this.fileName = files[0].name
        console.log(files[0])
      }
    },
    handleDrop(event) {
      // event.preventDefault()
      // const file = event.dataTransfer.files[0]
      // do something with the file, e.g. read its contents with a library like SheetJS
    },
    handleUpload() {
      // handle the upload button click event
      // when spin start upload , and change img
      this.classObject = {
        'fas': true,
        'fa-spinner': true,
        'fa-spin': true
      }
      // get input file
      const file = this.$refs.fileInput.files[0]
      console.log(file)
      // send file to parent component
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
      // handle the download button click event
      this.$emit('onDownloadBtnClick')
    },
    saveBtnClick() {
      this.$emit('onSaveBtnClick')
      // if save end
      this.closeFlag = true
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
