package org.jeecg.modules.cq.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("设置")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CqSettingsVO {

    @ApiModelProperty(value = "key", example = "N")
    private String key;

    @ApiModelProperty(value = "值", example = "100")
    private String value;

    @ApiModelProperty(value = "描述", example = "数据限制")
    private String remark;
}
