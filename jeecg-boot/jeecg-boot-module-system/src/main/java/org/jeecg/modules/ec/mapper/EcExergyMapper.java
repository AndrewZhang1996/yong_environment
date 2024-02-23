package org.jeecg.modules.ec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.ec.entity.EcExergy;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 09:59:00
 */
public interface EcExergyMapper extends BaseMapper<EcExergy> {

    List<String> selectRelatedPlanIdListByExergyId(@Param("id") String id);

}
