package org.jeecg.modules.ec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.ec.convert.*;
import org.jeecg.modules.ec.entity.*;
import org.jeecg.modules.ec.mapper.*;
import org.jeecg.modules.ec.service.IEcPlanHisService;
import org.jeecg.modules.ec.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/25 15:13:08
 */
@Slf4j
@Service
public class EcPlanHisServiceImpl implements IEcPlanHisService {

    @Resource
    private EcPlanHisMapper ecPlanHisMapper;

    @Resource
    private EcPhaseHisMapper ecPhaseHisMapper;

    @Resource
    private EcExergyFactorDetailHisMapper ecExergyFactorDetailHisMapper;

    @Resource
    private EcExergyHisMapper ecExergyHisMapper;

    @Resource
    private EcFactorHisMapper ecFactorHisMapper;

    @Resource
    private EcExergyFactorHisMapper ecExergyFactorHisMapper;

    @Resource
    private EcPhaseExergyFactorHisMapper ecPhaseExergyFactorHisMapper;

    @Resource
    private EcPlanPhaseHisMapper ecPlanPhaseHisMapper;

    /**
     * Save EcPlan to history.
     *
     * @param planVo planVo
     */
    @Override
    @Transactional
    public void saveEcPlanHis(@NonNull @Valid EcPlanVo planVo) {
        log.info("[saveEcPlanHis] planVo: {}", planVo);
        Date calculationTime = new Date();
        // 1. insert plan his, and get the history plan id
        EcPlanHis planHis = EcPlanHisConvert.instance.convertToEcPlanHis(planVo);
        planHis.setCalculationTime(calculationTime);
        ecPlanHisMapper.insert(planHis);
        String planHisId = planHis.getId();

        // 2. insert all plan phase, and get the history phase ids
        for (EcPhaseVo phaseVo : planVo.getPhaseList()) {
            EcPhaseHis phaseHis = EcPhaseHisConvert.instance.convertToEcPhaseHis(phaseVo);
            phaseHis.setId(null);
            ecPhaseHisMapper.insert(phaseHis);
            String phaseHisId = phaseHis.getId();

            // 2.1 get all exergy factor details of this phase
            List<EcExergyFactorDetailVo> ecExergyFactorDetailVoList = phaseVo.getExergyFactorList();

            // 2.2 insert all exergyFactorDetailHis, and get the exergyFactorDetailHis ids
            for (EcExergyFactorDetailVo ecExergyFactorDetailVo : ecExergyFactorDetailVoList) {


                // 2.2.1 get the exergy
//                EcExergyVo exergyVo = ecExergyFactorDetailVo.getExergy();

                // 2.2.2 insert the exergy, and get the exergy id
//                EcExergyHis ecExergyHis = EcExergyHisConvert.instance.convertToEcExergyHis(exergyVo);
//                ecExergyHisMapper.insert(ecExergyHis);
//                String exergyHisId = ecExergyHis.getId();

                // 2.2.3 insert exergyFactorDetailHis, and get the exergyFactorDetailHis id
                EcExergyFactorDetailHis ecExergyFactorDetailHis = EcExergyFactorDetailHisConvert.instance.convertToEcExergyFactorDetailHis(ecExergyFactorDetailVo);
//                ecExergyFactorDetailHis.setExergyId(exergyHisId);
                ecExergyFactorDetailHisMapper.insert(ecExergyFactorDetailHis);
                String exergyFactorDetailHisId = ecExergyFactorDetailHis.getId();

                // 2.2.1 get the exergyFactorList
                List<EcExergyFactorVo> ecExergyFactorVoList = ecExergyFactorDetailVo.getExergyFactorList();

                // 2.2.2 insert all
                for (EcExergyFactorVo ecExergyFactorVo: ecExergyFactorVoList) {
                    EcExergyVo exergyVo = ecExergyFactorVo.getExergy();
                    EcExergyHis ecExergyHis = EcExergyHisConvert.instance.convertToEcExergyHis(exergyVo);
                    ecExergyHisMapper.insert(ecExergyHis);
                    String exergyHisId = ecExergyHis.getId();
                    EcFactorHis factorHis = EcFactorHisConvert.instance.convertToEcFactorHis(ecExergyFactorVo.getFactor());
                    ecFactorHisMapper.insert(factorHis);
                    String factorHisId = factorHis.getId();
                    EcExergyFactorHis insertEcExergyFactorHis = EcExergyFactorHis.builder().factorId(factorHisId).exergyFactorDetailId(exergyFactorDetailHisId).exergyId(exergyHisId).build();
                    ecExergyFactorHisMapper.insert(insertEcExergyFactorHis);
                }

                // 2.2.4 get all factors of this exergyFactorDetailVo
//                List<EcFactorVo> ecFactorVoList = ecExergyFactorDetailVo.getFactorList();

                // 2.2.5 insert all factorHis, and get the factorHis ids
//                for (EcFactorVo ecFactorVo : ecFactorVoList) {
//                    EcFactorHis factorHis = EcFactorHisConvert.instance.convertToEcFactorHis(ecFactorVo);
//                    ecFactorHisMapper.insert(factorHis);
//                    String factorHisId = factorHis.getId();
//                    // 2.2.5.1 compose each ecExergyFactorHis, and insert all of them
//                    EcExergyFactorHis insertEcExergyFactorHis = EcExergyFactorHis.builder().factorId(factorHisId).exergyFactorDetailId(exergyFactorDetailHisId).build();
//                    ecExergyFactorHisMapper.insert(insertEcExergyFactorHis);
//                }

                // 2.2.6 compose each ecPhaseExergyFactorHis, and insert all of them
                EcPhaseExergyFactorHis insertEcPhaseExergyFactorHis = EcPhaseExergyFactorHis.builder().exergyFactorDetailId(exergyFactorDetailHisId).phaseId(phaseHisId).build();
                ecPhaseExergyFactorHisMapper.insert(insertEcPhaseExergyFactorHis);

            }

            // 2.3 compose each ecPlanPhaseHis, and insert all of them
            EcPlanPhaseHis insertEcPlanPhaseHis = EcPlanPhaseHis.builder().planId(planHisId).phaseId(phaseHisId).build();
            ecPlanPhaseHisMapper.insert(insertEcPlanPhaseHis);
        }
    }

    @Override
    public IPage<EcPlanHis> page(Page<EcPlanHis> page, QueryWrapper<EcPlanHis> queryWrapper) {
        return ecPlanHisMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void deletePlanHis(String id) {
        log.info("[deletePlanHis] id: {}", id);
        EcPlanHis ecPlanHis = Objects.requireNonNull(ecPlanHisMapper.selectById(id), "历史记录不存在！");
        // 1. get all phases ids
        List<EcPlanPhaseHis> planPhaseHisList = ecPlanPhaseHisMapper
                .selectList(new QueryWrapper<EcPlanPhaseHis>()
                        .eq("plan_id", id)
                );
        // 2. get all ecPhaseExergyFactorHis
        for (EcPlanPhaseHis planPhaseHis: planPhaseHisList) {
            List<EcPhaseExergyFactorHis> phaseExergyFactorHisList = ecPhaseExergyFactorHisMapper
                    .selectList(new QueryWrapper<EcPhaseExergyFactorHis>()
                            .eq("phase_id", planPhaseHis.getPhaseId())
                    );
            // 3. get all ecFactorHis
            for (EcPhaseExergyFactorHis phaseExergyFactorHis: phaseExergyFactorHisList) {
                List<EcExergyFactorHis> factorHisList = ecExergyFactorHisMapper
                        .selectList(new QueryWrapper<EcExergyFactorHis>().eq("exergy_factor_detail_id", phaseExergyFactorHis.getExergyFactorDetailId())
                        );
                // 3.1 delete factors
                ecFactorHisMapper.deleteBatchIds(factorHisList.stream().map(EcExergyFactorHis::getFactorId).collect(Collectors.toList()));
                // 3.2 delete exergy factor relations
                ecExergyFactorHisMapper.deleteBatchIds(factorHisList.stream().map(EcExergyFactorHis::getId).collect(Collectors.toList()));
                // 3.3 delete exergy
                ecExergyHisMapper.deleteBatchIds(factorHisList.stream().map(EcExergyFactorHis::getExergyId).collect(Collectors.toList()));
            }
            if (!phaseExergyFactorHisList.isEmpty()) {
                // 4. delete exergy factor detail
                ecExergyFactorDetailHisMapper.deleteBatchIds(phaseExergyFactorHisList.stream().map(EcPhaseExergyFactorHis::getExergyFactorDetailId).collect(Collectors.toList()));
                // 5. delete phase exergy factor detail relations
                ecPhaseExergyFactorHisMapper.deleteBatchIds(phaseExergyFactorHisList.stream().map(EcPhaseExergyFactorHis::getId).collect(Collectors.toList()));
            }
        }
        if (!planPhaseHisList.isEmpty()) {
            // 6. delete phases
            ecPhaseHisMapper.deleteBatchIds(planPhaseHisList.stream().map(EcPlanPhaseHis::getPhaseId).collect(Collectors.toList()));
            // 7. delete plan phase relation
            ecPlanPhaseHisMapper.deleteBatchIds(planPhaseHisList.stream().map(EcPlanPhaseHis::getId).collect(Collectors.toList()));
        }
        // 8. delete plan
        ecPlanHisMapper.deleteById(id);
    }
}
