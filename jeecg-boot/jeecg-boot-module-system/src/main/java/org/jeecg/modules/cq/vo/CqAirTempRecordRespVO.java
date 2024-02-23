package org.jeecg.modules.cq.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel("温度记录 response")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CqAirTempRecordRespVO {
    @ApiModelProperty("空气温度记录列表")
    private List<CqAirTempRecordVO> airTempRecordList;

    @ApiModelProperty("舒适温度上限")
    private CqSettingsVO cozyTimeUpper;

    @ApiModelProperty("舒适温度下限")
    private CqSettingsVO cozyTimeLower;

    @ApiModelProperty("昨天的日期")
    private String yesterdayDayTime;

    @ApiModelProperty("今天的日期")
    private String todayDayTime;
}
