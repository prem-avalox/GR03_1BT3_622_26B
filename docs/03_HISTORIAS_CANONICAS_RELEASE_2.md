# Escuela Politécnica Nacional
## Facultad de Ingeniería de Sistemas
### Metodologías Ágiles de Desarrollo de Software (ISWD622) — Período 2026-B

---

# CATÁLOGO CANÓNICO DE HISTORIAS DE USUARIO — RELEASE 2
## Gobernanza Cívica (Práctica 2 TDD) y Protección Geoespacial / Evacuación (Tarea 3)

* **Docente:** Prof. Julio Sandobalín, Ph.D.
* **Grupo:** Grupo 03 (GR2SW)
* **Integrantes:** Martín Dávalos, Nayia Cóndor, Anthony Minayo, Adayely Rochina, María Fernanda Alquinga
* **Formato Oficial:** Ficha Story Card de Cátedra (Encabezado `#C4E5F5`, Narrativa en Singular Estricto, 3 Escenarios Gherkin en Singular Estricto y Tareas Técnicas BCE ubicadas inmediatamente después de cada tarjeta).
* **Roles Canónicos Empleados:** Exclusivamente `Ciudadano` y `Bombero`.

---

## 1. Estructura del Release 2 en el User Story Mapping

El **Release 2** formaliza bajo el estándar de Extreme Programming (XP) los módulos implementados en la **Práctica 2 (Reputación y Confiabilidad Cívica)** y en la **Tarea 3 (Zonas Seguras, Rutas de Evacuación y Evidencia Fotográfica)**, junto con la resolución de deuda técnica del selector de despacho:

* **Iteración 1 del Release 2 (Gobernanza Cívica y Evidencia — 20 HI / 40 HR):**
  * `HU-R2-01`: Calificación de veracidad de conato y bonificación de reputación (`+50` pts). *(6 HI)*
  * `HU-R2-02`: Penalización por falsa alarma (`-100` pts) y suspensión de cuenta ciudadana. *(7 HI)*
  * `HU-R2-03`: Carga de evidencia fotográfica georreferenciada del conato. *(5 HI)*
* **Iteración 2 del Release 2 (Autoprotección Civil, Evacuación y Selector Reactivo — 20 HI / 40 HR):**
  * `HU-R2-04`: Consulta cartográfica de zonas seguras y puntos de encuentro activos. *(7 HI)*
  * `HU-R2-05`: Cálculo de ruta de evacuación con evaluación de nivel de riesgo térmico. *(7 HI)*
  * `HU-R2-06`: Filtrado reactivo de estaciones disponibles en selector de despacho. *(4 HI)*

---

## 2. Especificación Canónica — Iteración 1 del Release 2 (20 HI / 40 HR)

### Historia de Usuario HU-R2-01 (Práctica 2 — Reputación Positiva y Confiabilidad)

> **Especificación de Color de Cabecera Oficial:** Celeste `#C4E5F5` (`rgb(196, 229, 245)`).

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#C4E5F5; font-weight:bold;">
    <td style="border:1px solid #000000; padding:6px;">Title: HU-R2-01 - Calificación de veracidad de conato y bonificación de reputación</td>
    <td style="border:1px solid #000000; padding:6px;">Priority: Alta</td>
    <td style="border:1px solid #000000; padding:6px;">Estimate: 6 horas</td>
  </tr>
</table>

| **Title:** HU-R2-01 - Calificación de veracidad de conato y bonificación de reputación | **Priority:** Alta | **Estimate:** 6 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como bombero,*<br/>*quiero calificar como verídico el reporte de incendio emitido por un ciudadano,*<br/>*para incrementar su puntaje de reputación cívica dentro de la plataforma.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Bonificación de 50 puntos por reporte verídico):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano posee una cuenta activa con 100 puntos de reputación base,<br/>&nbsp;&nbsp;**Cuando** el bombero confirma la veracidad del conato reportado en terreno,<br/>&nbsp;&nbsp;**Entonces** el sistema adiciona 50 puntos al perfil del ciudadano alcanzando un total de 150 puntos.<br/><br/>**• Escenario 2 (Ascenso automático al nivel de confiabilidad ORO):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano registra un acumulado de 260 puntos de reputación,<br/>&nbsp;&nbsp;**Cuando** el bombero valida un nuevo reporte verídico sumando 50 puntos adicionales,<br/>&nbsp;&nbsp;**Entonces** el sistema actualiza su puntaje a 310 puntos y promueve su rango al nivel `ORO`.<br/><br/>**• Escenario 3 (Rechazo de calificación sobre ciudadano suspendido):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano se encuentra en estado `SUSPENDIDO` dentro del padrón,<br/>&nbsp;&nbsp;**Cuando** el bombero intenta procesar una bonificación sobre dicho perfil,<br/>&nbsp;&nbsp;**Entonces** el sistema lanza una excepción de negocio y conserva inalterado el puntaje. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T3.1.1` `«boundary»`: Incorporar botón de acción *"Confirmar Veracidad (+50 pts)"* e insignia de nivel de confiabilidad (`BRONCE`, `PLATA`, `ORO`) en la consola `actualizar-estado.jsp`. [2 HI / 4 HR]
* `T3.1.2` `«control»`: Implementar métodos `evaluarVeracidad` y `determinarNivelConfiabilidad` en `GestorReputacion.java` validados con suite TDD en `GestorReputacionTest.java`. [2 HI / 4 HR]
* `T3.1.3` `«entity»`: Persistir la actualización del atributo `puntosReputacion` de la entidad `Ciudadano.java` mediante `UsuarioDAO.java` / `ICiudadanoDAO.java`. [2 HI / 4 HR]
* **Validación de Estimación:** $2 + 2 + 2 = \mathbf{6\text{ HI}}$ ($12\text{ HR}$). Coincide 100% con la Story Card.

---

### Historia de Usuario HU-R2-02 (Práctica 2 — Penalización por Falsa Alarma y Suspensión)

> **Especificación de Color de Cabecera Oficial:** Celeste `#C4E5F5` (`rgb(196, 229, 245)`).

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#C4E5F5; font-weight:bold;">
    <td style="border:1px solid #000000; padding:6px;">Title: HU-R2-02 - Penalización por falsa alarma y suspensión de cuenta ciudadana</td>
    <td style="border:1px solid #000000; padding:6px;">Priority: Alta</td>
    <td style="border:1px solid #000000; padding:6px;">Estimate: 7 horas</td>
  </tr>
</table>

| **Title:** HU-R2-02 - Penalización por falsa alarma y suspensión de cuenta ciudadana | **Priority:** Alta | **Estimate:** 7 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como bombero,*<br/>*quiero sancionar el reporte clasificado como falsa alarma,*<br/>*para suspender al ciudadano reincidente que compromete la disponibilidad operativa.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Descuento de 100 puntos por primera falsa alarma):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano cuenta con 150 puntos de reputación y 0 reportes falsos previos,<br/>&nbsp;&nbsp;**Cuando** el bombero dictamina que el conato reportado constituye una falsa alarma,<br/>&nbsp;&nbsp;**Entonces** el sistema descuenta 100 puntos dejando el saldo en 50 e incrementa el contador de reportes falsos a 1.<br/><br/>**• Escenario 2 (Cota mínima de cero puntos sin valores negativos):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano dispone únicamente de 40 puntos de reputación vigentes,<br/>&nbsp;&nbsp;**Cuando** el bombero registra la penalización de 100 puntos por falsa alarma,<br/>&nbsp;&nbsp;**Entonces** el sistema trunca el puntaje exactamente en 0 puntos evitando saldos negativos.<br/><br/>**• Escenario 3 (Suspensión automática al alcanzar la tercera reincidencia):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano acumula previamente 2 reportes falsos registrados en su historial,<br/>&nbsp;&nbsp;**Cuando** el bombero sanciona una tercera falsa alarma consecutiva,<br/>&nbsp;&nbsp;**Entonces** el sistema incrementa el contador a 3 y cambia el estado del ciudadano a `SUSPENDIDO`. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T3.2.1` `«boundary»`: Añadir botón *"Reportar Falsa Alarma (-100 pts)"* y alerta visual de bloqueo para cuentas suspendidas en `actualizar-estado.jsp` y `reportar-incendio.jsp`. [2 HI / 4 HR]
* `T3.2.2` `«control»`: Programar regla de descuento con piso en cero y umbral `LIMITE_FALSAS_ALARMAS = 3` en `GestorReputacion.java` con pruebas parametrizadas en `GestorReputacionParametersTest.java`. [3 HI / 6 HR]
* `T3.2.3` `«entity»`: Actualizar columnas `puntos_reputacion`, `reportes_falsos` y `suspendido` en la tabla `ciudadanos` mediante `Ciudadano.java` y `UsuarioDAO.java`. [2 HI / 4 HR]
* **Validación de Estimación:** $2 + 3 + 2 = \mathbf{7\text{ HI}}$ ($14\text{ HR}$). Coincide 100% con la Story Card.

---

### Historia de Usuario HU-R2-03 (Tarea 1 / Tarea 3 — Evidencia Fotográfica del Conato)

> **Especificación de Color de Cabecera Oficial:** Celeste `#C4E5F5` (`rgb(196, 229, 245)`).

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#C4E5F5; font-weight:bold;">
    <td style="border:1px solid #000000; padding:6px;">Title: HU-R2-03 - Carga de evidencia fotográfica georreferenciada del conato</td>
    <td style="border:1px solid #000000; padding:6px;">Priority: Media</td>
    <td style="border:1px solid #000000; padding:6px;">Estimate: 7 horas</td>
  </tr>
</table>

| **Title:** HU-R2-03 - Carga de evidencia fotográfica georreferenciada del conato | **Priority:** Media | **Estimate:** 7 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como ciudadano,*<br/>*quiero adjuntar una fotografía del foco de humo al formulario de alerta,*<br/>*para respaldar visualmente la existencia real del conato ante el cuerpo de bomberos.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Asociación exitosa de archivo de imagen válido):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano completa los datos de ubicación en el formulario de reporte,<br/>&nbsp;&nbsp;**Cuando** selecciona un archivo de imagen con extensión `.jpg` o `.png` y envía la alerta,<br/>&nbsp;&nbsp;**Entonces** el sistema almacena la evidencia vinculada al conato con tipo `FOTO` y marca temporal actual.<br/><br/>**• Escenario 2 (Rechazo de archivo con formato no permitido):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano intenta adjuntar un documento en el campo de evidencia,<br/>&nbsp;&nbsp;**Cuando** carga un archivo cuya extensión no corresponde a una imagen válida,<br/>&nbsp;&nbsp;**Entonces** el método de validación rechaza el adjunto y solicita un formato gráfico compatible.<br/><br/>**• Escenario 3 (Visualización de la fotografía en la consola bomberil):**<br/>&nbsp;&nbsp;**Dado que** un conato registrado posee una evidencia fotográfica almacenada,<br/>&nbsp;&nbsp;**Cuando** el bombero inspecciona el detalle de la emergencia en el panel,<br/>&nbsp;&nbsp;**Entonces** el sistema despliega la miniatura de la fotografía junto a las coordenadas del siniestro. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T3.3.1` `«boundary»`: Incorporar control `input type="file"` con soporte `multipart/form-data` en `reportar-incendio.jsp` y previsualización en `mapa.jsp`. [2 HI / 4 HR]
* `T3.3.2` `«control»`: Procesar el flujo binario de subida y validación de extensión en `ReporteServlet.java` y `GestorReportes.java`. [3 HI / 6 HR]
* `T3.3.3` `«entity»`: Mapear la entidad `Evidencia.java` (`@Table(name="evidencias")`), enumeración `TipoEvidencia.java` y método `validar()` con relación `@ManyToOne` hacia `Incendio.java`. [2 HI / 4 HR]
* **Validación de Estimación:** $2 + 3 + 2 = \mathbf{7\text{ HI}}$ ($14\text{ HR}$). Coincide 100% con la Story Card.

---

## 3. Especificación Canónica — Iteración 2 del Release 2 (20 HI / 40 HR)

### Historia de Usuario HU-R2-04 (Tarea 3 — Zonas Seguras y Puntos de Encuentro)

> **Especificación de Color de Cabecera Oficial:** Celeste `#C4E5F5` (`rgb(196, 229, 245)`).

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#C4E5F5; font-weight:bold;">
    <td style="border:1px solid #000000; padding:6px;">Title: HU-R2-04 - Consulta cartográfica de zonas seguras y puntos de encuentro</td>
    <td style="border:1px solid #000000; padding:6px;">Priority: Alta</td>
    <td style="border:1px solid #000000; padding:6px;">Estimate: 7 horas</td>
  </tr>
</table>

| **Title:** HU-R2-04 - Consulta cartográfica de zonas seguras y puntos de encuentro | **Priority:** Alta | **Estimate:** 7 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como ciudadano,*<br/>*quiero consultar la ubicación de las zonas seguras habilitadas en el mapa,*<br/>*para identificar el punto de refugio más cercano ante un incendio forestal.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Despliegue de zonas seguras con capacidad y tipología):**<br/>&nbsp;&nbsp;**Dado que** existen zonas seguras registradas en la base de datos metropolitana,<br/>&nbsp;&nbsp;**Cuando** el ciudadano accede al módulo de evacuación (`/evacuacion`),<br/>&nbsp;&nbsp;**Entonces** el sistema proyecta en el mapa cada zona segura mostrando su nombre, tipo y capacidad máxima de aforo.<br/><br/>**• Escenario 2 (Diferenciación iconográfica frente a focos de incendio):**<br/>&nbsp;&nbsp;**Dado que** el visor cartográfico presenta simultáneamente conatos activos y puntos de refugio,<br/>&nbsp;&nbsp;**Cuando** el ciudadano examina las capas del mapa interactivo,<br/>&nbsp;&nbsp;**Entonces** el sistema distingue visualmente las zonas seguras mediante marcadores verdes de resguardo.<br/><br/>**• Escenario 3 (Inspección detallada de zona segura al clic):**<br/>&nbsp;&nbsp;**Dado que** los marcadores de refugio están renderizados sobre el mapa Leaflet,<br/>&nbsp;&nbsp;**Cuando** el ciudadano hace clic sobre una zona segura específica,<br/>&nbsp;&nbsp;**Entonces** el sistema abre una tarjeta informativa con la dirección exacta y las coordenadas del albergue. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T4.1.1` `«boundary»`: Diseñar vista interactiva de resguardo en `rutas-evacuacion.jsp` y `mapa.jsp` con tarjetas informativas de aforo y marcadores verdes en Leaflet. [2 HI / 4 HR]
* `T4.1.2` `«control»`: Implementar carga de puntos de refugio en `EvacuacionServlet.java` y `GestorMapa.java` para inyectar la colección en la vista. [3 HI / 6 HR]
* `T4.1.3` `«entity»`: Programar entidad JPA `ZonaSegura.java` (`@Table(name="zonas_seguras")`) y consultas de recuperación en `ZonaSeguraDAO.java`. [2 HI / 4 HR]
* **Validación de Estimación:** $2 + 3 + 2 = \mathbf{7\text{ HI}}$ ($14\text{ HR}$). Coincide 100% con la Story Card.

---

### Historia de Usuario HU-R2-05 (Tarea 3 — Cálculo de Ruta de Evacuación y Nivel de Riesgo)

> **Especificación de Color de Cabecera Oficial:** Celeste `#C4E5F5` (`rgb(196, 229, 245)`).

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#C4E5F5; font-weight:bold;">
    <td style="border:1px solid #000000; padding:6px;">Title: HU-R2-05 - Cálculo de ruta de evacuación con evaluación de nivel de riesgo</td>
    <td style="border:1px solid #000000; padding:6px;">Priority: Alta</td>
    <td style="border:1px solid #000000; padding:6px;">Estimate: 7 horas</td>
  </tr>
</table>

| **Title:** HU-R2-05 - Cálculo de ruta de evacuación con evaluación de nivel de riesgo | **Priority:** Alta | **Estimate:** 7 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como ciudadano,*<br/>*quiero calcular la ruta de evacuación ordenada según su índice de peligro térmico,*<br/>*para desplazarme hacia una zona segura evitando la proximidad de los focos activos.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Ordenamiento de rutas alternativas por menor nivel de riesgo):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano ingresa su coordenada de origen en el buscador de evacuación,<br/>&nbsp;&nbsp;**Cuando** solicita el cálculo de rutas hacia las zonas seguras cercanas,<br/>&nbsp;&nbsp;**Entonces** el sistema evalúa la cercanía de los incendios activos y presenta las rutas ordenadas de menor a mayor riesgo.<br/><br/>**• Escenario 2 (Incremento de riesgo por foco activo a menos de 1 kilómetro):**<br/>&nbsp;&nbsp;**Dado que** una ruta de evacuación pasa a una distancia inferior a 1.0 km de un incendio activo,<br/>&nbsp;&nbsp;**Cuando** el calculador algorítmico ejecuta la ponderación de peligrosidad,<br/>&nbsp;&nbsp;**Entonces** el sistema adiciona una penalización de +6.0 puntos al índice de riesgo de dicho trayecto.<br/><br/>**• Escenario 3 (Trazado poligonal de la ruta segura en el mapa):**<br/>&nbsp;&nbsp;**Dado que** el sistema determina la ruta recomendada con menor puntaje de riesgo,<br/>&nbsp;&nbsp;**Cuando** el ciudadano selecciona la ruta en el listado de resultados,<br/>&nbsp;&nbsp;**Entonces** el visor cartográfico dibuja la polilínea JSON del trayecto desde el origen hasta la zona segura destino. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T4.2.1` `«boundary»`: Construir interfaz de solicitud de ruta y renderizado de polilíneas GeoJSON con indicadores de riesgo (escala `0.5` a `10.0`) en `rutas-evacuacion.jsp`. [2 HI / 4 HR]
* `T4.2.2` `«control»`: Implementar `GestorEvacuacion.java` y `CalculadorRiesgoEvacuacion.java` (`evaluarRiesgo`, `calcularIncrementoPorDistancia`) junto a `EvacuacionServlet.java`. [3 HI / 6 HR]
* `T4.2.3` `«entity»`: Modelar entidad `RutaEvacuacion.java` (`@Table(name="rutas_evacuacion")`, `calcularDistanciaMinima`, `trazadoJson`) y persistencia en `RutaEvacuacionDAO.java`. [2 HI / 4 HR]
* **Validación de Estimación:** $2 + 3 + 2 = \mathbf{7\text{ HI}}$ ($14\text{ HR}$). Coincide 100% con la Story Card.

---

### Historia de Usuario HU-R2-06 (Refinamiento de Deuda Técnica — Selector de Estaciones Libres)

> **Especificación de Color de Cabecera Oficial:** Celeste `#C4E5F5` (`rgb(196, 229, 245)`).

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#C4E5F5; font-weight:bold;">
    <td style="border:1px solid #000000; padding:6px;">Title: HU-R2-06 - Filtrado reactivo de estaciones disponibles en selector de despacho</td>
    <td style="border:1px solid #000000; padding:6px;">Priority: Media</td>
    <td style="border:1px solid #000000; padding:6px;">Estimate: 6 horas</td>
  </tr>
</table>

| **Title:** HU-R2-06 - Filtrado reactivo de estaciones disponibles en selector de despacho | **Priority:** Media | **Estimate:** 6 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como bombero,*<br/>*quiero visualizar únicamente las estaciones libres como opciones seleccionables en el formulario de despacho,*<br/>*para evitar intentos fallidos de asignación sobre cuarteles ocupados.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Habilitación exclusiva de cuarteles en estado disponible):**<br/>&nbsp;&nbsp;**Dado que** existen estaciones disponibles y estaciones ocupadas en la base de datos,<br/>&nbsp;&nbsp;**Cuando** el bombero despliega el selector de asignación frente a un conato activo,<br/>&nbsp;&nbsp;**Entonces** el sistema habilita únicamente las opciones correspondientes a cuarteles en estado `DISPONIBLE`.<br/><br/>**• Escenario 2 (Bloqueo visual de estación ocupada en el menú desplegable):**<br/>&nbsp;&nbsp;**Dado que** una estación se encuentra asignada atendiendo otra emergencia en curso,<br/>&nbsp;&nbsp;**Cuando** el bombero abre la lista desplegable de cuarteles en la consola,<br/>&nbsp;&nbsp;**Entonces** dicha estación aparece con el atributo `disabled` impidiendo su selección.<br/><br/>**• Escenario 3 (Reactivación inmediata tras liquidación de emergencia):**<br/>&nbsp;&nbsp;**Dado que** el bombero liquida un incendio liberando el cuartel previamente ocupado,<br/>&nbsp;&nbsp;**Cuando** recarga el selector de despacho para atender un nuevo conato,<br/>&nbsp;&nbsp;**Entonces** la estación liberada vuelve a mostrarse como opción seleccionable activa. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T4.3.1` `«boundary»`: Refactorizar el componente `<select name="estacionId">` en `actualizar-estado.jsp` aplicando el atributo condicional `disabled` a cuarteles con `!est.disponible`. [2 HI / 4 HR]
* `T4.3.2` `«control»`: Ajustar el método de preparación de vista en `IncendioServlet.java` para ordenar primero las estaciones disponibles por menor ETA. [2 HI / 4 HR]
* `T4.3.3` `«entity»`: Incorporar método de consulta filtrada `obtenerEstacionesDisponibles()` en `EstacionDAO.java` mediante sentencia HQL `WHERE disponible = true`. [2 HI / 4 HR]
* **Validación de Estimación:** $2 + 2 + 2 = \mathbf{6\text{ HI}}$ ($12\text{ HR}$). Coincide 100% con la Story Card.

---

## 4. Tablas Resumen Oficiales del Release 2 (Formato Diapositiva `#FDE7BB`)

#### Iteración 1 (Release 2)

| No. | Título | Estimación |
| :---: | :--- | :---: |
| 1 | Calificación de veracidad de conato y bonificación de reputación | 6 horas |
| 2 | Penalización por falsa alarma y suspensión de cuenta ciudadana | 7 horas |
| 3 | Carga de evidencia fotográfica georreferenciada del conato | 7 horas |
| | **Estimado** | **20 horas** |
| | **Real** | **40 horas** |

#### Iteración 2 (Release 2)

| No. | Título | Estimación |
| :---: | :--- | :---: |
| 1 | Consulta cartográfica de zonas seguras y puntos de encuentro | 7 horas |
| 2 | Cálculo de ruta de evacuación con evaluación de nivel de riesgo | 7 horas |
| 3 | Filtrado reactivo de estaciones disponibles en selector de despacho | 6 horas |
| | **Estimado** | **20 horas** |
| | **Real** | **40 horas** |
