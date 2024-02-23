<template>
  <div>
    <v-chart :height="height" :forceFit="true"  :data="data" :scale="scale" :padding="padding">
      <v-tooltip/>
      <v-axis dataKey="recordTime" :label="label"/>
      <v-guide type="text" :top="true" :position="textGuidePosition" :content="'高于35度显示为35度'" :vStyle="textGuideStyle" :offsetY="-10"/>
      <v-guide type="region" :start="start" :end="end" :vStyle="guideStyle" />
      <v-guide type="line" :start="upLineStart" :end="upLineEnd" :text="upLineText" />
      <v-guide type="line" :start="bottomLineStart" :end="bottomLineEnd" :text="bottomLineText" />
      <v-line position="recordTime*temperature" />
      <v-point position="recordTime*temperature" :shape="'circle'"/>
    </v-chart>
  </div>
</template>

<script>
  import { getAction } from '../../api/manage';

  const label = {
    rotate: 330,
    autoRotate: false,
    formatter: val => {
      return val.substring(0,5)
    }
  }

  export default {
    name: 'NormalUserRoomLineChartMultid',
    props: {
      field: {
        type: String
      },
      roomId: {
        type: String
      }
    },
    data() {
      return {
        data: [],
        scale: [{
          dataKey: 'temperature',
          min: 20,
          max: 35,
          alias: '温度',
          tickCount: 4
        },{
          dataKey: 'recordTime',
          type: 'time',
          mask: 'HH:mm:ss',
          min: '2022-06-29 09:00:00',
          max: '2022-06-30 08:00:00',
          nice: false,
          tickCount: 11,
          alias: '时间'
        }],
        height: 250,
        label,
        start: ['min', 24],
        end: ['max', 27],
        bottomLineStart: ['min', 24],
        bottomLineEnd: ['max', 24],
        bottomLineText: {
        position: 'start',
        content: '舒适温度下限: '
        },
        upLineStart: ['min', 27],
        upLineEnd: ['max', 27],
        upLineText: {
        position: 'start',
        content: '舒适温度上限: '
        },
        guideStyle: { 
        fill: "rgb(0, 185, 107)",
        fillOpacity: 0.1,
        lineWidth: 1.2,
        },
        textGuideStyle: {
          fill: "#8c8c8c",
          textAlign: 'center',
          fontSize: 17
        },
        textGuidePosition: ['median', 35],
        padding: {}
      }
    },mounted() {
      // 监听resize方法
      window.addEventListener("resize", this.orientationChange, false);
    },
    created() {
      this.orientationChange();
      this.refresh();
    },
    methods: {
      orientationChange() {
        if (screen.width < screen.height) {
          this.height = 110;
          this.padding = { top: 30, right: 30, bottom: 30, left: 30 }
        } else {
          this.height = 190;
          this.padding = { top: 30, right: 30, bottom: 30, left: 30 }
        }
      },
      refresh() {
        console.log("刷新" + this.field + "数据")
        let url = "cq/user/getAirTempRecord";
        let param = {
          "roomId": this.roomId
        };
        getAction(url, param).then((res) => {
          if (res.success) {
            console.log(res)
            this.data = [];
            res.result.airTempRecordList.forEach(element => {
                let record = {
                recordTime: element.recordTime, temperature: element.temperature
              };
                this.data.push(record);
            });
            let cozyTimeLower = parseFloat(res.result.cozyTimeLower.value);
            let cozyTimeUpper = parseFloat(res.result.cozyTimeUpper.value);
            this.start = ['min', cozyTimeLower];
            this.end = ['max', cozyTimeUpper];
            this.bottomLineStart = ['min', cozyTimeLower];
            this.bottomLineEnd = ['min', cozyTimeLower];
            this.bottomLineText["content"] = '舒适温度下限: ' + cozyTimeLower;
            this.upLineStart = ['min', cozyTimeUpper];
            this.upLineEnd = ['min', cozyTimeUpper];
            this.upLineText["content"] = '舒适温度上限: ' + cozyTimeUpper;

            // 定义时间
            var yesterdayDayTime = res.result.yesterdayDayTime;
            this.scale[1]["min"] = yesterdayDayTime + " 09:00:00";
            var todayDayTime = res.result.todayDayTime;
            this.scale[1]["max"] = todayDayTime + " 08:00:00";
          }
        });
        return new Date().toLocaleString()
      },
    }
  }
</script>

<style lang="less" scoped>
  @import "chart";
</style>