package org.jeecg.modules.cq.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel("通知 request")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CqNotificationReqVO {
    @ApiModelProperty(value = "用户名称", required = true)
    @NotNull(message = "用户名称 不能为空")
    private String username;
}
