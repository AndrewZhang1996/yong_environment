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
              <a-form-item label="影响因子名称">
                <a-input placeholder="请输入影响因子名称"  v-decorator="[ 'name', {rules: [{ required: true, message: '请输入影响因子名称', whitespace: false}]} ]"/>
                <p style="font-size: smaller; padding-left: 0.7rem; color: darkgray;">注释：影响因子=㶲*因子1+㶲*因子2*...+㶲*因子n</p>
              </a-form-item>
          </a-row>
          <a-row class="form-row" :gutter="16">
              <a-form-item label="影响因子描述">
                <a-textarea placeholder="请输入影响因子描述" :auto-size="{ minRows: 4, maxRows: 6 }" v-decorator="['description', {rules: [{ required: true, message: '请输入影响因子描述', whitespace: true}]} ]"/>
              </a-form-item>
          </a-row>
          <!-- <a-row class="form-row" :gutter="16">
              <a-form-item label="㶲">
                
              <a-button type="default" @click="refreshExergy">刷新㶲</a-button>
                <a-select
                        show-search
                        placeholder="选择㶲"
                        option-filter-prop="children"
                        style="margin: -5px 0"
                        :filter-option="filterOption"
                        @change="value => handleChange(value)"
                        v-decorator="['exergyId', {rules: [{ required: true, message: '请选择㶲', whitespace: true}]} ]"
                    >
                      <a-select-option v-for="d in selectorData" :key="d.id">
                      {{ d.name }} ({{ d.unit }})
                      </a-select-option>
                  </a-select>
                </a-form-item>
                  
          </a-row> -->
        </a-card>

        <a-tabs defaultActiveKey="1" >
          
          <a-tab-pane tab="因子设置" key="1">
            <FactorExergyTabPane ref="factorExergy" :selectorData="selectorData"></FactorExergyTabPane>
          </a-tab-pane>
          
        </a-tabs>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import {httpAction} from '@/api/manage'
  import FactorExergyTabPane from './FactorExergyTabPane.vue';
  import { getAction } from '@/api/manage'

  export default {
    name: "FactorAddModal",
    components: {
      FactorExergyTabPane
    },
    data() {
      return {
        title: "添加影响因子",
        visible: false,
        model: {},
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {},
        selectorData: [],
        url: {
          add: "/ec/exergyFactorDetail/addNewExergyFactor"
        },
      }
    },
    created() {
    },
    methods: {
      add() {
        // this.form.resetFields();
        // this.model = {};
        this.initExergy();
        this.visible = true;
      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
      handleOk() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            httpurl += this.url.add;
            method = 'post';
            let formData = Object.assign(this.model, values);
            formData.exergyFactorList = that.$refs.factorExergy.data;
            console.log(formData)
            httpAction(httpurl, formData, method).then((res) => {
              if (res.success && res.result) {
                that.$message.success(res.message);
                that.$emit('ok');
                that.confirmLoading = false;
                that.form.resetFields();
                that.model = {};
                that.$refs.factorExergy.data = [];
                that.close();
              } else {
                that.$message.error("添加失败！");
                that.confirmLoading = false;
              }
            }).finally(() => {
            })
          }
        })
      },
      handleCancel() {
        this.close()
      },
      handleChange(value) {
        // this.model.exergyId = value;
      },
      handleFocus() {
          // console.log('focus');
      },
      // 直接在options里面搜索
      filterOption(input, option) {
          return (
          option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
          );
      },
      initExergy() {
        var url = '/ec/exergy/getAllExergy';
        var param = {};
        const that = this;
        this.confirmLoading = true;
        getAction(url,param).then((res) => {
          if (res.success) {
            that.selectorData = res.result;
          } else {
            that.$notification['error']({
                message: res.message,
              })
          }
          that.confirmLoading = false;
        })
      },
      refreshExergy() {
        var url = '/ec/exergy/getAllExergy';
        var param = {};
        const that = this;
        this.confirmLoading = true;
        getAction(url,param).then((res) => {
          if (res.success) {
            that.selectorData = res.result;
            that.$notification['info']({
                message: '刷新成功！',
              })
          } else {
            that.$notification['error']({
                message: res.message,
              })
          }
          that.confirmLoading = false;
        })
      }
    }
  }
</script>

<style scoped>
  .ant-modal-body {
    padding: 8px!important;
  }
</style>