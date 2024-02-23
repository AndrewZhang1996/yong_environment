package org.jeecg.modules.cq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cq.entity.CqAirConditionerRecord;

import java.util.List;

public interface CqAirConditionerRecordMapper extends BaseMapper<CqAirConditionerRecord> {

    CqAirConditionerRecord getRecordsByUsername(@Param("username") String username);
}
