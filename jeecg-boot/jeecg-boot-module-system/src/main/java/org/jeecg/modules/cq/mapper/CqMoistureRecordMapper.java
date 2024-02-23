package org.jeecg.modules.cq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cq.entity.CqAirTemperatureRecord;
import org.jeecg.modules.cq.entity.CqMoistureRecord;

import java.util.List;

public interface CqMoistureRecordMapper extends BaseMapper<CqMoistureRecord> {

    List<CqMoistureRecord> getRecordsByRoomId(@Param("roomId") String roomId, @Param("limit") Integer limit);
}
