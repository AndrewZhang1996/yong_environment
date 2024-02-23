package org.jeecg.modules.cq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cq.entity.CqRoomInfo;
import org.jeecg.modules.cq.entity.CqUserDisplayInfo;

import java.util.List;

public interface CqUserDisplayInfoMapper extends BaseMapper<CqRoomInfo>  {
    /**
     * 通过用户名称查询用户显示配置
     * @param username 用户名称
     * @return
     */
    List<CqUserDisplayInfo> getUserDisplayInfoByUsername(@Param("username") String username);
}
