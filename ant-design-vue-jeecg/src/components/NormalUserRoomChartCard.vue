<template>
  <a-card :loading="loading" :body-style='cardStyle' :bordered="true">
    <div class="chart-card-header">
      <div class="meta">
        <span class="chart-card-title">（昨日）{{ title }}温度折线图</span>
        <span class="chart-card-action">
          <slot name="action"></slot>
        </span>
      </div>
    </div>
    <div class="chart-card-content">
      <div class="content-fix">
        <slot></slot>
      </div>
    </div>
    <div class="chart-card-footer">
      <div class="field">
        <slot name="footer"></slot>
      </div>
    </div>
  </a-card>
</template>

<script>
  export default {
    name: "NormalUserRoomChartCard",
    data() {
      return {
        cardStyle: { 
          "padding": "20px 24px 8px"
          }
      }
    },
    props: {
      title: {
        type: String,
        default: ''
      },
      loading: {
        type: Boolean,
        default: false
      }
    },
    created() {

    },
    methods: {

    }
  }
</script>

<style lang="less" scoped>

  .chart-card-title {
    font-size: 22px;
  }

  .ant-card-bordered {
    border-radius: 2em
  }
  .chart-card-header {
    position: relative;
    overflow: hidden;
    width: 100%;

    .meta {
      position: relative;
      overflow: hidden;
      width: 100%;
      color: rgba(0, 0, 0, .45);
      font-size: 14px;
      line-height: 22px;
    }
  }

  .chart-card-action {
    cursor: pointer;
    position: absolute;
    top: 0;
    right: 0;
  }

  .chart-card-footer {
    border-top: 1px solid #e8e8e8;
    padding-top: 9px;
    margin-top: 8px;

    > * {
      position: relative;
    }

    .field {
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      margin: 0;
    }
  }

@media all and (orientation : landscape) { 
.chart-card-content {
    margin-bottom: 0px;
    position: relative;
    height: 200px;
    width: 100%;

    .content-fix {
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
    }
  }
} 

@media all and (orientation : portrait){ 

.chart-card-content {
    margin-bottom: 0px;
    position: relative;
    height: 100px;
    width: 100%;

    .content-fix {
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
    }
  }
} 
  
</style>