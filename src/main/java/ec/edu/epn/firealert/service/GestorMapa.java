package ec.edu.epn.firealert.service;

import ec.edu.epn.firealert.dao.IncendioDAO;
import ec.edu.epn.firealert.dao.ZonaSeguraDAO;
import ec.edu.epn.firealert.model.Incendio;
import ec.edu.epn.firealert.model.ZonaSegura;

import java.util.List;

/*
 * ============================================================================
 * CONTROLADOR / GESTOR PARA CASO DE USO 3 (CU3: Visualizar Mapa de Incendios)
 * Implementa el flujo del Diagrama de Secuencia:
 * 1. listarIncendiosActivos()
 * 2. obtenerDetalle(idIncendio)
 * 3. Provee las zonas seguras activas para renderizar en el mapa
 * ============================================================================
 */
public class GestorMapa {

    private final IncendioDAO incendioDAO;
    private final ZonaSeguraDAO zonaSeguraDAO;

    public GestorMapa() {
        this.incendioDAO = new IncendioDAO();
        this.zonaSeguraDAO = new ZonaSeguraDAO();
    }

    public List<Incendio> listarIncendiosActivos() {
        return incendioDAO.listarActivos();
    }

    public List<ZonaSegura> obtenerZonasSeguras() {
        return zonaSeguraDAO.listarTodas();
    }

    public Incendio obtenerDetalle(Long idIncendio) {
        if (idIncendio == null) return null;
        Incendio inc = incendioDAO.buscarPorId(idIncendio);
        if (inc != null) {
            // Invoca explícitamente obtenerDetalles() como especifica el diagrama
            inc.obtenerDetalles();
        }
        return inc;
    }
}
