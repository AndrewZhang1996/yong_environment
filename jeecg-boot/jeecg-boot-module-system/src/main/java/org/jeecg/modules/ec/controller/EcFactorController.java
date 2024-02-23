package org.jeecg.modules.ec.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.ec.convert.EcFactorConvert;
import org.jeecg.modules.ec.entity.EcFactor;
import org.jeecg.modules.ec.service.IEcFactorService;
import org.jeecg.modules.ec.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 11:19:27
 */
@Api(tags = "EC Factor API")
@RestController
@RequestMapping("/ec/factor")
public class EcFactorController {

    @Resource
    private IEcFactorService ecFactorService;

    @ApiOperation("insert new factor")
    @PostMapping("/insertNewEcFactor")
    public Result<String> insertNewEcFactor(@Valid EcFactorReqVo factor) {
        Result<String> result = new Result<>();
        String factorId = ecFactorService.insert(EcFactorConvert.instance.convertToEcFactor(factor));
        if (StringUtils.isNotEmpty(factorId)) {
            result.setSuccess(true);
            result.setResult(factorId);
        } else {
            result.setSuccess(false);
            result.setMessage("插入新影响因子失败！");
        }
        return result;
    }

    @ApiOperation("require all factors by exergyId")
    @GetMapping("/getAllFactorsByExergyFactorDetailId")
    public Result<List<EcExergyFactorRespVo>> getAllFactorsByExergyFactorDetailId(@RequestParam String exergyFactorDetailId) {
        Result<List<EcExergyFactorRespVo>> result = new Result<>();
        List<EcExergyFactorRespVo> factorVos = ecFactorService.getAllFactorsByExergyFactorDetailId(exergyFactorDetailId);
        result.setSuccess(true);
        result.setResult(factorVos);
        return result;
    }
}
