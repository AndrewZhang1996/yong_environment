package org.jeecg.modules.cq.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户显示配置")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CqUserDisplayInfoRespVO {

    @ApiModelProperty(value = "用户名称", example = "xxx")
    private String username;

    @ApiModelProperty(value = "是否显示温度", example = "1")
    private Integer isAirTemperatureDisplay;

    @ApiModelProperty(value = "是否显示湿度", example = "1")
    private Integer isMoistureContentDisplay;

    @ApiModelProperty(value = "是否显示空调", example = "1")
    private Integer isAirConditionerDisplay;

    @ApiModelProperty(value = "是否显示通知", example = "1")
    private Integer isNotificationDisplay;
}
