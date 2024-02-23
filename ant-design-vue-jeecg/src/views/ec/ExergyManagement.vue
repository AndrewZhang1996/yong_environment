<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="㶲名称">
              <j-input placeholder="请输入㶲名称模糊查询" v-model="queryParam.name" style="width: calc(100% + 12px);"></j-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="最小㶲值">
              <a-input-number placeholder="最小㶲值" :precision="10" :step="0.0000000001" type="ge" v-model="queryParam.value_begin" style="width: calc(100% + 12px);"></a-input-number>
              
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="最大㶲值">
              <a-input-number placeholder="最大㶲值" :precision="10" :step="0.0000000001" type="le" v-model="queryParam.value_end" style="width: calc(100% + 12px);"></a-input-number>
            </a-form-item>
          </a-col>
          <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </a-col>
          </span>
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="addExergy" type="primary" icon="plus">新增</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <!-- <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item> -->
          <a-menu-item key="1">
            暂无批量操作
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{
          selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
        <span style="float:right;">
          <a @click="loadData()"><a-icon type="sync" />刷新</a>
          <a-divider type="vertical" />
          <a-popover title="自定义列" trigger="click" placement="leftBottom">
            <template slot="content">
              <a-checkbox-group @change="onColSettingsChange" v-model="settingColumns" :defaultValue="settingColumns">
                <a-row style="width: 400px">
                  <template v-for="(item,index) in defColumns">
                    <template v-if="item.key!='rowIndex'&& item.dataIndex!='action'">
                        <a-col :span="12"><a-checkbox :value="item.dataIndex"><j-ellipsis :value="item.title" :length="10"></j-ellipsis></a-checkbox></a-col>
                    </template>
                  </template>
                </a-row>
              </a-checkbox-group>
            </template>
            <a><a-icon type="setting" />设置</a>
          </a-popover>
        </span>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <div slot="filterDropdown">
          <a-card>
            <a-checkbox-group @change="onColSettingsChange" v-model="settingColumns" :defaultValue="settingColumns">
              <a-row style="width: 400px">
                <template v-for="(item,index) in defColumns">
                  <template v-if="item.key!='rowIndex'&& item.dataIndex!='action'">
                    <a-col :span="12"><a-checkbox :value="item.dataIndex"><j-ellipsis :value="item.title" :length="10"></j-ellipsis></a-checkbox></a-col>
                  </template>
                </template>
              </a-row>
            </a-checkbox-group>
          </a-card>
        </div>
        <a-icon slot="filterIcon" type='setting' :style="{ fontSize:'16px',color:  '#108ee9' }" />

        <span slot="action" slot-scope="text, record">
          <a-dropdown>
            <a class="ant-dropdown-link">操作 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="editExergy(record)">编辑</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="deleteExergy(record.id)">删除</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 一对多表单区域 -->
    <ExergyAddModal ref="exergyAddModal" @ok="modalFormOk"></ExergyAddModal>
    <ExergyEditModal ref="exergyEditModal" @ok="modalFormOk"></ExergyEditModal>

    <!-- 弹窗区域 -->

  </a-card>
</template>

<script>
  import ExergyAddModal from './modules/ExergyAddModal'
  import ExergyEditModal from './modules/ExergyEditModal'
  import JInput from '@/components/jeecg/JInput.vue';
  import { ExergyManagementListMixin } from './ExergyManagementListMixin'
  import Vue from 'vue'
  import { Modal } from 'ant-design-vue';
  import { getAction,postAction } from '@/api/manage'

  export default {
    name: "ExergyManagement",
    mixins:[ExergyManagementListMixin],
    components: {
      ExergyAddModal,
      ExergyEditModal,
      JInput
    },
    data() {
      return {
        description: '㶲管理列表',
        //表头
        columns:[],
        //列设置
        settingColumns:[],
        loading: false,
        //列定义
        defColumns: [
          {
            title: '#',
            dataIndex: '',
            key: 'rowIndex',
            width: 60,
            align: "center",
            customRender: function (t, r, index) {
              return parseInt(index) + 1;
            }
          },
          {
            title: '㶲名称',
            align: "center",
            dataIndex: 'name',
            width: "20%"
          },
          {
            title: '㶲描述',
            align: "center",
            dataIndex: 'description',
            width: "30%"
          },
          {
            title: '㶲值',
            align: "center",
            dataIndex: 'value',
            width: "16%"
          },
          {
            title: '单位',
            align: "center",
            dataIndex: 'unit',
            width: "10%"
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: "center",
            scopedSlots: {
              // filterDropdown: 'filterDropdown',
              // filterIcon: 'filterIcon',
              customRender: 'action'},
          }

        ],
        url: {
          list: "/ec/exergy/list",
          delete: "",
          deleteBatch: "",
          exportXlsUrl: ""
        },
        dataSource: [],
        queryParam: {}
      }
    },
    methods: {
      addExergy: function () {
        this.$refs.exergyAddModal.add();
        this.$refs.exergyAddModal.title = "新增㶲";
      },
      editExergy: function (record) {
        this.$refs.exergyEditModal.edit(record);
        this.$refs.exergyEditModal.title = "编辑㶲";
      },
      deleteExergy(exergyId) {
        var params = {
                    id: exergyId,
                  };
        var that = this;
        const modal = Modal.confirm({
                title: '确定删除吗',
                content: '如果存在与该㶲相关联的影响因子，则会删除失败',
                okText: '确定',
                okType: 'danger',
                cancelText: '取消',
                onOk: (exergyId) => {
                  console.log(exergyId)
                  postAction('/ec/exergy/deleteExergy', params).then((res) => {
                    if (res.success && res.result) {
                      //重新计算分页问题
                      that.reCalculatePage(1)
                      that.$message.info('删除成功！')
                      that.loadData();
                      modal.destroy();
                    } else {
                      that.$message.error('删除失败！')
                    }
                  }).finally(() => {
                    // modal.destroy();
                  })
                },
              });
      },
      //列设置更改事件
      onColSettingsChange (checkedValues) {
        var key = this.$route.name+":colsettings";
        Vue.ls.set(key, checkedValues, 7 * 24 * 60 * 60 * 1000)
        this.settingColumns = checkedValues;
        const cols = this.defColumns.filter(item => {
          if(item.key =='rowIndex'|| item.dataIndex=='action'){
            return true
          }
          if (this.settingColumns.includes(item.dataIndex)) {
            return true
          }
          return false
        })
        this.columns =  cols;
      },
      initColumns(){
        var key = this.$route.name+":colsettings";
        let colSettings= Vue.ls.get(key);
        if(colSettings==null||colSettings==undefined){
          let allSettingColumns = [];
          this.defColumns.forEach(function (item,i,array ) {
            allSettingColumns.push(item.dataIndex);
          })
          this.settingColumns = allSettingColumns;
          this.columns = this.defColumns;
        }else{
          this.settingColumns = colSettings;
          const cols = this.defColumns.filter(item => {
            if(item.key =='rowIndex'|| item.dataIndex=='action'){
              return true;
            }
            if (colSettings.includes(item.dataIndex)) {
              return true;
            }
            return false;
          })
          this.columns =  cols;
        }
      }
    },
    mounted() {
    },
    created() {
      this.initColumns();
    },
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>