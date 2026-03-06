# DIAGRAMA DE CASOS DE USO

Sistema de Monitoreo de Sensores

Patrones de Diseño: Observer · Strategy · Singleton

---

## 1. Vista General del Sistema

```
                    +--------------------------------------+
                    |   SISTEMA DE MONITOREO DE SENSORES   |
                    |                                      |
 Operador ----------> (Registrar Sensor)                   |
                    |                                      |
 Operador ----------> (Ingresar Lectura)                   |
                    |                |                     |
                    |                v                     |
                    |          (Analizar Lectura)          |
                    |                |                     |
 Supervisor --------> (Cambiar Estrategia)                 |
                    |                |                     |
                    |                v                     |
                    |       (Notificar Observadores)       |
                    |          /       |       \           |
                    |         v        v        v          |
                    |     (Alarma) (Pantalla) (Log)        |
                    |                                      |
 Auditor -----------> (Ver Historial de Log)               |
                    +--------------------------------------+
```

---

## 2. Actores

- Operador
- Supervisor
- Auditor

---

## 3. Casos de Uso

- Registrar Sensor
- Ingresar Lectura
- Analizar Lectura
- Cambiar Estrategia de Análisis
- Notificar Observadores
- Ver Historial de Log
