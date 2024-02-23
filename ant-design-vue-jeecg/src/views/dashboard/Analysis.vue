<template>
<div style="text-align: center">
  <span style="font-size: xx-large; font-family: inherit;">
    您好, {{ nickname() }}!
  </span>
</div>
</template>

<script>
import { mapGetters } from 'vuex'
  export default {
    name: "Analysis",
    methods: {
        ...mapGetters(["nickname"]),
    },
    created() {
      
    },
  }
</script>

<style scoped>
</style>
Footer
