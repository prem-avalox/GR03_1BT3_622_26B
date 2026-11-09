# Proyecto GR03_1BT3_622_26B: AlerFire — Sistema de Alerta Temprana y Gobernanza de Incendios

Este repositorio contiene la implementación del sistema **AlerFire**, correspondiente a la materia **Metodologías Ágiles de Desarrollo de Software (ISWD622)** de la **Escuela Politécnica Nacional (EPN)**, bajo la cátedra del **Prof. Julio Sandobalín, Ph.D.**

---

## 📌 Descripción del Proyecto
**AlerFire** es una plataforma web desarrollada bajo el patrón arquitectónico **Modelo - Vista - Controlador (MVC / BCE)** en Java EE. Facilita la notificación comunitaria ante conatos de incendios forestales y urbanos, el seguimiento y despacho operativo por parte del Cuerpo de Bomberos, la visualización geoespacial en tiempo real y el cálculo de rutas de evacuación.

En el **Release 2 — Iteración 1 ($20\text{ HI} = 40\text{ HR}$)** se incorpora el **Módulo de Gobernanza Cívica y Reputación Ciudadana**, permitiendo a los bomberos recompensar reportes verídicos (+50 pts), sancionar falsas alarmas (-100 pts con piso en cero), suspender preventivamente a infractores reincidentes a la tercera falta, bloquear la emisión de reportes para cuentas suspendidas y habilitar la consulta ciudadana del perfil cívico e historial propio (`/mi-historial`).

La aplicación utiliza persistencia mediante **Hibernate ORM con JPA** y la base de datos embebida **H2 Database en modo archivo**, lo que garantiza portabilidad total sin requerir motores externos instalados.

---

## 👥 Integrantes del Equipo — Grupo 03 (GR2SW)

| Integrante | Rol XP | Correo Institucional | Usuario GitHub |
| :--- | :--- | :--- | :--- |
| **Martín Alejandro Dávalos Cedeño** | Developer / Tracker | `martin.davalos@epn.edu.ec` | `prem-avalox` |
| **Nayia Danae Cóndor Lema** | Developer / Tester | `nayia.condor@epn.edu.ec` | `Nayia` |
| **Anthony Joel Minayo Gualotuña** | Developer / Tester | `anthony.miayo@epn.edu.ec` | `AnnthonyThony` |
| **Adayely Marley Rochina Llumitaxi** | Developer / Customer Advocate | `adayely.rochina@epn.edu.ec` | `Adayely Rochina` |
| **María Fernanda Alquinga Galarza** | Developer / Tracker | `maria.alquinga@epn.edu.ec` | `Maria-Alq` |

---

## 🏛️ Historias de Usuario Implementadas — Release 2 (Iteración 1: 20 HI)

| Código | Título | Estimación | Criterios de Aceptación Verificados | URL / Vista |
| :---: | :--- | :---: | :--- | :--- |
| **HU-I1-01** | Calificación de veracidad de conato y recompensa de reputación ciudadana | 7 HI (14 HR) | • **CA-01:** Bonificación de +50 pts solo a conatos en atención/extintos y bloqueo de doble evaluación.<br>• **CA-02:** Recálculo automático de rango de confiabilidad (Bronce, Plata, Oro).<br>• **CA-03:** Rechazo de recompensa si el conato sigue en estado *Reportado* o cuenta *Suspendida*. | `/incendios` (`actualizar-estado.jsp`) |
| **HU-I1-02** | Sanción por falsa alarma y bloqueo inmediato de ciudadano reincidente | 7 HI (14 HR) | • **CA-01:** Descuento de 100 pts con piso en cero ($\max(0, \text{pts} - 100)$), estado *Falsa Alarma* y liberación de cuartel.<br>• **CA-02:** Suspensión preventiva automática e inmediata al acumular la 3ª falsa alarma.<br>• **CA-03:** Bloqueo reactivo del formulario `/reportar` (GET y POST) con banner rojo e inhabilitación del envío. | `/incendios`<br>`/reportar` (`reportar-incendio.jsp`) |
| **HU-I1-03** | Consulta ciudadana de calificación de confiabilidad e historial propio | 6 HI (12 HR) | • **CA-01:** Resumen con 4 tarjetas de perfil: Puntos, Insignia de Nivel, Infracciones X/3 y Estado Operativo.<br>• **CA-02:** Tabla cronológica descendente de reportes emitidos por el ciudadano con dictamen de veracidad.<br>• **CA-03:** Despliegue de valores por defecto (100 pts base) y banner preventivo ante cuenta suspendida. | `/mi-historial` (`mi-historial.jsp`) |

---

## 🧪 Pruebas Unitarias, Mocks, Pruebas Parametrizadas y Refactorizaciones (TDD)

El ciclo de desarrollo se condujo bajo **Test-Driven Development (Red $\rightarrow$ Green $\rightarrow$ Refactor)** documentado en `GestorReputacionTest.java` y `GestorReputacionParametersTest.java`:

### 1. Pruebas Unitarias y Mocks (`GestorReputacionTest`)
* `testEvaluarVeracidad_ReporteVeridico_SumaPuntos()`: Verifica incremento de +50 puntos en reporte verídico.
* `testEvaluarVeracidad_FalsaAlarma_DescuentaPuntosConPisoEnCero()`: Comprueba descuento de 100 pts truncando en 0.
* `testEvaluarVeracidad_TresFalsasAlarmas_SuspendeCiudadano()`: Verifica suspensión automática al llegar a 3 infracciones.
* `testEvaluarVeracidad_CiudadanoSuspendido_LanzaExcepcion()`: Valida excepción `IllegalStateException` y bloqueo de evaluación a infractor.
* `testEvaluarVeracidad_ConPersistenciaAisladaMock_VerificaLlamadaDAO()`: **Prueba con Mockito** verificando `Mockito.verify(ciudadanoDAOMock).actualizar(ciudadano)`.
* `testEvaluarVeracidad_CiudadanoSuspendido_NuncaDebeLlamarDAO()`: **Segunda prueba con Mockito** verificando `Mockito.verify(ciudadanoDAOMock, never()).actualizar(any())`.

### 2. Pruebas Parametrizadas (`GestorReputacionParametersTest`)
* Suite JUnit 4 ejecutada con `@RunWith(Parameterized.class)` evaluando **16 ejecuciones deterministas** para la clasificación de niveles:
  * Umbrales de puntaje: `0`, `49`, `50`, `99`, `100`, `199`, `200`, `300+`.
  * Verificación de niveles `BRONCE`, `PLATA`, `ORO` y sus descripciones oficiales.

### 3. Refactorizaciones Formales Aplicadas (Refactoring Catalog)
1. **Replace Magic Literal with Symbolic Constant:** Constantes `PUNTOS_REPORTE_VERIDICO` ($50$), `PENALIZACION_FALSA_ALARMA` ($100$) y `LIMITE_FALSAS_ALARMAS` ($3$).
2. **Replace Conditional Logic with Built-in Library / Math.max:** Reemplazo de bloques `if (nuevoPuntaje < 0)` con `Math.max(PISO_MINIMO_PUNTOS, ...)`.
3. **Replace Nested Conditional with Guard Clauses:** Extracción de verificaciones de suspensión preventiva al inicio del método para reducir complejidad ciclomática.
4. **Dependency Injection & Interface Segregation:** Desacoplamiento de capa DAO mediante `ICiudadanoDAO` inyectado en `GestorReputacion`, facilitando aislamiento en pruebas.

---

## 🔄 Integración Continua (CI) y Matriz de Commits

En cumplimiento de la rúbrica ($\ge 2$ integraciones en la iteración y $\ge 2$ commits por developer por integración con matching Author/Committer):

* **Integración 1 (TDD Core y Refactorización):**
  * Martín Dávalos: `ce08b28` (feat TDD Test 1) \| `cc3949b` (refactor constante veracidad)
  * Nayia Cóndor: `1adbc68` (feat TDD Test 2) \| `eb9a85f` (refactor constante penalización)
  * Anthony Minayo: `42dfd69` (feat TDD Test 3) \| `e2072c1` (refactor constante límite)
  * Adayely Rochina: `4d68634` (feat TDD Test 4) \| `a32b963` (refactor guard clause) \| `3a2fda2` (test parametrizado)
  * María Alquinga: `92ee6d0` (feat TDD Mock 1) \| `dfbc088` (refactor TDD Mock 2)
* **Integración 2 (Capa Web, DAOs, Vistas y Despliegue):**
  * Nayia Cóndor: `192b738` (feat entidades Incendio) \| `992c8d3` (feat UI bloqueo reporte)
  * María Alquinga: `be608bc` (feat DAOs Usuario e Incendio) \| `da0f4f7` (feat UI calificación bomberil)
  * Anthony Minayo: `03243ee` (feat servlets Reporte e Historial) \| `README` (doc Release 2)
  * Martín Dávalos: `3d76e48` (feat ReputacionServlet) \| `df43b73` (feat docker-compose)
  * Adayely Rochina: `564b250` (feat UI mi-historial) \| `5274fc9` (feat trazabilidad footer)

---

## 🚀 Instrucciones de Ejecución

### Opción A: Ejecución con Docker / Docker Compose (Recomendada)
1. Construir e iniciar el contenedor:
   ```bash
   docker-compose up --build -d
   ```
2. O alternativamente mediante Docker CLI directo:
   ```bash
   docker build -t alerfire:latest .
   docker run -d -p 8080:8080 --name alerfire_app alerfire:latest
   ```
3. Abrir en el navegador: `http://localhost:8080`

### Opción B: Ejecución Local con Maven
1. Compilar y ejecutar pruebas unitarias:
   ```bash
   mvn clean test
   ```
2. Levantar el servidor web embebido:
   ```bash
   mvn clean compile jetty:run
   ```
3. Abrir en el navegador: `http://localhost:8080`

---

## 📋 URLs y Módulos de Acceso

| Módulo | URL | Rol | Descripción |
| :--- | :--- | :--- | :--- |
| **Dashboard** | `http://localhost:8080/home` | Público | Métricas de focos activos y estado de alertas |
| **Reportar Incendio (CU1)** | `http://localhost:8080/reportar` | Ciudadano | Envío de alerta GPS con bloqueo reactivo si la cuenta está suspendida |
| **Panel Bomberos (CU2)** | `http://localhost:8080/incendios` | Bombero | Despacho de cuarteles, liquidación y calificación veraz / falsa alarma |
| **Mi Historial Cívico (CU6)** | `http://localhost:8080/mi-historial` | Ciudadano | Perfil de 4 tarjetas (Puntos, Nivel Oro/Plata/Bronce, Faltas, Estado) y tabla cronológica |
| **Mapa en Vivo (CU3)** | `http://localhost:8080/mapa` | General | Cartografía interactiva con focos georreferenciados |
| **Rutas de Evacuación (CU4)** | `http://localhost:8080/evacuacion` | General | Cálculo de rutas seguras hacia zonas de refugio |
