package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.dao.IncendioDAO;
import ec.edu.epn.alerfire.dao.RutaEvacuacionDAO;
import ec.edu.epn.alerfire.dao.ZonaSeguraDAO;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.model.RutaEvacuacion;
import ec.edu.epn.alerfire.model.Ubicacion;
import ec.edu.epn.alerfire.model.ZonaSegura;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 * ============================================================================
 * CONTROLADOR / GESTOR PARA CASO DE USO 4 (CU4: Solicitar Ruta de Evacuación)
 * Implementa el flujo del Diagrama de Secuencia:
 * 1. solicitarRuta(ubicación)
 * 2. Consulta Mapa/BD: obtenerZonasSeguras() y obtenerIncendiosActivos()
 * 3. Ejecuta determinarRutasCercanas(ubicación)
 * 4. Ejecuta evaluarRiesgo(ruta, incendio)
 * 5. Presenta las rutas alternativas ordenadas por menor riesgo
 * ============================================================================
 */
public class GestorEvacuacion {

    private final RutaEvacuacionDAO rutaDAO;
    private final ZonaSeguraDAO zonaSeguraDAO;
    private final IncendioDAO incendioDAO;
    private final CalculadorRiesgoEvacuacion calculadorRiesgo;

    public GestorEvacuacion() {
        this.rutaDAO = new RutaEvacuacionDAO();
        this.zonaSeguraDAO = new ZonaSeguraDAO();
        this.incendioDAO = new IncendioDAO();
        this.calculadorRiesgo = new CalculadorRiesgoEvacuacion();
    }

    public List<ZonaSegura> obtenerZonasSeguras() {
        return zonaSeguraDAO.listarTodas();
    }

    public List<Incendio> obtenerIncendiosActivos() {
        return incendioDAO.listarActivos();
    }

    /**
     * Determina las rutas de evacuación candidatas para una ubicación dada.
     */
    public List<RutaEvacuacion> determinarRutasCercanas(Ubicacion origen) {
        List<RutaEvacuacion> todas = rutaDAO.listarTodas();
        if (origen == null) {
            return todas;
        }

        // Si existen rutas precargadas, actualizamos dinámicamente el origen y la distancia
        List<RutaEvacuacion> candidatas = new ArrayList<>();
        for (RutaEvacuacion r : todas) {
            if (r.getZonaSeguraDestino() != null && r.getZonaSeguraDestino().getUbicacion() != null) {
                double dist = origen.distancia(r.getZonaSeguraDestino().getUbicacion());
                r.setOrigen(origen);
                r.setDistanciaKm(dist);
            }
            candidatas.add(r);
        }
        return candidatas;
    }

    /**
     * Flujo principal de CU4: solicita rutas seguras para la ubicación del ciudadano.
     */
    public List<RutaEvacuacion> solicitarRuta(Ubicacion ubicacion) {
        List<Incendio> incendiosActivos = obtenerIncendiosActivos();
        List<RutaEvacuacion> rutas = determinarRutasCercanas(ubicacion);

        for (RutaEvacuacion r : rutas) {
            double riesgo = calculadorRiesgo.evaluarRiesgo(r, incendiosActivos);
            r.setNivelRiesgo(riesgo);
            // Invoca explícitamente obtenerTrazado() como especifica el diagrama
            r.obtenerTrazado();
        }

        // Ordenar primero por menor nivel de riesgo y luego por menor distancia
        rutas.sort(Comparator.comparingDouble(RutaEvacuacion::getNivelRiesgo)
                .thenComparingDouble(RutaEvacuacion::getDistanciaKm));

        return rutas;
    }
}
