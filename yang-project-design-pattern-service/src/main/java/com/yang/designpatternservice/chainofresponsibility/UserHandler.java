package com.yang.designpatternservice.chainofresponsibility;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author YangAns
 * @since 2024/11/21
 */
public class UserHandler implements Handler {
    @Override
    public List<String> handle(String message, List<String> result) {
        if (message != null && message.contains("user")) {
            result.add("user");
        }
        return result;
    }
}
