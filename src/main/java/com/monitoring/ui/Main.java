package com.monitoring.ui;

import com.monitoring.core.MonitoringCenter;
import com.monitoring.model.SensorType;
import com.monitoring.model.SensorReading;
import com.monitoring.observer.AlarmModule;
import com.monitoring.observer.DisplayPanel;
import com.monitoring.observer.LogModule;
import com.monitoring.strategy.AnalysisStrategy;
import com.monitoring.strategy.StrictStrategy;
import com.monitoring.strategy.NormalStrategy;
import com.monitoring.strategy.RelaxedStrategy;

import java.util.Scanner;

public class Main {
    private static MonitoringCenter center;
    private static Scanner scanner;

    public static void main(String[] args) {
        center = MonitoringCenter.getInstance();
        scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("  SISTEMA DE MONITOREO DE SENSORES     ");
        System.out.println("========================================");
        
        initializeObservers();
        
        int option;
        do {
            showMenu();
            option = readOption();
            executeOption(option);
        } while (option != 0);
        
        scanner.close();
        System.out.println("Sistema finalizado.");
    }

    private static void initializeObservers() {
        System.out.println("\nInicializando modulos observadores...");
        center.subscribe(new AlarmModule());
        center.subscribe(new DisplayPanel());
        center.subscribe(new LogModule());
    }

    private static void showMenu() {
        System.out.println("\n========================================");
        System.out.println("           MENU PRINCIPAL               ");
        System.out.println("========================================");
        System.out.println("1. Registrar sensor");
        System.out.println("2. Ingresar lectura");
        System.out.println("3. Cambiar modo de analisis");
        System.out.println("4. Ver sensores registrados");
        System.out.println("0. Salir");
        System.out.println("========================================");
        System.out.print("Seleccione una opcion: ");
    }

    private static int readOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void executeOption(int option) {
        switch (option) {
            case 1:
                registerSensor();
                break;
            case 2:
                addReading();
                break;
            case 3:
                changeStrategy();
                break;
            case 4:
                listSensors();
                break;
            case 0:
                break;
            default:
                System.out.println("Opcion invalida.");
        }
    }

    private static void registerSensor() {
        System.out.println("\n=== REGISTRAR SENSOR ===");
        System.out.println("1. Temperatura");
        System.out.println("2. Humo");
        System.out.println("3. Presion");
        System.out.print("Seleccione tipo de sensor: ");
        
        int typeOption = readOption();
        if (typeOption < 1 || typeOption > 3) {
            System.out.println("Opcion invalida.");
            return;
        }

        SensorType type = null;
        switch (typeOption) {
            case 1:
                type = SensorType.TEMPERATURE;
                break;
            case 2:
                type = SensorType.SMOKE;
                break;
            case 3:
                type = SensorType.PRESSURE;
                break;
        }

        if (center.registerSensor(type)) {
            System.out.println("Sensor registrado exitosamente.");
        }
    }

    private static void addReading() {
        System.out.println("\n=== INGRESAR LECTURA ===");
        System.out.println("1. Temperatura");
        System.out.println("2. Humo");
        System.out.println("3. Presion");
        System.out.print("Seleccione tipo de sensor: ");
        
        int typeOption = readOption();
        if (typeOption < 1 || typeOption > 3) {
            System.out.println("Opcion invalida.");
            return;
        }

        SensorType type = null;
        switch (typeOption) {
            case 1:
                type = SensorType.TEMPERATURE;
                break;
            case 2:
                type = SensorType.SMOKE;
                break;
            case 3:
                type = SensorType.PRESSURE;
                break;
        }

        if (!center.hasSensor(type)) {
            System.out.println("El sensor no esta registrado. Registrelo primero.");
            return;
        }

        System.out.print("Ingrese el valor de la lectura: ");
        try {
            double value = Double.parseDouble(scanner.nextLine());
            SensorReading reading = new SensorReading(type, value);
            center.addReading(reading);
        } catch (NumberFormatException e) {
            System.out.println("Valor invalido.");
        }
    }

    private static void changeStrategy() {
        System.out.println("\n=== CAMBIAR MODO DE ANALISIS ===");
        System.out.println("Estrategia actual: " + center.getStrategy().getName());
        System.out.println("1. Estricto (umbral bajo)");
        System.out.println("2. Normal (umbral medio)");
        System.out.println("3. Relajado (umbral alto)");
        System.out.print("Seleccione estrategia: ");
        
        int strategyOption = readOption();
        if (strategyOption < 1 || strategyOption > 3) {
            System.out.println("Opcion invalida.");
            return;
        }

        AnalysisStrategy strategy = null;
        switch (strategyOption) {
            case 1:
                strategy = new StrictStrategy();
                break;
            case 2:
                strategy = new NormalStrategy();
                break;
            case 3:
                strategy = new RelaxedStrategy();
                break;
        }

        center.setStrategy(strategy);
        System.out.println("Estrategia cambiada exitosamente.");
    }

    private static void listSensors() {
        center.listSensors();
    }
}

