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
@ApiModel("Ec Plan Result")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcPlanVo {

    @ApiModelProperty("plan id")
    private String id;

    @ApiModelProperty("plan name")
    private String name;

    @ApiModelProperty("plan description")
    private String description;

    @ApiModelProperty("plan value")
    @Builder.Default
    private BigDecimal value = BigDecimal.ZERO;

    @ApiModelProperty("plan status")
    @Builder.Default
    private Integer status = 0;

    @ApiModelProperty("plan phase list")
    private List<EcPhaseVo> phaseList;
}
