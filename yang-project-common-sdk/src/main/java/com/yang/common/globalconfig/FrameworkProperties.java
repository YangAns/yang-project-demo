
package com.yang.common.globalconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "framework")
public class FrameworkProperties {
    //是否开启日志记录
    private boolean  enableRequestLog = true;

}
