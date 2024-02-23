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
@ApiModel("Ec Exergy Request Body")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcExergyReqVo {

    /**
     * 名称
     */
    @ApiModelProperty(value = "exergy id", required = false)
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "exergy name", required = true)
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value = "exergy description", required = true)
    private String description;

    /**
     * 单位
     */
    @ApiModelProperty(value = "exergy unit", required = true)
    private String unit;

    /**
     * 数值
     */
    @ApiModelProperty(value = "exergy unit")
    @Builder.Default
    private BigDecimal value = BigDecimal.ZERO;

}
