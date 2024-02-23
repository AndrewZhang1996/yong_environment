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
              <a-form-item label="㶲名称">
                <a-input placeholder="请输入㶲名称"  v-decorator="[ 'name', {rules: [{ required: true, message: '请输入㶲名称', whitespace: false}]} ]"/>
              </a-form-item>
          </a-row>
          <a-row class="form-row" :gutter="16">
              <a-form-item label="㶲描述">
                <a-textarea placeholder="请输入㶲描述" :auto-size="{ minRows: 4, maxRows: 6 }" v-decorator="['description', {rules: [{ required: true, message: '请输入㶲描述', whitespace: true}]} ]"/>
              </a-form-item>
          </a-row>
          <a-row class="form-row" :gutter="16">
            <a-form-item label="㶲值">
              <a-input-number placeholder="请输入㶲值" style="width: 100%" :precision="10"  v-decorator="[ 'value', {rules: [{ required: true, message: '请输入㶲值'}]} ]"/>
            </a-form-item>  
          </a-row>
          <a-row class="form-row" :gutter="16">
            <a-form-item label="单位">
              <a-input placeholder="请输入单位" v-decorator="['unit', {rules: [{ required: true, message: '请输入单位', whitespace: false}]} ]"/>
            </a-form-item>
          </a-row>
        </a-card>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import {httpAction} from '@/api/manage'

  export default {
    name: "ExergyAddModal",
    components: {
    },
    data() {
      return {
        title: "添加㶲",
        visible: false,
        model: {},
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {},
        selectorData: [],
        url: {
          add: "/ec/exergy/addExergy"
        },
      }
    },
    created() {
    },
    methods: {
      add() {
        // this.form.resetFields();
        // this.model = {};
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
            console.log(formData)
            httpAction(httpurl, formData, method).then((res) => {
              if (res.success && res.result) {
                that.$message.success(res.message);
                that.$emit('ok');
                that.confirmLoading = false;
                that.form.resetFields();
                that.model = {};
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
    }
  }
</script>

<style scoped>
  .ant-modal-body {
    padding: 8px!important;
  }
</style>