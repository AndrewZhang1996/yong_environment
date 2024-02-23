package org.jeecg.modules.ec.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcExergyFactorVo {
    @ApiModelProperty(value = "exergy_factor_detail exergy_id", required = true)
    private EcExergyVo exergy;

    @ApiModelProperty(value = "related factor", required = true)
    private EcFactorVo factor;

    @ApiModelProperty("exergy_factor_detail total value = exergy.value * each item of factorList")
    private BigDecimal value;
}
