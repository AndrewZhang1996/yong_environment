package org.jeecg.modules.ec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.ec.entity.EcExergyFactorDetail;
import org.jeecg.modules.ec.vo.EcExergyFactorDetailRespVo;

import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 10:00:17
 */
public interface EcExergyFactorDetailMapper extends BaseMapper<EcExergyFactorDetail> {

    List<EcExergyFactorDetailRespVo> getAllExergyFactor();
}
