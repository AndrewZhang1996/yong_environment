package org.jeecg.modules.cq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cq.entity.CqSettings;

public interface CqSettingsMapper extends BaseMapper<CqSettings> {
    CqSettings getSettingsByKey(@Param("keyEntry") String keyEntry);
}
