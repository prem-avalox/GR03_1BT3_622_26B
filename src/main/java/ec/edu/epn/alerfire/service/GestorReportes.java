package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.dao.IncendioDAO;
import ec.edu.epn.alerfire.dao.UsuarioDAO;
import ec.edu.epn.alerfire.model.Ciudadano;
import ec.edu.epn.alerfire.model.EstadoIncendio;
import ec.edu.epn.alerfire.model.Evidencia;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.model.Ubicacion;

import java.time.LocalDateTime;
import java.util.List;

/*
 * ============================================================================
 * CONTROLADOR / GESTOR PARA CASO DE USO 1 (CU1: Reportar Incendio)
 * Implementa el flujo del Diagrama de Secuencia:
 * 1. Recibe crearReporte(ciudadano, evidencias, descripción)
 * 2. Invoca ServicioGPS.obtenerUbicación()
 * 3. Ejecuta validarEvidencias(evidencias)
 * 4. Ejecuta incendio.validarReporte()
 * 5. Persiste el incendio con estado REPORTADO
 * 6. Invoca ServicioNotificaciones.notificarCercanos(incendio)
 * ============================================================================
 */
public class GestorReportes {

    private final IncendioDAO incendioDAO;
    private final UsuarioDAO usuarioDAO;
    private final ServicioGPS servicioGPS;
    private final ServicioNotificaciones servicioNotificaciones;

    public GestorReportes() {
        this.incendioDAO = new IncendioDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.servicioGPS = new ServicioGPS();
        this.servicioNotificaciones = new ServicioNotificaciones();
    }

    public boolean validarEvidencias(List<Evidencia> evidencias) {
        if (evidencias == null || evidencias.isEmpty()) {
            return true; // Es permitido enviar reporte con o sin evidencia visual inmediata
        }
        for (Evidencia ev : evidencias) {
            if (!ev.validar()) {
                return false;
            }
        }
        return true;
    }

    public Incendio crearReporte(Ciudadano ciudadano, List<Evidencia> evidencias, String descripcion, Ubicacion ubicacionEspecifica) {
        // Paso 1: Resolver ubicación (vía GPS o manual si fue indicada en mapa)
        Ubicacion ubicacion = ubicacionEspecifica;
        if (ubicacion == null || (ubicacion.getLatitud() == 0.0 && ubicacion.getLongitud() == 0.0)) {
            ubicacion = servicioGPS.obtenerUbicacion();
        }

        // Paso 2: Si no viene ciudadano asignado, asociar el usuario ciudadano por defecto
        if (ciudadano == null) {
            ciudadano = usuarioDAO.obtenerCiudadanoDefault();
        }

        // Paso 3: Validar evidencias
        if (!validarEvidencias(evidencias)) {
            throw new IllegalArgumentException("Una o más evidencias no tienen formato o URL válida.");
        }

        // Paso 4: Construir entidad Incendio y validar con validarReporte()
        Incendio incendio = new Incendio();
        incendio.setDescripcion(descripcion);
        incendio.setUbicacion(ubicacion);
        incendio.setCiudadano(ciudadano);
        incendio.setFechaHora(LocalDateTime.now());
        incendio.setEstado(EstadoIncendio.REPORTADO);

        if (evidencias != null) {
            for (Evidencia ev : evidencias) {
                incendio.agregarEvidencia(ev);
            }
        }

        if (!incendio.validarReporte()) {
            throw new IllegalArgumentException("El reporte no cumple con los criterios mínimos de validación.");
        }

        // Paso 5: Persistir en base de datos
        Incendio guardado = incendioDAO.guardar(incendio);

        // Paso 6: Notificar a ciudadanos y servicios cercanos
        if (guardado != null) {
            servicioNotificaciones.notificarCercanos(guardado);
        }

        return guardado;
    }
}
