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

@ApiModel("湿度记录 response")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CqMoistureRecordRespVO {
    @ApiModelProperty(value = "房间 id", required = true, example = "1234")
    @NotNull(message = "房间 id 不能为空")
    private String roomId;

    @ApiModelProperty(value = "湿度", required = true, example = "120.2")
    private BigDecimal moistureContent;

    @ApiModelProperty(value = "记录时间", example = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;
}
