package org.jeecg.modules.ec.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 10:56:31
 */
@ApiModel("Ec Factor Result")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcFactorVo {

    @ApiModelProperty("factor key")
    private String key;

    @ApiModelProperty("factor id")
    private String id;

    @ApiModelProperty("factor name")
    private String name;

    @ApiModelProperty("factor description")
    private String description;

    @ApiModelProperty("factor unit")
    private String unit;

    @ApiModelProperty("factor value")
    @Builder.Default
    private BigDecimal value = BigDecimal.ONE;
}
