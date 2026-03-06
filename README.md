# SMS-sensor-monitoring-system

## Patrones Obligatorios
>- Observer 
>- Strategy 
>- Singleton

---

## Contexto
> Un centro de monitoreo recibe valores de sensores de temperatura, humo o presión. Dependiendo del modo de análisis, la interpretación de la lectura cambia, y cuando ocurre un evento crítico se debe notificar a los módulos suscritos.

---

## Requerimiento Funcional:
>- Registrar sensores.
>- Ingresar lecturas.
>- Cambiar modo de análisis.
>- Notificar alarmas y paneles de visualización.

---

## Patrones a evidenciar

### Observer: 
> módulos como alarma, pantalla o log reaccionan a cambios.

### Strategy:
> distintas reglas de análisis de lectura.

### Singleton: 
> gestor central de monitoreo.

---

> [!CAUTION]
> **Evidencia de consola:** 
Menú para registrar lecturas
Cambio dinámico del criterio de evaluación
Notificación de eventos críticos
Evidencia esperada
Una misma lectura puede evaluarse distinto según la estrategia
Varios observadores reciben la notificación
El sistema tiene un punto único de control

---

## Integrantes:
- Cristofer David Lozano Contreras.
- Jhampier Santos Ortiz.
- Keneth Santiago Rubiano Silva.
- Maria de los angeles Olaya Garcia.