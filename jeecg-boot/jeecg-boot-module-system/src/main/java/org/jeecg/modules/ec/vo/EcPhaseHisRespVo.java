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
public class EcPhaseHisRespVo {

    @ApiModelProperty("phase id")
    private String id;

    @ApiModelProperty("phase total value = sum all phase exergy*factor value")
    private BigDecimal value;

    @ApiModelProperty("phase all exergy*factor")
    private List<EcExergyFactorDetailHisRespVo> exergyFactorDetailHis;
}
