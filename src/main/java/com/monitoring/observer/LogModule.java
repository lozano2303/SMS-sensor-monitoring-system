package com.monitoring.observer;

import com.monitoring.model.AnalysisResult;
import com.monitoring.model.SensorReading;

public class LogModule implements MonitoringObserver {

    @Override
    public void onReading(SensorReading reading, AnalysisResult result) {
        String type = result.isCritical() ? "CRITICO" : "INFO";
        System.out.println("\n[LOG] Registro de lectura:");
        System.out.println("Timestamp: " + reading.getTimestamp());
        System.out.println("Tipo: " + reading.getType().getDisplayName());
        System.out.println("Valor: " + reading.getValue() + " " + reading.getType().getUnit());
        System.out.println("Resultado: " + result.getMessage());
        System.out.println("Nivel: " + type);
    }

    @Override
    public String getModuleName() {
        return "Modulo de Registro";
    }
}
