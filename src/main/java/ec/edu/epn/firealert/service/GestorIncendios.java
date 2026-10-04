package ec.edu.epn.firealert.service;

import ec.edu.epn.firealert.dao.IncendioDAO;
import ec.edu.epn.firealert.dao.UsuarioDAO;
import ec.edu.epn.firealert.model.Bombero;
import ec.edu.epn.firealert.model.EstadoIncendio;
import ec.edu.epn.firealert.model.HistorialEstado;
import ec.edu.epn.firealert.model.Incendio;

import java.time.LocalDateTime;
import java.util.List;

/*
 * ============================================================================
 * CONTROLADOR / GESTOR PARA CASO DE USO 2 (CU2: Actualizar Estado de Incendio)
 * Implementa el flujo del Diagrama de Secuencia:
 * 1. obtenerReportesPendientes()
 * 2. actualizarEstado(idIncendio, nuevoEstado, bombero)
 * 3. Invoca incendio.cambiarEstado(nuevo)
 * 4. Registra en HistorialEstado la auditoría del cambio
 * 5. Invoca ServicioNotificaciones.notificarCambioEstado(incendio)
 * ============================================================================
 */
public class GestorIncendios {

    private final IncendioDAO incendioDAO;
    private final UsuarioDAO usuarioDAO;
    private final ServicioNotificaciones servicioNotificaciones;

    public GestorIncendios() {
        this.incendioDAO = new IncendioDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.servicioNotificaciones = new ServicioNotificaciones();
    }

    public List<Incendio> obtenerReportesPendientes() {
        return incendioDAO.listarPendientes();
    }

    public boolean actualizarEstado(Long idIncendio, EstadoIncendio nuevoEstado, Bombero bombero, String observacion) {
        if (idIncendio == null || nuevoEstado == null) {
            return false;
        }

        Incendio incendio = incendioDAO.buscarPorId(idIncendio);
        if (incendio == null) {
            return false;
        }

        if (bombero == null) {
            bombero = usuarioDAO.obtenerBomberoDefault();
        }

        EstadoIncendio estadoAnterior = incendio.getEstado();

        // 1. Invocar cambiarEstado(nuevo)
        incendio.cambiarEstado(nuevoEstado);

        // 2. Registrar historial de transición
        HistorialEstado historial = new HistorialEstado();
        historial.setFechaHora(LocalDateTime.now());
        historial.setEstadoAnterior(estadoAnterior);
        historial.setEstadoNuevo(nuevoEstado);
        historial.setBombero(bombero);
        historial.setIncendio(incendio);
        historial.setObservacion(observacion != null ? observacion : "Cambio de estado realizado por el bombero");

        incendio.getHistorial().add(historial);

        // 3. Persistir en base de datos
        boolean actualizado = incendioDAO.actualizar(incendio);

        // 4. Notificar cambio de estado a la comunidad
        if (actualizado) {
            servicioNotificaciones.notificarCambioEstado(incendio);
        }

        return actualizado;
    }
}
