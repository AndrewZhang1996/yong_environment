package org.jeecg.modules.cq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cq.service.ICqUserService;
import org.jeecg.modules.cq.vo.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cq 用户 API")
@RestController
@RequestMapping("/cq/user")
public class CqUserController {

    @Resource
    private ICqUserService cqUserService;

    @ApiOperation("获取用户显示配置")
    @GetMapping("/getUserDisplayInfo")
    @ApiImplicitParam(name = "username", value = "用户名称", required = true, dataTypeClass = String.class)
    public Result<List<CqUserDisplayInfoRespVO>> getUserDisplayInfo(@RequestParam("username") String username) {
        Result<List<CqUserDisplayInfoRespVO>> result = new Result<>();
        List<CqUserDisplayInfoRespVO> cqUserDisplayInfoRespVOList = cqUserService.getUserDisplayInfo(username);
        result.setSuccess(true);
        result.setResult(cqUserDisplayInfoRespVOList);
        return result;
    }

    @ApiOperation("获取房间信息")
    @GetMapping("/getRoomInfo")
    @ApiImplicitParam(name = "username", value = "用户名称", required = true, dataTypeClass = String.class)
    public Result<List<CqRoomInfoRespVO>> getRoomInfo(@RequestParam("username") String username) {
        Result<List<CqRoomInfoRespVO>> result = new Result<>();
        List<CqRoomInfoRespVO> cqRoomInfoRespVOList = cqUserService.getRoomInfo(username);
        result.setSuccess(true);
        result.setResult(cqRoomInfoRespVOList);
        return result;
    }

    @ApiOperation("获取温度信息")
    @GetMapping("/getAirTempRecord")
    public Result<CqAirTempRecordRespVO> getAirTempRecord(@Valid CqAirTempRecordReqVO airTempRecordReqVO) {
        Result<CqAirTempRecordRespVO> result = new Result<>();
        CqAirTempRecordRespVO airTempRecordResp = cqUserService.getAirTempRecords(airTempRecordReqVO.getRoomId());
        result.setSuccess(true);
        result.setResult(airTempRecordResp);
        return result;
    }

    @ApiOperation("获取湿度信息")
    @GetMapping("/getMoistureRecord")
    public Result<List<CqMoistureRecordRespVO>> getMoistureRecord(@Valid CqMoistureRecordReqVO moistureRecordReqVO) {
        Result<List<CqMoistureRecordRespVO>> result = new Result<>();
        List<CqMoistureRecordRespVO> airTempRecords = cqUserService.getMoistureRecord(moistureRecordReqVO.getRoomId());
        result.setSuccess(true);
        result.setResult(airTempRecords);
        return result;
    }

    @ApiOperation("获取空调能耗信息")
    @GetMapping("/getAirCondConsumRecord")
    public Result<CqAirCondConsumeRespVO> getAirCondConsumRecord(@RequestParam("username") String username) {
        Result<CqAirCondConsumeRespVO> result = new Result<>();
        CqAirCondConsumeRespVO airCondConsumeResp = cqUserService.getAirCondConsume(username);
        result.setSuccess(true);
        result.setResult(airCondConsumeResp);
        return result;
    }

    @ApiOperation("获取房间通知")
    @GetMapping("/getNotification")
    public Result<CqNotificationRespVO> getNotification(@Valid CqNotificationReqVO req) {
        Result<CqNotificationRespVO> result = new Result<>();
        CqNotificationRespVO notificationReq = cqUserService.getNotification(req.getUsername());
        result.setSuccess(true);
        result.setResult(notificationReq);
        return result;
    }
}
