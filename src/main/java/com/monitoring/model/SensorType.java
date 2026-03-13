package com.monitoring.model;

public enum SensorType {
    TEMPERATURE("Temperature", "C"),
    SMOKE("Smoke", "ppm"),
    PRESSURE("Pressure", "bar");

    private final String displayName;
    private final String unit;

    SensorType(String displayName, String unit) {
        this.displayName = displayName;
        this.unit = unit;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUnit() {
        return unit;
    }
}
