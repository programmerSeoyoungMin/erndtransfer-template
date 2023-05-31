<template>
  <div class="modal" :style="{ top: top + 'px', left: left + 'px', 'max-width': maxWidth, 'max-height': maxHeight }">
    <div class="modal-header" @mousedown="handleMouseDown">
      <h4>{{ title }}</h4>
      <button class="close" @click="closeModal">X</button>
    </div>
    <div class="modal-content">
      <slot name="content" />
    </div>
    <div class="modal-footer">
      <slot name="footer" />
      <el-button class="close-btn" @click="closeModal">닫기</el-button>
      <el-button v-if="saveBtn" class="save-btn" @click="$emit('onSaveBtnClick')">저장</el-button>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Modal',
  props: {
    title: {
      type: String,
      default: ''
    },
    saveBtn: {
      type: Boolean,
      default: false
    },
    maxWidth: {
      type: String,
      default: '900px'
    },
    maxHeight: {
      type: String,
      default: '70%'
    },
    closeFlag: {
      type: Boolean,
      default: true
    }
  },
  data: function() {
    return {
      isSidebarOpened: this.$store.state.app.sidebar.opened,
      isDragging: false,
      lastX: 0,
      lastY: 0,
      left: 60,
      top: 90
    }
  },
  mounted() {
    window.addEventListener('mousemove', this.handleMouseMove)
    window.addEventListener('mouseup', this.handleMouseUp)
  },
  methods: {
    closeModal() {
      if (this.closeFlag) {
        this.$emit('close')
      } else {
        this.$confirm('저장되지 않은 데이터가 존재합니다.\n저장하시겠습니까?', '알림', {
          cancelButtonText: '닫기',
          confirmButtonText: '확인',
          callback: action => {
            if (action === 'confirm') {
              this.$emit('onSaveBtnClick')
            } else {
              this.$emit('close')
            }
          }
        })
      }
    },
    handleMouseDown(event) {
      this.isDragging = true
      this.lastX = event.clientX
      this.lastY = event.clientY
      event.preventDefault()
    },
    handleMouseMove(event) {
      if (this.isDragging) {
        const deltaX = event.clientX - this.lastX
        const deltaY = event.clientY - this.lastY
        this.left += deltaX
        this.top += deltaY
        this.lastX = event.clientX
        this.lastY = event.clientY
      }
    },
    handleMouseUp() {
      this.isDragging = false
    }
  },
  beforeUnmount() {
    window.removeEventListener('mousemove', this.handleMouseMove)
    window.removeEventListener('mouseup', this.handleMouseUp)
  }
}
</script>

<style>

.modal {
  position: absolute;
  background-color: white;
  border: 1px solid gray;
  z-index: 1000;
  width:80%;
  height: 80%;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #5cb85c;
  padding: 8px;
  border-bottom: 1px solid gray;
  cursor: move;
  height: 7%;
  margin-bottom: 1px;
  color:#fff;
}
.modal-content {
  position:relative;
  background-color: #fefefe;
  padding:20px;
  width: 100%;
  height: 86%;
  overflow-y: auto;
}

.modal-footer {
  position: relative;
  margin-top: 5px;
  background-color: #fefefe;
  width: 100%;
  margin-bottom: 5px;
  overflow-y: auto;
  padding: 3px;
}

.close {
  position: absolute;
  top: 0;
  right: 0;
  padding: 1.5%;
  font-size: 20px;
  line-height: 1;
  color: #fff;
  background-color: transparent;
  border: none;
  outline: none;
  cursor: pointer;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.close-btn, .close-btn:hover, close-btn:focus{
  background-color: #E7E6E6;
  color: black;
  border: none;
  border-radius: 5px;
  float: right;
  cursor: pointer;
  margin-right: 2px;
  transition: background-color 0.3s ease;
}
.save-btn, .save-btn:hover, save-btn:focus {
  background-color: #203D91;
  color: #fff;
  border: none;
  border-radius: 5px;
  float: right;
  cursor: pointer;
  margin-right: 5px;
  transition: background-color 0.3s ease;
}
</style>
