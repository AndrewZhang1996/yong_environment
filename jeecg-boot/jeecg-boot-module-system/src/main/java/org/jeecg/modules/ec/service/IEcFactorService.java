package org.jeecg.modules.ec.service;

import org.jeecg.modules.ec.entity.EcFactor;
import org.jeecg.modules.ec.vo.EcExergyFactorReqVo;
import org.jeecg.modules.ec.vo.EcExergyFactorRespVo;
import org.jeecg.modules.ec.vo.EcFactorVo;

import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 11:20:46
 */
public interface IEcFactorService {

    /**
     * 新增
     * @param factor factor
     * @return ID
     */
    String insert(EcFactor factor);

    List<EcExergyFactorRespVo>    getAllFactorsByExergyFactorDetailId(String exergyFactorDetailId);
}
