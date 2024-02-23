package org.jeecg.modules.cq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cq.service.ICqSettingsService;
import org.jeecg.modules.cq.service.ICqSysService;
import org.jeecg.modules.cq.vo.CqSettingsVO;
import org.jeecg.modules.cq.vo.CqSyncUserReqVO;
import org.jeecg.modules.cq.vo.CqSyncUsersReqVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "Cq 系统 API")
@RestController
@RequestMapping("/cq/sys")
public class CqSysController {

    @Resource
    private ICqSettingsService settingsService;
    @Resource
    private ICqSysService sysService;

    @ApiOperation("获取设置")
    @GetMapping("/getSettings")
    @ApiImplicitParam(name = "key", value = "设置的 key", required = true, dataTypeClass = String.class)
    public Result<CqSettingsVO> getSettings(@RequestParam("key") String key) {
        Result<CqSettingsVO> result = new Result<>();
        CqSettingsVO settings = settingsService.getSettings(key);
        result.setSuccess(true);
        result.setResult(settings);
        return result;
    }

    @ApiOperation("同步用户")
    @PostMapping("/syncUser")
    public Result<Boolean> syncUser(@Valid @RequestBody CqSyncUserReqVO syncUserReqVO) {
        Result<Boolean> result = new Result<>();
        sysService.syncUser(syncUserReqVO.getUsername());
        result.setSuccess(true);
        result.setResult(true);
        return result;
    }

    @ApiOperation("同步多用户")
    @PostMapping("/syncUsers")
    public Result<Boolean> syncUsers(@Valid @RequestBody CqSyncUsersReqVO syncUsersReqVO) {
        Result<Boolean> result = new Result<>();
        sysService.syncUsers(syncUsersReqVO.getUsernames());
        result.setSuccess(true);
        result.setResult(true);
        return result;
    }

    @ApiOperation("同步全体用户")
    @PostMapping("/syncAllUsers")
    public Result<Boolean> syncAllUsers() {
        Result<Boolean> result = new Result<>();
        sysService.syncAllUsers();
        result.setSuccess(true);
        result.setResult(true);
        return result;
    }

    @ApiOperation("强制刷新所有用户")
    @PostMapping("/forceSyncAllUsers")
    public Result<Boolean> forceSyncAllUsers() {
        Result<Boolean> result = new Result<>();
        sysService.forceSyncAllUsers();
        result.setSuccess(true);
        result.setResult(true);
        return result;
    }


}
