package org.jeecg.modules.cq.convert;

import org.jeecg.modules.cq.entity.CqRoomInfo;
import org.jeecg.modules.cq.vo.CqRoomInfoRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CqRoomInfoConvert {
    CqRoomInfoConvert instance = Mappers.getMapper(CqRoomInfoConvert.class);

    @Mappings({
            @Mapping(source = "roomType.value", target = "roomType")
    })
    CqRoomInfoRespVO convert(CqRoomInfo bean);

    List<CqRoomInfoRespVO> convert(List<CqRoomInfo> beanList);

}
