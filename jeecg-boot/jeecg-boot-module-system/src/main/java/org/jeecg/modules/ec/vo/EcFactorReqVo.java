package org.jeecg.modules.ec.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/25 09:58:03
 */
@ApiModel("Ec Factor Request Body")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcFactorReqVo {

    @ApiModelProperty(value = "factor id", required = false)
    private String id;

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

    /**
     * 数值
     */
    @ApiModelProperty(value = "factor unit", required = false)
    @Builder.Default
    private BigDecimal value = BigDecimal.ONE;

}
