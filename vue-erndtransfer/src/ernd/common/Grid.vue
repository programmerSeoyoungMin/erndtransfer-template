<template>
  <div class="app-container">
    <div class="filter-container">

      <span v-show="rowLimit > 0">
        <span style="font-size:x-large; font-weight: bold">{{ gridName }}</span><span> 총 {{ totalCnt }} 건 </span>
      </span>
      <!-- v-model에서 rowLimit 같이 사용할 경우, Avoid mutating a prop directly 오류 발생으로 별도 model binding 함.-->
      <el-select v-show="rowLimit > 0" v-model="gridLimit" size="small" @change="$emit('onRowLimitSelect', gridLimit)">
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
      <!-- 2023.05.02 msy : 가로스크롤 숨김현상으로 인해 virtualscroll component 추가 => useVscroll prop true시, 사용가능.
                            virtualScroll 컴포넌트에서는 key-prop를 사용하고 있음.
                            기본으로 no로 사용하도록 지정
                            useCommon을 기본으로 사용하도록 함.
                            useVscroll을 사용하려면 useCommon을 false로 지정하고 사용할 것.
      -->

      <!-- default!!! useCommon true -->
      <div v-if="common" class="common-container">
        <el-table v-show="!useCheckbox" ref="table" v-loading="listLoading" :height="gridHeight" :data="dataList" border highlight-current-row style="width: 100%;" header-align="center" @current-change="rowSelect">
          <el-table-column v-for="header in headers" :key="header.key" :label="header.name" :align="header.align" :width="header.width" header-align="center" show-overflow-tooltip>
            <template v-slot="{row}">
              <div v-html="filterHtml(row, header.key, header.type)" />
            </template>
          </el-table-column>
          <slot name="rows" />
        </el-table>

        <el-table v-show="useCheckbox" ref="table" v-loading="listLoading" :data="dataList" border style="width: 100%;" header-align="center" @selection-change="rowSelect">
          <el-table-column type="selection" align="center" width="40" />
          <el-table-column v-for="header in headers" :key="header.key" :label="header.name" :align="header.align" :width="header.width" header-align="center" show-overflow-tooltip>
            <template v-slot="{row}">
              <div v-html="filterHtml(row, header.key, header.type)" />
            </template>
          </el-table-column>
          <slot name="rows" />
        </el-table>
      </div>

      <!-- useVscroll true -->
      <div v-else-if="vscroll" class="vscroll-container">
        <virtual-scroll
          ref="virtualScroll"
          name="VirtualScroll"
          :data="dataList"
          :item-size="50"
          key-prop="no"
          row-prop="no"
          @change="(dataList) => tableData = dataList"
        >
          <el-table
            v-if="!useCheckbox"
            ref="table"
            v-loading="listLoading"
            :data="tableData"
            :stripe="stripe"
            :border="border"
            :height="gridHeight+'px'"
            style="width: 100%"
            row-key="no"
            @current-change="rowSelect"
          >
            <el-table-column v-for="header in headers" :key="header.key" :prop="header.key" :label="header.name" :width="header.width" :type="header.type" :align="header.align" header-align="center" show-overflow-tooltip>
              <template v-slot="{row}">
                <div v-html="filterHtml(row, header.key, header.type)" />
              </template>
            </el-table-column>
          </el-table>

          <el-table
            v-if="useCheckbox"
            ref="table"
            v-loading="listLoading"
            :data="tableData"
            :stripe="stripe"
            :border="border"
            :height="gridHeight+'px'"
            style="width: 100%"
            row-key="no"
            @selection-change="rowSelect"
          >
            <el-table-column type="selection" align="center" width="50" />
            <el-table-column v-for="header in headers" :key="header.key" :prop="header.key" :label="header.name" :width="header.width" :type="header.type" :align="header.align" header-align="center" show-overflow-tooltip>
              <template v-slot="{row}">
                <div v-html="filterHtml(row, header.key, header.type)" />
              </template>
            </el-table-column>
          </el-table>
        </virtual-scroll>

      </div>
    </div>
  </div>
</template>

<script>
import VirtualScroll from 'el-table-virtual-scroll'

export default {
  name: 'Grid',
  component: {
    VirtualScroll
  },
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
    },
    gridHeight: {
      type: String,
      default: '500px'
    },
    useVscroll: {
      type: Boolean,
      required: false
    },
    useCommon: {
      type: Boolean,
      required: false
    },
    totalCnt: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      selectedRow: null,
      tableData: [],
      stripe: false,
      border: true,
      status: false,
      fixed: false,
      gridLimit: 10,
      common: true,
      vscroll: false
    }
  },
  watch: {
    useVscroll: {
      handler: function() {
        if (this.useVscroll) {
          this.vscroll = true
          this.common = false
        } else {
          this.vscroll = false
          this.common = true
        }
      },
      immediate: true
    },
    useCommon: {
      handler: function() {
        if (this.useCommon) {
          this.common = true
          this.vscroll = false
        } else {
          this.common = false
          this.vscroll = true
        }
      },
      immediate: true
    }
  },
  mounted() {
    // when mounted end then
    this.$nextTick(() => {
      console.log(this.gridName + 'grid mounted')
      this.setGridHeight()
    })
  },
  methods: {
    rowSelect(row) {
      this.selectedRow = row
      this.$emit('onRowSelect', this.selectedRow)
    },
    deleteBtnClick() {
      if (this.selectedRow === undefined || this.selectedRow === null || this.selectedRow.length === 0) {
        this.$alert('삭제할 행을 선택하세요.', '삭제')
        return
      }
      if (Array.isArray(this.selectedRow)) {
        this.selectedRow.forEach((element) => this.dataList.splice(this.dataList.findIndex((value) => value.no === element.no), 1))
      } else {
        this.dataList.splice(this.dataList.findIndex((value) => value.no === this.selectedRow.no), 1)
      }
    },
    // 2023.05.09 filterHtml 추가 (type이 number인 경우, 3자리마다 콤마 추가)
    filterHtml(row, key, type) {
      const html = {
        openTag: '<span>',
        value: '',
        closeTag: '</span>'
      }
      if (row.errorYn === 'Y') {
        html.openTag = '<span style="color:red">'
      }
      if (row[key] != null && row[key] !== '') {
        // type이 number인 경우, 3자리마다 콤마 추가
        if (type != null && type === 'number') {
          html.value = row[key].toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        } else {
          html.value = row[key]
        }
      }
      return html.openTag + html.value + html.closeTag
    },
    setGridHeight() {
      if (this.common) {
        const table = document.querySelector('.common-container .el-table>.el-table__body-wrapper.is-scrolling-left')
        const header = document.querySelector('.common-container .el-table .el-table__header-wrapper')
        if (table) {
          table.style.height = Number(parseInt(this.gridHeight) - parseInt(header.clientHeight)) + 'px'
          table.style.minHeight = Number(parseInt(this.gridHeight) - parseInt(header.clientHeight)) + 'px'
        }
      } else {
        const table = document.querySelector('.el-table-virtual-scroll>.el-table>.el-table__body-wrapper.is-scrolling-left')
        const header = document.querySelector('.el-table-virtual-scroll .el-table .el-table__header-wrapper')
        if (table) {
          table.style.height = Number(parseInt(this.gridHeight) - parseInt(header.clientHeight)) + 'px'
          table.style.minHeight = Number(parseInt(this.gridHeight) - parseInt(header.clientHeight)) + 'px'
        }
      }
    }

  }
}
</script>

<style scoped>
</style>

