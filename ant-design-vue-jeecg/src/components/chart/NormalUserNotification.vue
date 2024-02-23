<template>
  <div style="style">
    <div v-html="content" :class="[{'good': ( colorType === 0 )},
            {'warning': ( colorType === 1 )},
            {'nothing': ( colorType === 2 )}]"></div>
  </div>
</template>

<script>
  import { getAction } from '../../api/manage';

  export default {
    name: 'NormalUserNotification',
    data() {
      return {
        style: {},
        content: "",
        colorType: 2
      }
    },mounted() {
      // 监听resize方法
      window.addEventListener("resize", this.orientationChange, false);
    },
    created() {
      this.orientationChange();
      // 初始化刷新
      this.refresh();
    },
    methods: {
      orientationChange() {
        if (screen.width < screen.height) {
          this.style = { "height": 60, "overflow": "auto"}
        } else {
          this.style = { "height": 100, "overflow": "auto"}
        }
      },
      refresh() {
        console.log("刷新通知数据")
        let url = "cq/user/getNotification";
        let param = {
          "username": this.$store.getters.userInfo.username
        };
        getAction(url, param).then((res) => {
          if (res.success && res.result != undefined) {
            this.content = res.result.content;
            this.colorType = res.result.colorType;
          }
        })
        return new Date().toLocaleString()
      },
    }
  }
</script>

<style lang="less" scoped>
  @import "chart";

  @media all and (orientation : landscape) { 
    .nothing {
        margin-top: 20px;
        padding: 0;
        color: rgba(0, 0, 0, 0.65);
        font-variant: tabular-nums;
        line-height: 1.5;
        list-style: none;
        font-feature-settings: 'tnum';
        position: relative;
        padding: 8px 15px 8px 15px;
        word-wrap: break-word;
        border-radius: 4px;
        height: 100px;
        overflow: auto;
      }

      .good {
        background-color: #f8cecc;
        border: 1px solid #fc847e;
        box-sizing: border-box;
        margin-top: 20px;
        padding: 0;
        color: rgba(0, 0, 0, 0.65);
        font-variant: tabular-nums;
        line-height: 1.5;
        list-style: none;
        font-feature-settings: 'tnum';
        position: relative;
        padding: 8px 15px 8px 15px;
        word-wrap: break-word;
        border-radius: 4px;
        height: 100px;
        overflow: auto;
      }

      .warning {
        background-color: #fffbe6;
        border: 1px solid #ffe58f;
        box-sizing: border-box;
        margin-top: 20px;
        padding: 0;
        color: rgba(0, 0, 0, 0.65);
        font-variant: tabular-nums;
        line-height: 1.5;
        list-style: none;
        font-feature-settings: 'tnum';
        position: relative;
        padding: 8px 15px 8px 15px;
        word-wrap: break-word;
        border-radius: 4px;
        height: 100px;
        overflow: auto;
      }
  }

  @media all and (orientation : portrait){ 
      .nothing {
        margin-top: 10px;
        padding: 0;
        color: rgba(0, 0, 0, 0.65);
        font-variant: tabular-nums;
        line-height: 1.5;
        list-style: none;
        font-feature-settings: 'tnum';
        position: relative;
        padding: 8px 15px 8px 15px;
        word-wrap: break-word;
        border-radius: 4px;
        height: 60px;
        overflow: auto;
      }

      .good {
        background-color: #f6ffed;
        border: 1px solid #b7eb8f;
        box-sizing: border-box;
        margin-top: 10px;
        padding: 0;
        color: rgba(0, 0, 0, 0.65);
        font-variant: tabular-nums;
        line-height: 1.5;
        list-style: none;
        font-feature-settings: 'tnum';
        position: relative;
        padding: 8px 15px 8px 15px;
        word-wrap: break-word;
        border-radius: 4px;
        height: 60px;
        overflow: auto;
      }

      .warning {
        background-color: #fffbe6;
        border: 1px solid #ffe58f;
        box-sizing: border-box;
        margin-top: 10px;
        padding: 0;
        color: rgba(0, 0, 0, 0.65);
        font-variant: tabular-nums;
        line-height: 1.5;
        list-style: none;
        font-feature-settings: 'tnum';
        position: relative;
        padding: 8px 15px 8px 15px;
        word-wrap: break-word;
        border-radius: 4px;
        height: 60px;
        overflow: auto;
      }
  }
  
</style>