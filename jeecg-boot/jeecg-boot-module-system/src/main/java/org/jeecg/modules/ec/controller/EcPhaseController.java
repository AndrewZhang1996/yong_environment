package org.jeecg.modules.ec.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.ec.service.IEcPhaseService;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailRespVo;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailVo;
import org.jeecg.modules.ec.vo.EcPhaseVo;
import org.jeecg.modules.ec.vo.EcPlanVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 11:19:27
 */
@Api(tags = "EC Phase API")
@RestController
@RequestMapping("/ec/phase")
public class EcPhaseController {

    @Resource
    private IEcPhaseService ecPhaseService;

    @ApiOperation("require phase detail")
    @GetMapping("/getEcPhaseDetail")
    public Result<EcPhaseVo> getEcPhaseDetail(@RequestParam("phaseId") String phaseId, @RequestParam(value = "calc", required = false, defaultValue = "false") boolean calculation) {
        Result<EcPhaseVo> result = new Result<>();
        EcPhaseVo phaseVo = ecPhaseService.getPhaseDetail(phaseId, calculation);
        result.setSuccess(true);
        result.setResult(phaseVo);
        return result;
    }

    @ApiOperation("require phase total value")
    @GetMapping("/getEcPhaseTotalValue")
    public Result<BigDecimal> getEcPhaseTotalValue(@RequestParam("phaseId") String phaseId) {
        Result<BigDecimal> result = new Result<>();
        BigDecimal value = ecPhaseService.getPhaseTotalValueInBigDecimal(phaseId);
        result.setSuccess(true);
        result.setResult(value);
        return result;
    }

    @ApiOperation("require phase exergy factors by phase id without calculation")
    @GetMapping("/getExergyFactorDetailByPhaseTypeAndPlanId")
    public Result<List<EcExergyFactorDetailRespVo>> getExergyFactorDetailByPhaseTypeAndPlanId(@RequestParam("planId") String planId, @RequestParam("phaseType") String phaseType) {
        Result<List<EcExergyFactorDetailRespVo>> result = new Result<>();
        List<EcExergyFactorDetailRespVo> exergyFactorDetailRespVoList = ecPhaseService.getExergyFactorDetailByPhaseTypeAndPlanId(planId, phaseType);
        result.setSuccess(true);
        result.setResult(exergyFactorDetailRespVoList);
        return result;
    }

    @ApiOperation("require all plans of phases by phase id")
    @GetMapping("/getAllPlansOfPhaseByPhaseId")
    public Result<List<EcPlanVo>> getAllPlansOfPhaseByPhaseId(@RequestParam("phaseId") String phaseId) {
        Result<List<EcPlanVo>> result = new Result<>();
        List<EcPlanVo> ecPlanVoList = ecPhaseService.getAllPlansOfPhaseByPhaseId(phaseId);
        result.setSuccess(true);
        result.setResult(ecPlanVoList);
        return result;
    }
}
