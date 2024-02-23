package org.jeecg.modules.cq.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cq.Constants;
import org.jeecg.modules.cq.convert.*;
import org.jeecg.modules.cq.entity.*;
import org.jeecg.modules.cq.enums.CqRoomType;
import org.jeecg.modules.cq.mapper.*;
import org.jeecg.modules.cq.service.ICqSettingsService;
import org.jeecg.modules.cq.service.ICqUserService;
import org.jeecg.modules.cq.vo.*;
import org.jeecg.modules.cq.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @Description: CQ 用户服务实现类
 * @Version:V1.0
 */

@Slf4j
@Service
public class CqUserServiceImpl implements ICqUserService {
    @Resource
    private CqRoomInfoMapper roomInfoMapper;
    @Resource
    private CqUserDisplayInfoMapper userDisplayInfoMapper;
    @Resource
    private CqAirTemperatureRecordMapper airTemperatureRecordMapper;
    @Resource
    private CqMoistureRecordMapper moistureRecordMapper;
    @Resource
    private CqAirConditionerRecordMapper airConditionerRecordMapper;
    @Resource
    private ICqSettingsService settingsService;
    @Resource
    private CqNotificationMapper notificationMapper;

    @Override
    public List<CqUserDisplayInfoRespVO> getUserDisplayInfo(String username) {
        log.info("[getUserDisplayInfo] username:{}", username);
        List<CqUserDisplayInfo> userDisplayInfoList = userDisplayInfoMapper.getUserDisplayInfoByUsername(username);
        return CqUserDisplayInfoConvert.instance.convert(userDisplayInfoList);
    }

    @Override
    public List<CqRoomInfoRespVO> getRoomInfo(String username) {
        log.info("[getRoomInfo] username:{}", username);
        List<CqRoomInfo> roomInfoList = roomInfoMapper.getRoomInfoByUsername(username);
        return CqRoomInfoConvert.instance.convert(roomInfoList);
    }

    @Override
    public CqAirTempRecordRespVO getAirTempRecords(String roomId) {
        log.info("[getAirTempRecords] roomId:{}", roomId);
        // 1.获取配置
        CqSettings settings = settingsService.getSettingsValue(Constants.N);

        // 2.获取记录
        Date beginTime = DateUtil.convertTimeStampToDate(DateUtil.getYesterdayMorning(), DateUtil.YMD_HMS);
        Date endTime = DateUtil.convertTimeStampToDate(DateUtil.getTodayMorning(), DateUtil.YMD_HMS);
        List<CqAirTemperatureRecord> airTemperatureRecords =
                airTemperatureRecordMapper.getRecordsByRoomId(roomId, beginTime, endTime)
                        .stream().sorted(Comparator.comparing(CqAirTemperatureRecord::getRecordTime, Comparator.reverseOrder()))
                        .collect(Collectors.toList());
        List<CqAirTempRecordVO> airTempRecordVOList = CqAirTemperatureRecordConvert.instance.convert(airTemperatureRecords);
        CqRoomInfo cqRoomInfo = Objects.requireNonNull(roomInfoMapper.selectById(roomId), "room info doesn't exist");

        // 3.获取配置
        CqSettings cozyTempUpper;
        CqSettings cozyTempLower;
        switch (cqRoomInfo.getRoomType()) {
            case LIVING_ROOM:
                cozyTempUpper = settingsService.getSettingsValue(Constants.COZY_LIVING_ROOM_TEMP_UPPER);
                cozyTempLower = settingsService.getSettingsValue(Constants.COZY_LIVING_ROOM_TEMP_LOWER);
                break;
            case BED_ROOM:
            default:
                cozyTempUpper = settingsService.getSettingsValue(Constants.COZY_BEDROOM_TEMP_UPPER);
                cozyTempLower = settingsService.getSettingsValue(Constants.COZY_BEDROOM_TEMP_LOWER);
        }

        return CqAirTempRecordRespVO.builder().airTempRecordList(airTempRecordVOList)
                .cozyTimeLower(CqSettingsConvert.instance.convert(cozyTempLower))
                .cozyTimeUpper(CqSettingsConvert.instance.convert(cozyTempUpper))
                .yesterdayDayTime(DateUtil.getPreDay(DateUtil.getTodayDate())).todayDayTime(DateUtil.getTodayYMD()).build();
    }

    @Override
    public List<CqMoistureRecordRespVO> getMoistureRecord(String roomId) {
        log.info("[getMoistureRecord] roomId:{}", roomId);
        // 1.获取配置
        CqSettings settings = settingsService.getSettingsValue(Constants.N);

        // 2.获取记录
        List<CqMoistureRecord> moistureRecords =
                moistureRecordMapper.getRecordsByRoomId(roomId, Integer.valueOf(settings.getValue()))
                        .stream().sorted(Comparator.comparing(CqMoistureRecord::getRecordTime, Comparator.reverseOrder()))
                        .collect(Collectors.toList());
        return CqMoistureRecordConvert.instance.convert(moistureRecords);
    }

    @Override
    public CqAirCondConsumeRespVO getAirCondConsume(String username) {
        log.info("[getAirCondConsume] username:{}", username);
        // 1.获取配置
        CqSettings averageConsumLevel = settingsService.getSettingsValue(Constants.AVERAGE_CONSUM_LEVEL);

        // 2.获取记录
        CqAirConditionerRecord airConditionerRecords =
                airConditionerRecordMapper.getRecordsByUsername(username);
        log.info("[getAirCondConsume] air cond consum record:{}", airConditionerRecords);
        CqAirCondConsumeVO airCondConsumeVOS = CqAirConditionerRecordConvert.instance.convert(airConditionerRecords);
        return CqAirCondConsumeRespVO.builder().airCondConsumLevel(airCondConsumeVOS)
                .averageConsumLevel(CqSettingsConvert.instance.convert(averageConsumLevel))
                .build();
    }

    @Override
    public CqNotificationRespVO getNotification(String username) {
        log.info("[getNotification] username:{}", username);
        // 1.获取通知
        CqNotification notification = notificationMapper.getNotificationUsername(username);


        // 2.获取配置
        CqSettings averageConsumLevel = settingsService.getSettingsValue(Constants.AVERAGE_CONSUM_LEVEL);

        // 获取用户平均能耗
        CqAirConditionerRecord airConditionerRecords =
                airConditionerRecordMapper.getRecordsByUsername(username);
        int colorType;
        if (airConditionerRecords == null) {
            colorType = 2;
        } else if (airConditionerRecords.getEnergyConsumption().compareTo(new BigDecimal(averageConsumLevel.getValue())) < 0) {
            colorType = 0;
        } else if (airConditionerRecords.getEnergyConsumption().compareTo(new BigDecimal(averageConsumLevel.getValue())) > 0
                && isExistLowTempRoom(username)) {
            colorType = 1;
        } else {
            colorType = 2;
        }
        log.info("[getNotification] username:{}, notification:{}, colorType:{}", username, notification, colorType);
        return CqNotificationConvert.instance.convert(notification, colorType);
    }

    private boolean isExistLowTempRoom(String username) {
        boolean isExist = false;

        // 获取房间
        List<CqRoomInfo> roomInfoList = roomInfoMapper.getRoomInfoByUsername(username);
        List<String> roomIdList = roomInfoList.stream().map(CqRoomInfo::getId).collect(Collectors.toList());
        // 获取当前温度信息
        for (String roomId : roomIdList) {
            CqAirTempRecordRespVO recordRespVO = this.getAirTempRecords(roomId);
            BigDecimal cozyRoomTemp = new BigDecimal(recordRespVO.getCozyTimeLower().getValue());
            isExist =  recordRespVO.getAirTempRecordList().stream().anyMatch(tempRecord ->
                    tempRecord.getTemperature().compareTo(cozyRoomTemp) < 0);
        }
        return isExist;
    }
}
