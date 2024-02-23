package org.jeecg.modules.ec.annotation;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author zys
 * @version 1.0
 * @date 2022/10/25 16:07:00
 */
@Retention(RetentionPolicy.CLASS)
@Mappings(value = {
        @Mapping(target = "id", ignore = true)
})
public @interface MappingIgnore {
}
