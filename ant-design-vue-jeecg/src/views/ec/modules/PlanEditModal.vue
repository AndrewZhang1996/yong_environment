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
      <a-form :form="form">

        <a-card class="card"  :bordered="false">
          <a-row class="form-row" :gutter="16">
              <a-form-item label="项目名称">
                <a-input placeholder="请输入项目名称"  v-decorator="[ 'name', {rules: [{ required: true, message: '请输入项目名称', whitespace: false}]} ]"/>
              </a-form-item>
          </a-row>
          <a-row class="form-row" :gutter="16">
              <a-form-item label="项目描述">
                <a-textarea placeholder="请输入项目描述" :auto-size="{ minRows: 4, maxRows: 6 }" v-decorator="['description', {rules: [{ required: true, message: '请输入项目描述', whitespace: true}]} ]"/>
              </a-form-item>
          </a-row>
        </a-card>

        <a-tabs defaultActiveKey="1">
          
          <a-tab-pane tab="原有建筑的废弃拆除" key="1" forceRender>
            <PlanPhaseTabPane :phase="1" ref="phase1"></PlanPhaseTabPane>
          </a-tab-pane>

          <a-tab-pane tab="原材料加工生产" key="2" forceRender>
            <PlanPhaseTabPane :phase="2" ref="phase2"></PlanPhaseTabPane>
          </a-tab-pane>

          <a-tab-pane tab="建筑施工" key="3" forceRender>
            <PlanPhaseTabPane :phase="3" ref="phase3"></PlanPhaseTabPane>
          </a-tab-pane>

          <a-tab-pane tab="运营维护" key="4" forceRender>
            <PlanPhaseTabPane :phase="4" ref="phase4"></PlanPhaseTabPane>
          </a-tab-pane>

          <a-tab-pane tab="废弃拆除" key="5" forceRender>
            <PlanPhaseTabPane :phase="5" ref="phase5"></PlanPhaseTabPane>
          </a-tab-pane>
          
        </a-tabs>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import {httpAction} from '@/api/manage'
  import PlanPhaseTabPane from './PlanPhaseTabPane.vue';
  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage';
  import { Modal } from 'ant-design-vue';

  export default {
    name: "PlanEditModal",
    components: {
      PlanPhaseTabPane
    },
    data() {
      return {
        title: "编辑方案",
        visible: false,
        model: {},
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {},
        url: {
          edit: "/ec/plan/editPlan",
        },
      }
    },
    created() {
    },
    methods: {
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.initFactors();
        console.log(this.model)
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'name', 'description'));
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
        const that = this;
        if (!this.checkCanSubmit()) {
          this.$message.error("存在未确定数据");
          return;
        }
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            httpurl += that.url.edit;
            method = 'post';
            let formData = Object.assign(that.model, values);
            var phase1ExergyFactorIds = [];
            var phase2ExergyFactorIds = [];
            var phase3ExergyFactorIds = [];
            var phase4ExergyFactorIds = [];
            var phase5ExergyFactorIds = [];
            
            that.$refs.phase1.data.forEach(e => {
              phase1ExergyFactorIds.push(e.id);
            });
            that.$refs.phase2.data.forEach(e => {
              phase2ExergyFactorIds.push(e.id);
            });
            that.$refs.phase3.data.forEach(e => {
              phase3ExergyFactorIds.push(e.id);
            });
            that.$refs.phase4.data.forEach(e => {
              phase4ExergyFactorIds.push(e.id);
            });
            that.$refs.phase5.data.forEach(e => {
              phase5ExergyFactorIds.push(e.id);
            });

            formData.phases = [
              {
                phaseType: 1,
                exergyFactorIds: phase1ExergyFactorIds
              },
              {
                phaseType: 2,
                exergyFactorIds: phase2ExergyFactorIds
              },
              {
                phaseType: 3,
                exergyFactorIds: phase3ExergyFactorIds
              },
              {
                phaseType: 4,
                exergyFactorIds: phase4ExergyFactorIds
              },
              {
                phaseType: 5,
                exergyFactorIds: phase5ExergyFactorIds
              },
            ];
            console.log(formData)
            httpAction(httpurl, formData, method).then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
                that.$refs.phase1.data = [];
                that.$refs.phase2.data = [];
                that.$refs.phase3.data = [];
                that.$refs.phase4.data = [];
                that.$refs.phase5.data = [];
                that.form.resetFields();
                that.model = {};
                that.confirmLoading = false;
                that.close();
              } else {
                that.$message.error("编辑失败！");
                that.confirmLoading = false;
              }
            }).finally(() => {
            })
          }
        })
      },
      handleCancel() {
        var that = this;
        const modal = Modal.confirm({
          title: '确定取消编辑吗？',
          content: '所有更改的内容将会丢失！',
          okText: '确定',
          okType: 'danger',
          cancelText: '取消',
          onOk: () => {
            that.model = {};
            that.$refs.phase1.data = [];
            that.$refs.phase2.data = [];
            that.$refs.phase3.data = [];
            that.$refs.phase4.data = [];
            that.$refs.phase5.data = [];
            modal.destroy();
            that.close()
          },
        });
      },
      initFactors() {
        var url = '/ec/exergyFactorDetail/getAllExergyFactor';
        var param = {};
        var that = this;
        that.confirmLoading = true;
        getAction(url,param).then((res) => {
          if (res.success) {
            console.log(res.result)
            that.$refs.phase1.selectorData = res.result;
            that.$refs.phase2.selectorData = res.result;
            that.$refs.phase3.selectorData = res.result;
            that.$refs.phase4.selectorData = res.result;
            that.$refs.phase5.selectorData = res.result;
          } else {
            that.$notification['error']({
                message: res.message,
              })
          }
          that.confirmLoading = false;
        })
      },
      checkCanSubmit() {
        for (const item of this.$refs.phase1.data) {
          if (item.editable) {
            return false;
          }
        }
        for (const item of this.$refs.phase2.data) {
          if (item.editable) {
            return false;
          }
        }
        for (const item of this.$refs.phase3.data) {
          if (item.editable) {
            return false;
          }
        }
        for (const item of this.$refs.phase4.data) {
          if (item.editable) {
            return false;
          }
        }
        for (const item of this.$refs.phase5.data) {
          if (item.editable) {
            return false;
          }
        }
        return true;
      },
    }
  }
</script>

<style scoped>
  .ant-modal-body {
    padding: 8px!important;
  }
</style>