package org.jeecg.modules.ec.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcExergyFactorReqVo {
    @ApiModelProperty(value = "exergy_factor_detail exergy_id", required = true)
    private String exergyId;

    @ApiModelProperty(value = "related factor", required = true)
    private EcFactorReqVo factor;
}
