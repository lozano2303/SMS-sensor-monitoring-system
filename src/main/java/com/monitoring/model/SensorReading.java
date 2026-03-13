package com.monitoring.model;

import java.time.LocalDateTime;

public class SensorReading {
    private final SensorType type;
    private final double value;
    private final LocalDateTime timestamp;

    public SensorReading(SensorType type, double value) {
        this.type = type;
        this.value = value;
        this.timestamp = LocalDateTime.now();
    }

    public SensorType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %.2f %s", 
            timestamp.toString(), 
            type.getDisplayName(), 
            value, 
            type.getUnit());
    }
}
