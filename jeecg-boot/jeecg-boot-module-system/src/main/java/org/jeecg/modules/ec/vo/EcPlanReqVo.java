package org.jeecg.modules.ec.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 10:10:20
 */
@ApiModel("Ec Plan Request Vo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcPlanReqVo {

    @ApiModelProperty(value = "plan id", required = false)
    private String id;

    @ApiModelProperty(value = "plan name", required = true)
    private String name;

    @ApiModelProperty(value = "plan description", required = true)
    private String description;

    @ApiModelProperty(value = "plan status", required = false)
    @Builder.Default
    private Integer status = 0;

    @ApiModelProperty(value = "plan phase list", required = true)
    private List<EcPhaseReqVo> phases;
}
