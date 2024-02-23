package org.jeecg.modules.ec.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.ec.entity.EcExergyFactorDetail;
import org.jeecg.modules.ec.service.IEcExergyFactorDetailService;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailDelReqVo;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailReqVo;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailRespVo;
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
@Api(tags = "EC Exergy Factor Detail API")
@RestController
@RequestMapping("/ec/exergyFactorDetail")
@Slf4j
public class EcExergyFactorDetailController {

    @Resource
    private IEcExergyFactorDetailService ecExergyFactorDetailService;

    @ApiOperation("list all exergyFactorDetail")
    @GetMapping("/list")
    public Result<IPage<EcExergyFactorDetailRespVo>> list(@Valid EcExergyFactorDetail ecExergyFactorDetail, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        log.info("请求参数ecExergyFactorDetail: {}", ecExergyFactorDetail.toString());
        QueryWrapper<EcExergyFactorDetail> queryWrapper = QueryGenerator.initQueryWrapper(ecExergyFactorDetail, req.getParameterMap());
        Page<EcExergyFactorDetail> page = new Page<>(pageNo, pageSize);
        IPage<EcExergyFactorDetailRespVo> pageList = ecExergyFactorDetailService.page(page, queryWrapper);
        log.info("查询当前页：" + pageList.getCurrent());
        log.info("查询当前页数量：" + pageList.getSize());
        log.info("查询结果数量：" + pageList.getRecords().size());
        log.info("数据总数：" + pageList.getTotal());
        return Result.OK(pageList);
    }

    @ApiOperation("add new exergy factor")
    @PostMapping("addNewExergyFactor")
    public Result<Boolean> addNewExergyFactor(@Valid @RequestBody EcExergyFactorDetailReqVo ecExergyFactorDetailReqVo) {
        log.info("请求参数ecExergyFactorDetailReqVo: {}", ecExergyFactorDetailReqVo.toString());
        try {
            ecExergyFactorDetailService.addNewExergyFactor(ecExergyFactorDetailReqVo);
        } catch (Exception e) {
            log.error("插入失败", e);
            return Result.error(e.getMessage(), false);
        }
        return Result.OK(true);
    }

    @ApiOperation("edit exergy factor")
    @PostMapping("editExergyFactor")
    public Result<Boolean> editExergyFactor(@Valid @RequestBody EcExergyFactorDetailReqVo ecExergyFactorDetailReqVo) {
        log.info("请求参数ecExergyFactorDetailReqVo: {}", ecExergyFactorDetailReqVo.toString());
        try {
            ecExergyFactorDetailService.editExergyFactor(ecExergyFactorDetailReqVo);
        } catch (Exception e) {
            log.error("编辑失败", e);
            return Result.error(e.getMessage(), false);
        }
        return Result.OK(true);
    }

    @ApiOperation("delete exergy factor")
    @PostMapping("deleteExergyFactor")
    public Result<Boolean> deleteExergyFactor(@Valid @RequestBody EcExergyFactorDetailDelReqVo ecExergyFactorDetailDelReqVo) {
        log.info("[deleteExergyFactor] 请求参数id: {}", ecExergyFactorDetailDelReqVo.getId());
        try {
            ecExergyFactorDetailService.deleteExergyFactor(ecExergyFactorDetailDelReqVo.getId());
        } catch (Exception e) {
            log.error("删除失败", e);
            return Result.error(e.getMessage(), false);
        }
        return Result.OK(true);
    }

    @ApiOperation("require all exergy factor")
    @GetMapping("/getAllExergyFactor")
    public Result<List<EcExergyFactorDetailRespVo>> getAllExergyFactor() {
        Result<List<EcExergyFactorDetailRespVo>> result = new Result<>();
        List<EcExergyFactorDetailRespVo> exergyFactorDetailRespVoList = ecExergyFactorDetailService.getAllExergyFactor();
        result.setSuccess(true);
        result.setResult(exergyFactorDetailRespVoList);
        return result;
    }
}
