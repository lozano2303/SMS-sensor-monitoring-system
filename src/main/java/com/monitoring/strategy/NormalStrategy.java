package com.monitoring.strategy;

import com.monitoring.model.AnalysisResult;
import com.monitoring.model.SensorReading;
import com.monitoring.model.SensorType;

public class NormalStrategy implements AnalysisStrategy {

    @Override
    public AnalysisResult analyze(SensorReading reading) {
        SensorType type = reading.getType();
        double value = reading.getValue();
        
        double threshold = 0.0;
        
        switch (type) {
            case TEMPERATURE:
                threshold = 70.0;
                if (value >= threshold) {
                    return new AnalysisResult(true, 
                        "Temperatura critica - Modo Normal", threshold);
                }
                break;
            case SMOKE:
                threshold = 35.0;
                if (value >= threshold) {
                    return new AnalysisResult(true, 
                        "Nivel de humo critico - Modo Normal", threshold);
                }
                break;
            case PRESSURE:
                threshold = 7.5;
                if (value >= threshold) {
                    return new AnalysisResult(true, 
                        "Presion critica - Modo Normal", threshold);
                }
                break;
        }
        
        return new AnalysisResult(false, "Normal", threshold);
    }

    @Override
    public String getName() {
        return "Normal";
    }
}
