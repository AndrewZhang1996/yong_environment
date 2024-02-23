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
  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  import { Modal } from 'ant-design-vue';

  export default {
    name: "ExergyEditModal",
    components: {
    },
    data() {
      return {
        title: "编辑㶲",
        visible: false,
        model: {},
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {},
        selectorData: [],
        url: {
          edit: "/ec/exergy/editExergy",
        },
      }
    },
    created() {
    },
    methods: {
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        console.log(this.model)
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'name', 'description', 'value', 'unit'));
        });

      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
      handleOk() {
        const that = this;
        const modal = Modal.confirm({
          title: '确定提交更改吗？',
          content: '所有与该㶲关联的方案的计算结果都将被重置，请确定关键方案是否已经保存！',
          okText: '确定',
          okType: 'primary',
          cancelText: '取消',
          onOk: () => {
            const other = that;
            // 触发表单验证
            that.form.validateFields((err, values) => {
              if (!err) {
                other.confirmLoading = true;
                let httpurl = '';
                let method = '';
                httpurl += other.url.edit;
                method = 'post';
                let formData = Object.assign(other.model, values);
                console.log(formData)
                httpAction(httpurl, formData, method).then((res) => {
                  if (res.success && res.result) {
                    other.$message.success(res.message);
                    other.$emit('ok');
                    other.confirmLoading = false;
                    other.model = {};
                    other.close();
                  } else {
                    other.$message.error("编辑失败！");
                    other.confirmLoading = false;
                  }
                }).finally(() => {
                  modal.destroy();
                })
              }
            })
          },
        });
        
      },
      handleCancel() {
        const modal = Modal.confirm({
          title: '确定取消编辑吗？',
          content: '所有更改的内容将会丢失！',
          okText: '确定',
          okType: 'danger',
          cancelText: '取消',
          onOk: () => {
            this.model = {}
            modal.destroy();
            this.close()
          },
        });
        
      },
    }
  }
</script>

<style scoped>
  .ant-modal-body {
    padding: 8px!important;
  }
</style>