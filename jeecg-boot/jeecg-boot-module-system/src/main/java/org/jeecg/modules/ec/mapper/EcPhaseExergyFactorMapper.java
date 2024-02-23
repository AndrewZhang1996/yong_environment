package org.jeecg.modules.ec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.ec.entity.EcExergyFactorDetail;
import org.jeecg.modules.ec.entity.EcPhaseExergyFactor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 10:01:35
 */
public interface EcPhaseExergyFactorMapper extends BaseMapper<EcPhaseExergyFactor> {

    List<EcExergyFactorDetail> selectEcExergyFactorDetailListByPhaseId(@Param("phaseId") String phaseId);

    List<String> selectRelatedPlanIdListByExergyFactorDetailId(@Param("id") String id);
}
