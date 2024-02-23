package org.jeecg.modules.cq.service;

import org.jeecg.modules.cq.vo.*;

import java.util.List;

public interface ICqUserService {

    /**
     * 获取用户显示配置
     * @param username 用户名称
     * @return 用户显示配置
     */
    List<CqUserDisplayInfoRespVO> getUserDisplayInfo(String username);

    /**
     * 获取房间信息
     * @param username 用户名称
     * @return 房间信息
     */
    List<CqRoomInfoRespVO> getRoomInfo(String username);

    /**
     * 获取温度记录
     * @param roomId 房间号
     * @return
     */
    CqAirTempRecordRespVO getAirTempRecords(String roomId);

    /**
     * 获取湿度记录
     * @param roomId 房间号
     * @return
     */
    List<CqMoistureRecordRespVO> getMoistureRecord(String roomId);


    /**
     * 获取空调能耗记录
     * @param username 用户名称
     * @return
     */
    CqAirCondConsumeRespVO getAirCondConsume(String username);

    /**
     * 获取通知
     * @param username 用户名称
     * @return
     */
    CqNotificationRespVO getNotification(String username);
}
