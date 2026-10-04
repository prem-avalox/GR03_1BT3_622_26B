# Proyecto GR03_1BT3_622_26B: AlerFire — Sistema de Alerta Temprana y Evacuación de Incendios

Este repositorio contiene la implementación práctica del sistema **AlerFire**, correspondiente a la **Tarea 3 / Sprint 1** de la materia **Metodologías Ágiles de Desarrollo de Software (622_26B)** de la Escuela Politécnica Nacional.

---

## 📌 Descripción del Proyecto
**AlerFire** es una plataforma web desarrollada bajo el patrón arquitectónico **Modelo - Vista - Controlador (MVC)** en Java EE. Facilita la notificación comunitaria ante conatos de incendios forestales y urbanos, el seguimiento y actualización de estados operativos por parte del Cuerpo de Bomberos, la visualización geoespacial en tiempo real y el cálculo de rutas seguras de evacuación evaluando el nivel de riesgo frente a los focos activos.

La aplicación utiliza persistencia mediante **Hibernate ORM con JPA** y la base de datos embebida **H2 Database en modo archivo**, lo que garantiza que no se requiere instalar ni configurar motores externos de base de datos para ejecutar y calificar el proyecto.

---

## 🏛️ Arquitectura del Sistema (MVC / BCE)

La solución mapea directamente los diagramas de robustez y de secuencia:

```
GR03_1BT3_622_26B/
├── pom.xml                                           # Configuración Maven, dependencias y Jetty plugin
├── README.md                                         # Documentación técnica, arquitectura y guía de uso
├── src/
│   └── main/
│       ├── java/
│       │   └── ec/edu/epn/alerfire/
│       │       ├── model/                            # CAPA MODELO (Entidades JPA)
│       │       │   ├── Usuario.java                  # Clase base de usuarios
│       │       │   ├── Ciudadano.java                # Rol Ciudadano (CU1, CU3, CU4)
│       │       │   ├── Bombero.java                  # Rol Bombero (CU2)
│       │       │   ├── Incendio.java                 # Entidad central del evento
│       │       │   ├── EstadoIncendio.java           # Enum (REPORTADO, EN_PROCESO, etc.)
│       │       │   ├── Evidencia.java                # Fotos / Videos adjuntos
│       │       │   ├── TipoEvidencia.java            # Enum (FOTO, VIDEO)
│       │       │   ├── HistorialEstado.java          # Auditoría de transiciones de estado
│       │       │   ├── Ubicacion.java                # Value Object (lat, lng, distancia Haversine)
│       │       │   ├── ZonaSegura.java               # Refugios y puntos de encuentro
│       │       │   ├── RutaEvacuacion.java           # Trazados y niveles de riesgo
│       │       │   └── Notificacion.java             # Alertas broadcast comunitarias
│       │       ├── dao/                              # CAPA DE PERSISTENCIA (Hibernate DAO)
│       │       │   ├── IncendioDAO.java              # CRUD y consultas de incendios
│       │       │   ├── ZonaSeguraDAO.java            # Consulta de zonas seguras
│       │       │   ├── RutaEvacuacionDAO.java        # Consulta de rutas
│       │       │   ├── UsuarioDAO.java               # Gestión de usuarios y sesiones
│       │       │   └── NotificacionDAO.java          # Historial de alertas enviadas
│       │       ├── service/                          # CAPA CONTROLADORES DE NEGOCIO (Gestores)
│       │       │   ├── GestorReportes.java           # CU1: crearReporte, validarEvidencias
│       │       │   ├── GestorIncendios.java          # CU2: obtenerPendientes, actualizarEstado
│       │       │   ├── GestorMapa.java               # CU3: listarActivos, obtenerDetalle
│       │       │   ├── GestorEvacuacion.java         # CU4: solicitarRuta, evaluarRiesgo
│       │       │   ├── ServicioGPS.java              # Georreferenciación y coordenadas
│       │       │   └── ServicioNotificaciones.java   # Despacho de notificaciones
│       │       ├── servlet/                          # CAPA CONTROLADOR WEB (HTTP Servlets)
│       │       │   ├── HomeServlet.java              # Dashboard principal y métricas
│       │       │   ├── ReporteServlet.java           # Enrutamiento CU1 (/reportar)
│       │       │   ├── IncendioServlet.java          # Enrutamiento CU2 (/incendios)
│       │       │   ├── MapaServlet.java              # Enrutamiento CU3 (/mapa)
│       │       │   └── EvacuacionServlet.java        # Enrutamiento CU4 (/evacuacion)
│       │       └── util/
│       │           ├── HibernateUtil.java            # Singleton SessionFactory
│       │           └── DataInitializer.java          # Carga de datos semilla de Quito
│       ├── resources/
│       │   └── hibernate.cfg.xml                     # Configuración JDBC y mapeos H2
│       └── webapp/                                   # CAPA VISTA (JSP + Bootstrap 5 + Leaflet)
│           ├── common/
│           │   ├── navbar.jsp                        # Barra de navegación superior
│           │   └── footer.jsp                        # Pie de página institucional
│           ├── WEB-INF/
│           │   └── web.xml                           # Descriptor de despliegue web
│           ├── index.jsp                             # Dashboard interactivo con mini-mapa
│           ├── reportar-incendio.jsp                 # PantallaReporte (CU1)
│           ├── actualizar-estado.jsp                 # PantallaActualizarEstado (CU2)
│           ├── mapa.jsp                              # PantallaMapa (CU3)
│           └── rutas-evacuacion.jsp                  # PantallaRutas / PantallaSalida (CU4)
```

---

## 🛠️ Tecnologías Empleadas
1. **Lenguaje:** Java 17 (LTS).
2. **Controlador:** Java Servlets (`javax.servlet.http.HttpServlet`) siguiendo el patrón PRG (Post/Redirect/Get).
3. **Vista:** JavaServer Pages (JSP) con JSTL, estilizado con **Bootstrap 5**, Bootstrap Icons y mapas interactivos con **Leaflet / OpenStreetMap**.
4. **ORM (Mapeo Objeto-Relacional):** Hibernate ORM 5.6 (`org.hibernate:hibernate-core`) con anotaciones estándar de JPA.
5. **Base de Datos:** H2 Database Engine 2.2 embebida en archivo (`jdbc:h2:./data/alerfiredb`). Se inicializa de forma automática con datos georreferenciados de Quito (La Carolina, El Ejido, Guápulo, Bellavista).
6. **Servidor Embebido:** Jetty Maven Plugin para levantamiento en un solo paso.
7. **Gestor de Construcción:** Apache Maven 3.8+.

---

## 🚀 Instrucciones de Ejecución

### Prerrequisitos
* Java Development Kit (JDK 17 o superior).
* Apache Maven (3.8 o superior).

### Pasos para Ejecutar
1. Clonar el repositorio y acceder a la carpeta del proyecto:
   ```bash
   cd GR03_1BT3_622_26B
   ```

2. Compilar y levantar la aplicación web con Jetty embebido:
   ```bash
   mvn clean compile jetty:run
   ```

3. Abrir en cualquier navegador web:
   ```
   http://localhost:8080
   ```

---

## 📋 Casos de Uso Implementados y URLs de Acceso

| Caso de Uso | Módulo / Pantalla | URL | Rol |
| :--- | :--- | :--- | :--- |
| **Dashboard** | Métricas y resumen | `http://localhost:8080/home` | Público |
| **CU1** | Reportar Incendio | `http://localhost:8080/reportar` | Ciudadano |
| **CU2** | Actualizar Estado de Incendio | `http://localhost:8080/incendios` | Bombero |
| **CU3** | Visualizar Mapa de Incendios | `http://localhost:8080/mapa` | Ciudadano / General |
| **CU4** | Solicitar Ruta de Evacuación | `http://localhost:8080/evacuacion` | Ciudadano |

---

## 👥 Integrantes — Grupo 03
* Barahona Lisbeth
* Calva Daniela
* Dávalos Martín
* Quimbiulco Mateo
* Vinocunga Patricia
