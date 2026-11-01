# Escuela Politécnica Nacional
## Facultad de Ingeniería de Sistemas
### Metodologías Ágiles de Desarrollo de Software (ISWD622)

---

# TALLER PRÁCTICO: PLANIFICACIÓN DE RELEASE, ITERACIONES Y ESTIMACIONES XP (20 PUNTOS)
## Proyecto: AlerFire — Plataforma de Detección Temprana y Despacho Operativo Bomberil

* **Docente:** Prof. Julio Sandobalín, Ph.D.
* **Grupo:** Grupo 03 (GR2SW)
* **Integrantes:**
  * Martín Alejandro Dávalos Cedeño
  * Nayia Cóndor
  * Anthony Minayo
  * Adayely Rochina
  * María Fernanda Alquinga
* **Repositorio Oficial:** [GR03_1BT3_622_26B (GitHub)](https://github.com/prem-avalox/GR03_1BT3_622_26B)
* **Roles Canónicos del Dominio (`Usuario`):** `Ciudadano` y `Bombero`
* **Período Académico:** 2026-B
* **Fecha:** Octubre de 2026

---

## 1. Planificación del Release 1 (6 Puntos)

### 1.1 Justificación del Alcance Implementado
La planificación del **Release 1** de AlerFire se construye estrictamente a partir de la funcionalidad ya construida, testeada mediante pruebas automatizadas JUnit 4 y operativa en el repositorio oficial. El sistema cubre desde la captura georreferenciada de conatos de incendio forestal por parte de ciudadanos y bomberos, hasta el panel de comando bomberil con despacho asistido de cuarteles, cálculo algorítmico de tiempos de arribo (ETA) y liquidación de emergencias con liberación automática de recursos.

### 1.2 Épica Principal (Epic)
* **Código de Épica:** `EP-01`
* **Título:** Gestión Integral de Alerta Temprana, Detección Cartográfica y Despacho Operativo Bomberil.
* **Objetivo Comercial (Valor de Negocio):** Reducir el tiempo de respuesta interinstitucional ante conatos de incendios forestales en el Distrito Metropolitano de Quito mediante una plataforma centralizada que integra la recepción georreferenciada de reportes de ciudadanos y bomberos, el monitoreo geoespacial interactivo y el despacho coordinado de cuarteles de bomberos con cálculo dinámico de tiempos de arribo.
* **Declaración de Valor:** La mitigación temprana del fuego evita la propagación descontrolada en zonas de alta vulnerabilidad ecológica (laderas del Pichincha, cerro Ilaló, parque Metropolitano), protegiendo vidas humanas, infraestructura urbana y patrimonio ambiental.

### 1.3 Descomposición en Features por Iteración
Para asegurar una entrega incremental y continua de valor de acuerdo con Extreme Programming (XP), la Épica `EP-01` se descompone en 2 Features comerciales distribuidos equitativamente en 2 iteraciones de desarrollo:

1. **Feature FT-01 (Iteración 1): Detección Cartográfica Temprana de Focos de Calor**
   * *Objetivo Comercial:* Habilitar el canal digital de detección y monitoreo en vivo para que el ciudadano registre eventos con coordenadas geográficas precisas, visualizables de forma inmediata por el bombero en una consola interactiva de mando cartográfico.
   * *Historias que lo componen:* `HU-I1-01`, `HU-I1-02`, `HU-I1-03`.
   * *Esfuerzo Planificado:* 20 Horas Ideales (40 Horas Reales de Jornada).

2. **Feature FT-02 (Iteración 2): Comando Bomberil, Despacho Asistido y Liquidación**
   * *Objetivo Comercial:* Automatizar el protocolo de despacho táctico de cuarteles según proximidad geoespacial, cálculo algorítmico de tiempos de arribo (ETA) y liquidación definitiva del incidente por parte del bombero con liberación automática de estaciones para futuros eventos.
   * *Historias que lo componen:* `HU-I2-01`, `HU-I2-02`, `HU-I2-03`.
   * *Esfuerzo Planificado:* 20 Horas Ideales (40 Horas Reales de Jornada).

---

## 2. Planificación de Iteraciones y Capacidad del Equipo (7 Puntos)

### 2.1 Negociación y Cuadrícula de Capacidad del Equipo (Modelo de Cátedra)
Siguiendo rigurosamente la metodología de cátedra enseñada por el Prof. Sandobalín en la pizarra de clase magistral y respaldada por el **Sílabo Oficial de Metodologías Ágiles (ISWD622)**, la capacidad del equipo se calcula deduciendo las ausencias y ceremonias de la jornada semanal, aplicando el factor de foco efectivo diario:

$$	ext{Días Netos} = 	ext{Días Semana} - 	ext{Ausencias} - 	ext{Ceremonias}$$
$$	ext{Capacidad Individual (HI)} = 	ext{Días Netos} 	imes 	ext{Horas Foco Diario}$$
$$	ext{Capacidad Total del Equipo} = \sum_{i=1}^{5} 	ext{Capacidad Individual}_i$$

* **Parámetros de la Planificación:**
  * **Duración de la Iteración:** 1 semana calendario = 5 días laborables.
  * **Integrantes del Equipo:** 5 desarrolladores (Martín Dávalos, Nayia Cóndor, Anthony Minayo, Adayely Rochina, María Fernanda Alquinga).
  * **Ausencias Previstas:** 0 días.
  * **Dedicación a Ceremonias XP:** 1 día equivalente distribuido en la semana (Stand-up diario, Iteration Planning, Spike Solutions, Pair Programming switches, Retrospectiva y Demostración al Cliente).
  * **Días Netos de Desarrollo Enfocado:** $5 - 0 - 1 = \mathbf{4	ext{ días netos}}$.
  * **Horas Foco Diario (Velocidad Neta de Programación):** **1.0 hora ideal de programación por día** (equivalente a 2.0 horas reales de dedicación presencial en pareja).

#### Tabla Oficial de Capacidad del Equipo (Formato Canónico de Cátedra)

| Integrante del Equipo | Días Sem. | Ausencias | Ceremonias XP | Días Netos | Foco Diario | Capacidad (HI) |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| Martín Alejandro Dávalos Cedeño | 5 | 0 | 1 d (4h) | 4 d | 1.0 h/día | **4.0 HI** |
| Nayia Cóndor | 5 | 0 | 1 d (4h) | 4 d | 1.0 h/día | **4.0 HI** |
| Anthony Minayo | 5 | 0 | 1 d (4h) | 4 d | 1.0 h/día | **4.0 HI** |
| Adayely Rochina | 5 | 0 | 1 d (4h) | 4 d | 1.0 h/día | **4.0 HI** |
| María Fernanda Alquinga | 5 | 0 | 1 d (4h) | 4 d | 1.0 h/día | **4.0 HI** |
| **TOTAL CAPACIDAD POR ITERACIÓN** | **25 d** | **0 d** | **5 d** | **20 d** | **1.0 h/d** | **20.0 HI (40 HR)** |

> [!NOTE] Respaldo Académico en el Sílabo Oficial (EPN FIS ISWD622)
> El sílabo oficial de la asignatura fija exactamente **2 créditos académicos (6 horas semanales)**:
> * 2.0 horas de Aprendizaje en Contacto con el Docente (AC - Clase presencial).
> * 2.0 horas de Aprendizaje Práctico-Experimental (AP - Laboratorio y Taller guiado).
> * 2.0 horas de Aprendizaje Autónomo (AA - Trabajo de programación fuera de clase).  
>
> Los componentes AP (2h) + AA (2h) suman exactamente **4.0 horas de dedicación fuera de clase por estudiante a la semana**. Multiplicado por los 5 integrantes:
> $$5 	imes 4.0	ext{ horas} = \mathbf{20	ext{ Horas Ideales por semana}}$$
> Esto coincide al 100% con los **20 puntos circulados en la pizarra del profesor**, demostrando que la velocidad del equipo no es un invento arbitrario sino una derivación matemática exacta de la carga académica curricular.

### 2.2 Factor de Carga y Relación de Horas en XP
* **Factor de Carga (Load Factor / Drag Factor):** $FC = 0.5$.
* **Relación Matemática:** $	ext{Horas Reales (HR)} = rac{	ext{Horas Ideales (HI)}}{FC} = rac{	ext{HI}}{0.5} = 2 	imes 	ext{HI}$.
* Por tanto, **1 Hora Ideal (HI) = 2 Horas Reales de Jornada (HR)**.
* **Capacidad por Iteración:** 20 Horas Ideales equivalen con total consistencia a **40 Horas Reales** de jornada del equipo (5 días laborables $	imes$ 8 horas estándar de jornada laboral compartida en pair programming).

---

## 3. Planificación Detallada de la Iteración 1 (Semana 1: 20 HI / 40 HR)

* **Objetivo Comercial:** Establecer el canal digital de detección y monitoreo cartográfico en vivo para identificar tempranamente focos de calor y consolidar las alertas en la consola operativa de bomberos.
* **Velocidad Asignada:** 20 Horas Ideales (40 Horas Reales) | 3 Historias de Usuario | 9 Tareas de Ingeniería (BCE).

---

### Historia de Usuario HU-I1-01

| **Title:** HU-I1-01 - Registro georreferenciado de conato de incendio forestal | **Priority:** Alta | **Estimate:** 7 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como ciudadano,*<br/>*quiero registrar un nuevo conato de incendio forestal con sus coordenadas geográficas,*<br/>*para alertar oportunamente al cuerpo de bomberos sobre el foco de calor.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Registro con coordenada válida):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano selecciona una coordenada válida dentro del mapa de Quito,<br/>&nbsp;&nbsp;**Cuando** envía el formulario con la descripción del conato,<br/>&nbsp;&nbsp;**Entonces** el sistema persiste el reporte en estado `REPORTADO` y genera un identificador numérico de seguimiento.<br/><br/>**• Escenario 2 (Validación de campos obligatorios):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano accede al formulario de reporte,<br/>&nbsp;&nbsp;**Cuando** intenta enviar la solicitud omitiendo la selección de coordenadas en el mapa,<br/>&nbsp;&nbsp;**Entonces** el sistema bloquea el envío y resalta el mapa requiriendo la fijación del punto geográfico.<br/><br/>**• Escenario 3 (Validación de cobertura territorial metropolitana):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano ingresa coordenadas situadas fuera del perímetro metropolitano de Quito,<br/>&nbsp;&nbsp;**Cuando** confirma el envío del reporte,<br/>&nbsp;&nbsp;**Entonces** el sistema rechaza la transacción y notifica que el evento excede la cobertura territorial. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T1.1.1` `«boundary»`: Diseñar formulario web en JSP (`reportar-incendio.jsp`) con selector interactivo de coordenadas sobre mapa Leaflet y campos descriptivos. [2 HI / 4 HR]
* `T1.1.2` `«control»`: Implementar controlador `ReporteServlet` para validar campos obligatorios, sanitizar coordenadas lat/lon y procesar la petición HTTP POST. [3 HI / 6 HR]
* `T1.1.3` `«entity»`: Programar mapeo JPA/Hibernate en la entidad `Incendio` y método transaccional de guardado en `IncendioDAO`. [2 HI / 4 HR]
* **Validación de Estimación:** $2 + 3 + 2 = \mathbf{7	ext{ HI}}$ ($14	ext{ HR}$). Coincide 100% con la Story Card.

---

### Historia de Usuario HU-I1-02

| **Title:** HU-I1-02 - Visualización geoespacial de focos activos en mapa interactivo | **Priority:** Alta | **Estimate:** 7 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como bombero,*<br/>*quiero visualizar los conatos de incendio activos en un mapa interactivo georreferenciado,*<br/>*para analizar visualmente la distribución espacial de las emergencias forestales.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Carga y renderizado geoespacial inicial):**<br/>&nbsp;&nbsp;**Dado que** existen conatos en estado `REPORTADO` en la base de datos,<br/>&nbsp;&nbsp;**Cuando** el bombero abre el visor cartográfico principal,<br/>&nbsp;&nbsp;**Entonces** el sistema dibuja marcadores georreferenciados en cada coordenada sobre el mapa Leaflet.<br/><br/>**• Escenario 2 (Inspección de detalles en popup interactivo):**<br/>&nbsp;&nbsp;**Dado que** el bombero visualiza los marcadores en el mapa,<br/>&nbsp;&nbsp;**Cuando** hace clic sobre un marcador específico de conato,<br/>&nbsp;&nbsp;**Entonces** el sistema despliega un popup informativo con la fecha/hora, descripción y estado operativo.<br/><br/>**• Escenario 3 (Actualización dinámica de focos reportados):**<br/>&nbsp;&nbsp;**Dado que** un nuevo conato es ingresado al sistema,<br/>&nbsp;&nbsp;**Cuando** el bombero refresca la visualización cartográfica,<br/>&nbsp;&nbsp;**Entonces** el mapa renderiza inmediatamente el nuevo marcador en sus coordenadas exactas. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T1.2.1` `«boundary»`: Construir visor cartográfico en JSP (`mapa.jsp`) integrando la librería Leaflet sobre OpenStreetMap y capas de calor. [2 HI / 4 HR]
* `T1.2.2` `«control»`: Desarrollar endpoint API REST en `MapaServlet` que serialice la colección de incendios activos en formato GeoJSON. [3 HI / 6 HR]
* `T1.2.3` `«entity»`: Construir consulta JPQL optimizada en `IncendioDAO` para recuperar conatos no liquidados con coordenadas válidas. [2 HI / 4 HR]
* **Validación de Estimación:** $2 + 3 + 2 = \mathbf{7	ext{ HI}}$ ($14	ext{ HR}$). Coincide 100% con la Story Card.

---

### Historia de Usuario HU-I1-03

| **Title:** HU-I1-03 - Monitoreo operativo y cronológico de emergencias en panel de control | **Priority:** Media | **Estimate:** 6 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como bombero,*<br/>*quiero consultar la lista cronológica de incidentes con sus estados actuales,*<br/>*para priorizar la toma de decisiones sobre las emergencias sin atender.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Visualización cronológica y badge de estado):**<br/>&nbsp;&nbsp;**Dado que** existen reportes registrados en la base de datos,<br/>&nbsp;&nbsp;**Cuando** el bombero accede a la consola de despacho,<br/>&nbsp;&nbsp;**Entonces** el sistema presenta la tabla de incidentes ordenada descendentemente por hora con insignias cromáticas de estado.<br/><br/>**• Escenario 2 (Búsqueda y filtrado dinámico en tabla):**<br/>&nbsp;&nbsp;**Dado que** la lista contiene múltiples incidentes registrados,<br/>&nbsp;&nbsp;**Cuando** el bombero ingresa un criterio en la barra de búsqueda,<br/>&nbsp;&nbsp;**Entonces** la tabla actualiza los registros en tiempo real mostrando únicamente las filas coincidentes.<br/><br/>**• Escenario 3 (Presentación ante lista vacía de incidentes):**<br/>&nbsp;&nbsp;**Dado que** no existen conatos registrados en el sistema,<br/>&nbsp;&nbsp;**Cuando** el bombero carga el panel operativo,<br/>&nbsp;&nbsp;**Entonces** el sistema muestra un mensaje informativo indicando la ausencia de eventos pendientes de atención. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T1.3.1` `«boundary»`: Maquetar panel de control responsivo en JSP (`actualizar-estado.jsp`) con tabla estilizada, insignias cromáticas por estado y buscador en vivo. [2 HI / 4 HR]
* `T1.3.2` `«control»`: Implementar controlador `IncendioServlet` para gestionar ordenamiento cronológico y filtros de estado. [2 HI / 4 HR]
* `T1.3.3` `«entity»`: Programar consultas de agregación y conteo por estados en `IncendioDAO` mediante consultas JPA/HQL. [2 HI / 4 HR]
* **Validación de Estimación:** $2 + 2 + 2 = \mathbf{6	ext{ HI}}$ ($12	ext{ HR}$). Coincide 100% con la Story Card.

---

## 4. Planificación Detallada de la Iteración 2 (Semana 2: 20 HI / 40 HR)

* **Objetivo Comercial:** Automatizar el despacho táctico y la asignación asistida de cuarteles de bomberos según proximidad geográfica, proveyendo estimación de tiempo de arribo y cierre de ciclo operativo.
* **Velocidad Asignada:** 20 Horas Ideales (40 Horas Reales) | 3 Historias de Usuario | 9 Tareas de Ingeniería (BCE).

---

### Historia de Usuario HU-I2-01

| **Title:** HU-I2-01 - Despacho de estación de bomberos disponible a un conato activo | **Priority:** Alta | **Estimate:** 7 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como bombero,*<br/>*quiero despachar una estación de bomberos libre hacia un foco de calor activo,*<br/>*para movilizar oportunamente el contingente de respuesta contra el fuego.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Despacho exitoso de cuartel disponible):**<br/>&nbsp;&nbsp;**Dado que** un conato se encuentra en estado `REPORTADO` y existe al menos una estación en estado `LIBRE`,<br/>&nbsp;&nbsp;**Cuando** el bombero confirma la asignación de la estación al conato,<br/>&nbsp;&nbsp;**Entonces** el sistema transiciona el conato a estado `ASIGNADO` y la estación a estado `OCUPADA`.<br/><br/>**• Escenario 2 (Prevención de doble despacho sobre estación ocupada):**<br/>&nbsp;&nbsp;**Dado que** una estación se encuentra en estado `OCUPADA` atendiendo un incidente,<br/>&nbsp;&nbsp;**Cuando** el bombero intenta despacharla a un segundo foco de calor,<br/>&nbsp;&nbsp;**Entonces** el sistema bloquea la asignación y muestra una alerta indicando que la unidad carece de disponibilidad operativa.<br/><br/>**• Escenario 3 (Registro de auditoría temporal del despacho):**<br/>&nbsp;&nbsp;**Dado que** el despacho se ejecuta exitosamente,<br/>&nbsp;&nbsp;**Cuando** la transacción se completa en la base de datos,<br/>&nbsp;&nbsp;**Entonces** el sistema almacena la marca temporal exacta (`timestamp`) de asignación y el identificador de la estación. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T2.1.1` `«boundary»`: Desarrollar modal interactivo en JSP (`actualizar-estado.jsp`) con listado desplegable de cuarteles y botón de confirmación de despacho. [2 HI / 4 HR]
* `T2.1.2` `«control»`: Implementar lógica transaccional en `GestorDespacho` / `IncendioServlet` que verifique disponibilidad, gestione bloqueos y orqueste el cambio de estado. [3 HI / 6 HR]
* `T2.1.3` `«entity»`: Programar métodos atómicos en `EstacionDAO` e `IncendioDAO` para mutar simultáneamente los estados en una única transacción de base de datos. [2 HI / 4 HR]
* **Validación de Estimación:** $2 + 3 + 2 = \mathbf{7	ext{ HI}}$ ($14	ext{ HR}$). Coincide 100% con la Story Card.

---

### Historia de Usuario HU-I2-02

| **Title:** HU-I2-02 - Liquidación de conato y liberación automática de recursos | **Priority:** Alta | **Estimate:** 6 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como bombero,*<br/>*quiero registrar la liquidación total de un conato de incendio bajo control,*<br/>*para reincorporar la estación asignada al estado de disponibilidad operativa.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Liquidación exitosa y liberación del cuartel):**<br/>&nbsp;&nbsp;**Dado que** un conato se encuentra en estado `ASIGNADO` con una estación bomberil despachada,<br/>&nbsp;&nbsp;**Cuando** el bombero confirma la extinción total en el formulario de cierre,<br/>&nbsp;&nbsp;**Entonces** el sistema transiciona el conato a estado `CONTROLADO` y libera automáticamente la estación a estado `LIBRE`.<br/><br/>**• Escenario 2 (Restricción de liquidación sobre conato no asignado):**<br/>&nbsp;&nbsp;**Dado que** un conato se encuentra en estado `REPORTADO` sin estación asignada,<br/>&nbsp;&nbsp;**Cuando** el bombero intenta liquidar el evento directamente,<br/>&nbsp;&nbsp;**Entonces** el sistema rechaza la operación advirtiendo que un incendio no atendido no puede liquidarse sin despacho previo.<br/><br/>**• Escenario 3 (Cierre de bitácora y reporte final):**<br/>&nbsp;&nbsp;**Dado que** la liquidación se registra exitosamente,<br/>&nbsp;&nbsp;**Cuando** el sistema procesa el cierre del evento,<br/>&nbsp;&nbsp;**Entonces** se almacena la fecha/hora de finalización y se genera el resumen de la intervención en la bitácora operativa. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T2.2.1` `«boundary»`: Diseñar vista de liquidación y cierre operativo (`actualizar-estado.jsp`) con resumen de tiempos y confirmación de extinción. [2 HI / 4 HR]
* `T2.2.2` `«control»`: Implementar método `finalizarAtencion` en `GestorDespacho` para validar la precondición de asignación y coordinar la liberación de la estación. [2 HI / 4 HR]
* `T2.2.3` `«entity»`: Desarrollar método de cierre operativo en `IncendioDAO` y método de liberación de cuartel en `EstacionDAO`. [2 HI / 4 HR]
* **Validación de Estimación:** $2 + 2 + 2 = \mathbf{6	ext{ HI}}$ ($12	ext{ HR}$). Coincide 100% con la Story Card.

---

### Historia de Usuario HU-I2-03

| **Title:** HU-I2-03 - Estimación y cálculo de Tiempo Estimado de Arribo (ETA) | **Priority:** Media | **Estimate:** 7 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como bombero,*<br/>*quiero calcular el tiempo estimado de arribo de la unidad de socorro al lugar del siniestro,*<br/>*para comunicar el tiempo de espera a la ciudadanía y coordinar refuerzos.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Cálculo preciso por proximidad euclidiana/geodésica):**<br/>&nbsp;&nbsp;**Dado que** un conato posee coordenadas geográficas válidas y se selecciona una estación con ubicación conocida,<br/>&nbsp;&nbsp;**Cuando** el bombero ejecuta la opción de cálculo de ruta,<br/>&nbsp;&nbsp;**Entonces** el sistema proyecta la distancia geodésica y despliega el tiempo estimado de llegada en minutos.<br/><br/>**• Escenario 2 (Alerta visual por tiempo de arribo crítico):**<br/>&nbsp;&nbsp;**Dado que** el cálculo de ETA arroja una estimación superior a 25 minutos debido a distancia crítica,<br/>&nbsp;&nbsp;**Cuando** el resultado se presenta en pantalla,<br/>&nbsp;&nbsp;**Entonces** el sistema resalta una alerta visual en rojo sugiriendo el despacho de apoyo helitransportado o refuerzo adicional.<br/><br/>**• Escenario 3 (Manejo de excepción ante coordenadas geográficas ausentes):**<br/>&nbsp;&nbsp;**Dado que** un reporte carece de coordenadas geográficas válidas por error de captura,<br/>&nbsp;&nbsp;**Cuando** se intenta invocar el cálculo de tiempo de arribo,<br/>&nbsp;&nbsp;**Entonces** el sistema captura la excepción, inhabilita el botón de cálculo y solicita la calibración de ubicación. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T2.3.1` `«boundary»`: Integrar indicador dinámico de tiempo de arribo (ETA) en el visor del mapa Leaflet y en la ficha del incidente. [2 HI / 4 HR]
* `T2.3.2` `«control»`: Desarrollar algoritmo de cálculo de distancia geodésica (fórmula de Haversine) y estimación de velocidad promedio en `GestorDespacho`. [3 HI / 6 HR]
* `T2.3.3` `«entity»`: Modelar atributos de distancia estimada y minutos de arribo en las entidades de dominio y actualizar consultas HQL/JPQL. [2 HI / 4 HR]
* **Validación de Estimación:** $2 + 3 + 2 = \mathbf{7	ext{ HI}}$ ($14	ext{ HR}$). Coincide 100% con la Story Card.

---

## 5. Tablas Resumen de Planificación y Estimaciones del Release 1 (7 Puntos)

Las siguientes tablas resumen respetan con absoluta fidelidad el formato oficial de la cátedra de User Stories del Prof. Sandobalín:

### 5.1 Tabla Resumen Oficial: Iteración 1

#### Iteración 1

| No. | Título | Estimación |
| :---: | :--- | :---: |
| 1 | Registro georreferenciado de conato de incendio forestal | 7 horas |
| 2 | Visualización geoespacial de focos activos en mapa interactivo | 7 horas |
| 3 | Monitoreo operativo y cronológico de emergencias en panel de control | 6 horas |
| | **Estimado** | **20 horas** |
| | **Real** | **40 horas** |

* **Total Horas Ideales Planificadas (Estimado):** 20 horas.
* **Total Horas Reales de Jornada (Real, $FC = 0.5$):** 40 horas.
* **Cumplimiento de Capacidad:** 100.0%.

---

### 5.2 Tabla Resumen Oficial: Iteración 2

#### Iteración 2

| No. | Título | Estimación |
| :---: | :--- | :---: |
| 1 | Despacho de estación de bomberos disponible a un conato activo | 7 horas |
| 2 | Liquidación de conato y liberación automática de recursos | 6 horas |
| 3 | Estimación y cálculo de Tiempo Estimado de Arribo (ETA) | 7 horas |
| | **Estimado** | **20 horas** |
| | **Real** | **40 horas** |

* **Total Horas Ideales Planificadas (Estimado):** 20 horas.
* **Total Horas Reales de Jornada (Real, $FC = 0.5$):** 40 horas.
* **Cumplimiento de Capacidad:** 100.0%.

---

### 5.3 Tabla Consolidada de Tareas Técnicas (BCE) del Release 1

| Iteración / Story ID | Título de la Historia de Usuario | Prioridad | Est. Story (HI) | Tarea «Boundary» (JSP/UI) | Tarea «Control» (Servlet) | Tarea «Entity» (JPA/DAO) | Total Story |
| :---: | :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **HU-I1-01** | Registro conato georreferenciado | Alta | 7 HI | T1.1.1 (2 HI / 4 HR) | T1.1.2 (3 HI / 6 HR) | T1.1.3 (2 HI / 4 HR) | **7 HI (14 HR)** |
| **HU-I1-02** | Visualización en mapa interactivo | Alta | 7 HI | T1.2.1 (2 HI / 4 HR) | T1.2.2 (3 HI / 6 HR) | T1.2.3 (2 HI / 4 HR) | **7 HI (14 HR)** |
| **HU-I1-03** | Monitoreo operativo en consola | Media | 6 HI | T1.3.1 (2 HI / 4 HR) | T1.3.2 (2 HI / 4 HR) | T1.3.3 (2 HI / 4 HR) | **6 HI (12 HR)** |
| **SUBTOTAL I-1** | **Iteración 1 (3 Historias, 9 Tareas)** | **-** | **20 HI** | **6 HI (12 HR)** | **8 HI (16 HR)** | **6 HI (12 HR)** | **20 HI (40 HR)** |
| **HU-I2-01** | Despacho de estación disponible | Alta | 7 HI | T2.1.1 (2 HI / 4 HR) | T2.1.2 (3 HI / 6 HR) | T2.1.3 (2 HI / 4 HR) | **7 HI (14 HR)** |
| **HU-I2-02** | Liquidación y liberación de cuartel | Alta | 6 HI | T2.2.1 (2 HI / 4 HR) | T2.2.2 (2 HI / 4 HR) | T2.2.3 (2 HI / 4 HR) | **6 HI (12 HR)** |
| **HU-I2-03** | Cálculo de Tiempo de Arribo (ETA) | Media | 7 HI | T2.3.1 (2 HI / 4 HR) | T2.3.2 (3 HI / 6 HR) | T2.3.3 (2 HI / 4 HR) | **7 HI (14 HR)** |
| **SUBTOTAL I-2** | **Iteración 2 (3 Historias, 9 Tareas)** | **-** | **20 HI** | **6 HI (12 HR)** | **8 HI (16 HR)** | **6 HI (12 HR)** | **20 HI (40 HR)** |
| **TOTAL RELEASE 1** | **Release 1 Completo (6 HU, 18 Tareas)** | **-** | **40 HI** | **12 HI (24 HR)** | **16 HI (32 HR)** | **12 HI (24 HR)** | **40 HI (80 HR)** |

---

## 6. Refinamiento de Deuda Técnica y Product Backlog (Hacia el Release 2)

Durante la auditoría del código fuente y las pruebas de aceptación con ciudadanos y bomberos se identificaron los siguientes aspectos técnicos y funcionales a refinarse para los siguientes incrementos:

### 6.1 Deuda Técnica Identificada
1. **Filtrado Exclusivo de Estaciones Libres en Interfaz Web:**  
   * *Diagnóstico:* En el modal de despacho actual (`actualizar-estado.jsp`), el elemento `<select>` carga todas las estaciones registradas en la base de datos sin filtrar por estado `LIBRE`. Si el bombero selecciona inadvertidamente un cuartel ocupado, la validación en el backend (`GestorDespacho`) rechaza la petición arrojando un error.
   * *Solución Técnica:* Refactorizar la consulta JPQL en `EstacionDAO` para alimentar la vista únicamente con cuarteles en estado `LIBRE`, bloqueando errores desde el nivel de presentación.
2. **Historial Cartográfico de Conatos Liquidados:**  
   * *Diagnóstico:* Los conatos extintos se retiran del visor activo de Leaflet para optimizar el rendimiento. Sin embargo, el bombero requiere auditar los incendios pasados.
   * *Solución Técnica:* Habilitar una capa cartográfica conmutador en Leaflet (*Layer Control*) que consulte eventos con estado `CONTROLADO` bajo demanda.
3. **Mecanismo de Despacho en Tiempo Real:**  
   * *Diagnóstico:* La consola actual depende de refresco manual o sondeo HTTP (polling) periódico.
   * *Solución Técnica:* Migrar la infraestructura hacia WebSockets o notificaciones push SMS para alerta inmediata a bomberos en terreno agreste.

### 6.2 Historias Refinadas para el Release 2 (Candidatas)
* `HU-R2-01`: Calificación de veracidad del conato y bonificación de reputación cívica (+50 pts) (Prioridad: Alta | Est: 6 HI / 12 HR).
* `HU-R2-02`: Penalización por falsa alarma (-100 pts) y suspensión automática del ciudadano a la 3ª reincidencia (Prioridad: Alta | Est: 7 HI / 14 HR).
* `HU-R2-03`: Carga de evidencia fotográfica georreferenciada del conato (Prioridad: Media | Est: 7 HI / 14 HR).
* `HU-R2-04`: Consulta de zonas seguras y cálculo de rutas de evacuación preventiva (Prioridad: Alta | Est: 7 HI / 14 HR).
