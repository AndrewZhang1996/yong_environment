<template>
  <div :style="{'width':width==null?'auto':width+'px'}">
    <v-chart :forceFit="width==null" :height="height" :data="data" :padding="padding" :scale="scale">
      <v-coord type="rect" direction="LT" />
      <v-tooltip/>
      <v-axis dataKey="room" :label="label"/>
      <v-interval position="room*consum" :color="color" :label="labelInterval"/>
    </v-chart>
  </div>
</template>

<script>
  import { getAction } from '../../api/manage';

  const tooltip = [
    'room*consum',
    (room, consum) => ({
      name: room,
      value: consum
    })
  ]

  const scale = [{
    dataKey: 'room',
    min: 0
    }, {
      dataKey: 'consum',
      min: 0,
      max: 3,
      tickInterval: 1,
      alias: "（kWh/㎡）"
    }]

  const label = {
    textStyle: {
      fill: '#aaaaaa'
    }
  }

  const labelInterval = ['consum', {
    formatter: function formatter(text) {
      var val = parseFloat(text);
      if (val < 10) {
        return val;
      }
      if (val > 20) {
        return val;
      }
    },
    offset: 10
  }]

  let color = []

  export default {
    name: 'NormalUserAirCondConsumMiniBar',
    props: {
      dataSource: {
        type: Array,
        default: () => [
          { room: "我的房间", consum: 1},
          { room: "相似房间", consum: 2}
        ]
      },
      width: {
        type: Number,
        default: null
      }
    },mounted() {
      // 监听resize方法
      window.addEventListener("resize", this.orientationChange, false);
    },
    created() {
      this.orientationChange();
      this.refresh();
    },
    data() {
      return {
        tooltip,
        data: [],
        scale,
        height: 200,
        color,
        label,
        labelInterval,
        padding: { }
      }
    },
    methods: {
      refresh() {
        console.log("刷新空调能耗数据")
        let url = "cq/user/getAirCondConsumRecord";
        let param = {
          "username": this.$store.getters.userInfo.username
        };
        getAction(url, param).then((res) => {
          if (res.success) {
            let myRoomConsum = {};
            myRoomConsum["room"] = "当前家庭";
            if(res.result.airCondConsumLevel != null) {
              myRoomConsum["consum"] = res.result.airCondConsumLevel.energyConsumption;
            }
            let averageRoomConsum = {};
            averageRoomConsum["room"] = "相似家庭";
            averageRoomConsum["consum"] = parseFloat(res.result.averageConsumLevel.value);
            this.data = []
            this.data.push(myRoomConsum);
            this.data.push(averageRoomConsum);
            this.color = ['room*consum', function(room, consum) {
                if (room === '我的房间') {
                  // 暂时注释，取消颜色逻辑
                  // if (parseInt(consum) > averageRoomConsum["consum"]) {
                  //   return 'rgb(248,172,48)';
                  // }
                  // if (parseInt(consum) < averageRoomConsum["consum"]) {
                  //   return 'rgb(87,194,45)'
                  // }
                  return '#2194ff';
                } else {
                  return '#2194ff';
                }
                
              }]
          }
        });
        return new Date().toLocaleString()
      },
      orientationChange() {
        if (screen.width < screen.height) {
          this.height = 100;
          this.padding = { top: 20, right: 80, bottom: 20, left: 90 }
        } else {
          this.height = 220;
          this.padding = { top: 60, right: 80, bottom: 5, left: 90 }
        }
      },
    }
  }
</script>

<style lang="less" scoped>
  @import "chart";
</style>