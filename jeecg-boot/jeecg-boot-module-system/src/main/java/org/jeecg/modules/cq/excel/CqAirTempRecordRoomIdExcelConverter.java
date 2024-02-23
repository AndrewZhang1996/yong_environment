package org.jeecg.modules.cq.excel;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.cq.entity.CqRoomInfo;
import org.jeecg.modules.cq.mapper.CqRoomInfoMapper;
import org.jeecg.modules.online.cgform.converter.FieldCommentConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author zys
 * @version 1.0
 * @date 2022/07/05 13:45:20
 */
@Slf4j
@Component("cqAirTempRecordRoomIdExcelConverter")
public class CqAirTempRecordRoomIdExcelConverter implements FieldCommentConverter {

    @Resource
    private CqRoomInfoMapper cqRoomInfoMapper;

    @Override
    public String converterToVal(String roomName) {
        if (Objects.nonNull(roomName)) {
            roomName = roomName.trim();
            if (StringUtils.isNotEmpty(roomName)) {
                LambdaQueryWrapper<CqRoomInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(CqRoomInfo::getRoomName, roomName);
                CqRoomInfo cqRoomInfo = cqRoomInfoMapper.selectOne(queryWrapper);
                if (Objects.nonNull(cqRoomInfo) && StringUtils.isNotEmpty(cqRoomInfo.getId())) {
                    return cqRoomInfo.getId();
                }
            }
        }
        return roomName;
    }

    @Override
    public String converterToTxt(String roomId) {
        if (Objects.nonNull(roomId)) {
            roomId = roomId.trim();
            if (StringUtils.isNotEmpty(roomId)) {
                LambdaQueryWrapper<CqRoomInfo> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(CqRoomInfo::getId, roomId);
                CqRoomInfo cqRoomInfo = cqRoomInfoMapper.selectOne(queryWrapper);
                if (Objects.nonNull(cqRoomInfo) && StringUtils.isNotEmpty(cqRoomInfo.getRoomName())) {
                    return cqRoomInfo.getRoomName();
                }
            }
        }
        return roomId;
    }

    @Override
    public Map<String, String> getConfig() {
        return null;
    }
}
