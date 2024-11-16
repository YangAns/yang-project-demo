package com.yang.common.logging;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class CustomLogMessageFormat implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return String.format("[%s] [%s] [%d ms] %s", now, category, elapsed, sql);
    }
}