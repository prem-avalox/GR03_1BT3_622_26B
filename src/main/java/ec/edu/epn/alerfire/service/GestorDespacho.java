package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.EstadoIncendio;
import ec.edu.epn.alerfire.model.EstacionBomberos;
import ec.edu.epn.alerfire.model.Incendio;

public class GestorDespacho implements IServicioDespacho {

    private IEstacionDAO estacionDAO;

    public GestorDespacho() {
    }

    public GestorDespacho(IEstacionDAO estacionDAO) {
        this.estacionDAO = estacionDAO;
    }

    @Override
    public boolean asignarEstacion(Incendio conato, EstacionBomberos estacion) {
        if (conato != null && estacion != null && conato.getEstado() == EstadoIncendio.REPORTADO && estacion.isDisponible()) {
            vincularEstacionAConato(conato, estacion);
            return true;
        }
        return false;
    }

    private void vincularEstacionAConato(Incendio conato, EstacionBomberos estacion) {
        conato.setEstacionAsignada(estacion);
        conato.cambiarEstado(EstadoIncendio.EN_PROCESO_DE_ATENCION);
        estacion.setDisponible(false);
    }

    @Override
    public boolean finalizarAtencion(Incendio conato) {
        // TODO: Implementar mediante TDD (HU-02)
        return false;
    }

    @Override
    public int calcularTiempoArriboMinutos(double distanciaKm) {
        // TODO: Implementar mediante TDD Parametrizado (HU-03)
        return 0;
    }

    @Override
    public boolean esRespuestaExtendida(double distanciaKm) {
        return false;
    }
}