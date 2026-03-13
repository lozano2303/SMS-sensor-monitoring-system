package com.monitoring.observer;

import com.monitoring.model.AnalysisResult;
import com.monitoring.model.SensorReading;

public class AlarmModule implements MonitoringObserver {

    @Override
    public void onReading(SensorReading reading, AnalysisResult result) {
        if (result.isCritical()) {
            System.out.println("\n[ALARMA] *** ALERTA CRITICA ***");
            System.out.println("Tipo: " + reading.getType().getDisplayName());
            System.out.println("Valor: " + reading.getValue() + " " + reading.getType().getUnit());
            System.out.println("Mensaje: " + result.getMessage());
            System.out.println("*******************************");
        }
    }

    @Override
    public String getModuleName() {
        return "Modulo de Alarma";
    }
}
