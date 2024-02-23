package org.jeecg.modules.cq.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel("空调能耗记录 VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CqAirCondConsumeVO {
    @ApiModelProperty(value = "用户名称", required = true, example = "1234")
    @NotNull(message = "用户名称 不能为空")
    private String username;

    @ApiModelProperty(value = "平均能耗", required = true, example = "333")
    private BigDecimal energyConsumption;

}
