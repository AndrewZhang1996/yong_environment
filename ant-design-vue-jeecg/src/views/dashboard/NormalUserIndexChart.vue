<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24" wrap>
      <a-col :sm="24" :md="10" :lg="10" :style="chartCardStyle">
        <normal-user-sys-chart-card :loading="loading" title="当前日期">
          <div v-if="isNotificationDisplay">
            <normal-user-notification ref="normalUserNotification"/>
          </div>
        </normal-user-sys-chart-card>
      </a-col>
      <a-col :sm="24" :md="14" :lg="14" :style="chartCardStyle" v-if="isAirConditionerDisplay">
        <normal-user-cond-consum-chart-card :loading="loading" title="（昨日）家庭单位面积空调耗电量（kWh/㎡）">
          <div>
            <normal-user-air-cond-consum-mini-bar ref="normalUserAirCondConsumMiniBar" v-if="isAirConditionerDisplay"/>
          </div>
          <template slot="footer">数据更新时间: <span>{{ normalUserAirCondConsumFoot }}</span></template>
        </normal-user-cond-consum-chart-card>
      </a-col>
    </a-row>
      <a-row :gutter="24" wrap>
      <a-col :sm="24" :md="12" :lg="12" :style="chartCardStyle" v-if="isAirTemperatureDisplay && isHasBedroom">
        <a-carousel autoplay>
          <div v-for="(bedroom,index) in bedrooms" :key="index" id="bedrrom.roomId">
            <normal-user-room-chart-card :loading="loading" :title= "bedroom.roomName" >
              <div>
                <normal-user-room-line-chart-multid ref="normalUserBedroomLineChartMultid" :roomId="bedroom.roomId" :field="bedroom.roomName"/>
              </div>
              <template slot="footer">数据更新时间: <span>{{ normalUserBedroomFoot }}</span></template>
            </normal-user-room-chart-card>
          </div>
        </a-carousel>
      </a-col>
      <a-col :sm="24" :md="12" :lg="12" :style="chartCardStyle" v-if="isAirTemperatureDisplay && isHasLivingRoom">
          <div>
            <normal-user-room-chart-card :loading="loading" :title="livingRoomName">
              <div>
                <normal-user-room-line-chart-multid ref="normalUserLivingRoomLineChartMultid" :roomId="livingRoomId" :field="livingRoomName"/>
              </div>
              <template slot="footer">数据更新时间: <span>{{ normalUserLivingRoomFoot }}</span></template>
            </normal-user-room-chart-card>
          </div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import NormalUserSysChartCard from '@/components/NormalUserSysChartCard'
  import NormalUserCondConsumChartCard from '@/components/NormalUserCondConsumChartCard'
  import NormalUserRoomChartCard from '@/components/NormalUserRoomChartCard'
  import NormalUserAirCondConsumMiniBar from '@/components/chart/NormalUserAirCondConsumMiniBar'
  import NormalUserRoomLineChartMultid from '@/components/chart/NormalUserRoomLineChartMultid'
  import NormalUserNotification from '@/components/chart/NormalUserNotification'
  import { getAction } from '@/api/manage'

  // 定时主动刷新时间自定义
  // const refreshInterval = 60*60*1000; // 1小时
  // const refreshInterval = 10*1000; // 10s

  export default {
    name: "NormalUserIndexChart",
    components: {
      NormalUserSysChartCard,
      NormalUserCondConsumChartCard,
      NormalUserRoomChartCard,
      NormalUserAirCondConsumMiniBar,
      NormalUserRoomLineChartMultid,
      NormalUserNotification,
    },
    data() {
      return {
        ATooltip,
        loading: true,
        chartCardStyle: {"margin-bottom": "15px", "margin-top": "5px"},
        // 刷新定时器
        dataRefreshTimer: null,
        // 空调能耗数据刷新时间
        normalUserAirCondConsumFoot: "",
        // 客厅刷新时间
        normalUserLivingRoomFoot: "",
        // 卧室刷新时间
        normalUserBedroomFoot: "",
        // 通知刷新时间
        normalUserNotificationFoot: "",
        // 是否显示温度
        isAirTemperatureDisplay: false,
        // 是否显示空调能耗
        isAirConditionerDisplay: false,
        // 是否显示通知
        isNotificationDisplay: false,
        // 是否有客厅
        isHasLivingRoom: false,
        // 是否有卧室
        isHasBedroom: false,
        // 客厅roomId
        livingRoomId: "",
        // 卧室s
        bedrooms: [],
        livingRoomName: "",
        // 自动刷新时间（分钟）
        refreshInterval: 60
      }
    },
    created() {
      setTimeout(() => {
        this.loading = !this.loading
      }, 1000)
      this.getUserDisplayInfo(this);
      this.getRoomInfo(this);
      this.refreshAllIntervalStart(this);
      this.normalUserAirCondConsumFoot = this.normalUserLivingRoomFoot = this.normalUserBedroomFoot = this.normalUserNotificationFoot = new Date().toLocaleString();
    },
    methods: {
      // 定时刷新所有数据
      refreshAllIntervalStart(that) {
        let url = "cq/sys/getSettings";
        let param = {
          "key": "refresh_interval"
        };
        getAction(url, param).then((res) => {
          if (res.success) {
            that.refreshInterval = parseInt(res.result.value) * 60 * 1000;
          }
        });
				that.dataRefreshTimer = setInterval(()=>{
					console.log("定时刷新所有数据")
          that.refreshAll(that);
				}, that.refreshInterval)
      },
      // 关掉时间调度, 避免浏览器OOM
      refreshAllIntervalDestroy(that) {
        clearInterval(that.dataRefreshTimer);
        that.dataRefreshTimer = null;
      },
      // 刷新所有
      refreshAll(that) {
          // 异步刷新 解决刷新DOM没加载出来的问题
          that.$nextTick(function(){
            console.log(that.$refs)
            that.getUserDisplayInfo(that);
            that.refreshUserAirCondConsumData(that);
            that.refreshUserLivingRoomData(that);
            that.refreshUserBedroomData(that);
            that.refreshUserNotificationData(that);
            that.refreshAllIntervalDestroy(that);
            that.refreshAllIntervalStart(that);
          })
      },
      // 刷新能耗数据
      refreshUserAirCondConsumData(that) {
        if (that.$refs.normalUserAirCondConsumMiniBar != undefined){
          that.normalUserAirCondConsumFoot = that.$refs.normalUserAirCondConsumMiniBar.refresh();
        }
      },
      // 刷新客厅数据
      refreshUserLivingRoomData(that) {
        if (that.$refs.normalUserLivingRoomLineChartMultid != undefined){
          that.normalUserLivingRoomFoot = that.$refs.normalUserLivingRoomLineChartMultid.refresh();
        }
      },
      // 刷新卧室数据
      refreshUserBedroomData(that) {
        if (that.$refs.normalUserBedroomLineChartMultid != undefined){
          that.$refs.normalUserBedroomLineChartMultid.forEach(element => {
            that.normalUserBedroomFoot = element.refresh();
          });
        }
      },
      // 刷新通知数据
      refreshUserNotificationData(that) {
        if (that.$refs.normalUserNotification != undefined) {
          that.normalUserNotificationFoot = that.$refs.normalUserNotification.refresh();
        }
      },
      // 获得用户显示配置的信息
      getUserDisplayInfo(that) {
        let url = "cq/user/getUserDisplayInfo";
        let param = {
          "username": that.$store.getters.userInfo.username
        };
        getAction(url, param).then((res) => {
          if (res.success && res.result.length > 0) {
            that.isAirTemperatureDisplay = res.result[0].isAirTemperatureDisplay == 1;
            that.isAirConditionerDisplay = res.result[0].isAirConditionerDisplay == 1;
            that.isNotificationDisplay = res.result[0].isNotificationDisplay == 1;
          }
        })
      },
      // 获取用户的房间信息
      getRoomInfo(that) {
        let url = "cq/user/getRoomInfo";
        let param = {
          "username": that.$store.getters.userInfo.username
        };
        getAction(url, param).then((res) => {
          if (res.success) {
            that.bedrooms = [];
            res.result.forEach(element => {
              if (element.roomType == 0) {
                that.isHasLivingRoom = true;
                that.livingRoomId = element.id;
                that.livingRoomName = element.roomName;
              }
              if (element.roomType == 1) {
                that.isHasBedroom = true;
                let record = {}
                record["roomId"] = element.id;
                record["roomName"] = element.roomName;
                that.bedrooms.push(record);
              }
            });
          }
        });
      },
      initWebSocket: function () {
        // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
        var userId = this.$store.getters.userInfo.id;
        var url = window._CONFIG['domianURL'].replace("https://","wss://").replace("http://","ws://")+"/websocket/"+userId;
        //console.log(url);
        this.websock = new WebSocket(url);
        this.websock.onopen = this.websocketOnopen;
        this.websock.onerror = this.websocketOnerror;
        this.websock.onmessage = this.websocketOnmessage;
        this.websock.onclose = this.websocketOnclose;
      },
      websocketOnopen: function () {
        console.log("WebSocket连接成功");
        //心跳检测重置
        //this.heartCheck.reset().start();
      },
      websocketOnerror: function (e) {
        console.log("WebSocket连接发生错误");
        this.reconnect();
      },
      websocketOnmessage: function (e) {
        console.log("-----接收到消息-------",e.data);
        var data = eval("(" + e.data + ")"); //解析json对象
        if(data.cmd === "refresh"){
          console.log("刷新所有")
          this.refreshAll(this);
        }
        if(data.cmd === "force_refresh"){
          console.log("强制刷新页面");
          location.reload();
        }
      },
      websocketOnclose: function (e) {
        console.log("connection closed (" + e + ")");
        if(e){
          console.log("connection closed (" + e.code + ")");
        }
        this.reconnect();
      },
      reconnect() {
        var that = this;
        if(that.lockReconnect) return;
        that.lockReconnect = true;
        //没连接上会一直重连，设置延迟避免请求过多
        setTimeout(function () {
          console.info("尝试重连...");
          that.initWebSocket();
          that.lockReconnect = false;
        }, 5000);
      },
      heartCheckFun(){
        var that = this;
        //心跳检测,每20s心跳一次
        that.heartCheck = {
          timeout: 20000,
          timeoutObj: null,
          serverTimeoutObj: null,
          reset: function(){
            clearTimeout(this.timeoutObj);
            //clearTimeout(this.serverTimeoutObj);
            return this;
          },
          start: function(){
            var self = this;
            this.timeoutObj = setTimeout(function(){
              //这里发送一个心跳，后端收到后，返回一个心跳消息，
              //onmessage拿到返回的心跳就说明连接正常
              that.websocketSend("HeartBeat");
              console.info("客户端发送心跳");
              //self.serverTimeoutObj = setTimeout(function(){//如果超过一定时间还没重置，说明后端主动断开了
              //  that.websock.close();//如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
              //}, self.timeout)
            }, this.timeout)
          }
        }
      },
    },
    mounted() {
      this.initWebSocket();
    },
    destroyed() {
      this.refreshAllIntervalDestroy(this);
      this.websocketOnclose();
    },
    
  }
</script>

<style lang="less" scoped>

  /* 圆角卡片 */
  .ant-card-bordered {
    border-radius: 2em
  }

</style>