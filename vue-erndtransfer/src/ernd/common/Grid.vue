<template>
  <div class="app-container">
    <div class="filter-container">

      <span v-show="rowLimit > 0">
        <span style="font-size:x-large; font-weight: bold">{{ gridName }}</span><span> 총 {{ dataList.length > rowLimit ? rowLimit : dataList.length }} / {{ dataList.length }} 건 </span>
      </span>
      <el-select v-show="rowLimit > 0" v-model="rowLimit" size="small" @change="$emit('onRowLimitSelect', rowLimit)">
        <el-option
          v-for="item in options"
          :key="item"
          :label="item"
          :value="item"
        />
      </el-select>

      <div style="float: right;">
        <el-button v-show="deleteBtn" type="danger" class="filter-item" plain @click="deleteBtnClick">
          <i class="el-icon-delete-solid" /> 행삭제
        </el-button>
        <el-button v-show="dataMapBtn" type="primary" class="filter-item" plain @click="$emit('onDataMapBtnClick')">
          <i class="el-icon-document" /> 데이터정제
        </el-button>
        <el-button v-show="excelUpload" type="success" class="filter-item" plain @click="$emit('onExcelUploadClick')">
          <svg-icon icon-class="excel" /> 엑셀업로드
        </el-button>
        <el-button v-show="excelDownload" type="success" class="filter-item" plain @click="$emit('onExcelDownloadClick')">
          <svg-icon icon-class="excel" /> 엑셀다운로드
        </el-button>
      </div>

      <el-table v-show="!useCheckbox" v-loading="listLoading" :data="dataList" border highlight-current-row style="width: 100%;" @current-change="rowSelect">
        <el-table-column v-for="header in headers" :key="header.key" :label="header.name" align="center" :width="header.width">
          <template v-slot="{row}">
            <span v-if="row.errorYn === 'Y'" style="color:red">{{ row[header.key] }}</span>
            <span v-else>{{ row[header.key] }}</span>
          </template>
        </el-table-column>
        <slot name="rows" />
      </el-table>

      <el-table v-show="useCheckbox" v-loading="listLoading" :data="dataList" border style="width: 100%;" @selection-change="rowSelect">
        <el-table-column type="selection" align="center" width="40" />
        <el-table-column v-for="header in headers" :key="header.key" :label="header.name" align="center" :width="header.width">
          <template v-slot="{row}">
            <span>{{ row[header.key] }}</span>
          </template>
        </el-table-column>
        <slot name="rows" />
      </el-table>

    </div>
  </div>
</template>

<script>

export default {
  name: 'Grid',
  props: {
    gridName: {
      type: String,
      default: ''
    },
    headers: {
      type: Array,
      required: true
    },
    dataList: {
      type: Array,
      required: true
    },
    listLoading: {
      type: Boolean,
      default: true
    },
    useCheckbox: {
      type: Boolean,
      default: false
    },
    deleteBtn: {
      type: Boolean,
      default: false
    },
    dataMapBtn: {
      type: Boolean,
      default: false
    },
    excelUpload: {
      type: Boolean,
      default: false
    },
    excelDownload: {
      type: Boolean,
      default: false
    },
    rowLimit: {
      type: Number,
      default: 0
    },
    options: {
      type: Array,
      default: () => {
        return [10, 50, 100]
      }
    }
  },
  data() {
    return {
      selectedRow: null
    }
  },
  methods: {
    rowSelect(row) {
      this.selectedRow = row
      this.$emit('onRowSelect', this.selectedRow)
    },
    deleteBtnClick() {
      if (this.selectedRow === undefined || this.selectedRow === null) {
        this.$alert('삭제할 행을 선택하세요.', '삭제')
        return
      }

      if (Array.isArray(this.selectedRow)) { this.selectedRow.forEach((element) => this.dataList.splice(this.dataList.findIndex((value) => value.no === element.no), 1)) } else { this.dataList.splice(this.dataList.findIndex((value) => value.no === this.selectedRow.no), 1) }
    }
  }
}
</script>

<style scoped>

</style>
