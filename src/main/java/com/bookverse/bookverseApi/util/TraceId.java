package com.bookverse.bookverseApi.util;

import org.slf4j.MDC;

public final class TraceId {
    private static final String key = "traceId";
    private TraceId() {}
    public static String current() {
        return MDC.get(key);
    }
    public static void put(String id) { MDC.put(key, id); }
    public static void clear() { MDC.remove(key); }
}
