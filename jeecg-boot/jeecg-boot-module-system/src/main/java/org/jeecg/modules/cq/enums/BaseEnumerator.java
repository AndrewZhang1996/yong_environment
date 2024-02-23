package org.jeecg.modules.cq.enums;

/**
 * 枚举类的基础接口
 *
 * @author daben
 */
public interface BaseEnumerator {

    /**
     * @return 枚举值
     */
    Integer getValue();

    /**
     * @return 枚举描述
     */
    String getDesc();

}