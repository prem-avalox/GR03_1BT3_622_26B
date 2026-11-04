# Escuela Politécnica Nacional
## Facultad de Ingeniería de Sistemas
### Metodologías Ágiles de Desarrollo de Software (ISWD622) — Período 2026-B

---

# CATÁLOGO CANÓNICO DE HISTORIAS DE USUARIO — RELEASE 2
## Proyecto: AlerFire — Gobernanza Cívica, Alertas Proactivas y Auditoría Operativa

* **Grupo:** Grupo 03 (GR2SW)
* **Integrantes:** Martín Dávalos, Nayia Cóndor, Anthony Minayo, Adayely Rochina, María Fernanda Alquinga
* **Repositorio Oficial:** [GR03_1BT3_622_26B (GitHub)](https://github.com/prem-avalox/GR03_1BT3_622_26B)
* **Formato Oficial:** Story Cards de Cátedra (Encabezado `#C4E5F5`, 3 Escenarios Gherkin en Singular Estricto y Tareas BCE).
* **Roles Canónicos Empleados:** `Ciudadano` y `Bombero`.

---

## 1. Distribución del Release 2 por Iteraciones (Épica y Features en Formato HU)

### Épica Principal del Release: `EP-02`
* **Título:** Gobernanza Cívica, Alertas Proactivas y Auditoría Operativa
* **Formato Historia de Usuario:**
  * **Como** bombero y ciudadano del Distrito Metropolitano de Quito,
  * **quiero** disponer de un ecosistema integral de reputación ciudadana, alertas comunitarias preventivas y auditoría cartográfica de siniestros,
  * **para** erradicar el desperdicio operativo por falsas alarmas, salvaguardar vidas en sectores vulnerables y fiscalizar las intervenciones realizadas.
* **Esfuerzo:** **40 Horas Ideales (80 Horas Reales)** distribuidas en 2 iteraciones de 1 semana.

### Features del Release en Formato HU:
* **Feature FT-01 (Iteración 1: 20 HI / 40 HR):**
  * *Como* bombero,
  * *quiero* evaluar la veracidad de los reportes para bonificar a ciudadanos responsables y penalizar a infractores recurrentes,
  * *para* garantizar la confiabilidad de las alertas ciudadanas y evitar la movilización inútil de motobombas ante falsas alarmas.
  * *Historias:* `HU-R2-01` (7 HI), `HU-R2-02` (7 HI), `HU-R2-03` (6 HI).
* **Feature FT-02 (Iteración 2: 20 HI / 40 HR):**
  * *Como* bombero y ciudadano,
  * *quiero* recibir notificaciones preventivas de conatos cercanos, despachar únicamente cuarteles libres y conmutar la capa de siniestros extintos,
  * *para* evacuar oportunamente a la comunidad en riesgo, eliminar asignaciones erróneas en consola y auditar las operaciones cerradas.
  * *Historias:* `HU-R2-04` (7 HI), `HU-R2-05` (6 HI), `HU-R2-06` (7 HI).

---

## 2. Historias de Usuario — Iteración 1 del Release 2 (20 HI / 40 HR)

### Historia de Usuario HU-R2-01
* **Pertenece a:** Release 2 — Iteración 1 (Semana 1)
* **Épica:** `EP-02` Gobernanza Cívica, Alertas Proactivas y Auditoría Operativa
* **Feature:** `FT-01` Gobernanza Cívica, Reputación y Control de Infractores

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#C4E5F5; font-weight:bold;">
    <td style="border:1px solid #000000; padding:6px;">Title: HU-R2-01 - Calificación de veracidad de conato y bonificación de reputación</td>
    <td style="border:1px solid #000000; padding:6px;">Priority: Alta</td>
    <td style="border:1px solid #000000; padding:6px;">Estimate: 7 horas</td>
  </tr>
</table>

| **Title:** HU-R2-01 - Calificación de veracidad de conato y bonificación de reputación | **Priority:** Alta | **Estimate:** 7 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como bombero,*<br/>*quiero bonificar con 50 puntos el conato calificado como verídico,*<br/>*para incentivar la emisión de reportes reales por parte de la comunidad.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Bonificación de 50 puntos por reporte verídico confirmado):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano posee una cuenta activa con 100 puntos de reputación base,<br/>&nbsp;&nbsp;**Cuando** el bombero confirma la veracidad del conato desde el panel de control,<br/>&nbsp;&nbsp;**Entonces** el sistema suma 50 puntos al ciudadano, persiste el nuevo total de 150 puntos y mantiene su nivel en `PLATA`.<br/><br/>**• Escenario 2 (Promoción automática al nivel de confiabilidad ORO):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano registra un puntaje acumulado de 260 puntos de reputación,<br/>&nbsp;&nbsp;**Cuando** el bombero califica como verídico un nuevo reporte suyo adicionando 50 puntos,<br/>&nbsp;&nbsp;**Entonces** el sistema actualiza su saldo a 310 puntos y asciende su categoría al nivel `ORO`.<br/><br/>**• Escenario 3 (Bloqueo de bonificación sobre ciudadano previamente suspendido):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano figura con estado `SUSPENDIDO` en la base de datos,<br/>&nbsp;&nbsp;**Cuando** el bombero intenta registrar una evaluación de veracidad sobre dicho perfil,<br/>&nbsp;&nbsp;**Entonces** el sistema rechaza la operación lanzando una excepción de negocio y conserva intacto el puntaje. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T1.1.1` `«boundary»`: Incorporar botón de acción *"Confirmar Veracidad (+50 pts)"* e insignia visual del nivel de confiabilidad (`BRONCE`, `PLATA`, `ORO`) en `actualizar-estado.jsp`. **[2 HI / 4 HR]**
* `T1.1.2` `«control»`: Crear controlador `ReputacionServlet.java` y completar en `GestorReputacion.java` la validación de estado activo y el enlace con la capa de datos. **[3 HI / 6 HR]**
* `T1.1.3` `«entity»`: Implementar la interfaz `ICiudadanoDAO` en `UsuarioDAO.java` mediante el método transaccional `actualizar(Ciudadano)` para persistir `puntos_reputacion` en la tabla `ciudadanos`. **[2 HI / 4 HR]**
* **Validación de Estimación:** $2 + 3 + 2 = \mathbf{7	ext{ HI}}$ ($14	ext{ HR}$).

---

### Historia de Usuario HU-R2-02
* **Pertenece a:** Release 2 — Iteración 1 (Semana 1)
* **Épica:** `EP-02` Gobernanza Cívica, Alertas Proactivas y Auditoría Operativa
* **Feature:** `FT-01` Gobernanza Cívica, Reputación y Control de Infractores

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#C4E5F5; font-weight:bold;">
    <td style="border:1px solid #000000; padding:6px;">Title: HU-R2-02 - Penalización por falsa alarma y suspensión automática de ciudadano</td>
    <td style="border:1px solid #000000; padding:6px;">Priority: Alta</td>
    <td style="border:1px solid #000000; padding:6px;">Estimate: 7 horas</td>
  </tr>
</table>

| **Title:** HU-R2-02 - Penalización por falsa alarma y suspensión automática de ciudadano | **Priority:** Alta | **Estimate:** 7 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como bombero,*<br/>*quiero sancionar con suspensión automática al ciudadano con tres falsas alarmas,*<br/>*para impedir nuevos envíos fraudulentos en la plataforma.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Descuento de 100 puntos por reporte falso con piso en cero):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano dispone de 60 puntos de reputación y 1 reporte falso previo,<br/>&nbsp;&nbsp;**Cuando** el bombero dictamina en la consola que el nuevo conato es una falsa alarma,<br/>&nbsp;&nbsp;**Entonces** el sistema descuenta la penalización truncando el saldo en 0 puntos, incrementa las faltas a 2 y degrada su nivel a `BRONCE`.<br/><br/>**• Escenario 2 (Suspensión automática al acumular la tercera falsa alarma):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano registra 2 reportes falsos previos en su historial,<br/>&nbsp;&nbsp;**Cuando** el bombero sanciona una tercera falsa alarma asociada a su cuenta,<br/>&nbsp;&nbsp;**Entonces** el sistema eleva el contador de faltas a 3 y actualiza el atributo `suspendido` a `true`.<br/><br/>**• Escenario 3 (Denegación de nuevo reporte a ciudadano suspendido):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano posee el indicador `suspendido` activo en su cuenta,<br/>&nbsp;&nbsp;**Cuando** intenta enviar un nuevo formulario de conato en `reportar-incendio.jsp`,<br/>&nbsp;&nbsp;**Entonces** el sistema bloquea el registro y despliega un mensaje notificando la suspensión de la cuenta. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T1.2.1` `«boundary»`: Añadir botón *"Sancionar Falsa Alarma (-100 pts)"* en `actualizar-estado.jsp` y banner de bloqueo de acceso para cuentas suspendidas en `reportar-incendio.jsp`. **[2 HI / 4 HR]**
* `T1.2.2` `«control»`: Implementar el método `validarAcceso(Ciudadano)` en `GestorReputacion.java` e integrarlo como guarda de seguridad en `ReporteServlet.java` y `ReputacionServlet.java`. **[3 HI / 6 HR]**
* `T1.2.3` `«entity»`: Persistir las columnas `reportes_falsos` y `suspendido` de `Ciudadano.java` en `UsuarioDAO.java` dentro de una transacción Hibernate. **[2 HI / 4 HR]**
* **Validación de Estimación:** $2 + 3 + 2 = \mathbf{7	ext{ HI}}$ ($14	ext{ HR}$).

---

### Historia de Usuario HU-R2-03
* **Pertenece a:** Release 2 — Iteración 1 (Semana 1)
* **Épica:** `EP-02` Gobernanza Cívica, Alertas Proactivas y Auditoría Operativa
* **Feature:** `FT-01` Gobernanza Cívica, Reputación y Control de Infractores

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#C4E5F5; font-weight:bold;">
    <td style="border:1px solid #000000; padding:6px;">Title: HU-R2-03 - Consulta ciudadana de perfil de confiabilidad e historial de reportes</td>
    <td style="border:1px solid #000000; padding:6px;">Priority: Media</td>
    <td style="border:1px solid #000000; padding:6px;">Estimate: 6 horas</td>
  </tr>
</table>

| **Title:** HU-R2-03 - Consulta ciudadana de perfil de confiabilidad e historial de reportes | **Priority:** Media | **Estimate:** 6 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como ciudadano,*<br/>*quiero consultar mi rango de confiabilidad cívica actual,*<br/>*para verificar el estado de atención de mis alertas.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Visualización de medalla de confiabilidad y puntaje vigente):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano ha emitido reportes previamente en la plataforma,<br/>&nbsp;&nbsp;**Cuando** accede a la sección de perfil cívico (`/mi-historial`),<br/>&nbsp;&nbsp;**Entonces** el sistema muestra su puntaje numérico actual, el contador de faltas y la insignia de su nivel (`BRONCE`, `PLATA` u `ORO`).<br/><br/>**• Escenario 2 (Listado cronológico de alertas propias y su estado operativo):**<br/>&nbsp;&nbsp;**Dado que** existen conatos reportados por el ciudadano en la base de datos,<br/>&nbsp;&nbsp;**Cuando** se carga la tabla de seguimiento personal,<br/>&nbsp;&nbsp;**Entonces** el sistema lista únicamente los reportes de dicho ciudadano ordenados por fecha con su estado actual (`REPORTADO`, `EN_PROCESO` o `EXTINTO`).<br/><br/>**• Escenario 3 (Mensaje informativo para ciudadano sin reportes previos):**<br/>&nbsp;&nbsp;**Dado que** el ciudadano aún no ha registrado ningún conato en el sistema,<br/>&nbsp;&nbsp;**Cuando** consulta su vista de historial cívico,<br/>&nbsp;&nbsp;**Entonces** el sistema presenta su puntaje inicial de 100 puntos (`PLATA`) y un aviso indicando que no registra alertas emitidas. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T1.3.1` `«boundary»`: Diseñar la vista `mi-historial.jsp` e incorporar su enlace en `navbar.jsp` con tarjetas de puntaje, insignia de rango y tabla de seguimiento. **[2 HI / 4 HR]**
* `T1.3.2` `«control»`: Desarrollar el controlador `HistorialCiudadanoServlet.java` (`/mi-historial`) que consulte el perfil del ciudadano e invoque `clasificarNivelConfiabilidad`. **[2 HI / 4 HR]**
* `T1.3.3` `«entity»`: Programar la consulta HQL `listarPorCiudadano(Long ciudadanoId)` en `IncendioDAO.java` ordenando por `fechaHora DESC`. **[2 HI / 4 HR]**
* **Validación de Estimación:** $2 + 2 + 2 = \mathbf{6	ext{ HI}}$ ($12	ext{ HR}$).

---

#### Tabla Resumen Oficial — Iteración 1 (Release 2)

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#FDE7BB; font-weight:bold; text-align:center;">
    <td style="border:1px solid #000000; padding:6px; width:10%;">No.</td>
    <td style="border:1px solid #000000; padding:6px; width:65%;">Título</td>
    <td style="border:1px solid #000000; padding:6px; width:25%;">Estimación</td>
  </tr>
  <tr>
    <td style="border:1px solid #000000; padding:6px; text-align:center;">1</td>
    <td style="border:1px solid #000000; padding:6px;">Calificación de veracidad de conato y bonificación de reputación</td>
    <td style="border:1px solid #000000; padding:6px; text-align:center;">7 horas</td>
  </tr>
  <tr>
    <td style="border:1px solid #000000; padding:6px; text-align:center;">2</td>
    <td style="border:1px solid #000000; padding:6px;">Penalización por falsa alarma y suspensión automática de ciudadano</td>
    <td style="border:1px solid #000000; padding:6px; text-align:center;">7 horas</td>
  </tr>
  <tr>
    <td style="border:1px solid #000000; padding:6px; text-align:center;">3</td>
    <td style="border:1px solid #000000; padding:6px;">Consulta ciudadana de perfil de confiabilidad e historial de reportes</td>
    <td style="border:1px solid #000000; padding:6px; text-align:center;">6 horas</td>
  </tr>
  <tr style="background-color:#FDE7BB; font-weight:bold; text-align:center;">
    <td style="border:1px solid #000000; padding:6px; background-color:#FFFFFF;"></td>
    <td style="border:1px solid #000000; padding:6px;">Estimado</td>
    <td style="border:1px solid #000000; padding:6px;">20 horas</td>
  </tr>
  <tr style="background-color:#FDE7BB; font-weight:bold; text-align:center;">
    <td style="border:1px solid #000000; padding:6px; background-color:#FFFFFF;"></td>
    <td style="border:1px solid #000000; padding:6px;">Real</td>
    <td style="border:1px solid #000000; padding:6px;">40 horas</td>
  </tr>
</table>

---

## 3. Historias de Usuario — Iteración 2 del Release 2 (20 HI / 40 HR)

### Historia de Usuario HU-R2-04
* **Pertenece a:** Release 2 — Iteración 2 (Semana 2)
* **Épica:** `EP-02` Gobernanza Cívica, Alertas Proactivas y Auditoría Operativa
* **Feature:** `FT-02` Alertas Comunitarias, Despacho Reactivo y Auditoría Histórica

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#C4E5F5; font-weight:bold;">
    <td style="border:1px solid #000000; padding:6px;">Title: HU-R2-04 - Bandeja de alertas comunitarias por proximidad y cambios de estado</td>
    <td style="border:1px solid #000000; padding:6px;">Priority: Alta</td>
    <td style="border:1px solid #000000; padding:6px;">Estimate: 7 horas</td>
  </tr>
</table>

| **Title:** HU-R2-04 - Bandeja de alertas comunitarias por proximidad y cambios de estado | **Priority:** Alta | **Estimate:** 7 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como ciudadano,*<br/>*quiero visualizar las alertas de conatos cercanos en mi bandeja de notificaciones,*<br/>*para despejar anticipadamente las vías de acceso vehicular.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Generación y visualización de alerta por nuevo incendio):**<br/>&nbsp;&nbsp;**Dado que** se registra un nuevo conato de incendio en un sector metropolitano,<br/>&nbsp;&nbsp;**Cuando** el ciudadano abre el centro de alertas (`/notificaciones`),<br/>&nbsp;&nbsp;**Entonces** el sistema despliega la notificación tipo `NUEVO_INCENDIO` con el sector afectado, fecha/hora y recomendación preventiva.<br/><br/>**• Escenario 2 (Notificación automática tras actualización de estado operativo):**<br/>&nbsp;&nbsp;**Dado que** el bombero cambia el estado de una emergencia a `EN_PROCESO` o `EXTINTO`,<br/>&nbsp;&nbsp;**Cuando** se procesa la transición en el servidor,<br/>&nbsp;&nbsp;**Entonces** el sistema registra y publica un aviso tipo `CAMBIO_ESTADO` visible en la bandeja de notificaciones.<br/><br/>**• Escenario 3 (Filtrado de alertas por tipo de evento):**<br/>&nbsp;&nbsp;**Dado que** la bandeja contiene múltiples avisos de nuevos incendios y cambios de estado,<br/>&nbsp;&nbsp;**Cuando** el ciudadano selecciona un filtro por categoría (`NUEVO_INCENDIO` o `CAMBIO_ESTADO`),<br/>&nbsp;&nbsp;**Entonces** la vista presenta únicamente las notificaciones que coinciden con el tipo elegido. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T2.1.1` `«boundary»`: Construir la vista `notificaciones.jsp` con tarjetas cromáticas por tipo de alerta, contador en `navbar.jsp` y filtro rápido por categoría. **[2 HI / 4 HR]**
* `T2.1.2` `«control»`: Implementar `NotificacionServlet.java` (`/notificaciones`) e invocar `ServicioNotificaciones.notificarCambioEstado` dentro de `IncendioServlet.java` y `GestorDespacho.java`. **[3 HI / 6 HR]**
* `T2.1.3` `«entity»`: Ampliar `NotificacionDAO.java` con el método HQL `listarPorTipo(String tipo)` sobre la entidad `Notificacion.java`. **[2 HI / 4 HR]**
* **Validación de Estimación:** $2 + 3 + 2 = \mathbf{7	ext{ HI}}$ ($14	ext{ HR}$).

---

### Historia de Usuario HU-R2-05
* **Pertenece a:** Release 2 — Iteración 2 (Semana 2)
* **Épica:** `EP-02` Gobernanza Cívica, Alertas Proactivas y Auditoría Operativa
* **Feature:** `FT-02` Alertas Comunitarias, Despacho Reactivo y Auditoría Histórica

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#C4E5F5; font-weight:bold;">
    <td style="border:1px solid #000000; padding:6px;">Title: HU-R2-05 - Filtrado reactivo de estaciones disponibles en selector de despacho</td>
    <td style="border:1px solid #000000; padding:6px;">Priority: Alta</td>
    <td style="border:1px solid #000000; padding:6px;">Estimate: 6 horas</td>
  </tr>
</table>

| **Title:** HU-R2-05 - Filtrado reactivo de estaciones disponibles en selector de despacho | **Priority:** Alta | **Estimate:** 6 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como bombero,*<br/>*quiero visualizar únicamente las estaciones disponibles en el selector de despacho,*<br/>*para suprimir errores de asignación sobre cuarteles ocupados.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Ordenamiento y habilitación de cuarteles libres por menor ETA):**<br/>&nbsp;&nbsp;**Dado que** existen estaciones de bomberos en estado `DISPONIBLE` en la base de datos,<br/>&nbsp;&nbsp;**Cuando** el bombero abre el menú desplegable de despacho frente a un conato activo,<br/>&nbsp;&nbsp;**Entonces** el sistema presenta habilitadas las estaciones libres ordenadas ascendentemente por menor tiempo de arribo.<br/><br/>**• Escenario 2 (Deshabilitación visual de cuartel ocupado en el selector):**<br/>&nbsp;&nbsp;**Dado que** una estación se encuentra en estado `OCUPADA` atendiendo otro siniestro,<br/>&nbsp;&nbsp;**Cuando** el bombero despliega el selector de asignación en `actualizar-estado.jsp`,<br/>&nbsp;&nbsp;**Entonces** dicha estación aparece bloqueada con el atributo `disabled` impidiendo su selección.<br/><br/>**• Escenario 3 (Aviso preventivo cuando todas las estaciones están ocupadas):**<br/>&nbsp;&nbsp;**Dado que** la totalidad de estaciones registradas se encuentran en estado `OCUPADA`,<br/>&nbsp;&nbsp;**Cuando** el bombero visualiza una emergencia pendiente de despacho,<br/>&nbsp;&nbsp;**Entonces** el sistema inhabilita el botón de envío y muestra una etiqueta indicando espera de liberación de recursos. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T2.2.1` `«boundary»`: Refactorizar el `<select name="estacionId">` en `actualizar-estado.jsp` aplicando `disabled` a estaciones ocupadas y bloqueando el botón si no existen unidades libres. **[2 HI / 4 HR]**
* `T2.2.2` `«control»`: Implementar en `GestorDespacho.java` e `IncendioServlet.java` el ordenamiento automático de estaciones disponibles por menor ETA hacia el conato. **[2 HI / 4 HR]**
* `T2.2.3` `«entity»`: Programar la consulta HQL `listarDisponibles()` en `EstacionDAO.java` filtrando por `WHERE e.disponible = true`. **[2 HI / 4 HR]**
* **Validación de Estimación:** $2 + 2 + 2 = \mathbf{6	ext{ HI}}$ ($12	ext{ HR}$).

---

### Historia de Usuario HU-R2-06
* **Pertenece a:** Release 2 — Iteración 2 (Semana 2)
* **Épica:** `EP-02` Gobernanza Cívica, Alertas Proactivas y Auditoría Operativa
* **Feature:** `FT-02` Alertas Comunitarias, Despacho Reactivo y Auditoría Histórica

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#C4E5F5; font-weight:bold;">
    <td style="border:1px solid #000000; padding:6px;">Title: HU-R2-06 - Conmutación de capa histórica de conatos extintos y bitácora de auditoría</td>
    <td style="border:1px solid #000000; padding:6px;">Priority: Media</td>
    <td style="border:1px solid #000000; padding:6px;">Estimate: 7 horas</td>
  </tr>
</table>

| **Title:** HU-R2-06 - Conmutación de capa histórica de conatos extintos y bitácora de auditoría | **Priority:** Media | **Estimate:** 7 horas |
| :--- | :--- | :--- |
| **User Story:**<br/><br/>*Como bombero,*<br/>*quiero conmutar la capa histórica de conatos extintos en el mapa,*<br/>*para auditar la cronología de intervenciones cerradas.* | | |
| **Acceptance Criteria:**<br/><br/>**• Escenario 1 (Ocultamiento por defecto de conatos extintos en el visor principal):**<br/>&nbsp;&nbsp;**Dado que** en la base de datos coexisten incendios activos e incendios en estado `EXTINTO`,<br/>&nbsp;&nbsp;**Cuando** el bombero carga el visor cartográfico (`/mapa`) en modo predeterminado,<br/>&nbsp;&nbsp;**Entonces** el mapa muestra únicamente los focos activos manteniendo oculta la capa histórica.<br/><br/>**• Escenario 2 (Activación bajo demanda de la capa histórica en Leaflet):**<br/>&nbsp;&nbsp;**Dado que** el bombero requiere inspeccionar los siniestros liquidados en el DMQ,<br/>&nbsp;&nbsp;**Cuando** activa el conmutador *"Mostrar Histórico Extinto"* en el control de capas del mapa,<br/>&nbsp;&nbsp;**Entonces** el sistema proyecta marcadores grises en las coordenadas de los conatos extintos.<br/><br/>**• Escenario 3 (Consulta de la bitácora de transiciones de estado del siniestro):**<br/>&nbsp;&nbsp;**Dado que** un conato ha pasado por los estados `REPORTADO`, `EN_PROCESO` y `EXTINTO`,<br/>&nbsp;&nbsp;**Cuando** el bombero abre el detalle de auditoría del incidente,<br/>&nbsp;&nbsp;**Entonces** el sistema exhibe la traza cronológica almacenada en `HistorialEstado` con fecha, estado anterior, estado nuevo y observación. | | |

**Descomposición en Tareas Técnicas (BCE):**
* `T2.3.1` `«boundary»`: Incorporar control conmutador `L.control.layers` para la capa de extintos en `mapa.jsp` y modal de línea de tiempo de auditoría en `actualizar-estado.jsp`. **[2 HI / 4 HR]**
* `T2.3.2` `«control»`: Exponer la lista de conatos extintos y su historial de transiciones en `MapaServlet.java`, `GestorMapa.java` y `GestorIncendios.java`. **[3 HI / 6 HR]**
* `T2.3.3` `«entity»`: Crear `HistorialEstadoDAO.java` y mapear la persistencia de `HistorialEstado.java` en `hibernate.cfg.xml` e `IncendioDAO.java`. **[2 HI / 4 HR]**
* **Validación de Estimación:** $2 + 3 + 2 = \mathbf{7	ext{ HI}}$ ($14	ext{ HR}$).

---

#### Tabla Resumen Oficial — Iteración 2 (Release 2)

<table style="width:100%; border-collapse:collapse; border:1px solid #000000;">
  <tr style="background-color:#FDE7BB; font-weight:bold; text-align:center;">
    <td style="border:1px solid #000000; padding:6px; width:10%;">No.</td>
    <td style="border:1px solid #000000; padding:6px; width:65%;">Título</td>
    <td style="border:1px solid #000000; padding:6px; width:25%;">Estimación</td>
  </tr>
  <tr>
    <td style="border:1px solid #000000; padding:6px; text-align:center;">1</td>
    <td style="border:1px solid #000000; padding:6px;">Bandeja de alertas comunitarias por proximidad y cambios de estado</td>
    <td style="border:1px solid #000000; padding:6px; text-align:center;">7 horas</td>
  </tr>
  <tr>
    <td style="border:1px solid #000000; padding:6px; text-align:center;">2</td>
    <td style="border:1px solid #000000; padding:6px;">Filtrado reactivo de estaciones disponibles en selector de despacho</td>
    <td style="border:1px solid #000000; padding:6px; text-align:center;">6 horas</td>
  </tr>
  <tr>
    <td style="border:1px solid #000000; padding:6px; text-align:center;">3</td>
    <td style="border:1px solid #000000; padding:6px;">Conmutación de capa histórica de conatos extintos y bitácora de auditoría</td>
    <td style="border:1px solid #000000; padding:6px; text-align:center;">7 horas</td>
  </tr>
  <tr style="background-color:#FDE7BB; font-weight:bold; text-align:center;">
    <td style="border:1px solid #000000; padding:6px; background-color:#FFFFFF;"></td>
    <td style="border:1px solid #000000; padding:6px;">Estimado</td>
    <td style="border:1px solid #000000; padding:6px;">20 horas</td>
  </tr>
  <tr style="background-color:#FDE7BB; font-weight:bold; text-align:center;">
    <td style="border:1px solid #000000; padding:6px; background-color:#FFFFFF;"></td>
    <td style="border:1px solid #000000; padding:6px;">Real</td>
    <td style="border:1px solid #000000; padding:6px;">40 horas</td>
  </tr>
</table>
