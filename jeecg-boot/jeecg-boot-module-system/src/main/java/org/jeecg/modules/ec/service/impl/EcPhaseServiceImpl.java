package org.jeecg.modules.ec.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.ec.convert.EcExergyConvert;
import org.jeecg.modules.ec.convert.EcExergyFactorDetailConvert;
import org.jeecg.modules.ec.convert.EcFactorConvert;
import org.jeecg.modules.ec.convert.EcPhaseConvert;
import org.jeecg.modules.ec.entity.*;
import org.jeecg.modules.ec.mapper.*;
import org.jeecg.modules.ec.service.ICalculationService;
import org.jeecg.modules.ec.service.IEcPhaseService;
import org.jeecg.modules.ec.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/24 08:36:17
 */
@Slf4j
@Service
public class EcPhaseServiceImpl implements IEcPhaseService {

    @Resource
    private ICalculationService calculationService;

    @Resource
    private EcPhaseMapper ecPhaseMapper;

    @Resource
    private EcExergyFactorMapper ecExergyFactorMapper;

    @Resource
    private EcPhaseExergyFactorMapper ecPhaseExergyFactorMapper;

    @Resource
    private EcExergyMapper ecExergyMapper;

    @Resource
    private EcFactorMapper ecFactorMapper;

    /**
     * Calculate the total value of current phase. Or Get the detail info of the phase without calculation.
     *
     * @param phaseId        phase id
     * @param calculationFlag calculation flag
     * @return EcPhaseVo
     */
    @Override
    public EcPhaseVo getPhaseDetail(String phaseId, boolean calculationFlag) {
        log.info("[getPhaseDetail] phaseId: [{}], calculationFlag: {}", phaseId, calculationFlag);
        EcPhase ecPhase = ecPhaseMapper.selectById(phaseId);
        List<EcExergyFactorDetailVo> ecExergyFactorVoList = this.getExergyFactorsByPhaseIdWithoutCalculation(phaseId);
        EcPhaseVo ecPhaseVo = EcPhaseConvert.instance.convertToEcPhaseVo(ecPhase);
        ecPhaseVo.setExergyFactorList(ecExergyFactorVoList);
        if (calculationFlag) {
            ecPhaseVo = calculationService.calculateEcPhaseVo(ecPhaseVo);
        }
        return ecPhaseVo;
    }

    /**
     * Only calculate and get the total value of current phase.
     *
     * @param phaseId phase id
     * @return current plan total value
     */
    @Override
    public BigDecimal getPhaseTotalValueInBigDecimal(String phaseId) {
        log.info("[getPhaseTotalValueInBigDecimal] planId: {}", phaseId);
        return getPhaseDetail(phaseId, true).getValue();
    }

    /**
     * Get all exergy factor of the phase in order using phase id.
     *
     * @param phaseId phase id
     * @return EcExergyFactorVo list
     */
    @Override
    public List<EcExergyFactorDetailVo> getExergyFactorsByPhaseIdWithoutCalculation(String phaseId) {
        log.info("[getExergyFactorsByPhaseIdWithoutCalculation] phaseId: {}", phaseId);
        List<EcExergyFactorDetail> ecExergyFactorDetailList = ecPhaseExergyFactorMapper.selectEcExergyFactorDetailListByPhaseId(phaseId);
        List<EcExergyFactorDetailVo> ecExergyFactorDetailVoList = EcExergyFactorDetailConvert.instance.convertToEcExergyFactorDetailVoList(ecExergyFactorDetailList);
        for (EcExergyFactorDetailVo item: ecExergyFactorDetailVoList) {
            List<EcExergyFactor> ecExergyFactor = ecExergyFactorMapper.selectList(new QueryWrapper<EcExergyFactor>().eq("exergy_factor_detail_id", item.getId()));
            List<EcExergyFactorVo> ecExergyFactorVoList = new ArrayList<>();

            ecExergyFactor.forEach(exergyFactor -> ecExergyFactorVoList.add(EcExergyFactorVo.builder().factor(EcFactorConvert.instance.convertToEcFactorVo(ecFactorMapper.selectById(exergyFactor.getFactorId())))
                    .exergy(EcExergyConvert.instance.convertToEcExergyVo(ecExergyMapper.selectById(exergyFactor.getExergyId()))).build()));

            item.setExergyFactorList(ecExergyFactorVoList);

//            EcExergy ecExergy = ecExergyMapper.selectById(item.getExergyId());
//            List<EcFactor> ecFactorList = new ArrayList<>();
//            ecExergyFactor.forEach(exergyFactor -> ecFactorList.add(ecFactorMapper.selectById(exergyFactor.getFactorId())));
//            item.setExergy(EcExergyConvert.instance.convertToEcExergyVo(ecExergy));
//            item.setFactorList(EcFactorConvert.instance.convertToEcFactorVoList(ecFactorList));


        }
        return ecExergyFactorDetailVoList;
    }

    /**
     * Get all related plans of phases.
     *
     * @param phaseId phase id
     * @return list of ecPlanVo
     */
    @Override
    public List<EcPlanVo> getAllPlansOfPhaseByPhaseId(String phaseId) {
        log.info("[getAllPlansOfPhaseByPhaseId] phaseId: {}", phaseId);
        return ecPhaseMapper.getAllPlansOfPhaseByPhaseId(phaseId);
    }

    @Override
    public List<EcExergyFactorDetailRespVo> getExergyFactorDetailByPhaseTypeAndPlanId(String planId, String phaseType) {
        log.info("[getExergyFactorDetailByPhaseTypeAndPlanId] planId: {}, phaseType: {}", planId, phaseType);
        List<EcExergyFactorDetailRespVo> result = ecPhaseMapper.getExergyFactorDetailByPhaseTypeAndPlanId(planId, phaseType);
        result.forEach(item -> item.setKey(UUID.fastUUID().toString(true)));
        return result;
    }
}
