package org.jeecg.modules.cq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cq.entity.CqAirTemperatureRecord;

import java.util.Date;
import java.util.List;

public interface CqAirTemperatureRecordMapper extends BaseMapper<CqAirTemperatureRecord> {

    List<CqAirTemperatureRecord> getRecordsByRoomId(@Param("roomId") String roomId, @Param("startTime") Date startTime,
                                                    @Param("endTime") Date endTime);

    CqAirTemperatureRecord getCurrentRecordsByRoomId(@Param("roomId") String roomId);
}
