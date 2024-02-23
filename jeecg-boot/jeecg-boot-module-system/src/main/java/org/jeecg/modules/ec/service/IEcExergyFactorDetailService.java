package org.jeecg.modules.ec.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.ec.entity.EcExergyFactorDetail;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailReqVo;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailRespVo;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailVo;

import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/11/07 10:17:00
 */
public interface IEcExergyFactorDetailService {

    IPage<EcExergyFactorDetailRespVo> page(Page<EcExergyFactorDetail> page, QueryWrapper<EcExergyFactorDetail> queryWrapper);

    void addNewExergyFactor(EcExergyFactorDetailReqVo ecExergyFactorDetailReqVo);

    void editExergyFactor(EcExergyFactorDetailReqVo ecExergyFactorDetailReqVo);

    List<EcExergyFactorDetailRespVo> getAllExergyFactor();

    void deleteExergyFactor(String id);
}
