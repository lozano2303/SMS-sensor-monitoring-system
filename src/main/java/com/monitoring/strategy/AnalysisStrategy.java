package com.monitoring.strategy;

import com.monitoring.model.AnalysisResult;
import com.monitoring.model.SensorReading;

public interface AnalysisStrategy {
    AnalysisResult analyze(SensorReading reading);
    String getName();
}
