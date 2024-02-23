package org.jeecg.modules.ec.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcExergyFactorRespVo {
    @ApiModelProperty(value = "exergy_name", required = true)
    private String exergyName;

    @ApiModelProperty(value = "exergy_factor_detail exergy_id", required = true)
    private String exergyId;

    @ApiModelProperty(value = "factor id", required = false)
    private String factorId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "factor name", required = true)
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value = "factor description", required = false)
    private String description;

    /**
     * 单位
     */
    @ApiModelProperty(value = "factor unit", required = true)
    private String unit;

    @ApiModelProperty("exergy_factor_detail key")
    private String key;


    @ApiModelProperty("factor value")
    @Builder.Default
    private BigDecimal value = BigDecimal.ONE;
}
