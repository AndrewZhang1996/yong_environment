package org.jeecg.modules.cq.convert;

import org.jeecg.modules.cq.entity.CqUserDisplayInfo;
import org.jeecg.modules.cq.vo.CqUserDisplayInfoRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CqUserDisplayInfoConvert {
    CqUserDisplayInfoConvert instance = Mappers.getMapper(CqUserDisplayInfoConvert.class);

    CqUserDisplayInfoRespVO convert(CqUserDisplayInfo bean);

    List<CqUserDisplayInfoRespVO> convert(List<CqUserDisplayInfo> beanList);
}
