# DIAGRAMA DE CLASES COMPLETO

## Sistema de Monitoreo de Sensores
**Patrones de Diseño:** Observer · Strategy · Singleton  
**Lenguaje:** Java 21  
**Paquete base:** com.monitoring

---

## 1. Vista General del Sistema

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        SISTEMA DE MONITOREO DE SENSORES                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐                   │
│  │   MODEL     │    │  STRATEGY   │    │  OBSERVER   │                   │
│  │             │    │             │    │             │                   │
│  │ SensorType  │    │  Analysis   │    │  Monitoring │                   │
│  │ SensorRead  │◄───│  Strategy   │───►│  Observer   │                   │
│  │ AnalysisRes │    │ Strict/     │    │ AlarmModule │                   │
│  │             │    │ Relaxed/    │    │ DisplayPanel│                   │
│  └─────────────┘    │ Adaptive    │    │ LogModule   │                   │
│                     └─────────────┘    └──────┬──────┘                   │
│                            │                  │                           │
│                            ▼                  │                           │
│                     ┌─────────────┐          │                           │
│                     │   CORE      │◄─────────┘                           │
│                     │             │                                      │
│                     │ Monitoring  │───────────┐                           │
│                     │   Center    │           │                           │
│                     │ (Singleton) │           ▼                           │
│                     └─────────────┘    ┌─────────────┐                   │
│                                        │    UI      │                   │
│                                        │            │                   │
│                                        │ConsoleMenu │                   │
│                                        └─────────────┘                   │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 2. Paquete: com.monitoring.model

### 2.1 SensorType (enum)
```
┌────────────────────────────────────────┐
│           <<enumeration>>             │
│            SensorType                  │
├────────────────────────────────────────┤
│ TEMPERATURA                            │
│ HUMO                                   │
│ PRESION                                │
├────────────────────────────────────────┤
│ - displayName: String                  │
│ - unit: String                         │
├────────────────────────────────────────┤
│ + fromInput(String): SensorType        │
│ + getDisplayName(): String             │
│ + getUnit(): String                    │
│ + values(): SensorType[]               │
└────────────────────────────────────────┘
```

**Descripción:** Enum que representa los tipos de sensores soportados.

---

### 2.2 SensorReading (final class)
```
┌────────────────────────────────────────┐
│           <<record>>                   │
│          SensorReading                 │
├────────────────────────────────────────┤
│ - type: SensorType                     │
│ - value: double                        │
│ - timestamp: LocalDateTime            │
├────────────────────────────────────────┤
│ + getType(): SensorType                │
│ + getValue(): double                   │
│ + getTimestamp(): LocalDateTime       │
│ + toString(): String                   │
└────────────────────────────────────────┘
```

**Descripción:** Clase inmutable que representa una lectura de sensor con su timestamp.

---

### 2.3 AnalysisResult (final class)
```
┌────────────────────────────────────────┐
│           <<record>>                   │
│          AnalysisResult                │
├────────────────────────────────────────┤
│ - critical: boolean                    │
│ - message: String                      │
│ - strategyName: String                │
├────────────────────────────────────────┤
│ + isCritical(): boolean                │
│ + getMessage(): String                 │
│ + getStrategyName(): String           │
│ + toString(): String                   │
└────────────────────────────────────────┘
```

**Descripción:** Resultado del análisis de una lectura.

---

## 3. Paquete: com.monitoring.strategy

### 3.1 AnalysisStrategy (interface)
```
┌────────────────────────────────────────┐
│           <<interface>>               │
│        AnalysisStrategy               │
├────────────────────────────────────────┤
├────────────────────────────────────────┤
│ + analyze(SensorReading): AnalysisResult │
│ + getName(): String                    │
└────────────────────────────────────────┘
        △
        │ implements
        │
┌───────┴────────┬─────────────────┬────────────────┐
│                │                 │                │
▼                ▼                 ▼                ▼
┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────────┐
│ Strict   │  │ Relaxed  │  │Adaptive  │  │ (futuras)    │
│Strategy  │  │Strategy  │  │Strategy  │  │              │
└──────────┘  └──────────┘  └──────────┘  └──────────────┘
```

---

### 3.2 StrictStrategy (class)
```
┌────────────────────────────────────────┐
│           StrictStrategy              │
├────────────────────────────────────────┤
│ (sin atributos - no mantiene estado)  │
├────────────────────────────────────────┤
│ + analyze(SensorReading): AnalysisResult │
│ + getName(): String                    │
├────────────────────────────────────────┤
│ UMBRALES:                              │
│ • Temperatura: 60 °C                   │
│ • Humo: 30 %                          │
│ • Presión: 100 kPa                    │
└────────────────────────────────────────┘
```

---

### 3.3 RelaxedStrategy (class)
```
┌────────────────────────────────────────┐
│           RelaxedStrategy             │
├────────────────────────────────────────┤
│ (sin atributos - no mantiene estado)  │
├────────────────────────────────────────┤
│ + analyze(SensorReading): AnalysisResult │
│ + getName(): String                    │
├────────────────────────────────────────┤
│ UMBRALES:                              │
│ • Temperatura: 120 °C                  │
│ • Humo: 70 %                          │
│ • Presión: 200 kPa                    │
└────────────────────────────────────────┘
```

---

### 3.4 AdaptiveStrategy (class)
```
┌────────────────────────────────────────┐
│          AdaptiveStrategy             │
├────────────────────────────────────────┤
│ - history: Map<SensorType, List<Double>> │
├────────────────────────────────────────┤
│ + analyze(SensorReading): AnalysisResult │
│ + getName(): String                    │
│ + getHistory(): Map                    │
├────────────────────────────────────────┤
│ ALGORITMO:                             │
│ • Mantiene historial por tipo         │
│ • Calcula promedio histórico          │
│ • Crítico si desviación > 40%         │
└────────────────────────────────────────┘
```

---

## 4. Paquete: com.monitoring.observer

### 4.1 MonitoringObserver (interface)
```
┌────────────────────────────────────────┐
│        <<interface>>                  │
│      MonitoringObserver               │
├────────────────────────────────────────┤
├────────────────────────────────────────┤
│ + onReading(SensorReading, AnalysisResult): void │
│ + getModuleName(): String              │
└────────────────────────────────────────┘
        △
        │ implements
        │
┌───────┴────────┬─────────────────┬────────────────┐
│                │                 │                │
▼                ▼                 ▼                ▼
┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐
│ Alarm    │  │Display   │  │  Log     │  │ (futuros)│
│ Module   │  │ Panel    │  │ Module   │  │          │
└──────────┘  └──────────┘  └──────────┘  └──────────┘
```

---

### 4.2 AlarmModule (class)
```
┌────────────────────────────────────────┐
│             AlarmModule                │
├────────────────────────────────────────┤
│ - alarmCount: int = 0                 │
├────────────────────────────────────────┤
│ + onReading(SensorReading, AnalysisResult): void │
│ + getModuleName(): String              │
├────────────────────────────────────────┤
│ CONDICIÓN: Solo cuando isCritical()    │
│ ACCIÓN: Imprime alerta numerada        │
│ Ejemplo: "⚠️ ALARMA #1: Temperatura    │
│          CRÍTICO - 85°C"              │
└────────────────────────────────────────┘
```

---

### 4.3 DisplayPanel (class)
```
┌────────────────────────────────────────┐
│            DisplayPanel                │
├────────────────────────────────────────┤
│ (sin atributos de estado)             │
├────────────────────────────────────────┤
│ + onReading(SensorReading, AnalysisResult): void │
│ + getModuleName(): String              │
├────────────────────────────────────────┤
│ CONDICIÓN: Siempre (toda lectura)      │
│ ACCIÓN: Muestra estado con icono      │
│ Ejemplo: "[CRITICO] Temp: 85°C        │
│          [OK] Humo: 25%"              │
└────────────────────────────────────────┘
```

---

### 4.4 LogModule (class)
```
┌────────────────────────────────────────┐
│              LogModule                 │
├────────────────────────────────────────┤
│ - logs: List<String> = new ArrayList<>() │
├────────────────────────────────────────┤
│ + onReading(SensorReading, AnalysisResult): void │
│ + getModuleName(): String              │
│ + getLogs(): List<String>              │
│ + printHistory(): void                 │
├────────────────────────────────────────┤
│ CONDICIÓN: Siempre (toda lectura)      │
│ ACCIÓN: Registra con timestamp         │
│ Formato: "2024-01-15 14:30:25 |       │
│          TEMPERATURA | 85°C |          │
│          ESTRICTA | CRÍTICO"           │
└────────────────────────────────────────┘
```

---

## 5. Paquete: com.monitoring.core

### 5.1 MonitoringCenter (Singleton + Subject + Context)
```
┌─────────────────────────────────────────────────────────────────┐
│                    MonitoringCenter                             │
│                 (Singleton + Subject + Context)                │
├─────────────────────────────────────────────────────────────────┤
│ - instance: MonitoringCenter                           [static] │
│ - strategy: AnalysisStrategy                                    │
│ - observers: List<MonitoringObserver>                          │
│ - sensors: Map<SensorType, List<SensorReading>>                │
├─────────────────────────────────────────────────────────────────┤
│ // Singleton                                                   │
│ - MonitoringCenter()                                          │
│ + getInstance(): MonitoringCenter                     [static] │
│                                                                 │
│ // Strategy - Context                                          │
│ + setStrategy(AnalysisStrategy): void                          │
│ + getStrategy(): AnalysisStrategy                              │
│                                                                 │
│ // Observer - Subject                                          │
│ + subscribe(MonitoringObserver): void                          │
│ + unsubscribe(MonitoringObserver): void                        │
│ + notifyAll(SensorReading, AnalysisResult): void               │
│                                                                 │
│ // Gestión de sensores                                         │
│ + registerSensor(SensorType): boolean                          │
│ + addReading(SensorReading): AnalysisResult                    │
│ + getSensors(): Map<SensorType, List<SensorReading>>           │
│ + hasSensor(SensorType): boolean                               │
└─────────────────────────────────────────────────────────────────┘
```

**Patrones aplicados:**
- **Singleton:** Una sola instancia en toda la aplicación
- **Subject (Observer):** Gestiona la suscripción y notificación de observadores
- **Context (Strategy):** Delega el análisis a la estrategia activa

---

## 6. Paquete: com.monitoring.ui

### 6.1 ConsoleMenu (class)
```
┌────────────────────────────────────────┐
│            ConsoleMenu                │
├────────────────────────────────────────┤
│ - scanner: Scanner                     │
├────────────────────────────────────────┤
│ + showMainMenu(): void                 │
│ + registerSensor(): void              │
│ + enterReading(): void                 │
│ + changeStrategy(): void               │
│ + showLogs(): void                     │
│ + showSensors(): void                  │
│ + run(): void                          │
│ + showDemo(): void                     │
└────────────────────────────────────────┘
```

---

## 7. Punto de Entrada

### 7.1 Main.java
```
┌────────────────────────────────────────┐
│               Main                     │
├────────────────────────────────────────┤
│ + main(String[] args): void           │
│   └── Inicializa:                     │
│       1. Obtiene MonitoringCenter      │
│       2. Crea observers               │
│       3. Suscribe observers           │
│       4. Inicia ConsoleMenu           │
└────────────────────────────────────────┘
```

---

## 8. Relaciones Entre Clases

```
                    ┌──────────────────────────────────────────────────────┐
                    │                  MonitoringCenter                    │
                    │                  (Singleton)                        │
                    ├──────────────────────────────────────────────────────┤
                    │  - strategy: AnalysisStrategy  ◄── 1:1 ──► [Strategy] │
                    │  - observers: List<Observer>  ◄── 1:N ──► [Observer] │
                    │  - sensors: Map<Type, List>   ◄── 1:N ──► [Model]    │
                    └─────────────────────┬────────────────────────────────┘
                                            │
                                            │ uses
                                            ▼
        ┌───────────────────────────────────────────────────────────────┐
        │                      RELACIONES DE DEPENDENCIA                 │
        ├───────────────┬──────────────┬───────────────┬────────────────┤
        │   DESDE       │  RELACIÓN    │     HASTA     │ CARDINALIDAD   │
        ├───────────────┼──────────────┼───────────────┼────────────────┤
        │MonitoringCenter│ usa         │AnalysisStrategy│     1:1        │
        │MonitoringCenter│ agrega      │Observer        │     1:N        │
        │MonitoringCenter│almacena     │SensorReading   │   1:N/tipo     │
        │StrictStrategy  │implementa   │AnalysisStrategy│      -         │
        │RelaxedStrategy │implementa   │AnalysisStrategy│      -         │
        │AdaptiveStrategy│implementa   │AnalysisStrategy│      -         │
        │AlarmModule     │implementa   │Observer        │      -         │
        │DisplayPanel    │implementa   │Observer        │      -         │
        │LogModule       │implementa   │Observer        │      -         │
        │Strategy        │depende      │SensorReading   │      -         │
        │Strategy        │depende      │AnalysisResult  │      -         │
        │ConsoleMenu     │usa          │MonitoringCenter│      -         │
        └───────────────┴──────────────┴───────────────┴────────────────┘
```

---

## 9. Interacción de Patrones (Flujo de una Lectura)

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     FLUJO DE EJECUCIÓN                                  │
└─────────────────────────────────────────────────────────────────────────┘

   1. Operador                                                     
      │                                                              
      ▼                                                              
   ┌───────────────────────┐                                         
   │    ConsoleMenu       │                                         
   │  (UI - entrada)      │                                         
   └───────────┬───────────┘                                         
               │                                                     
               │ getInstance()                                      
               ▼                                                     
   ┌───────────────────────┐                                         
   │  MonitoringCenter    │◄────────────────────────┐               
   │   (Singleton)        │                         │               
   └───────────┬───────────┘                         │               
               │                                    │               
               │ analyze(reading)                   │               
               ▼                                    │               
   ┌───────────────────────┐     implements        │               
   │ AnalysisStrategy     │◄───────────────────────┤               
   │ (Interface)          │                         │               
   └───────────┬───────────┘                         │               
               │                                    │               
    ┌──────────┼──────────┐                         │               
    ▼          ▼          ▼                         │               
 ┌──────┐ ┌──────┐ ┌───────────┐                   │               
 │Strict│ │Relax │ │ Adaptive  │                   │               
 │Strat.│ │Strat.│ │  Strat.  │                   │               
 └──┬───┘ └──┬───┘ └─────┬─────┘                   │               
    │        │          │                           │               
    └────────┴──────────┴───────────────────────────┘               
               │                                    │               
               ▼                                    │               
   ┌───────────────────────┐     notifyAll         │               
   │  AnalysisResult      │────────────────────────┘               
   │  (critical, message) │                                         
   └───────────┬───────────┘                                         
               │                                                     
               │ notifyAll(reading, result)                          
               ▼                                                     
   ┌───────────────────────┐                                         
   │   Lista de Observers  │◄──────────┐                            
   └───────────┬───────────┘           │                            
               │                       │                            
     ┌─────────┼─────────┐             │                            
     ▼         ▼         ▼             │                            
 ┌────────┐┌───────┐┌────────┐       │                            
 │ Alarm  ││Display││  Log   │       │                            
 │ Module ││ Panel ││ Module │       │                            
 └───┬────┘└───┬───┘└───┬────┘       │                            
     │         │         │             │                            
     │         │         │             │                            
     ▼         ▼         ▼             │                            
   ╔═════════════════════════════╗    │                            
   ║  onReading(reading, result)║────┘                            
   ╚═════════════════════════════╝                                
```

---

## 10. Justificación de Patrones

### 10.1 Singleton — MonitoringCenter

| Aspecto | Detalle |
|---------|---------|
| **Problema resuelto** | Un centro de monitoreo físico tiene un único estado global: sensores registrados, estrategia activa e historial de lecturas. Si existieran varias instancias, cada módulo podría ver un estado distinto, generando inconsistencias críticas. |
| **Implementación** | Constructor privado + método getInstance() sincronizado. La instancia se crea la primera vez que se solicita (inicialización diferida). |
| **Beneficio** | Toda la aplicación comparte exactamente la misma lista de sensores, estrategia y observadores. Es el punto único de control exigido por el enunciado. |
| **Por qué tiene sentido** | Un hospital tiene un solo panel de control de pacientes. Un centro de control aéreo tiene una sola torre. El Singleton modela esta restricción del dominio real. |

---

### 10.2 Strategy — AnalysisStrategy

| Estrategia | Umbral Temp. | Umbral Humo | Umbral Presión | Algoritmo |
|------------|--------------|-------------|----------------|-----------|
| **Estricta** | 60 °C | 30 % | 100 kPa | Umbral fijo bajo |
| **Relajada** | 120 °C | 70 % | 200 kPa | Umbral fijo alto |
| **Adaptativa** | Dinámico | Dinámico | Dinámico | Desviación > 40% del promedio histórico |

**Justificación:** Las reglas de evaluación cambian según el contexto operacional. Encapsular cada política en una clase separada permite cambiar el comportamiento en runtime sin modificar MonitoringCenter ni los observadores, cumpliendo el Principio Abierto/Cerrado (OCP).

---

### 10.3 Observer — MonitoringObserver

| Módulo | Condición de activación | Acción |
|--------|------------------------|--------|
| **AlarmModule** | Solo cuando isCritical() == true | Imprime alerta numerada con nombre del sensor y valor |
| **DisplayPanel** | Siempre (toda lectura) | Muestra estado [OK] o [CRITICO] con valor y unidad |
| **LogModule** | Siempre (toda lectura) | Registra timestamp, sensor, valor, estrategia y resultado |

**Justificación:** Los módulos deben reaccionar a lecturas sin que MonitoringCenter los conocía directamente. La suscripción dinámica permite agregar nuevos módulos (notificación por email, SMS, dashboard web) sin modificar el núcleo del sistema.

---

## 11. Principios de Diseño Aplicados

| Principio | Aplicación |
|-----------|------------|
| **SRP** (Responsabilidad Única) | Cada clase tiene una sola razón para cambiar. ConsoleMenu solo gestiona entrada/salida. MonitoringCenter solo coordina. Cada estrategia solo define su algoritmo. |
| **OCP** (Abierto/Cerrado) | El sistema es abierto a extensión (nueva estrategia = nueva clase) y cerrado a modificación (no se toca MonitoringCenter ni los observadores existentes). |
| **LSP** (Sustitución de Liskov) | Cualquier implementación de AnalysisStrategy o MonitoringObserver puede sustituir a la interfaz sin romper el comportamiento del sistema. |
| **DIP** (Inversión de Dependencias) | MonitoringCenter depende de las interfaces AnalysisStrategy y MonitoringObserver, no de implementaciones concretas. |

---

## 12. Estructura de Archivos

```
src/main/java/com/monitoring/
├── Main.java                          ← Punto de entrada
├── core/
│   └── MonitoringCenter.java          ← Singleton + Subject + Context
├── model/
│   ├── SensorType.java                ← Enum de tipos
│   ├── SensorReading.java             ← Valor inmutable
│   └── AnalysisResult.java            ← Resultado del análisis
├── strategy/
│   ├── AnalysisStrategy.java          ← Interfaz Strategy
│   ├── StrictStrategy.java            ← Implementación estricta
│   ├── RelaxedStrategy.java           ← Implementación relajada
│   └── AdaptiveStrategy.java          ← Implementación adaptativa
├── observer/
│   ├── MonitoringObserver.java        ← Interfaz Observer
│   ├── AlarmModule.java               ← Módulo de alarma
│   ├── DisplayPanel.java              ← Panel de visualización
│   └── LogModule.java                 ← Módulo de log
└── ui/
    └── ConsoleMenu.java              ← Menú interactivo de consola
```

---

## 13. Demo de Patrones (Evidencia en Consola)

| Patrón | Evidencia en consola |
|--------|---------------------|
| **Singleton** | Se crean c1 y c2 con getInstance(). Se muestra que c1 == c2 (true) → misma referencia. |
| **Strategy** | El valor 75 de temperatura se evalúa primero con Estricta (CRÍTICO, umbral 60) y luego con Relajada (OK, umbral 120). Mismo dato, distinto resultado. |
| **Observer** | Una lectura crítica de humo (95%) activa los tres módulos simultáneamente: ALARMA imprime la alerta, PANTALLA muestra [CRÍTICO], LOG registra la entrada. |

---

*Documento generado basado en "Sensor monitoring docs.pdf" - Sistema de Monitoreo de Sensores*
