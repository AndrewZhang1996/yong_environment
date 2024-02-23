package org.jeecg.modules.ec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.ec.entity.EcPhaseHis;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailHisRespVo;
import org.jeecg.modules.ec.vo.EcPhaseHisRespVo;

import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 10:01:13
 */
public interface EcPhaseHisMapper extends BaseMapper<EcPhaseHis> {

    List<EcExergyFactorDetailHisRespVo> getExergyFactorDetailHisByPhaseTypeAndPlanId(String planId, String phaseType);

    EcPhaseHisRespVo selectPhaseHisByPhaseTypeAndPlanId(String planId, String phaseType);
}
