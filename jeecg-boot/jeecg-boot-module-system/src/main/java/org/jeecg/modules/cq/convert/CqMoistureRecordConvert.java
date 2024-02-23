package org.jeecg.modules.cq.convert;

import org.jeecg.modules.cq.entity.CqMoistureRecord;
import org.jeecg.modules.cq.vo.CqMoistureRecordRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CqMoistureRecordConvert {
    CqMoistureRecordConvert instance = Mappers.getMapper(CqMoistureRecordConvert.class);

    CqMoistureRecordRespVO convert(CqMoistureRecord bean);

    List<CqMoistureRecordRespVO> convert(List<CqMoistureRecord> beanList);
}
