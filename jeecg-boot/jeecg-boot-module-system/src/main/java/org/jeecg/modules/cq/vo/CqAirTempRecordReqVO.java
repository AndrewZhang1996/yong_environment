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
import java.util.Date;

@ApiModel("温度记录 request")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CqAirTempRecordReqVO {
    @ApiModelProperty(value = "房间 id", required = true)
    @NotNull(message = "房间 id 不能为空")
    private String roomId;
}
