package org.jeecg.modules.cq.service;

import org.jeecg.modules.cq.entity.CqSettings;
import org.jeecg.modules.cq.vo.CqSettingsVO;

public interface ICqSettingsService {

    CqSettings getSettingsValue(String key);

    /**
     * 获取设置 - api接口
     * @param key key
     * @return
     */
    CqSettingsVO getSettings(String key);
}
