package org.jeecg.modules.ec.convert;

import org.jeecg.modules.ec.annotation.MappingIgnore;
import org.jeecg.modules.ec.entity.EcPlanHis;
import org.jeecg.modules.ec.vo.EcPlanVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EcPlanHisConvert {
    EcPlanHisConvert instance = Mappers.getMapper(EcPlanHisConvert.class);

    @MappingIgnore
    EcPlanHis convertToEcPlanHis(EcPlanVo planVo);
}
