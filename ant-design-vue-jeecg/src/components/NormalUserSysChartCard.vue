<template>
  <a-card :loading="loading" :body-style='cardStyle' :bordered="true">
    <div class="chart-card-header">
      <div class="meta">
        <span class="chart-card-title">{{ title }}</span>
        <span class="chart-card-action">
          <slot name="action"></slot>
        </span>
      </div>
      <div class="date-and-week"> {{ dateAndWeek }} </div>
      <div class="current-time"> {{ currentTime }} </div>
    </div>
    <div class="chart-card-content">
      <div class="content-fix">
        <slot></slot>
      </div>
    </div>
  </a-card>
</template>

<script>
  export default {
    name: "NormalUserSysChartCard",
    data() {
      return {
        cardStyle: { 
          "padding": "20px 24px 8px"
          },
        datetimeTimer: null,
        dateAndWeek: "",
        currentTime: ""
      }
    },
    props: {
      title: {
        type: String,
        default: ''
      },
      total: {
        type: String,
        default: ''
      },
      loading: {
        type: Boolean,
        default: false
      }
    },
    created() {
      this.dateFormat();
      this.getTime();
    },
    methods: {
      getTime() {
        var that = this;
				this.datetimeTimer = setInterval(()=>{
					//new Date() new一个data对象，当前日期和时间
					//toLocaleString() 方法可根据本地时间把 Date 对象转换为字符串，并返回结果。
					this.currentDatetime = this.dateFormat();
				},1000)
			},
      datetimeTimerDestroy() {
        clearInterval(this.datetimeTimer);
        this.datetimeTimer = null;
      },
      // 时间格式化
      dateFormat () {
        var date = new Date()
        var year = date.getFullYear()
        var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
        var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
        var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
        var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
        var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
        let week = date.getDay() // 星期
        let weekArr = [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ]
        // 拼接 时间格式处理
        this.dateAndWeek = year + '年' + month + '月' + day + '日 ' + weekArr[week];
        this.currentTime = hours + ':' + minutes + ':' + seconds;
      }

    },
    destroyed() {
      this.datetimeTimerDestroy()
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

@media all and (orientation : landscape) { 
.chart-card-content {
    margin-bottom: 12px;
    position: relative;
    height: 145px;
    width: 100%;
    overflow: auto;

    .content-fix {
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
      height: 90%;
      border: 2em;
    }
  }
  .date-and-week {
    text-overflow: ellipsis;
    word-break: break-all;
    white-space: nowrap;
    color: #000;
    margin-top: 4px;
    margin-bottom: 0;
    font-size: 20px;
    line-height: 38px;
    height: 38px;
  }

  .current-time {
    text-overflow: ellipsis;
    word-break: break-all;
    white-space: nowrap;
    color: #000;
    margin-top: 4px;
    margin-bottom: 0;
    font-size: 40px;
    line-height: 38px;
    height: 38px;
  }
} 

@media all and (orientation : portrait){ 

.chart-card-content {
    margin-bottom: 12px;
    position: relative;
    height: 100px;
    width: 100%;
    overflow: auto;

    .content-fix {
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
      height: 90%;
    }
  }

.date-and-week {
    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-all;
    white-space: nowrap;
    color: #000;
    margin-top: 4px;
    margin-bottom: 0;
    font-size: 30px;
    line-height: 38px;
    height: 38px;
  }

  .current-time {
    text-overflow: ellipsis;
    word-break: break-all;
    white-space: nowrap;
    color: #000;
    margin-top: 4px;
    margin-bottom: 0;
    font-size: 40px;
    line-height: 38px;
    height: 38px;
  }
} 

  

  
</style>