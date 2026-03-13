package com.monitoring.observer;

import com.monitoring.model.AnalysisResult;
import com.monitoring.model.SensorReading;

public class DisplayPanel implements MonitoringObserver {

    @Override
    public void onReading(SensorReading reading, AnalysisResult result) {
        String status = result.isCritical() ? "CRITICO" : "NORMAL";
        System.out.println("\n[PANTALLA] Lectura actualizada:");
        System.out.println("Sensor: " + reading.getType().getDisplayName());
        System.out.println("Valor: " + reading.getValue() + " " + reading.getType().getUnit());
        System.out.println("Estado: " + status);
    }

    @Override
    public String getModuleName() {
        return "Panel de Visualizacion";
    }
}
