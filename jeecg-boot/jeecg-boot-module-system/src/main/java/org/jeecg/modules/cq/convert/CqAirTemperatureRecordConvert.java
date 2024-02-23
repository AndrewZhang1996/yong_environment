package org.jeecg.modules.cq.convert;

import org.jeecg.modules.cq.entity.CqAirTemperatureRecord;
import org.jeecg.modules.cq.vo.CqAirTempRecordVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CqAirTemperatureRecordConvert {
    CqAirTemperatureRecordConvert instance = Mappers.getMapper(CqAirTemperatureRecordConvert.class);

    CqAirTempRecordVO convert(CqAirTemperatureRecord bean);

    List<CqAirTempRecordVO> convert(List<CqAirTemperatureRecord> beanList);
}
