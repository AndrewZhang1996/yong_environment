package org.jeecg.modules.cq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("cq_room_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CqUserDisplayInfo {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private java.lang.String id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 是否显示温度
     */
    private Integer isAirTemperatureDisplay;

    /**
     * 是否显示湿度
     */
    private Integer isMoistureContentDisplay;

    /**
     * 是否显示空调
     */
    private Integer isAirConditionerDisplay;

    /**
     * 是否显示通知
     */
    private Integer isNotificationDisplay;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 所属部门
     */
    private String sysOrgCode;
}
