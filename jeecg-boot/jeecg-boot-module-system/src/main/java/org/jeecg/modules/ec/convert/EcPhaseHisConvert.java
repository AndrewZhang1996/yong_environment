package org.jeecg.modules.ec.convert;

import org.jeecg.modules.ec.annotation.MappingIgnore;
import org.jeecg.modules.ec.entity.EcPhaseHis;
import org.jeecg.modules.ec.vo.EcPhaseVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EcPhaseHisConvert {
    EcPhaseHisConvert instance = Mappers.getMapper(EcPhaseHisConvert.class);

    @MappingIgnore
    EcPhaseHis convertToEcPhaseHis(EcPhaseVo phaseVo);
}
