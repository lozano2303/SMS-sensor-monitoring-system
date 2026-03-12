package com.monitoring.core;

import com.monitoring.model.SensorType;
import com.monitoring.model.SensorReading;
import com.monitoring.model.AnalysisResult;
import com.monitoring.strategy.AnalysisStrategy;
import com.monitoring.strategy.StrictStrategy;
import com.monitoring.observer.MonitoringObserver;

import java.util.*;

public class MonitoringCenter {
    
    private static volatile MonitoringCenter instance;
    private AnalysisStrategy strategy;
    private final List<MonitoringObserver> observers;
    private final Map<SensorType, List<SensorReading>> sensors;
    private final Set<SensorType> registeredSensors;
    
    private MonitoringCenter() {
        this.observers = new ArrayList<>();
        this.sensors = new HashMap<>();
        this.registeredSensors = new HashSet<>();
        this.strategy = new StrictStrategy();
        
        for (SensorType type : SensorType.values()) {
            sensors.put(type, new ArrayList<>());
        }
    }
    
    public static MonitoringCenter getInstance() {
        if (instance == null) {
            synchronized (MonitoringCenter.class) {
                if (instance == null) {
                    instance = new MonitoringCenter();
                }
            }
        }
        return instance;
    }
    
    public void setStrategy(AnalysisStrategy strategy) {
        if (strategy != null) {
            this.strategy = strategy;
            System.out.println("Estrategia cambiada a: " + strategy.getName());
        }
    }
    
    public AnalysisStrategy getStrategy() {
        return strategy;
    }
    
    public void subscribe(MonitoringObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observador suscrito: " + observer.getModuleName());
        }
    }
    
    public void unsubscribe(MonitoringObserver observer) {
        if (observer != null && observers.remove(observer)) {
            System.out.println("Observador desuscrito: " + observer.getModuleName());
        }
    }
    
    public void notifyAll(SensorReading reading, AnalysisResult result) {
        for (MonitoringObserver observer : observers) {
            observer.onReading(reading, result);
        }
    }
    
    public boolean registerSensor(SensorType type) {
        if (type == null) {
            return false;
        }
        
        if (registeredSensors.contains(type)) {
            System.out.println("El sensor ya está registrado: " + type);
            return false;
        }
        
        registeredSensors.add(type);
        System.out.println("Sensor registrado: " + type.getDisplayName());
        return true;
    }
    
    public boolean hasSensor(SensorType type) {
        return type != null && registeredSensors.contains(type);
    }
    
    public AnalysisResult addReading(SensorReading reading) {
        if (reading == null) {
            return null;
        }
        
        SensorType type = reading.getType();
        sensors.get(type).add(reading);
        
        AnalysisResult result = strategy.analyze(reading);
        
        notifyAll(reading, result);
        
        return result;
    }
    
    public Map<SensorType, List<SensorReading>> getSensors() {
        return Collections.unmodifiableMap(sensors);
    }
    
    public int getReadingCount(SensorType type) {
        if (type == null) {
            return 0;
        }
        return sensors.getOrDefault(type, Collections.emptyList()).size();
    }
    
    public void listSensors() {
        System.out.println("\n=== SENSORES REGISTRADOS ===");
        if (registeredSensors.isEmpty()) {
            System.out.println("No hay sensores registrados.");
            return;
        }
        
        for (SensorType type : registeredSensors) {
            int count = getReadingCount(type);
            System.out.printf("- %s (%s): %d lecturas%n", 
                type.getDisplayName(), 
                type.getUnit(), 
                count);
        }
    }
    
    public List<MonitoringObserver> getObservers() {
        return Collections.unmodifiableList(observers);
    }
}
