package org.jeecg.modules.cq.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel("湿度记录 request")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CqMoistureRecordReqVO {
    @ApiModelProperty(value = "房间 id", required = true)
    @NotNull(message = "房间 id 不能为空")
    private String roomId;
}
