<template>
  <div id="fullscreen-wrapper">
      <div id="fullscreen">
        <normal-user-index-chart></normal-user-index-chart>
      </div>
      <div class="fullscreen-section fullscreen-left"></div>
      <div class="fullscreen-section fullscreen-right"></div>
    </div>
</template>

<style>
    #fullscreen-wrapper {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      z-index: 999999;
    }
    #fullscreen {
      display: block;
      position: relative;
      /* left: 50%; */
      /* top: 50%; */
      /* width: 120px; */
      /* height: 120px; */
      margin: 10px 15px 10px 15px;
      /* border-radius: 50%; */
      /* border: 3px solid transparent; */
      /* COLOR 1 */
      border-top-color: #FFF;
      z-index: 1001;
    }
    #fullscreen-wrapper .fullscreen-section {
      position: fixed;
      top: 0;
      width: 51%;
      height: 100%;
      background: #49a9ee;
      /* Old browsers */
      z-index: 1000;
      -webkit-transform: translateX(0);
      /* Chrome, Opera 15+, Safari 3.1+ */
      -ms-transform: translateX(0);
      /* IE 9 */
      transform: translateX(0);
      /* Firefox 16+, IE 10+, Opera */
    }
    #fullscreen-wrapper .fullscreen-section.fullscreen-left {
      left: 0;
    }
    #fullscreen-wrapper .fullscreen-section.fullscreen-right {
      right: 0;
    }
</style>

<script>
  import NormalUserIndexChart from './NormalUserIndexChart'

  export default {
    name: "NormalUserAnalysis",
    components: {
      NormalUserIndexChart
    },
    created() {
      
    }
  }
</script>