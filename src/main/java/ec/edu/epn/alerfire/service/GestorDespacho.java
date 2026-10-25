package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.EstadoIncendio;
import ec.edu.epn.alerfire.model.EstacionBomberos;
import ec.edu.epn.alerfire.model.Incendio;

public class GestorDespacho implements IServicioDespacho {

    // Refactorización #4 (Fowler): Replace Magic Literal with Symbolic Constant
    public static final double VELOCIDAD_PROMEDIO_KMH = 40.0;
    public static final double MINUTOS_POR_HORA = 60.0;
    public static final double DISTANCIA_PROXIMIDAD_KM = 0.5;
    public static final int TIEMPO_ALISTAMIENTO_MIN = 2;
    public static final int ETA_MINIMO_OPERATIVO_MIN = 3;
    public static final int UMBRAL_RESPUESTA_EXTENDIDA_MIN = 15;

    private IEstacionDAO estacionDAO;

    public GestorDespacho() {
    }

    public GestorDespacho(IEstacionDAO estacionDAO) {
        this.estacionDAO = estacionDAO;
    }

    @Override
    public boolean asignarEstacion(Incendio conato, EstacionBomberos estacion) {
        // Refactorización #2 (Fowler): Replace Nested Conditional with Guard Clauses
        if (conato == null || estacion == null) {
            throw new IllegalArgumentException("El conato y la estación son obligatorios");
        }
        if (conato.getEstado() != EstadoIncendio.REPORTADO) {
            throw new IllegalArgumentException("Solo un conato en estado REPORTADO admite despacho");
        }
        if (!estacion.isDisponible()) {
            throw new IllegalStateException("La estación seleccionada no se encuentra disponible");
        }

        // Refactorización #1 (Fowler): Extract Method
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
        if (distanciaKm <= DISTANCIA_PROXIMIDAD_KM) {
            return ETA_MINIMO_OPERATIVO_MIN;
        }
        double horasTraslado = distanciaKm / VELOCIDAD_PROMEDIO_KMH;
        int minutosTraslado = (int) Math.ceil(horasTraslado * MINUTOS_POR_HORA);
        return TIEMPO_ALISTAMIENTO_MIN + minutosTraslado;
    }

    @Override
    public boolean esRespuestaExtendida(double distanciaKm) {
        return calcularTiempoArriboMinutos(distanciaKm) > UMBRAL_RESPUESTA_EXTENDIDA_MIN;
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
        // Refactorización #3 (Fowler): Decompose Conditional / Introduce Explaining Variable
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