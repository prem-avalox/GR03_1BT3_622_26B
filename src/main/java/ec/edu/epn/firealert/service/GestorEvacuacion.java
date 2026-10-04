package ec.edu.epn.firealert.service;

import ec.edu.epn.firealert.dao.IncendioDAO;
import ec.edu.epn.firealert.dao.RutaEvacuacionDAO;
import ec.edu.epn.firealert.dao.ZonaSeguraDAO;
import ec.edu.epn.firealert.model.Incendio;
import ec.edu.epn.firealert.model.RutaEvacuacion;
import ec.edu.epn.firealert.model.Ubicacion;
import ec.edu.epn.firealert.model.ZonaSegura;

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

    public GestorEvacuacion() {
        this.rutaDAO = new RutaEvacuacionDAO();
        this.zonaSeguraDAO = new ZonaSeguraDAO();
        this.incendioDAO = new IncendioDAO();
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
     * Evalúa el nivel de riesgo de una ruta (0.0 a 10.0) en función de la proximidad a incendios activos.
     */
    public double evaluarRiesgo(RutaEvacuacion ruta, List<Incendio> incendiosActivos) {
        if (incendiosActivos == null || incendiosActivos.isEmpty()) {
            return 1.0; // Riesgo mínimo si no hay focos activos
        }

        double riesgoBase = 1.0;
        Ubicacion destino = (ruta.getZonaSeguraDestino() != null) ? ruta.getZonaSeguraDestino().getUbicacion() : null;

        for (Incendio inc : incendiosActivos) {
            if (inc.getUbicacion() == null) continue;

            // Distancia del incendio al origen y al destino
            double distOrigen = (ruta.getOrigen() != null) ? ruta.getOrigen().distancia(inc.getUbicacion()) : 10.0;
            double distDestino = (destino != null) ? destino.distancia(inc.getUbicacion()) : 10.0;
            double menorDistancia = Math.min(distOrigen, distDestino);

            // Si el incendio está a menos de 1 km, riesgo muy alto
            if (menorDistancia < 1.0) {
                riesgoBase += 6.0;
            } else if (menorDistancia < 3.0) {
                riesgoBase += 3.0;
            } else if (menorDistancia < 5.0) {
                riesgoBase += 1.5;
            }

            // Agrava si el incendio no está controlado
            switch (inc.getEstado()) {
                case NO_CONTROLADO:
                    riesgoBase += 2.0;
                    break;
                case EN_PROCESO_DE_ATENCION:
                    riesgoBase += 1.0;
                    break;
                default:
                    break;
            }
        }

        // Acotar entre 0.5 y 10.0
        double riesgoFinal = Math.min(10.0, Math.max(0.5, riesgoBase));
        return Math.round(riesgoFinal * 10.0) / 10.0;
    }

    /**
     * Flujo principal de CU4: solicita rutas seguras para la ubicación del ciudadano.
     */
    public List<RutaEvacuacion> solicitarRuta(Ubicacion ubicacion) {
        List<Incendio> incendiosActivos = obtenerIncendiosActivos();
        List<RutaEvacuacion> rutas = determinarRutasCercanas(ubicacion);

        for (RutaEvacuacion r : rutas) {
            double riesgo = evaluarRiesgo(r, incendiosActivos);
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
