package org.jeecg.modules.ec.convert;

import org.jeecg.modules.ec.entity.EcPhase;
import org.jeecg.modules.ec.vo.EcPhaseReqVo;
import org.jeecg.modules.ec.vo.EcPhaseVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EcPhaseConvert {
    EcPhaseConvert instance = Mappers.getMapper(EcPhaseConvert.class);

    EcPhaseVo convertToEcPhaseVo(EcPhase phase);

    List<EcPhaseVo> convertToEcPhaseVoList(List<EcPhase> phaseList);

    List<EcPhase> convertToEcPhaseList(List<EcPhaseReqVo> ecPhaseReqVoList);

    EcPhase convertToEcPhase(EcPhaseReqVo ecPhaseReqVo);
}
