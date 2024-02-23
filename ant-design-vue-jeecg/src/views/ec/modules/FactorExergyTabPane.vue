<template>
    <div>
        <a-table :columns="columns" :dataSource="data" :pagination="false" size="middle">
                
            <template v-for="(col, i) in ['name','exergyName', 'value', 'unit', ]" :slot="col" slot-scope="text, record">
                <a-tooltip :title="col!='unit' ? '必填项' : '非必填项'" :defaultVisible="false" :overlayStyle="{ color: 'red' }">
                    <template v-if="record.editable">
                        <template v-if="col == 'value'">
                            <a-input-number :key="col" :precision="10" style="margin: -5px 0; width: 100%"  :value="text" :placeholder="columns.find(e => e.key === col).title" @change="e => handlerRowChange(e, record.key, col)"/>
                        </template>
                        <template v-else-if="col === 'exergyName'">
                            <a-select
                                    :key="col"
                                    show-search
                                    placeholder="选择㶲"
                                    option-filter-prop="children"
                                    style="margin: -5px 0"
                                    :filter-option="filterOption"
                                    @change="e => handlerRowChange(e, record.key, col)"
                                >
                                <a-select-option v-for="d in selectorData" :key="d.id">
                                {{ d.name }} ({{d.value}} {{ d.unit }})
                                </a-select-option>
                            </a-select>
                        </template>
                        <template v-else>
                            <a-input :key="col" style="margin: -5px 0"  :value="text" :placeholder="columns.find(e => e.key === col).title" @change="e => handlerRowChange(e.target.value, record.key, col)"/>
                        </template>
                    </template>
                    <template v-else>{{ text }}</template>
                    
                </a-tooltip>
            </template>

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


export default {
    name: "FactorExergyTabPane",
    props: {
        selectorData: []
    },
    data() {
        return {
            visible: false,
            model: {},
            // table
            columns: [
                {
                    title: '㶲',
                    dataIndex: 'exergyName',
                    key: 'exergyName',
                    width: '25%',
                    scopedSlots: {customRender: 'exergyName'}
                },
                {
                    title: '因子名称',
                    dataIndex: 'factor.name',
                    key: 'name',
                    width: '25%',
                    scopedSlots: {customRender: 'name'}
                },
                {
                    title: '值',
                    dataIndex: 'factor.value',
                    key: 'value',
                    width: '20%',
                    scopedSlots: {customRender: 'value'}
                },
                {
                    title: '单位',
                    dataIndex: 'factor.unit',
                    key: 'unit',
                    width: '10%',
                    scopedSlots: {customRender: 'unit'}
                },
                {
                    title: '操作',
                    key: 'action',
                    scopedSlots: {customRender: 'operation'}
                }
            ],
            data: []
        }
    },
    created() {
    },
    methods: {
        // 直接在options里面搜索
        filterOption(input, option) {
            return (
            option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
            );
        },
        // phase 1
        newRow () {
            // 通过时间戳生成 UUID
            let uuid = Math.round(new Date().getTime()).toString();
            this.data.push({
            key: uuid,
            exergyId: '',
            exergyName: '',
            factor: {
                id: '',
                name: '',
                value: '',
                unit: '',
            },
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
            if (this.newRowPass(target.factor, ['name', 'value'])) {
                target.editable = false
                target.isNew = false
            } else {
                this.$message.error('缺少必填项！');
            }
        },
        handlerRowChange (value, key, column) {
            const newData = [...this.data]
            const target = newData.filter(item => key === item.key)[0]
            if (target) {
                if (column !== 'exergyName') {
                    target.factor[column] = value
                } else{
                    target['exergyId'] = value
                    target['exergyName'] = this.selectorData.find((val) => val.id === value).name + '(' + this.selectorData.find((val) => val.id === value).value + ' ' + this.selectorData.find((val) => val.id === value).unit + ')'
                }
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
    }
    
}
</script>