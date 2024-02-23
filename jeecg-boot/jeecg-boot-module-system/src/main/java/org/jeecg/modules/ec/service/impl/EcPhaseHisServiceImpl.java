package org.jeecg.modules.ec.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.ec.convert.EcExergyFactorDetailConvert;
import org.jeecg.modules.ec.entity.*;
import org.jeecg.modules.ec.mapper.EcExergyHisMapper;
import org.jeecg.modules.ec.mapper.EcExergyMapper;
import org.jeecg.modules.ec.mapper.EcFactorMapper;
import org.jeecg.modules.ec.mapper.EcPhaseHisMapper;
import org.jeecg.modules.ec.service.IEcPhaseHisService;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailHisRespVo;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailRespVo;
import org.jeecg.modules.ec.vo.EcExergyFactorHisRespVo;
import org.jeecg.modules.ec.vo.EcPhaseHisRespVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/11/11 16:42:16
 */
@Slf4j
@Service
public class EcPhaseHisServiceImpl implements IEcPhaseHisService {

    @Resource
    private EcPhaseHisMapper ecPhaseHisMapper;

    @Resource
    private EcExergyHisMapper ecExergyHisMapper;

    @Resource
    private EcFactorMapper ecFactorMapper;

    @Override
    public EcPhaseHisRespVo getExergyFactorDetailHisByPhaseTypeAndPlanId(String planId, String phaseType) {
        log.info("[getExergyFactorDetailHisByPhaseTypeAndPlanId] planId: {}, phaseType: {}", planId, phaseType);
        EcPhaseHisRespVo ecPhaseHisRespVo = ecPhaseHisMapper.selectPhaseHisByPhaseTypeAndPlanId(planId, phaseType);
        if (ecPhaseHisRespVo == null) {
            log.error("没有对应的历史阶段, planId: {}, phaseType: {}", planId, phaseType);
            throw new RuntimeException("没有对应的历史阶段");
        }
        ecPhaseHisRespVo.setExergyFactorDetailHis(ecPhaseHisMapper.getExergyFactorDetailHisByPhaseTypeAndPlanId(planId, phaseType));
        return ecPhaseHisRespVo;
    }

    @Override
    public List<EcExergyFactorHisRespVo> getExergyAndFactorsHisByDetailId(String id) {
        List<EcFactorHis> ecFactorList = ecFactorMapper.selectFactorsByDetailId(id);

        List<EcExergyFactorHisRespVo> factorHisRespVoList = EcExergyFactorDetailConvert.instance.convertToEcExergyFactorHisRespVoList(ecFactorList);

        return factorHisRespVoList;
    }
}
