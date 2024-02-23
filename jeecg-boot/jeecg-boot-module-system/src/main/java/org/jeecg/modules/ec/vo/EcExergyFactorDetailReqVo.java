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
public class EcExergyFactorDetailReqVo {

    @ApiModelProperty(value = "exergy_factor_detail id", required = false)
    private String id;

    @ApiModelProperty(value = "exergy_factor_detail name", required = true)
    private String name;

    @ApiModelProperty(value = "exergy_factor_detail description", required = true)
    private String description;

    @ApiModelProperty(value = "exergy_factor_detail exergy factor list")
    private List<EcExergyFactorReqVo> exergyFactorList;
}



