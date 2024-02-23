package org.jeecg.modules.cq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cq.entity.CqNotification;

public interface CqNotificationMapper extends BaseMapper<CqNotification> {
    CqNotification getNotificationUsername(@Param("username") String username);
}
