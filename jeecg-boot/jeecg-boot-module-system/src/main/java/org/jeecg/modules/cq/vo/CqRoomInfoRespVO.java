package org.jeecg.modules.cq.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("房间详情信息")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CqRoomInfoRespVO {

    @ApiModelProperty(value = "房间 id", example = "1231231231")
    private String id;

    @ApiModelProperty(value = "房间名", example = "xxx 的 客厅")
    private String roomName;

    @ApiModelProperty(value = "房间类型", example = "0")
    private Integer roomType;

    @ApiModelProperty(value = "用户名称", example = "xxx")
    private String username;
}
