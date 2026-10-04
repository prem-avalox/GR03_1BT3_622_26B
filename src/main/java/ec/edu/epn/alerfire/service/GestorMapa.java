package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.dao.IncendioDAO;
import ec.edu.epn.alerfire.dao.ZonaSeguraDAO;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.model.ZonaSegura;
import ec.edu.epn.alerfire.util.DataInitializer;

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
        DataInitializer.inicializarDatos();
    }

    public List<Incendio> listarIncendiosActivos() {
        DataInitializer.inicializarDatos();
        return incendioDAO.listarActivos();
    }

    public List<ZonaSegura> obtenerZonasSeguras() {
        DataInitializer.inicializarDatos();
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
