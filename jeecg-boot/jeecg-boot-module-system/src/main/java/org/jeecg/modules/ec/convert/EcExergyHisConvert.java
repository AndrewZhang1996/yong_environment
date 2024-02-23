package org.jeecg.modules.ec.convert;

import org.jeecg.modules.ec.annotation.MappingIgnore;
import org.jeecg.modules.ec.entity.EcExergyHis;
import org.jeecg.modules.ec.vo.EcExergyVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EcExergyHisConvert {
    EcExergyHisConvert instance = Mappers.getMapper(EcExergyHisConvert.class);

    @MappingIgnore
    EcExergyHis convertToEcExergyHis(EcExergyVo exergyVo);
}
