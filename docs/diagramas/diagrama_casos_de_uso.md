# DIAGRAMA DE CASOS DE USO 

Sistema de Monitoreo de Sensores

Patrones de Diseño: Observer · Strategy · Singleton  
Lenguaje: Java 21  
Sistema: SMS - Sensor Monitoring System

---

## 1. Vista General del Sistema

```
                      +----------------------------------------------+
                      |        SISTEMA DE MONITOREO DE SENSORES      |
                      |                                              |
 Operador -----------> (Registrar Sensor)                           |
                      |                                              |
 Operador -----------> (Ingresar Lectura)                           |
                      |            |                                 |
                      |            v                                 |
                      |       (Validar Lectura)                      |
                      |            |                                 |
                      |            v                                 |
                      |       (Analizar Lectura)                     |
                      |            |                                 |
 Supervisor ---------> (Cambiar Estrategia de Análisis)              |
                      |            |                                 |
                      |            v                                 |
                      |      (Detectar Evento Crítico)               |
                      |            |                                 |
                      |            v                                 |
                      |     (Notificar Observadores)                 |
                      |       /        |          \                   |
                      |      v         v           v                  |
                      |  (Activar   (Mostrar   (Registrar en Log)    |
                      |   Alarma)    Panel)                           |
                      |                                              |
 Auditor ------------> (Consultar Historial de Eventos)              |
                      |                                              |
                      +----------------------------------------------+
```

---

## 2. Actores del Sistema

**Operador**
- Registra sensores
- Ingresa lecturas

**Supervisor**
- Cambia la estrategia de análisis

**Auditor**
- Consulta el historial de eventos

**Módulos Observadores**
- Alarma
- Panel de visualización
- Sistema de log

---

## 3. Casos de Uso

### Gestión de Sensores
- Registrar sensor

### Gestión de Lecturas
- Ingresar lectura
- Validar lectura
- Analizar lectura

### Configuración del Sistema
- Cambiar estrategia de análisis

### Gestión de Eventos
- Detectar evento crítico
- Notificar observadores
- Activar alarma
- Mostrar información en panel
- Registrar evento en log

### Consulta de Información
- Consultar historial de eventos

---

## 4. Patrones de Diseño Evidenciados

**Observer**
- Los módulos **Alarma, Panel y Log** reaccionan cuando ocurre un evento crítico.

**Strategy**
- El sistema permite **cambiar la estrategia de análisis dinámicamente**.

**Singleton**
- El **Monitoring Center** controla todo el sistema desde un único punto.
