package org.jeecg.modules.ec.convert;

import org.jeecg.modules.ec.entity.EcPlan;
import org.jeecg.modules.ec.vo.EcPlanReqVo;
import org.jeecg.modules.ec.vo.EcPlanVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EcPlanConvert {
    EcPlanConvert instance = Mappers.getMapper(EcPlanConvert.class);

    EcPlanVo convertToEcPlanVo(EcPlan plan);

    EcPlan convertToEcPlan(EcPlanReqVo ecPlanReqVo);
}
