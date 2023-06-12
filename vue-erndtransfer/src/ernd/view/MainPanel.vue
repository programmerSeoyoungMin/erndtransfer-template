<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper">
          <svg-icon icon-class="data-transfer-dashboard" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            이관과제 수
          </div>
          <count-to :start-val="0" :end-val="102400" :duration="2600" class="card-panel-num" />
          <div class="card-panel-detail-text">
            Error(s) {{ errorCount }}
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper">
          <svg-icon icon-class="process-check-dashboard" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            이관 진행률
          </div>
          <count-to :start-val="0" :end-val="81212" :duration="3000" class="card-panel-num" />
          <div class="card-panel-detail-text">
            사업 정제된 과제 기준
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper">
          <svg-icon icon-class="not-shared-dashboard" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            이관불가 과제 수
          </div>
          <count-to :start-val="0" :end-val="9280" :duration="3200" class="card-panel-num" />
          <div class="card-panel-detail-text">
            사업,공고 미정제 과제 기준
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper">
          <svg-icon icon-class="error-message-dashboard" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            오류발생률
          </div>
          <count-to :start-val="0" :end-val="13600" :duration="3600" class="card-panel-num" />
          <div class="card-panel-detail-text">
            이관 총 데이터 기준
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'

export default {

  components: {
    CountTo
  },
  data() {
    return {
      errorCount: 0
    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  padding: 20px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 130px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    background: rgba(179,213,115, 0.90);
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    .card-panel-icon-wrapper {
      float: right;
      margin: 14px 0 0 14px;
      margin-left: 0px;
      padding: 20px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: right;
      font-size: 48px;
    }

    .card-panel-description {
      float: left;
      font-weight: bold;
      margin: 23px 0 0 15px;

      .card-panel-text {
        line-height: 7px;
        color: black;
        font-size: 20px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 22px;
        color:#fff;
      }

      .card-panel-detail-text {
        margin-top: 40px;
        font-size: 12px;
        color: #fff;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;

  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
