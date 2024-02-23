package org.jeecg.modules.ec.convert;

import org.jeecg.modules.ec.entity.EcFactor;
import org.jeecg.modules.ec.entity.EcPhase;
import org.jeecg.modules.ec.vo.EcFactorReqVo;
import org.jeecg.modules.ec.vo.EcFactorVo;
import org.jeecg.modules.ec.vo.EcPhaseVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EcFactorConvert {
    EcFactorConvert instance = Mappers.getMapper(EcFactorConvert.class);

    EcFactorVo convertToEcFactorVo(EcFactor factor);

    EcFactor convertToEcFactor(EcFactorReqVo factorReqVo);

    List<EcFactorVo> convertToEcFactorVoList(List<EcFactor> factorList);

    List<EcFactor> convertToEcFactorList(List<EcFactorReqVo> factorReqVos);
}
