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
public class HandlerChain implements Handler{
    List<Handler> handlers;

    public HandlerChain(List<Handler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public List<String> handle(String message, List<String> result) {
        List<String> list = result;
        for (Handler handler : handlers) {
            list = handler.handle(message, list);
        }
        return list;
    }
}
