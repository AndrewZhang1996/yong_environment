package org.jeecg.modules.ec.convert;

import org.jeecg.modules.ec.entity.EcPlanPhase;
import org.jeecg.modules.ec.vo.EcPlanPhaseDeleteReqVo;
import org.jeecg.modules.ec.vo.EcPlanPhaseInsertReqVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EcPlanPhaseConvert {
    EcPlanPhaseConvert instance = Mappers.getMapper(EcPlanPhaseConvert.class);

    EcPlanPhase convertToEcPlanPhase(EcPlanPhaseInsertReqVo ecPlanPhaseReqVo);

    List<EcPlanPhase> convertToEcPlanPhaseList(List<EcPlanPhaseDeleteReqVo> ecPlanPhaseDeleteReqVoList);
}
