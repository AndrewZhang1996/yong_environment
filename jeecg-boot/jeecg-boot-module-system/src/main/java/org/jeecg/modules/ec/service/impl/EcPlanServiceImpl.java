package org.jeecg.modules.ec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.ec.convert.EcPhaseConvert;
import org.jeecg.modules.ec.convert.EcPlanConvert;
import org.jeecg.modules.ec.entity.EcPhase;
import org.jeecg.modules.ec.entity.EcPhaseExergyFactor;
import org.jeecg.modules.ec.entity.EcPlan;
import org.jeecg.modules.ec.entity.EcPlanPhase;
import org.jeecg.modules.ec.mapper.*;
import org.jeecg.modules.ec.service.ICalculationService;
import org.jeecg.modules.ec.service.IEcPhaseService;
import org.jeecg.modules.ec.service.IEcPlanHisService;
import org.jeecg.modules.ec.service.IEcPlanService;
import org.jeecg.modules.ec.vo.EcPhaseReqVo;
import org.jeecg.modules.ec.vo.EcPhaseVo;
import org.jeecg.modules.ec.vo.EcPlanReqVo;
import org.jeecg.modules.ec.vo.EcPlanVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 11:20:46
 */
@Slf4j
@Service
public class EcPlanServiceImpl implements IEcPlanService {

    @Resource
    private ICalculationService calculationService;

    @Resource
    private IEcPhaseService ecPhaseService;

    @Resource
    private EcPlanMapper ecPlanMapper;

    @Resource
    private EcPhaseMapper ecPhaseMapper;

    @Resource
    private EcPlanPhaseMapper ecPlanPhaseMapper;

    @Resource
    private EcPhaseExergyFactorMapper ecPhaseExergyFactorMapper;

    @Resource
    private IEcPlanHisService ecPlanHisService;

    /**
     * Calculate the total value of current plan. Or Get the detail info of the plan without calculation.
     *
     * @param planId          plan id
     * @param calculationFlag true for calculate the total value.
     * @return EcPlanVo
     */
    @Override
    public EcPlanVo getPlanDetail(String planId, boolean calculationFlag) {
        log.info("get plan detail planId: [{}], calculationFlag: [{}]", planId, calculationFlag);
        EcPlan plan = ecPlanMapper.selectById(planId);
        List<EcPhaseVo> ecPhaseList = this.getPlanPhasesWithoutCalculation(planId);
        EcPlanVo planVo = EcPlanConvert.instance.convertToEcPlanVo(plan);
        planVo.setPhaseList(ecPhaseList);
        if (calculationFlag) {
            planVo = calculationService.calculateEcPlanVo(planVo);
        }
        return planVo;
    }

    /**
     * Only calculate and get the total value of current plan.
     *
     * @param planId plan id
     * @return current plan total value
     */
    @Override
    public BigDecimal getPlanTotalValueInBigDecimal(String planId) {
        log.info("get plan total value in big decimal planId: [{}]", planId);
        return getPlanDetail(planId, true).getValue();
    }

    @Override
    public Boolean saveToHis(String planId) {
        EcPlan plan = ecPlanMapper.selectById(planId);
        if (plan == null) {
            log.error("没有这个方案, planId: {}", planId);
            throw new RuntimeException("没有这个方案！");
        }
        List<EcPhaseVo> ecPhaseList = this.getPlanPhasesWithoutCalculation(planId);
        EcPlanVo planVo = EcPlanConvert.instance.convertToEcPlanVo(plan);
        planVo.setPhaseList(ecPhaseList);
        planVo = calculationService.calculateEcPlanVo(planVo);
        try {
            ecPlanHisService.saveEcPlanHis(planVo);
        } catch (Exception e) {
            log.error("保存历史记录失败", e);
            throw new RuntimeException("保存历史记录失败");
        }

        return true;
    }

    /**
     * Get all phases of the plan in order.
     *
     * @param planId plan id
     * @return the phases list
     */
    @Override
    public List<EcPhaseVo> getPlanPhasesWithoutCalculation(String planId) {
        log.info("get plan phases list planId: [{}]", planId);
        List<EcPhase> ecPhaseList = ecPlanPhaseMapper.selectPhaseListByPlanId(planId);
        List<EcPhaseVo> ecPhaseVoList = EcPhaseConvert.instance.convertToEcPhaseVoList(ecPhaseList);
        ecPhaseVoList.forEach(item -> item.setExergyFactorList(ecPhaseService.getExergyFactorsByPhaseIdWithoutCalculation(item.getId())));
        return ecPhaseVoList;
    }

    /**
     * Get all phases of the plan in order.
     *
     * @param planId plan id
     * @return the phases list
     */
    @Override
    public List<EcPhaseVo> getEcPlanPhasesWithoutExergyFactors(String planId) {
        log.info("get plan phases list planId without exergy factors: [{}]", planId);
        List<EcPhase> ecPhaseList = ecPlanPhaseMapper.selectPhaseListByPlanId(planId);
        List<EcPhaseVo> ecPhaseVoList = EcPhaseConvert.instance.convertToEcPhaseVoList(ecPhaseList);
        return ecPhaseVoList;
    }

    /**
     * Add new phase to the plan
     *
     * @param ecPlanPhase ecPlanPhase
     * @return ecPlanPhaseId
     */
    @Override
    public String addNewPhaseToThePlan(EcPlanPhase ecPlanPhase) {
        log.info("try to insert phase (phase id = {}) to the plan (plan id = {})", ecPlanPhase.getPhaseId(), ecPlanPhase.getPlanId());
        int effectedLines = ecPlanPhaseMapper.insert(ecPlanPhase);
        if (effectedLines < 1) {
            log.error("insert phase (phase id = {}) to the plan (plan id = {}) error", ecPlanPhase.getPhaseId(), ecPlanPhase.getPlanId());
        }
        return ecPlanPhase.getId();
    }

    /**
     * Delete phase of the plan
     *
     * @param ecPlanPhaseList ecPlanPhaseList
     * @return result
     */
    @Override
    @Transactional
    public void delPhases(List<EcPlanPhase> ecPlanPhaseList) {
        log.info("[delPhases] ecPlanPhaseList: {}", ecPlanPhaseList);
        int shouldDelete = ecPlanPhaseList.size();
        int hasDelete = 0;
        for (EcPlanPhase ecPlanPhase: ecPlanPhaseList) {
            hasDelete += ecPlanPhaseMapper.delete(new QueryWrapper<EcPlanPhase>().eq("plan_id", ecPlanPhase.getPlanId()).eq("phase_id", ecPlanPhase.getPhaseId()));
        }
        if (shouldDelete != hasDelete) {
            log.error("should delete [{}], but has delete [{}]", shouldDelete, hasDelete);
            throw new RuntimeException();
        }
    }

    @Override
    public IPage<EcPlan> page(Page<EcPlan> page, QueryWrapper<EcPlan> queryWrapper) {
        IPage<EcPlan> pageList = ecPlanMapper.selectPage(page, queryWrapper);
        // remove non-computed record value
        for (EcPlan plan : pageList.getRecords()) {
            if (plan.getStatus() == null || 1 != plan.getStatus()) {
                plan.setValue(null);
            }
        }
        return pageList;
    }

    @Override
    @Transactional
    public void addNewPlan(EcPlanReqVo ecPlanReqVo) {
        ecPlanReqVo.setId(null);
        EcPlan ecPlan = EcPlanConvert.instance.convertToEcPlan(ecPlanReqVo);
        List<EcPhaseReqVo> ecPhaseReqVoList = ecPlanReqVo.getPhases();
        Map<Integer, List<String>> ecPhaseMapList = new HashMap<>();
        ecPhaseReqVoList.forEach(item -> {
            ecPhaseMapList.put(item.getPhaseType(), item.getExergyFactorIds());
        });

        // 1. insert new plan
        ecPlanMapper.insert(ecPlan);

        // 2. insert new phases
        ecPhaseMapList.forEach((key, value) -> {
            EcPhase ecPhase = EcPhase.builder().phaseType(key).build();
            ecPhaseMapper.insert(ecPhase);
            // 2.1 insert phase and exergy factor
            value.forEach(item2 -> {
                ecPhaseExergyFactorMapper.insert(EcPhaseExergyFactor.builder().phaseId(ecPhase.getId()).exergyFactorDetailId(item2).build());
            });
            // 2.2 insert plan and phases
            ecPlanPhaseMapper.insert(EcPlanPhase.builder().planId(ecPlan.getId()).phaseId(ecPhase.getId()).build());
        });

    }

    @Override
    @Transactional
    public void editPlan(EcPlanReqVo ecPlanReqVo) {
        if (StringUtils.isEmpty(ecPlanReqVo.getId())) {
            log.error("请求的ecPlanReqVo的id为空或空白！");
            throw new RuntimeException("请求的ecPlanReqVo的id为空或空白！");
        }
        EcPlan ecPlan = EcPlanConvert.instance.convertToEcPlan(ecPlanReqVo);
        ecPlan.setId(ecPlanReqVo.getId());
        ecPlan.setStatus(0);
        List<EcPhaseReqVo> ecPhaseReqVoList = ecPlanReqVo.getPhases();

        // 1. update plan
        ecPlanMapper.updateById(ecPlan);

        // 2. update phase and exergy factors
        ecPhaseReqVoList.forEach((item) -> {
            EcPhase ecPhase;
            EcPhase requireEcPhase = ecPlanPhaseMapper.selectPhaseByPlanIdAndPhaseType(ecPlan.getId(), item.getPhaseType());
            if (requireEcPhase == null) {
                ecPhase = EcPhase.builder().phaseType(item.getPhaseType()).build();
                ecPhaseMapper.insert(ecPhase);
            } else {
                ecPhase = requireEcPhase;
            }
            // delete all phase and exergy factors
            ecPhaseExergyFactorMapper.delete(new QueryWrapper<EcPhaseExergyFactor>().eq("phase_id", ecPhase.getId()));
            item.getExergyFactorIds().forEach(item2 -> {
                ecPhaseExergyFactorMapper.insert(EcPhaseExergyFactor.builder().phaseId(ecPhase.getId()).exergyFactorDetailId(item2).build());
            });

            // if plan phase not exist, insert
            if (ecPlanPhaseMapper.selectCount(new QueryWrapper<EcPlanPhase>().eq("plan_id", ecPlan.getId()).eq("phase_id", ecPhase.getId())) < 1) {
                ecPlanPhaseMapper.insert(EcPlanPhase.builder().planId(ecPlan.getId()).phaseId(ecPhase.getId()).build());
            }
        });
    }

    @Override
    @Transactional
    public void deletePlan(String id) {
        ecPlanMapper.deleteById(id);
        List<EcPhase> phaseList = ecPlanPhaseMapper.selectPhaseListByPlanId(id);
        ecPlanPhaseMapper.delete(new QueryWrapper<EcPlanPhase>().eq("plan_id", id));
        phaseList.forEach(item -> {
            ecPhaseExergyFactorMapper.delete(new QueryWrapper<EcPhaseExergyFactor>().eq("phase_id", item.getId()));
            ecPhaseMapper.deleteById(item.getId());
        });
    }
}
