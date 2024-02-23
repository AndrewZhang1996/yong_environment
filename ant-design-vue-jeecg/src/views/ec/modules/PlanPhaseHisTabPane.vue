<template>
    <a-card class="card"  :bordered="false">
        <a-row :gutter="16">
            <strong>阶段总㶲值:</strong>
            <br />
            <span>{{ phaseTotalValue }}</span>
        </a-row>
        <a-row :gutter="16"><br /></a-row>
        <a-row :gutter="16">
            <a-table
            class="components-table-demo-nested"
            rowKey="id"
            :loading="loading"
            :columns="columns"
            :dataSource="data"
            :pagination="false"
            @expand="expandRow"
            :expandedRowKeys="expandedRowKeys"
            >
    
                <a-table
                slot="expandedRowRender"
                rowKey="id"
                :loading="innerLoading"
                :columns="innerColumns"
                :dataSource="innerData"
                :pagination="false"
                >
                </a-table>

            </a-table>
        </a-row>
        
    </a-card>
</template>
<script>
import { getAction } from '@/api/manage'

export default {
    name: "PlanPhaseHisTabPane",
    props: ['phase'],
    data() {
        return {
            phaseType: this.phase,
            expandedRowKeys: [],
            phaseTotalValue: '',
            loading: false,
            innerLoading: false,
            // table
            columns: [
                {
                    title: '环境影响名称',
                    dataIndex: 'name',
                    key: 'name',
                    width: '30%',
                    scopedSlots: {customRender: 'name'}
                },
                {
                    title: '描述',
                    dataIndex: 'description',
                    key: 'description',
                    width: '40%',
                    scopedSlots: {customRender: 'description'}
                },
                {
                    title: '环境影响㶲值',
                    dataIndex: 'value',
                    key: 'value',
                    width: '30%',
                    scopedSlots: {customRender: 'value'}
                }
            ],
            innerColumns: [
                {
                    title: '㶲名称',
                    dataIndex: 'exergyName',
                    key: 'exergyName',
                    width: '25%',
                    scopedSlots: {customRender: 'exergyName'}
                },
                {
                    title: '因子名称',
                    dataIndex: 'factorName',
                    key: 'name',
                    width: '25%',
                    scopedSlots: {customRender: 'name'}
                },
                {
                    title: '㶲值',
                    dataIndex: 'value',
                    key: 'value',
                    width: '20%',
                    scopedSlots: {customRender: 'value'}
                },
            ],
            data: [],
            innerData: []
        }
    },
    created() {
    },
    methods: {
        expandRow(expanded, record) {
            this.expandedRowKeys = [];
            if (expanded) {
                this.expandedRowKeys.push(record.id);
                this.initRelatedFactors(record.id, record.exergyId);
            } else {
                this.innerData = [];
            }
        },
        initFactors(planId) {
            var url = '/ec/phaseHis/getExergyFactorDetailHisByPhaseTypeAndPlanId';
            var param = {
                phaseType: this.phaseType,
                planId: planId
            };
            var that = this;
            this.loading = true;
            getAction(url,param).then((res) => {
                if (res.success) {
                    console.log(res)
                    that.data = res.result.exergyFactorDetailHis;
                    that.phaseTotalValue = res.result.value;
                } else {
                    that.$notification['error']({
                        message: '获取影响因子失败!',
                    });
                }
                that.loading = false;
            })
        },
        initRelatedFactors(exergyFactorDetailHisId, exergyId) {
            var url = '/ec/phaseHis/getExergyAndFactorsHisByDetailId';
            var param = {
                id: exergyFactorDetailHisId
            };
            var that = this;
            this.innerLoading = true;
            getAction(url,param).then((res) => {
                if (res.success) {
                    console.log(res)
                    that.innerData = res.result;
                } else {
                    that.$notification['error']({
                        message: '获取因子失败!',
                    });
                }
                that.innerLoading = false;
            })
        }
    }
    
}
</script>