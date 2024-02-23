package org.jeecg.modules.ec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.ec.entity.EcPhase;
import org.jeecg.modules.ec.entity.EcPlanPhase;

import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 10:02:35
 */
public interface EcPlanPhaseMapper extends BaseMapper<EcPlanPhase> {

    List<EcPhase> selectPhaseListByPlanId(@Param("planId") String planId);

    EcPhase selectPhaseByPlanIdAndPhaseType(@Param("planId") String planId, @Param("phaseType") Integer phaseType);

}
