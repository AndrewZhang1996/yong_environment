package org.jeecg.modules.ec.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.ec.convert.EcPlanPhaseConvert;
import org.jeecg.modules.ec.entity.EcPlan;
import org.jeecg.modules.ec.service.IEcPlanService;
import org.jeecg.modules.ec.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 11:19:27
 */
@Api(tags = "EC Plan API")
@RestController
@RequestMapping("/ec/plan")
@Slf4j
public class EcPlanController {

    @Resource
    private IEcPlanService ecPlanService;

    @ApiOperation("require plan detail")
    @GetMapping("/list")
//    @PermissionData(pageComponent = "ec/PlanManagement")
    public Result<IPage<EcPlan>> list(EcPlan ecPlan, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        log.info("请求参数ecPlan: {}", ecPlan.toString());
        if (req.getParameterMap().containsKey("value_start") || req.getParameterMap().containsKey("value_end")) {
            ecPlan.setStatus(1);
        }
        QueryWrapper<EcPlan> queryWrapper = QueryGenerator.initQueryWrapper(ecPlan, req.getParameterMap());
        Page<EcPlan> page = new Page<>(pageNo, pageSize);
        IPage<EcPlan> pageList = ecPlanService.page(page, queryWrapper);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return Result.OK(pageList);
    }

    @ApiOperation("require plan total value")
    @GetMapping("/getEcPlanTotalValue")
    public Result<BigDecimal> getEcPlanTotalValue(@RequestParam("planId") String planId) {
        Result<BigDecimal> result = new Result<>();
        BigDecimal value = ecPlanService.getPlanTotalValueInBigDecimal(planId);
        result.setSuccess(true);
        result.setResult(value);
        return result;
    }

    @ApiOperation("save plan to his tables")
    @GetMapping("/saveToHis")
    public Result<Boolean> saveToHis(@RequestParam("planId") String planId) {
        Result<Boolean> result = new Result<>();
        Boolean value = ecPlanService.saveToHis(planId);
        result.setSuccess(true);
        result.setResult(value);
        return result;
    }

    @ApiOperation("require plan phases without calculation")
    @GetMapping("/getEcPlanPhasesWithoutCalc")
    public Result<List<EcPhaseVo>> getEcPlanPhasesWithoutCalc(@RequestParam("planId") String planId) {
        Result<List<EcPhaseVo>> result = new Result<>();
        List<EcPhaseVo> phaseVoList = ecPlanService.getPlanPhasesWithoutCalculation(planId);
        result.setSuccess(true);
        result.setResult(phaseVoList);
        return result;
    }

    @ApiOperation("require plan phases")
    @GetMapping("/getEcPlanPhasesWithoutExergyFactors")
    public Result<List<EcPhaseVo>> getEcPlanPhasesWithoutExergyFactors(@RequestParam("planId") String planId) {
        Result<List<EcPhaseVo>> result = new Result<>();
        List<EcPhaseVo> planVo = ecPlanService.getEcPlanPhasesWithoutExergyFactors(planId);
        result.setSuccess(true);
        result.setResult(planVo);
        return result;
    }

    @ApiOperation("add one new phase to the plan")
    @PostMapping("/addNewPhase")
    public Result<String> addNewPhase(@Valid EcPlanPhaseInsertReqVo ecPlanPhaseReqVo) {
        Result<String> result = new Result<>();
        String planPhaseId = ecPlanService.addNewPhaseToThePlan(EcPlanPhaseConvert.instance.convertToEcPlanPhase(ecPlanPhaseReqVo));
        if (StringUtils.isNotEmpty(planPhaseId)) {
            result.setSuccess(true);
            result.setResult(planPhaseId);
        } else {
            result.setSuccess(false);
            result.setMessage("插入新阶段到指定方案失败！");
        }
        return result;
    }

    @ApiOperation("delete phases of the plan")
    @PostMapping("/delPhases")
    public Result<Void> delPhases(@Valid List<EcPlanPhaseDeleteReqVo> planPhaseList) {
        Result<Void> result = new Result<>();
        boolean deleted = true;
        try {
            ecPlanService.delPhases(EcPlanPhaseConvert.instance.convertToEcPlanPhaseList(planPhaseList));
        } catch (Exception e) {
            log.error("delete phases error.", e);
            deleted = false;
        }
        if (deleted) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setMessage("删除指定方案的阶段失败！");
        }
        return result;
    }

    @ApiOperation("add new plan")
    @PostMapping("addNewPlan")
    public Result<Boolean> addNewPlan(@Valid @RequestBody EcPlanReqVo ecPlanReqVo) {
        log.info("[addNewPlan] 请求参数ecExergyFactorDetailReqVo: {}", ecPlanReqVo.toString());
        try {
            ecPlanService.addNewPlan(ecPlanReqVo);
        } catch (Exception e) {
            log.error("插入失败", e);
            return Result.error(e.getMessage(), false);
        }
        return Result.OK(true);
    }

    @ApiOperation("edit plan")
    @PostMapping("editPlan")
    public Result<Boolean> editPlan(@Valid @RequestBody EcPlanReqVo ecPlanReqVo) {
        log.info("[editPlan] 请求参数ecPlanReqVo: {}", ecPlanReqVo.toString());
        try {
            ecPlanService.editPlan(ecPlanReqVo);
        } catch (Exception e) {
            log.error("编辑失败", e);
            return Result.error(e.getMessage(), false);
        }
        return Result.OK(true);
    }

    @ApiOperation("delete plan")
    @PostMapping("deletePlan")
    public Result<Boolean> deletePlan(@Valid @RequestBody EcPlanDelReqVo planDelReqVo) {
        log.info("[deletePlan] 请求参数id: {}", planDelReqVo.getId());
        try {
            ecPlanService.deletePlan(planDelReqVo.getId());
        } catch (Exception e) {
            log.error("删除失败", e);
            return Result.error(e.getMessage(), false);
        }
        return Result.OK(true);
    }
}
