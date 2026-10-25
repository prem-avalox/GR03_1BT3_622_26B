package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.EstacionBomberos;
import ec.edu.epn.alerfire.model.Incendio;

public interface IServicioDespacho {
    boolean asignarEstacion(Incendio conato, EstacionBomberos estacion);
    boolean finalizarAtencion(Incendio conato);
    int calcularTiempoArriboMinutos(double distanciaKm);
    boolean esRespuestaExtendida(double distanciaKm);
}