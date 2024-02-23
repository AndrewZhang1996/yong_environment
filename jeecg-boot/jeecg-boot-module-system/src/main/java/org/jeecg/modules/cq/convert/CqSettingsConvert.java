package org.jeecg.modules.cq.convert;

import org.jeecg.modules.cq.entity.CqSettings;
import org.jeecg.modules.cq.vo.CqSettingsVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CqSettingsConvert {
    CqSettingsConvert instance = Mappers.getMapper(CqSettingsConvert.class);

    @Mappings({
            @Mapping(source = "keyEntry", target = "key")
    })
    CqSettingsVO convert(CqSettings bean);
}
