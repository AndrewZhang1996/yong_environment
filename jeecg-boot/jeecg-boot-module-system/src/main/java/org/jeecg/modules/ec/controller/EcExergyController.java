package org.jeecg.modules.ec.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.ec.convert.EcExergyConvert;
import org.jeecg.modules.ec.entity.EcExergy;
import org.jeecg.modules.ec.entity.EcExergyFactorDetail;
import org.jeecg.modules.ec.service.IEcExergyService;
import org.jeecg.modules.ec.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 11:19:27
 */
@Api(tags = "EC Exergy API")
@RestController
@RequestMapping("/ec/exergy")
@Slf4j
public class EcExergyController {

    @Resource
    private IEcExergyService ecExergyService;

    @ApiOperation("add new exergy")
    @PostMapping("/addExergy")
    public Result<Boolean> addExergy(@Valid @RequestBody EcExergyReqVo ecExergyReqVo) {
        log.info("请求参数ecExergyReqVo: {}", ecExergyReqVo.toString());
        try {
            ecExergyService.addExergy(ecExergyReqVo);
        } catch (Exception e) {
            log.error("插入失败", e);
            return Result.error(e.getMessage(), false);
        }
        return Result.OK(true);
    }

    @ApiOperation("edit exergy")
    @PostMapping("/editExergy")
    public Result<Boolean> editExergy(@Valid @RequestBody EcExergyReqVo ecExergyReqVo) {
        log.info("请求参数ecExergyReqVo: {}", ecExergyReqVo.toString());
        try {
            ecExergyService.editExergy(ecExergyReqVo);
        } catch (Exception e) {
            log.error("编辑失败", e);
            return Result.error(e.getMessage(), false);
        }
        return Result.OK(true);
    }

    @ApiOperation("delete exergy")
    @PostMapping("deleteExergy")
    public Result<Boolean> deleteExergy(@Valid @RequestBody EcExergyDelReqVo ecExergyDelReqVo) {
        log.info("[deleteExergy] 请求参数id: {}", ecExergyDelReqVo.getId());
        try {
            ecExergyService.deleteExergy(ecExergyDelReqVo.getId());
        } catch (Exception e) {
            log.error("删除失败", e);
            return Result.error(e.getMessage(), false);
        }
        return Result.OK(true);
    }

    @ApiOperation("require all exergy")
    @GetMapping("/getAllExergy")
    public Result<List<EcExergyVo>> getAllExergy() {
        Result<List<EcExergyVo>> result = new Result<>();
        List<EcExergyVo> exergyVos = ecExergyService.getAllExergy();
        result.setSuccess(true);
        result.setResult(exergyVos);
        return result;
    }

    @ApiOperation("list all exergy")
    @GetMapping("/list")
    public Result<IPage<EcExergyVo>> list(@Valid EcExergy ecExergy, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        log.info("请求参数ecExergy: {}", ecExergy.toString());
        QueryWrapper<EcExergy> queryWrapper = QueryGenerator.initQueryWrapper(ecExergy, req.getParameterMap());
        Page<EcExergy> page = new Page<>(pageNo, pageSize);
        IPage<EcExergyVo> pageList = ecExergyService.page(page, queryWrapper);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return Result.OK(pageList);
    }
}
