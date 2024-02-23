package org.jeecg.modules.ec.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 10:30:48
 */
@ApiModel("Ec Phase Result")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcPhaseReqVo {

    @ApiModelProperty(value = "phase id", required = false)
    private String id;

    @ApiModelProperty(value = "phase type", required = true)
    private Integer phaseType;

    @ApiModelProperty(value = "exergy factor ids", required = false)
    private List<String> exergyFactorIds;
}
