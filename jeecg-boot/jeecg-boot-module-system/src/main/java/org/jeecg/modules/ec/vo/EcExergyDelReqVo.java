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
public class EcExergyDelReqVo {

    /**
     * 名称
     */
    @ApiModelProperty(value = "exergy id", required = true)
    private String id;

}
