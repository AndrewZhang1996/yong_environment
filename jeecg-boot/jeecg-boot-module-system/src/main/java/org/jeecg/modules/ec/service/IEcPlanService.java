package org.jeecg.modules.ec.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.system.base.service.JeecgService;
import org.jeecg.modules.ec.entity.EcPlan;
import org.jeecg.modules.ec.entity.EcPlanPhase;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailReqVo;
import org.jeecg.modules.ec.vo.EcPhaseVo;
import org.jeecg.modules.ec.vo.EcPlanReqVo;
import org.jeecg.modules.ec.vo.EcPlanVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 11:20:46
 */
public interface IEcPlanService {

    /**
     * Calculate the total value of current plan. Or Get the detail info of the plan without calculation.
     * @param planId plan id
     * @param calculationFlag true for calculate the total value.
     * @return EcPlanVo
     */
    EcPlanVo getPlanDetail(String planId, boolean calculationFlag);

    /**
     * Only calculate and get the total value of current plan.
     * @param planId plan id
     * @return current plan total value
     */
    BigDecimal getPlanTotalValueInBigDecimal(String planId);

    Boolean saveToHis(String planId);

    /**
     * Get all phases of the plan in order.
     * @param planId plan id
     * @return the phases list
     */
    List<EcPhaseVo> getPlanPhasesWithoutCalculation(String planId);

    /**
     * Get all phases of the plan in order.
     * @param planId plan id
     * @return the phases list
     */
    List<EcPhaseVo> getEcPlanPhasesWithoutExergyFactors(String planId);

    /**
     * Add new phase to the plan
     * @param ecPlanPhase ecPlanPhase
     * @return ecPlanPhaseId
     */
    String addNewPhaseToThePlan(EcPlanPhase ecPlanPhase);

    /**
     * Delete phase of the plan
     * @param ecPlanPhaseList ecPlanPhaseList
     * @return result
     */
    void delPhases(List<EcPlanPhase> ecPlanPhaseList);


    IPage<EcPlan> page(Page<EcPlan> page, QueryWrapper<EcPlan> queryWrapper);

    void addNewPlan(EcPlanReqVo ecPlanReqVo);

    void editPlan(EcPlanReqVo ecPlanReqVo);

    void deletePlan(String id);
}
