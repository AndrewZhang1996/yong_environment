package org.jeecg.modules.ec.service;

import org.jeecg.modules.ec.vo.EcExergyFactorHisRespVo;
import org.jeecg.modules.ec.vo.EcPhaseHisRespVo;

import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/11/11 16:41:44
 */
public interface IEcPhaseHisService {

    EcPhaseHisRespVo getExergyFactorDetailHisByPhaseTypeAndPlanId(String planId, String phaseType);

    List<EcExergyFactorHisRespVo> getExergyAndFactorsHisByDetailId(String id);
}
