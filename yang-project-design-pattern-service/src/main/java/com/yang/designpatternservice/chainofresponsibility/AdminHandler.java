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
public class AdminHandler implements Handler {
    @Override
    public List<String> handle(String message, List<String> result) {
        if(message != null && message.contains("admin")) {
            result.add("admin");
        }
        return result;
    }
}
