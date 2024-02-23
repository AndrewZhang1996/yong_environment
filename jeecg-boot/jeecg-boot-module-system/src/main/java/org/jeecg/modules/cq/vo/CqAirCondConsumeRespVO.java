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
import java.util.List;

@ApiModel("空调能耗记录 response")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CqAirCondConsumeRespVO {
    @ApiModelProperty(value = "当前空调能耗水平", required = true)
    private CqAirCondConsumeVO airCondConsumLevel;

    @ApiModelProperty(value = "平均能耗水平")
    private CqSettingsVO averageConsumLevel;

}
