package org.jeecg.modules.cq.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cq.convert.CqSettingsConvert;
import org.jeecg.modules.cq.entity.CqSettings;
import org.jeecg.modules.cq.mapper.CqSettingsMapper;
import org.jeecg.modules.cq.service.ICqSettingsService;
import org.jeecg.modules.cq.vo.CqSettingsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: CQ settings 实现类
 * @Version:V1.0
 */

@Slf4j
@Service
public class CqSettingsServiceImpl implements ICqSettingsService {

    @Resource
    private CqSettingsMapper settingsMapper;

    @Override
    public CqSettings getSettingsValue(String key) {
        return settingsMapper.getSettingsByKey(key);
    }

    @Override
    public CqSettingsVO getSettings(String key) {
        return CqSettingsConvert.instance.convert(getSettingsValue(key));
    }
}
