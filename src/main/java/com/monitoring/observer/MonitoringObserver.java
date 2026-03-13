package com.monitoring.observer;

import com.monitoring.model.AnalysisResult;
import com.monitoring.model.SensorReading;

public interface MonitoringObserver {
    void onReading(SensorReading reading, AnalysisResult result);
    String getModuleName();
}
