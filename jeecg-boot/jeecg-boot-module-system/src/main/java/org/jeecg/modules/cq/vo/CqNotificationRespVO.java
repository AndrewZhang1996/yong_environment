package org.jeecg.modules.cq.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel("通知 response")
@Data
@Builder
@AllArgsConstructor
public class CqNotificationRespVO {
    @ApiModelProperty(value = "用户名称", required = true, example = "test_user")
    @NotNull(message = "用户名称 不能为空")
    private String username;

    @ApiModelProperty(value = "内容", required = true, example = "通知通知")
    private String content;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true, example = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "指示色",required = true, example = "1")
    private Integer colorType;
}
