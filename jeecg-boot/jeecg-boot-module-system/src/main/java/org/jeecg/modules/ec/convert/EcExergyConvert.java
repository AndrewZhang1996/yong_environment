package org.jeecg.modules.ec.convert;

import org.jeecg.modules.ec.entity.EcExergy;
import org.jeecg.modules.ec.vo.EcExergyReqVo;
import org.jeecg.modules.ec.vo.EcExergyVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EcExergyConvert {
    EcExergyConvert instance = Mappers.getMapper(EcExergyConvert.class);

    EcExergy convertToEcExergy(EcExergyReqVo ecExergyReqVo);

    EcExergyVo convertToEcExergyVo(EcExergy exergy);

    List<EcExergyVo> convertToEcExergyVoList(List<EcExergy> exergyList);
}
