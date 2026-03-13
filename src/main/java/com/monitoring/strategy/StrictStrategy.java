package com.monitoring.strategy;

import com.monitoring.model.AnalysisResult;
import com.monitoring.model.SensorReading;
import com.monitoring.model.SensorType;

public class StrictStrategy implements AnalysisStrategy {

    @Override
    public AnalysisResult analyze(SensorReading reading) {
        SensorType type = reading.getType();
        double value = reading.getValue();
        
        double threshold = 0.0;
        
        switch (type) {
            case TEMPERATURE:
                threshold = 50.0;
                if (value >= threshold) {
                    return new AnalysisResult(true, 
                        "Temperatura critica - Modo Estricto", threshold);
                }
                break;
            case SMOKE:
                threshold = 20.0;
                if (value >= threshold) {
                    return new AnalysisResult(true, 
                        "Nivel de humo critico - Modo Estricto", threshold);
                }
                break;
            case PRESSURE:
                threshold = 5.0;
                if (value >= threshold) {
                    return new AnalysisResult(true, 
                        "Presion critica - Modo Estricto", threshold);
                }
                break;
            default:
                break;
        }
        
        return new AnalysisResult(false, "Normal", threshold);
    }

    @Override
    public String getName() {
        return "Estricto";
    }
}
