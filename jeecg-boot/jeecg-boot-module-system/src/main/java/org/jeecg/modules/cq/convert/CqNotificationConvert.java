package org.jeecg.modules.cq.convert;

import org.jeecg.modules.cq.entity.CqNotification;
import org.jeecg.modules.cq.vo.CqNotificationRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CqNotificationConvert {
    CqNotificationConvert instance = Mappers.getMapper(CqNotificationConvert.class);

    CqNotificationRespVO convert(CqNotification bean, Integer colorType);
}
