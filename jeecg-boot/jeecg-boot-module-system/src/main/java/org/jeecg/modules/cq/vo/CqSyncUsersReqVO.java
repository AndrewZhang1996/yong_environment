package org.jeecg.modules.cq.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("通知批量用户请求VO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CqSyncUsersReqVO {
    @ApiModelProperty(value = "用户名列表", required = true)
    @NotNull(message = "用户名列表不能为空")
    private List<String> usernames;
}
