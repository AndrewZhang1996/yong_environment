package org.jeecg.modules.ec.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 11:11:34
 */
@ApiModel("Ec Exergy Result")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcExergyVo {

    @ApiModelProperty("exergy id")
    private String id;

    @ApiModelProperty("exergy name")
    private String name;

    @ApiModelProperty("exergy description")
    private String description;

    @ApiModelProperty("exergy unit")
    private String unit;

    @ApiModelProperty("exergy value")
    @Builder.Default
    private BigDecimal value = BigDecimal.ZERO;
}
