package com.egutter.trading.stats;

import java.util.HashMap;

public class NullMetricsRecorder implements MetricsRecorder {

    private static MetricsRecorder instance = new NullMetricsRecorder();

    static MetricsRecorder getInstance() {
        return NullMetricsRecorder.instance;
    }

    @Override
    public void incEvent(String eventName) {

    }

    @Override
    public HashMap<String, Integer> getMetrics() {
        return new HashMap<>();
    }

    @Override
    public String getMetricsAsString() {
        return "";
    }
}
