package org.jeecg.modules.ec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.ec.entity.EcFactor;
import org.jeecg.modules.ec.entity.EcFactorHis;
import org.jeecg.modules.ec.vo.EcExergyFactorReqVo;
import org.jeecg.modules.ec.vo.EcExergyFactorRespVo;
import org.jeecg.modules.ec.vo.EcFactorVo;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 10:00:53
 */
public interface EcFactorMapper extends BaseMapper<EcFactor> {

    List<EcExergyFactorRespVo> getAllFactorsByExergyFactorDetailId(@Param("exergyFactorDetailId") String exergyFactorDetailId);

    List<EcFactorHis> selectFactorsByDetailId(String id);
}
