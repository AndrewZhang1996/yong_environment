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
import org.jeecg.modules.ec.service.IEcPlanHisService;
import org.jeecg.modules.ec.vo.EcPlanDelReqVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author zys
 * @version 1.0
 * @date 2022/11/11 15:07:52
 */
@Api(tags = "EC Plan His API")
@RestController
@RequestMapping("/ec/planHis")
@Slf4j
public class EcPlanHisController {

    @Resource
    private IEcPlanHisService ecPlanHisService;

    @ApiOperation("require plan his")
    @GetMapping("/list")
//    @PermissionData(pageComponent = "ec/PlanManagement")
    public Result<IPage<EcPlanHis>> list(EcPlanHis ecPlanHis, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        log.info("请求参数ecPlanHis: {}", ecPlanHis.toString());
        QueryWrapper<EcPlanHis> queryWrapper = QueryGenerator.initQueryWrapper(ecPlanHis, req.getParameterMap());
        Page<EcPlanHis> page = new Page<>(pageNo, pageSize);
        IPage<EcPlanHis> pageList = ecPlanHisService.page(page, queryWrapper);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return Result.OK(pageList);
    }

    @ApiOperation("delete his of the plan")
    @PostMapping("/deletePlanHis")
    public Result<Boolean> deletePlanHis(@Valid @RequestBody EcPlanDelReqVo planDelReqVo) {
        log.info("[deletePlanHis] 请求参数id: {}", planDelReqVo.getId());
        try {
            ecPlanHisService.deletePlanHis(planDelReqVo.getId());
        } catch (Exception e) {
            log.error("删除失败", e);
            return Result.error(e.getMessage(), false);
        }
        return Result.OK(true);
    }
}
