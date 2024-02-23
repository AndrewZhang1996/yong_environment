package org.jeecg.modules.ec.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.ec.entity.EcPlanHis;
import org.jeecg.modules.ec.service.IEcPhaseHisService;
import org.jeecg.modules.ec.service.IEcPlanHisService;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailHisRespVo;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailRespVo;
import org.jeecg.modules.ec.vo.EcExergyFactorHisRespVo;
import org.jeecg.modules.ec.vo.EcPhaseHisRespVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/11/11 15:07:52
 */
@Api(tags = "EC Phase His API")
@RestController
@RequestMapping("/ec/phaseHis")
@Slf4j
public class EcPhaseHisController {

    @Resource
    private IEcPhaseHisService ecPhaseHisService;

    @ApiOperation("require phase exergy factors by phase id without calculation")
    @GetMapping("/getExergyFactorDetailHisByPhaseTypeAndPlanId")
    public Result<EcPhaseHisRespVo> getExergyFactorDetailHisByPhaseTypeAndPlanId(@RequestParam("planId") String planId, @RequestParam("phaseType") String phaseType) {
        Result<EcPhaseHisRespVo> result = new Result<>();
        EcPhaseHisRespVo ecPhaseHisRespVo = ecPhaseHisService.getExergyFactorDetailHisByPhaseTypeAndPlanId(planId, phaseType);
        result.setSuccess(true);
        result.setResult(ecPhaseHisRespVo);
        return result;
    }

    @ApiOperation("require phase exergy factors by phase id without calculation")
    @GetMapping("/getExergyAndFactorsHisByDetailId")
    public Result<List<EcExergyFactorHisRespVo>> getExergyAndFactorsHisByDetailId(@RequestParam("id") String id) {
        Result<List<EcExergyFactorHisRespVo>> result = new Result<>();
        List<EcExergyFactorHisRespVo> ecExergyFactorHisRespVo = ecPhaseHisService.getExergyAndFactorsHisByDetailId(id);
        result.setSuccess(true);
        result.setResult(ecExergyFactorHisRespVo);
        return result;
    }
}
