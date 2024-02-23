package org.jeecg.modules.ec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.ec.entity.EcPhase;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailRespVo;
import org.jeecg.modules.ec.vo.EcPlanVo;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 10:01:13
 */
public interface EcPhaseMapper extends BaseMapper<EcPhase> {

    List<EcPlanVo> getAllPlansOfPhaseByPhaseId(@Param("phaseId") String phaseId);

    List<EcExergyFactorDetailRespVo> getExergyFactorDetailByPhaseTypeAndPlanId(@Param("planId") String planId, @Param("phaseType") String phaseType);
}
