<template>
  <modal
    :title="'오류발생 상세보기'"
    :save-btn="false"
    :exclude="'NO'"
    :max-width="'1300px'"
    :max-height="'900px'"
    @close="closeBtnClick"
  >
    <div slot="content">
      <div class="trnsRslt">
        <h3 style="text-align: left; color: #3A71A8">이관정보</h3>
        <aside>
          <el-form>
            <el-row style="margin-top: 20px">
              <slot name="body">
                <el-col :span="10">
                  <el-form-item label-width="130px" label="이관ID">
                    <el-input type="text" disabled="disabled" :value="trnsfId" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="이관일시">
                    <el-input type="text" disabled="disabled" :value="errDtlData.brngDt" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="업무구분">
                    <el-input type="text" disabled="disabled" :value="errDtlData.prcdNm" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="프로시저명">
                    <el-input type="text" disabled="disabled" :value="prcdId" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="SOURCE">
                    <el-input type="text" disabled="disabled" :value="errDtlData.trnsfTbl" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="TARGET">
                    <el-input type="text" disabled="disabled" :value="errDtlData.bscTbl" />
                  </el-form-item>
                </el-col>
              </slot>
            </el-row>
          </el-form>
        </aside>

        <h3 style="text-align: left; color: #3A71A8">오류 상세 정보</h3>
        <aside>
          <el-form>
            <el-row style="margin-top: 20px">
              <slot name="body">
                <el-col :span="10">
                  <el-form-item label-width="130px" label="오류코드">
                    <el-input type="text" disabled="disabled" :value="errDtlData.errCd" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="오류발생일시">
                    <el-input type="text" disabled="disabled" :value="errDtlData.regDtlDt" />
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label-width="130px" label="오류내용">
                    <el-input type="text" disabled="disabled" :value="errDtlData.errCn" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="SOURCE KEY DESC">
                    <el-input type="text" disabled="disabled" :value="errDtlData.erndKeyCn" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="TARGET KEY DESC">
                    <el-input type="text" disabled="disabled" :value="errDtlData.irisKeyCn" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="SOURCE KEY">
                    <el-input type="text" disabled="disabled" :value="errDtlData.erndKeyVl" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="TARGET KEY">
                    <el-input type="text" disabled="disabled" :value="errDtlData.irisKeyVl" />
                  </el-form-item>
                </el-col>
              </slot>
            </el-row>
          </el-form>
        </aside>
      </div>
    </div>
  </modal>
</template>

<script>
import Modal from '@/ernd/common/Modal.vue'
import '@fortawesome/fontawesome-free/css/all.css'
import Axios from 'axios'
export default {
  name: 'TrnsResultPopUp',
  components: { Modal },
  props: {
    trnsfId: {
      type: String,
      default: ''
    },
    prcdId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      errDtlForm: {
        prcdId: '',
        trnsfId: ''
      },
      errDtlData: {
        trnsfDataNocs: '',
        errCd: '',
        errCn: '',
        erndKeyVl: '',
        erndKeyCn: '',
        irisKeyVl: '',
        irisKeyCn: '',
        regDtlDt: '',
        brngDt: '',
        prcdId: '',
        prcdNm: '',
        trnsfTbl: '',
        bscTbl: ''
      }
    }
  },
  mounted() {
    this.errSearch()
  },
  closeFlag: false,
  methods: {
    closeBtnClick() {
      this.closeFlag = true
      this.$emit('close')
    },
    errSearch() {
      const searchParams = this.errDtlForm
      searchParams.prcdId = this.prcdId
      searchParams.trnsfId = this.trnsfId
      Axios.post('http://localhost:8080/trnsfRslt/retriveTrnsfErrDtl', searchParams)
        .then(response => {
          this.errDtlData = response.data.trnsfErrDtl
        })
        .catch(error => {
          console.log(error)
        })
        .finally(() => {
          this.listLoading = false
        })
    }
  }

}
</script>

<style scoped>
.trnsRslt {
  border: 1px solid lightgray;
  display: block;
  margin: 2px;
  flex-direction: row;
  align-items: inherit;
  padding: 2px 5px;
}
</style>
