package com.egutter.trading.stats;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.StringJoiner;

public class MetricsRecorderImpl implements MetricsRecorder {

    private static MetricsRecorder instance = new MetricsRecorderImpl();
    private final HashMap<String, Integer> events;

    private MetricsRecorderImpl() {
        this.events = new LinkedHashMap<>();
    }

    static MetricsRecorder getInstance() {
        return MetricsRecorderImpl.instance;
    }

    @Override
    public void incEvent(String eventName) {
        Integer count = Optional.ofNullable(this.events.get(eventName)).orElse(0);
        this.events.put(eventName, count+1);
    }

    @Override
    public HashMap<String, Integer> getMetrics() {
        return this.events;
    }
    @Override
    public String getMetricsAsString() {
        StringJoiner sj = new StringJoiner("\n");
        this.events.forEach((k,v) -> sj.add(k + "=" + v));
        return sj.toString();
    }
}
