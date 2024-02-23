package org.jeecg.modules.ec.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.ec.entity.EcPlan;
import org.jeecg.modules.ec.mapper.EcPlanMapper;
import org.jeecg.modules.ec.service.ICalculationService;
import org.jeecg.modules.ec.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/25 14:56:21
 */
@Slf4j
@Service
public class CalculationServiceImpl implements ICalculationService {

    @Resource
    private EcPlanMapper ecPlanMapper;

    /**
     * Calculate the value of the plan.
     *
     * @param planVo planVo
     * @return planVo with value
     */
    @Override
    @Transactional
    public EcPlanVo calculateEcPlanVo(EcPlanVo planVo) {
        log.info("[calculateEcPlanVo] planVo: {}", planVo);
        Iterator<EcPhaseVo> phaseVoIterator = planVo.getPhaseList().listIterator();
        BigDecimal planTotalValue = BigDecimal.ZERO;
        while (phaseVoIterator.hasNext()) {
            EcPhaseVo phaseVo = phaseVoIterator.next();
            Iterator<EcExergyFactorDetailVo> exergyFactorVoIterator = phaseVo.getExergyFactorList().listIterator();
            BigDecimal phaseTotalValue = BigDecimal.ZERO;
            while (exergyFactorVoIterator.hasNext()) {
                EcExergyFactorDetailVo exergyFactorVo = exergyFactorVoIterator.next();
                try {
                    // multiply all exergyFactors
                    List<EcExergyFactorVo> exergyFactorList = exergyFactorVo.getExergyFactorList();
                    exergyFactorList.forEach(item -> item.setValue(item.getExergy().getValue().multiply(item.getFactor().getValue())));
                    // multiply all factors
//                    BigDecimal exergyFactorTotalValue = exergyFactorVo.getFactorList().stream().map(EcFactorVo::getValue).reduce(BigDecimal::multiply).orElseThrow(RuntimeException::new);
                    // exergy value multiplies factors
                    exergyFactorVo.setValue(exergyFactorList.stream().map(EcExergyFactorVo::getValue).reduce(BigDecimal::add).orElseThrow(RuntimeException::new));
                    // add all exergy_factor values
                    phaseTotalValue = phaseTotalValue.add(exergyFactorVo.getValue());
                } catch (Exception e) {
                    log.error("EcExergyFactor: {}, EcExergyFactors: {}, calculate the exergyFactorVoTotalValue error!", exergyFactorVo.getId(), exergyFactorVo.getExergyFactorList().stream().map(EcExergyFactorVo::getFactor).collect(Collectors.toList()), e);
                }
            }
            phaseVo.setValue(phaseTotalValue);
            // add all phase values
            planTotalValue = planTotalValue.add(phaseVo.getValue());
        }
        planVo.setValue(planTotalValue);
        ecPlanMapper.update(EcPlan.builder().value(planVo.getValue()).status(1).build(), new UpdateWrapper<EcPlan>().eq("id", planVo.getId()));
        return planVo;
    }


    /**
     * Calculate the value of the phase.
     *
     * @param phaseVo phaseVo
     * @return phaseVo with value
     */
    @Override
    public EcPhaseVo calculateEcPhaseVo(EcPhaseVo phaseVo) {
        Iterator<EcExergyFactorDetailVo> exergyFactorVoIterator = phaseVo.getExergyFactorList().listIterator();
        BigDecimal phaseTotalValue = BigDecimal.ZERO;
        while (exergyFactorVoIterator.hasNext()) {
            EcExergyFactorDetailVo exergyFactorVo = exergyFactorVoIterator.next();
            try {
                // multiply all exergyFactors
                List<EcExergyFactorVo> exergyFactorList = exergyFactorVo.getExergyFactorList();
                exergyFactorList.forEach(item -> item.setValue(item.getExergy().getValue().multiply(item.getFactor().getValue())));
                // Add all factors
//                BigDecimal exergyFactorTotalValue = exergyFactorVo.getFactorList().stream().map(EcFactorVo::getValue).reduce(BigDecimal::multiply).orElseThrow(RuntimeException::new);
                // exergy value multiplies factors
                exergyFactorVo.setValue(exergyFactorList.stream().map(EcExergyFactorVo::getValue).reduce(BigDecimal::multiply).orElseThrow(RuntimeException::new));
                // add all exergy_factor values
                phaseTotalValue = phaseTotalValue.add(exergyFactorVo.getValue());
            } catch (Exception e) {
                log.error("EcExergyFactor: {}, EcExergyFactors: {}, calculate the exergyFactorVoTotalValue error!", exergyFactorVo.getId(), exergyFactorVo.getExergyFactorList().stream().map(EcExergyFactorVo::getFactor).collect(Collectors.toList()));
            }
        }
        phaseVo.setValue(phaseTotalValue);
        return phaseVo;
    }

}
