# Documentación Ágil (XP) — Proyecto AlerFire
## Escuela Politécnica Nacional — Facultad de Ingeniería de Sistemas
### Metodologías Ágiles de Desarrollo de Software (ISWD622) — Grupo 03 (GR2SW)

* **Docente:** Prof. Julio Sandobalín, Ph.D.
* **Integrantes:**
  * Martín Alejandro Dávalos Cedeño
  * Nayia Cóndor
  * Anthony Minayo
  * Adayely Rochina
  * María Fernanda Alquinga
* **Período Académico:** 2026-B

---

## 📂 Índice de Artefactos de Ingeniería y Planificación Ágil

Este directorio (`docs/`) centraliza la documentación viva del proyecto **AlerFire** bajo el enfoque *Docs-as-Code*, garantizando trazabilidad directa `1:1` con las entidades del dominio JPA (`Usuario` $\rightarrow$ `Ciudadano` y `Bombero`), servicios de negocio y pruebas unitarias JUnit 4:

| No. | Documento | Descripción y Alcance |
| :---: | :--- | :--- |
| **01** | [`01_USER_STORY_MAPPING_ALERFIRE.md`](./01_USER_STORY_MAPPING_ALERFIRE.md) | **User Story Mapping (USM) Integral y Estandarizado:** Matriz bidimensional de Jeff Patton con las **5 Actividades del Backbone**, 15 pasos del recorrido del usuario, estado evolutivo por Releases (`🟢` / `🟡` / `⚪`) y matriz de trazabilidad hacia las clases Java/JSP. |
| **02** | [`02_PLANIFICACION_RELEASE_1_XP.md`](./02_PLANIFICACION_RELEASE_1_XP.md) | **Planificación y Estimaciones XP — Release 1 (MVP Operativo):** Épica `EP-01`, Features `FT-01` y `FT-02`, cálculo de capacidad del equipo (`20 HI = 40 HR` por iteración), 6 Story Cards canónicas (`HU-I1-01` a `HU-I2-03`), 18 escenarios Gherkin y 18 tareas técnicas BCE (`«boundary»`, `«control»`, `«entity»`). |
| **03** | [`03_HISTORIAS_CANONICAS_RELEASE_2.md`](./03_HISTORIAS_CANONICAS_RELEASE_2.md) | **Catálogo Canónico de Historias de Usuario — Release 2:** Especificación formal de las 6 historias de Gobernanza Cívica / Reputación y Autoprotección / Evacuación (`HU-R2-01` a `HU-R2-06`) divididas en 2 iteraciones de `20 HI = 40 HR` bajo el formato oficial de cátedra (`#C4E5F5`). |

---

## 🏛️ Convenciones Estandarizadas del Proyecto

1. **Roles Canónicos del Dominio (`ec.edu.epn.alerfire.model`):**
   * **`Ciudadano`** (`Ciudadano.java` $\leftarrow$ `Usuario.java`): Persona natural que reporta conatos georreferenciados con evidencia fotográfica y consulta zonas seguras y rutas de evacuación.
   * **`Bombero`** (`Bombero.java` $\leftarrow$ `Usuario.java`): Personal operativo que monitorea el mapa interactivo, calcula tiempos de arribo (ETA), despacha estaciones disponibles, liquida emergencias y califica la veracidad de los reportes.
2. **Estructura de Tarjetas (Story Cards) y Tareas BCE:**
   * Encabezado tripartito (`Title:` | `Priority:` | `Estimate:`), narrativa canónica con un único verbo en singular (*Como... quiero... para...*) y 3 escenarios Gherkin en singular estricto (*Dado que... Cuando... Entonces...*).
   * Descomposición en 3 tareas técnicas de ingeniería (`«boundary»`, `«control»`, `«entity»`) situadas inmediatamente después de cada Story Card.
3. **Capacidad y Velocidad Estándar por Iteración (1 Semana):**
   * **Estimado (Horas Ideales — HI):** `20 horas` ($5\text{ integrantes} \times 4\text{ HI/semana}$).
   * **Real (Horas Reales — HR):** `40 horas` (Factor de Carga $FC = 0.5 \implies 1\text{ HI} = 2\text{ HR}$).
