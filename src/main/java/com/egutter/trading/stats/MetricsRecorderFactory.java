package com.egutter.trading.stats;

public class MetricsRecorderFactory {

    public static MetricsRecorder getInstance() {
        return NullMetricsRecorder.getInstance();
    }

}
