package org.jeecg.modules.cq.excel;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.cq.entity.CqRoomInfo;
import org.jeecg.modules.cq.mapper.CqRoomInfoMapper;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author zys
 * @version 1.0
 * @date 2022/07/05 15:45:28
 */
@Slf4j
@Component("cqAirTempRecordExcelImporter")
public class CqAirTempRecordExcelImporter implements CgformEnhanceJavaInter {

    @Resource
    private CqRoomInfoMapper cqRoomInfoMapper;

    /**
     * @param s
     * @param map
     * @deprecated
     */
    @Override
    public int execute(String s, Map<String, Object> map) throws BusinessException {
        return 1;
    }

    @Override
    public int execute(String s, JSONObject jsonObject) throws BusinessException {
        String roomName = jsonObject.getString("room_id");
        if (Objects.nonNull(roomName)) {
            roomName = roomName.trim();
            if (StringUtils.isNotEmpty(roomName)) {
                LambdaQueryWrapper<CqRoomInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(CqRoomInfo::getRoomName, roomName);
                CqRoomInfo cqRoomInfo = cqRoomInfoMapper.selectOne(queryWrapper);
                if (Objects.nonNull(cqRoomInfo) && StringUtils.isNotEmpty(cqRoomInfo.getId())) {
                    jsonObject.put("room_id", cqRoomInfo.getId());
                    return 1;
                }
            }
        }
        return 0;
    }
}
