package org.jeecg.modules.cq.convert;

import org.jeecg.modules.cq.entity.CqAirConditionerRecord;
import org.jeecg.modules.cq.vo.CqAirCondConsumeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CqAirConditionerRecordConvert {
    CqAirConditionerRecordConvert instance = Mappers.getMapper(CqAirConditionerRecordConvert.class);

    CqAirCondConsumeVO convert(CqAirConditionerRecord bean);

    List<CqAirCondConsumeVO> convert(List<CqAirConditionerRecord> beanList);
}
