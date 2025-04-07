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
public interface Handler {
    List<String> handle(String message, List<String> result);
}
