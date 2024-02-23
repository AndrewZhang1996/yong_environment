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
              <a-form-item label="影响因子名称描述">
                <a-textarea placeholder="请输入影响因子名称描述" :auto-size="{ minRows: 4, maxRows: 6 }" v-decorator="['description', {rules: [{ required: true, message: '请输入影响因子名称描述', whitespace: true}]} ]"/>
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
                      @focus="handleFocus"
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
            <FactorExergyTabPane ref="factorList" :selectorData="selectorData"></FactorExergyTabPane>
          </a-tab-pane>
          
        </a-tabs>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import {httpAction} from '@/api/manage'
  import FactorExergyTabPane from './FactorExergyTabPane.vue';
  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  import { Modal } from 'ant-design-vue';

  export default {
    name: "FactorEditModal",
    components: {
      FactorExergyTabPane
    },
    data() {
      return {
        title: "编辑影响因子",
        visible: false,
        model: {},
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {},
        selectorData: [],
        url: {
          edit: "/ec/exergyFactorDetail/editExergyFactor",
        },
      }
    },
    created() {
    },
    methods: {
      edit(record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.initExergy();
        // console.log(this.model)
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'name', 'description', 'exergyId'));
          this.initExergyFactorList(this.model.id);
        });

      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
      handleOk() {
        if (!this.checkCanSubmit()) {
          this.$message.error("存在未确定数据");
          return;
        }
        const that = this;
        const modal = Modal.confirm({
          title: '确定提交更改吗？',
          content: '所有与该影响因子关联的方案的计算结果都将被重置，请确定关键方案是否已经保存！',
          okText: '确定',
          okType: 'primary',
          cancelText: '取消',
          onOk: () => {
              // 触发表单验证
              const other = that;
              that.form.validateFields((err, values) => {
                if (!err) {
                  other.confirmLoading = true;
                  let httpurl = '';
                  let method = '';
                  httpurl += this.url.edit;
                  method = 'post';
                  let formData = Object.assign(this.model, values);
                  formData.exergyFactorList = other.$refs.factorList.data;
                  console.log(formData)
                  httpAction(httpurl, formData, method).then((res) => {
                    if (res.success && res.result) {
                      other.$message.success(res.message);
                      other.$emit('ok');
                      other.confirmLoading = false;
                      other.model = {};
                      other.$refs.factorList.data = [];
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
        const that = this;
        const modal = Modal.confirm({
          title: '确定取消编辑吗？',
          content: '所有更改的内容将会丢失！',
          okText: '确定',
          okType: 'danger',
          cancelText: '取消',
          onOk: () => {
            that.model = {}
            that.$refs.factorList.data = []
            modal.destroy();
            that.close()
          },
        });
        
      },
      handleChange(value) {
        this.model.exergyId = value;
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
      },
      initExergyFactorList(id) {
        var url = '/ec/factor/getAllFactorsByExergyFactorDetailId';
        var param = {
          exergyFactorDetailId: id,
        };
        const that = this;
        this.confirmLoading = true;
        getAction(url,param).then((res) => {
          if (res.success) {
            res.result.forEach(e => {
              e.editable = false;
              e.isNew = false;
              e.factor = {
                id: e.factorId,
                name: e.name,
                value: e.value,
                unit: e.unit,
              }
            })
            that.$refs.factorList.data = res.result;
          } else {
            that.$notification['error']({
                message: '获取因子失败!',
              });
            that.close();
          }
          that.confirmLoading = false;
        })
      },
      checkCanSubmit() {
        for (const item of this.$refs.factorList.data) {
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