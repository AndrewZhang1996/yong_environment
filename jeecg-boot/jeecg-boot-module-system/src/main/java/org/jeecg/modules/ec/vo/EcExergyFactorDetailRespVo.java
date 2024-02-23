package org.jeecg.modules.ec.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 10:52:57
 */
@ApiModel("Ec Exergy Factor Response Result")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcExergyFactorDetailRespVo {

    @ApiModelProperty("exergy_factor_detail key")
    private String key;

    @ApiModelProperty("exergy_factor_detail id")
    private String id;

    @ApiModelProperty("exergy_factor_detail name")
    private String name;

    @ApiModelProperty("exergy_factor_detail description")
    private String description;

//    @ApiModelProperty("exergy_factor_detail exergy_id")
//    private String exergyId;

//    @ApiModelProperty("exergy_exergy exergy_name")
//    private String exergyName;
}
