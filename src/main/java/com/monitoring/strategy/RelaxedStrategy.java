package com.monitoring.strategy;

import com.monitoring.model.AnalysisResult;
import com.monitoring.model.SensorReading;
import com.monitoring.model.SensorType;

public class RelaxedStrategy implements AnalysisStrategy {

    @Override
    public AnalysisResult analyze(SensorReading reading) {
        SensorType type = reading.getType();
        double value = reading.getValue();
        
        double threshold = 0.0;
        
        switch (type) {
            case TEMPERATURE:
                threshold = 90.0;
                if (value >= threshold) {
                    return new AnalysisResult(true, 
                        "Temperatura critica - Modo Relajado", threshold);
                }
                break;
            case SMOKE:
                threshold = 50.0;
                if (value >= threshold) {
                    return new AnalysisResult(true, 
                        "Nivel de humo critico - Modo Relajado", threshold);
                }
                break;
            case PRESSURE:
                threshold = 10.0;
                if (value >= threshold) {
                    return new AnalysisResult(true, 
                        "Presion critica - Modo Relajado", threshold);
                }
                break;
        }
        
        return new AnalysisResult(false, "Normal", threshold);
    }

    @Override
    public String getName() {
        return "Relajado";
    }
}
