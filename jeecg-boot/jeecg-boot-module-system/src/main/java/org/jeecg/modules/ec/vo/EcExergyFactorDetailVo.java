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
@ApiModel("Ec Exergy Factor Result")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcExergyFactorDetailVo {

    @ApiModelProperty("exergy_factor_detail id")
    private String id;

    @ApiModelProperty("exergy_factor_detail name")
    private String name;

    @ApiModelProperty("exergy_factor_detail description")
    private String description;

    @ApiModelProperty("exergy_factor_detail exergy_id")
    private String exergyId;

    @ApiModelProperty("exergy_factor_detail total value = exergy.value * each item of factorList")
    private BigDecimal value;

//    @ApiModelProperty("exergy")
//    private EcExergyVo exergy;

    @ApiModelProperty(value = "exergy_factor_detail exergy factor list")
    private List<EcExergyFactorVo> exergyFactorList;

//    @ApiModelProperty("factors")
//    private List<EcFactorVo> factorList;
}
