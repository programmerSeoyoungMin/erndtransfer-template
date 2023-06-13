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
                    <el-input type="text" disabled="disabled" :value="dmndDt" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="업무구분">
                    <el-input type="text" disabled="disabled" :value="procdNm" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="프로시저명">
                    <el-input type="text" disabled="disabled" :value="prcdId" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="SOURCE">
                    <el-input type="text" disabled="disabled" :value="bscTb" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="TARGET">
                    <el-input type="text" disabled="disabled" :value="trnsfTb" />
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
                    <el-input type="text" disabled="disabled" :value="errCd" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="오류발생일시">
                    <el-input type="text" disabled="disabled" :value="regDtlDt" />
                  </el-form-item>
                </el-col>
                <el-col :span="20">
                  <el-form-item label-width="130px" label="오류내용">
                    <el-input type="text" disabled="disabled" :value="errCn" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="SOURCE KEY DESC">
                    <el-input type="text" disabled="disabled" :value="erndKeyCn" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="TARGET KEY DESC">
                    <el-input type="text" disabled="disabled" :value="irisKeyCn" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="SOURCE KEY">
                    <el-input type="text" disabled="disabled" :value="erndKey" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="130px" label="TARGET KEY">
                    <el-input type="text" disabled="disabled" :value="irisKey" />
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
export default {
  name: 'TrnsResultPopUp',
  components: { Modal },
  data() {
    return {
      id: '124'

    }
  },
  closeFlag: false,
  methods: {
    closeBtnClick() {
      this.closeFlag = true
      this.$emit('close')
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
