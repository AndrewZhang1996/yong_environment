package org.jeecg.modules.ec.convert;

import org.jeecg.modules.ec.annotation.MappingIgnore;
import org.jeecg.modules.ec.entity.EcFactorHis;
import org.jeecg.modules.ec.vo.EcFactorVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EcFactorHisConvert {
    EcFactorHisConvert instance = Mappers.getMapper(EcFactorHisConvert.class);

    @MappingIgnore
    EcFactorHis convertToEcFactorHis(EcFactorVo exergyVo);
}
