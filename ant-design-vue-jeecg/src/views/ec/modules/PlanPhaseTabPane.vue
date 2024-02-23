<template>
    <div>
        <a-table :columns="columns" :dataSource="data" :pagination="false" size="middle" :loading="loading">
            <template slot="name" slot-scope="text, record">
            <a-tooltip  title="必填项" :defaultVisible="false" :overlayStyle="{ color: 'red' }">
                <a-select
                    v-if="record.editable"
                    show-search
                    placeholder="选择影响因子"
                    option-filter-prop="children"
                    style="margin: -5px 0"
                    :value="text"
                    :filter-option="filterOption"
                    @focus="handleFocus"
                    @change="value => handleChange(value, record.key)"
                >
                    <a-select-option v-for="d in selectorData" :key="d.id">
                    {{ d.name }}
                    </a-select-option>
                </a-select>
            <template v-else>{{ text }}</template>
            </a-tooltip>
            </template>
            <template slot="description" slot-scope="text">
            <template>{{ text }}</template>
            </template>
            <!-- <template slot="unit" slot-scope="text">
            <template>{{ text }}</template>
            </template> -->
            <template slot="operation" slot-scope="text, record">
            <template v-if="record.editable">
                <span v-if="record.isNew">
                <a @click="saveRow(record.key)">添加</a>
                <a-divider type="vertical"/>
                <a-popconfirm title="是否要删除此行？" @confirm="removeRow(record.key)"><a>删除</a></a-popconfirm>
                </span>
                <span v-else>
                <a @click="saveRow(record.key)">保存</a>
                <a-divider type="vertical"/>
                <a @click="cancelEditRow(record.key)">取消</a>
                </span>
            </template>
            <span v-else>
                <a @click="editRow(record.key)">编辑</a>
                <a-divider type="vertical"/>
                <a-popconfirm title="是否要删除此行？" @confirm="removeRow(record.key)"><a>删除</a></a-popconfirm>
            </span>
            </template>
        </a-table>

        <a-button style="width: 100%; margin-top: 16px; margin-bottom: 8px" type="dashed" icon="plus" @click="newRow">新增成员</a-button>
    </div>
</template>
<script>
import { getAction } from '@/api/manage'

export default {
    name: "PlanPhaseTabPane",
    props: ['phase'],
    data() {
        return {
            phaseType: this.phase,
            loading: false,
            // table
            columns: [
                {
                    title: '环境影响名称',
                    dataIndex: 'name',
                    key: 'name',
                    width: '45%',
                    scopedSlots: {customRender: 'name'}
                },
                {
                    title: '描述',
                    dataIndex: 'description',
                    key: 'description',
                    width: '25%',
                    scopedSlots: {customRender: 'description'}
                },
                // {
                //     title: '㶲名称',
                //     dataIndex: 'exergyName',
                //     key: 'exergyName',
                //     width: '10%',
                //     scopedSlots: {customRender: 'exergyName'}
                // },
                {
                    title: '操作',
                    key: 'action',
                    scopedSlots: {customRender: 'operation'}
                }
            ],
            data: [],
            selectorData: []
        }
    },
    created() {
    },
    methods: {
        // phase 1
        newRow () {
            // 通过时间戳生成 UUID
            let uuid = Math.round(new Date().getTime()).toString();
            this.data.push({
                key: uuid,
                id: '',
                name: '',
                description: '',
                exergyName: '',
                phaseType: this.phaseType,
                editable: true,
                isNew: true
            })
        },
        removeRow (key) {
            const newData = this.data.filter(item => item.key !== key)
            this.data = newData
        },
        saveRow (key) {
            let target = this.data.filter(item => item.key === key)[0]
            if (this.newRowPass(target, ['name'])) {
            target.editable = false
            target.isNew = false
            } else {
            this.$message.error('缺少必填项！');
            }
        },
        handlerRowChangePhase1 (value, key, column) {
            const newData = [...this.data]
            const target = newData.filter(item => key === item.key)[0]
            if (target) {
            target[column] = value
            this.data = newData
            }
        },
        editRow (key) {
            let target = this.data.filter(item => item.key === key)[0]
            target.editable = !target.editable
        },
        cancelEditRow (key) {
            let target = this.data.filter(item => item.key === key)[0]
            target.editable = false
        },
        // 当值变化时，数据发生变化
        handleChange(value, key) {
            let input = this.selectorData.filter(item => item.id === value)[0]
            const newData = [...this.data]
            const target = newData.filter(item => key === item.key)[0]
            if (target) {
                target['id'] = input['id']
                target['name'] = input['name']
                target['description'] = input['description']
                target['exergyName'] = input['exergyName']
                this.data = newData
            }
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
        // 验证该输的参数是否为空
        newRowPass (obj, selectedColumns) {
            for (let key in obj) {
            console.log(selectedColumns.includes(key))
            if (selectedColumns.includes(key) && !((obj[key] === 0 || obj[key]) && obj[key].toString().replace(/(^\s*)|(\s*$)/g, '') !== '')) {
                return false;
            }
            }
            return true;
        },
        initFactors(planId) {
            var url = '/ec/phase/getExergyFactorDetailByPhaseTypeAndPlanId';
            var param = {
                phaseType: this.phaseType,
                planId: planId
            };
            var that = this;
            this.loading = true;
            getAction(url,param).then((res) => {
                if (res.success) {
                    res.result.forEach(e => {
                        e.editable = false;
                        e.isNew = false;
                    })
                    that.data = res.result;
                } else {
                    that.$notification['error']({
                        message: '获取影响因子失败!',
                    });
                }
                that.loading = false;
            })
        }
    }
    
}
</script>