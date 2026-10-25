package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.EstadoIncendio;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.model.RutaEvacuacion;
import java.util.List;

public class CalculadorRiesgoEvacuacion {

    public double evaluarRiesgo(RutaEvacuacion ruta, List<Incendio> incendiosActivos) {
        if (incendiosActivos == null || incendiosActivos.isEmpty()) {
            return 1.0;
        }

        double riesgoBase = 1.0;
        for (Incendio inc : incendiosActivos) {
            if (inc.getUbicacion() == null) continue;

            double menorDistancia = ruta.calcularDistanciaMinima(inc.getUbicacion());
            riesgoBase += calcularIncrementoPorDistancia(menorDistancia);
            riesgoBase += calcularIncrementoPorEstado(inc.getEstado());
        }

        double riesgoFinal = Math.min(10.0, Math.max(0.5, riesgoBase));
        return Math.round(riesgoFinal * 10.0) / 10.0;
    }

    private double calcularIncrementoPorDistancia(double distanciaKm) {
        if (distanciaKm < 1.0) return 6.0;
        if (distanciaKm < 3.0) return 3.0;
        if (distanciaKm < 5.0) return 1.5;
        return 0.0;
    }

    private double calcularIncrementoPorEstado(EstadoIncendio estado) {
        if (estado == null) return 0.0;
        switch (estado) {
            case NO_CONTROLADO:
                return 2.0;
            case EN_PROCESO_DE_ATENCION:
                return 1.0;
            default:
                return 0.0;
        }
    }
}
