package org.jeecg.modules.ec.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 10:52:57
 */
@ApiModel("Ec Exergy Factor Request Result")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcExergyFactorDetailDelReqVo {

    @ApiModelProperty(value = "exergy_factor_detail id", required = true)
    private String id;

}
