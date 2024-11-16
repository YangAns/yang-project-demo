
package com.yang.common.exception;

import com.yang.common.utils.YListUtils;
import com.yang.common.utils.YStrUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalException extends RuntimeException {
    private String message;

    private Integer code;

    public LocalException() {
        super();
    }

    public LocalException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }

    public LocalException(String... message) {
        super(toMessage(message));
        this.code = 500;
        this.message = toMessage(message);
    }

    public static String toMessage(String[] message) {
        return YStrUtils.join(YListUtils.array2list(message), "");
    }

    public LocalException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
