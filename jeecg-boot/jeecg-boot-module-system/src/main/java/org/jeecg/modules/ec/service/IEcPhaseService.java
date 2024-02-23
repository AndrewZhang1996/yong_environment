package org.jeecg.modules.ec.service;

import org.jeecg.modules.ec.vo.EcExergyFactorDetailRespVo;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailVo;
import org.jeecg.modules.ec.vo.EcPhaseVo;
import org.jeecg.modules.ec.vo.EcPlanVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 11:20:46
 */
public interface IEcPhaseService {

    /**
     * Calculate the total value of current phase. Or Get the detail info of the phase without calculation.
     * @param phaseId phase id
     * @param calculationFlag calculation flag
     * @return EcPhaseVo
     */
    EcPhaseVo getPhaseDetail(String phaseId, boolean calculationFlag);

    /**
     * Only calculate and get the total value of current phase.
     * @param phaseId phase id
     * @return current plan total value
     */
    BigDecimal getPhaseTotalValueInBigDecimal(String phaseId);

    /**
     * Get all exergy factor of the phase in order using phase id.
     * @param phaseId phase id
     * @return EcExergyFactorVo list
     */
    List<EcExergyFactorDetailVo> getExergyFactorsByPhaseIdWithoutCalculation(String phaseId);

    /**
     * Get all related plans of phases.
     * @param phaseId phase id
     * @return list of ecPlanVo
     */
    List<EcPlanVo> getAllPlansOfPhaseByPhaseId(String phaseId);

    List<EcExergyFactorDetailRespVo> getExergyFactorDetailByPhaseTypeAndPlanId(String planId, String phaseType);
}
