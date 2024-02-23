<template>
  <a-modal
    :title="title"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">

        <a-card class="card"  :bordered="false">
          <a-row :gutter="16">
                <strong>项目名称:</strong>
                <br />
                <span>{{ model.name }}</span>
          </a-row>
          <a-row :gutter="16"><br /></a-row>
          <a-row :gutter="16">
              <strong>项目描述:</strong>
              <br />
              <span>{{ model.description }}</span>
          </a-row>
          <a-row :gutter="16"><br /></a-row>
          <a-row :gutter="16">
            <strong>总㶲值:</strong>
            <br />
            <span>{{ model.value }}</span>
          </a-row>
          <a-row :gutter="16"><br /></a-row>
          <a-row :gutter="16">
            <strong>计算时间:</strong>
            <br />
            <span>{{ model.calculationTime }}</span>
          </a-row>
        </a-card>

        <a-tabs defaultActiveKey="1">
          
          <a-tab-pane tab="原有建筑的废弃拆除" key="1" forceRender>
            <PlanPhaseHisTabPane :phase="1" ref="phase1"></PlanPhaseHisTabPane>
          </a-tab-pane>

          <a-tab-pane tab="原材料加工生产" key="2" forceRender>
            <PlanPhaseHisTabPane :phase="2" ref="phase2"></PlanPhaseHisTabPane>
          </a-tab-pane>

          <a-tab-pane tab="建筑施工" key="3" forceRender>
            <PlanPhaseHisTabPane :phase="3" ref="phase3"></PlanPhaseHisTabPane>
          </a-tab-pane>

          <a-tab-pane tab="运营维护" key="4" forceRender>
            <PlanPhaseHisTabPane :phase="4" ref="phase4"></PlanPhaseHisTabPane>
          </a-tab-pane>

          <a-tab-pane tab="废弃拆除" key="5" forceRender>
            <PlanPhaseHisTabPane :phase="5" ref="phase5"></PlanPhaseHisTabPane>
          </a-tab-pane>
          
        </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>
  import PlanPhaseHisTabPane from './PlanPhaseHisTabPane.vue';

  export default {
    name: "PlanHisDetailModal",
    components: {
      PlanPhaseHisTabPane
    },
    data() {
      return {
        title: "编辑方案",
        visible: false,
        model: {},
        confirmLoading: false,
        validatorRules: {},
      }
    },
    created() {
    },
    methods: {
      show(record) {
        this.model = Object.assign({}, record);
        console.log(this.model)
        this.visible = true;
        this.$nextTick(() => {
          this.$refs.phase1.initFactors(this.model.id);
          this.$refs.phase2.initFactors(this.model.id);
          this.$refs.phase3.initFactors(this.model.id);
          this.$refs.phase4.initFactors(this.model.id);
          this.$refs.phase5.initFactors(this.model.id);
        });

      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
      handleOk() {
        this.$emit('ok');
        this.$refs.phase1.data = [];
        this.$refs.phase2.data = [];
        this.$refs.phase3.data = [];
        this.$refs.phase4.data = [];
        this.$refs.phase5.data = [];
        this.$refs.phase1.innerData = [];
        this.$refs.phase2.innerData = [];
        this.$refs.phase3.innerData = [];
        this.$refs.phase4.innerData = [];
        this.$refs.phase5.innerData = [];
        this.model = {};
        this.close();
      },
      handleCancel() {
        this.close()
      },
    }
  }
</script>

<style scoped>
  .ant-modal-body {
    padding: 8px!important;
  }
</style>