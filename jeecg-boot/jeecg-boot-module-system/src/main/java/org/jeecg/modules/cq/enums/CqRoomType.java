package org.jeecg.modules.cq.enums;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CqRoomType implements BaseEnumerator {
    LIVING_ROOM(0, "客厅"),
    BED_ROOM(1, "卧室");

    @EnumValue
    private final Integer value;

    private final String desc;

    @JsonCreator
    public static CqRoomType valueOfCode(Integer value) {
        for (CqRoomType i : CqRoomType.values()) {
            if (i.getValue().equals(value)) {
                return i;
            }
        }
        return null;
    }

    public static CqRoomType valueOfCode(String value) {
        if (NumberUtil.isNumber(value)) {
            return valueOfCode(Integer.valueOf(value));
        }
        return CqRoomType.valueOf(value);
    }
}
