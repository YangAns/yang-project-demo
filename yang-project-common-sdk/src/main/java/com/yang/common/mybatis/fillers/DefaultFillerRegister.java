
package com.yang.common.mybatis.fillers;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class DefaultFillerRegister {

    @PostConstruct
    public void afterPropertiesSet() {
        ColumnFillerCache.register(new CreateDateLongFiller());
        ColumnFillerCache.register(new PasswordStringFiller());
        ColumnFillerCache.register("updateDate", System.currentTimeMillis(), Long.class);
        ColumnFillerCache.register("delFlag", 0, Integer.class);
        ColumnFillerCache.register("delFlag", "0", String.class);
        ColumnFillerCache.register("delFlag", false, Boolean.class);
    }
}
