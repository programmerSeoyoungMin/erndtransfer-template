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
          <div class="dropzone">
            <span>{{ fileName }}</span>
            <el-button v-show="fileUpload" type="default" size="small" icon="el-icon-delete" @click="handleFileClear" />
            <el-button type="default" @click="openFileSelect">파일선택</el-button>
          </div>
        </div>
        <!-- fileupload 한 경우만 표시 -->
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
    },
    uploadResult: {
      type: String,
      default: ''
    },
    saveResult: {
      type: String,
      default: ''
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
      saveFlag: false,
      fileUpload: false
    }
  },
  watch: {
    // 업로드 결과가 변경되면, 결과에 따른 알림표시
    uploadResult: {
      handler: function(val) {
        this.classObject = {
          'fas': true,
          'fa-upload': true
        }
        this.fileUpload = false
        if (val === 'templateError') {
          this.$refs.fileInput.value = ''
          this.fileName = ''
          this.$alert('서식파일이 맞지 않습니다. 파일을 다시 선택해 주십시오.', '알림', {
            confirmButtonText: '확인',
            type: 'error'
          })
        } else if (val === 'sizeError') {
          this.$refs.fileInput.value = ''
          this.fileName = ''
          this.$alert('저장할 데이터가 존재하지 않습니다. 파일을 다시 선택해 주십시오.', '알림', {
            confirmButtonText: '확인',
            type: 'error'
          })
        } else if (val === 'success') {
          this.classObject = {
            'fas': true,
            'fa-check': true
          }
          this.closeFlag = false
          // 성공인 경우만, fileUpload flag를 true로 변경
          this.fileUpload = true
        }
      },
      immediate: true
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
      if (this.uploadResult === 'success') {
        this.$alert('업로드가 완료된 파일이 존재합니다. 삭제 후 선택해주십시오.', '알림', {
          confirmButtonText: '확인',
          type: 'warning'
        })
        return
      }
      this.$refs.fileInput.click()
    },
    async handleFileClear() {
      const h = this.$createElement
      const confirm = await this.$confirm('', '', {
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        customClass: 'confirm-model',
        message: h('div', null, [
          h('i', { class: 'el-icon-check', style: 'color:#f90;font-size:30px;' }),
          h('span', { style: 'margin-left:10px;font-size:16px;line-height:30px;font-weight:600;vertical-align:top;' }, '확인'),
          h('p', { style: 'margin:10px 0 0 40px;' }, '업로드한 파일을 삭제하시겠습니까?')
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
        this.$refs.fileInput.value = ''
        this.fileName = ''
        this.fileUpload = false
        this.closeFlag = true
        this.$emit('onClearList')
        this.$message({
          type: 'info',
          message: '삭제되었습니다.'
        })
      }
    },
    handleFileChange(event) {
      const files = event.target.files
      if (files.length > 0) {
        this.fileName = files[0].name
      }
    },
    async handleUpload() {
      const file = this.$refs.fileInput.files[0]

      if (file === undefined) {
        this.$alert('파일이 선택되지 않았습니다.', '알림', {
          confirmButtonText: '확인',
          type: 'warning'
        })
        return
      }

      const h = this.$createElement
      const confirm = await this.$confirm('', '', {
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        customClass: 'confirm-model',
        message: h('div', null, [
          h('i', { class: 'el-icon-check', style: 'color:#f90;font-size:30px;' }),
          h('span', { style: 'margin-left:10px;font-size:16px;line-height:30px;font-weight:600;vertical-align:top;' }, '확인'),
          h('p', { style: 'margin:10px 0 0 40px;' }, '파일을 업로드하시겠습니까?')
        ])
      }).catch((action) => {
        if (action === 'cancel') {
          this.$message({
            type: 'info',
            message: '취소되었습니다.'
          })
        }
      })
      if (confirm) {
        // 업로드 버튼을 누르면, 종료되기 전까지 spinner 표시
        this.classObject = {
          'fas': true,
          'fa-spinner': true,
          'fa-spin': true
        }
        // upload를 위해, file 객체 부모 컴포넌트로 전달.
        this.$emit('onUploadBtnClick', file)
      }
    },
    handleDownload() {
      this.$emit('onDownloadBtnClick')
    },
    async saveBtnClick() {
      this.saveFlag = true

      if (this.excelDataList.length === 0) {
        this.$alert('저장할 데이터가 없습니다.', '알림', {
          confirmButtonText: '확인',
          type: 'warning'
        })
        this.saveFlag = false
      } else {
        for (let i = 0; i < this.excelDataList.length; i++) {
          if (this.excelDataList[i].errorYn === 'Y') {
            this.$alert('저장할 수 없는 데이터가 존재합니다.', '알림', {
              confirmButtonText: '확인',
              type: 'error'
            })
            this.saveFlag = false
            break
          }
        }
      }
      if (this.saveFlag) {
        await this.$emit('onSaveBtnClick')

        if (this.saveResult === 'success') {
          this.closeFlag = true
          const h = this.$createElement
          const confirm = await this.$confirm('', '', {
            confirmButtonText: '확인',
            cancelButtonText: '취소',
            customClass: 'confirm-model',
            message: h('div', null, [
              h('i', { class: 'el-icon-check', style: 'color:#f90;font-size:30px;' }),
              h('span', { style: 'margin-left:10px;font-size:16px;line-height:30px;font-weight:600;vertical-align:top;' }, '확인'),
              h('p', { style: 'margin:10px 0 0 40px;' }, '저장되었습니다.'),
              h('p', { style: 'margin:10px 0 0 40px;' }, '창을 닫으시겠습니까?')
            ])
          }).catch((action) => {
            console.log(action)
            if (action === 'cancel') {
              this.$refs.fileInput.value = ''
              this.fileName = ''
              this.fileUpload = false
              this.closeFlag = true
              this.$emit('onClearList')
            }
          })
          if (confirm) {
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
.confirm-model .el-message-box__btns .el-button:nth-child(1) {
  float:right;
}
.confirm-model .el-message-box__btns .el-button:nth-child(2) {
  margin-right:10px;
  background-color:#2d8cf0;
  border-color:#2d8cf0;
}
</style>
