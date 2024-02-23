package org.jeecg.modules.ec.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/25 09:58:03
 */
@ApiModel("Ec PlanPhase Insert Request Body")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcPlanPhaseInsertReqVo {

    /**
     * plan_id
     */
    @ApiModelProperty(value = "plan id", required = true)
    private String planId;

    /**
     * phase_id
     */
    @ApiModelProperty(value = "phase id", required = true)
    private String phaseId;

}
