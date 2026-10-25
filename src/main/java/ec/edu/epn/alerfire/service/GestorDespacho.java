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
        if (conato == null || estacion == null) {
            throw new IllegalArgumentException("El conato y la estación son obligatorios");
        }
        if (conato.getEstado() != EstadoIncendio.REPORTADO) {
            throw new IllegalArgumentException("Solo un conato en estado REPORTADO admite despacho");
        }
        if (!estacion.isDisponible()) {
            throw new IllegalStateException("La estación seleccionada no se encuentra disponible");
        }

        vincularEstacionAConato(conato, estacion);
        sincronizarEstacionEnRepositorio(estacion);
        return true;
    }

    @Override
    public boolean finalizarAtencion(Incendio conato) {
        validarConatoEnAtencion(conato);
        EstacionBomberos estacion = conato.getEstacionAsignada();
        conato.cambiarEstado(EstadoIncendio.EXTINTO);
        estacion.setDisponible(true);
        sincronizarEstacionEnRepositorio(estacion);
        return true;
    }

    @Override
    public int calcularTiempoArriboMinutos(double distanciaKm) {
        return 0;
    }

    @Override
    public boolean esRespuestaExtendida(double distanciaKm) {
        return false;
    }

    private void vincularEstacionAConato(Incendio conato, EstacionBomberos estacion) {
        conato.setEstacionAsignada(estacion);
        conato.cambiarEstado(EstadoIncendio.EN_PROCESO_DE_ATENCION);
        estacion.setDisponible(false);
    }

    private void validarConatoEnAtencion(Incendio conato) {
        if (conato == null) {
            throw new IllegalArgumentException("El conato no puede ser nulo");
        }
        boolean estadoEnAtencionActiva = conato.getEstado() == EstadoIncendio.EN_PROCESO_DE_ATENCION
                || conato.getEstado() == EstadoIncendio.BAJO_CONTROL
                || conato.getEstado() == EstadoIncendio.NO_CONTROLADO;
        boolean tieneEstacionAsignada = conato.getEstacionAsignada() != null;

        if (!estadoEnAtencionActiva || !tieneEstacionAsignada) {
            throw new IllegalStateException("El conato debe estar en atención activa con estación asignada antes de extinguirse");
        }
    }


    private void sincronizarEstacionEnRepositorio(EstacionBomberos estacion) {
        if (estacionDAO != null && estacion != null) {
            estacionDAO.actualizar(estacion);
        }
    }
}