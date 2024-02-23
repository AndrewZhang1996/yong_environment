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
@ApiModel("Ec Exergy Factor His Resp Result")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcExergyFactorHisRespVo {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("exergyName")
    private String exergyName;

    @ApiModelProperty("factorName")
    private String factorName;

    @ApiModelProperty("description")
    private String description;

    @ApiModelProperty("unit")
    private String unit;

    @ApiModelProperty("value")
    @Builder.Default
    private BigDecimal value = BigDecimal.ZERO;
}
