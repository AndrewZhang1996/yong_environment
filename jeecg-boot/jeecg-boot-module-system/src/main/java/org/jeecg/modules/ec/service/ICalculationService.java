package org.jeecg.modules.ec.service;

import org.jeecg.modules.ec.vo.EcExergyFactorDetailVo;
import org.jeecg.modules.ec.vo.EcPhaseVo;
import org.jeecg.modules.ec.vo.EcPlanVo;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/25 14:50:59
 */
public interface ICalculationService {

    /**
     * Calculate the value of the plan.
     * @param planVo planVo
     * @return planVo with value
     */
    EcPlanVo calculateEcPlanVo(EcPlanVo planVo);

    /**
     * Calculate the value of the phase.
     * @param phaseVo phaseVo
     * @return phaseVo with value
     */
    EcPhaseVo calculateEcPhaseVo(EcPhaseVo phaseVo);
}
