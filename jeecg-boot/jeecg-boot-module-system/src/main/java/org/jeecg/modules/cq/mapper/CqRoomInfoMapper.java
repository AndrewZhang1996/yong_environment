package org.jeecg.modules.cq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cq.entity.CqRoomInfo;

import java.util.List;

public interface CqRoomInfoMapper extends BaseMapper<CqRoomInfo>  {
    /**
     * 通过用户名称查询房间信息
     * @param username 用户名称
     * @return
     */
    List<CqRoomInfo> getRoomInfoByUsername(@Param("username") String username);
}
