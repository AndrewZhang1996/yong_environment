package org.jeecg.modules.ec.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/21 10:10:20
 */
@ApiModel("Ec Plan Request Vo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EcPlanDelReqVo {

    @ApiModelProperty(value = "plan id", required = true)
    private String id;

}
