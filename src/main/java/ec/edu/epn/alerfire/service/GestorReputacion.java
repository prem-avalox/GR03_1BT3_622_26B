package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.Ciudadano;
import ec.edu.epn.alerfire.model.NivelConfiabilidad;

public class GestorReputacion implements IServicioReputacion {

    public static final int PUNTOS_REPORTE_VERIDICO = 50;
    public static final int PENALIZACION_FALSA_ALARMA = 100;
    public static final int LIMITE_FALSAS_ALARMAS = 3;

    private final ICiudadanoDAO ciudadanoDAO;

    public GestorReputacion() {
        this.ciudadanoDAO = null;
    }

    public GestorReputacion(ICiudadanoDAO ciudadanoDAO) {
        this.ciudadanoDAO = ciudadanoDAO;
    }

    @Override
    public int evaluarVeracidad(Ciudadano ciudadano, boolean esVeridico) {

        if (ciudadano == null) {
            throw new IllegalArgumentException("El ciudadano no puede ser nulo");
        }
        if (ciudadano.isSuspendido()) {
            throw new IllegalStateException("El ciudadano está suspendido preventivamente");
        }
        
        if (esVeridico) {
            ciudadano.setPuntosReputacion(ciudadano.getPuntosReputacion() + PUNTOS_REPORTE_VERIDICO);
        } else {

            int nuevoPuntaje = Math.max(0, ciudadano.getPuntosReputacion() - PENALIZACION_FALSA_ALARMA);
            ciudadano.setPuntosReputacion(nuevoPuntaje);
            int faltas = ciudadano.getReportesFalsos() + 1;
            ciudadano.setReportesFalsos(faltas);

            if (faltas >= LIMITE_FALSAS_ALARMAS) {
                ciudadano.setSuspendido(true);
            }
        }

        if (ciudadanoDAO != null) {
            ciudadanoDAO.actualizar(ciudadano);
        }
        return ciudadano.getPuntosReputacion();
    }

    @Override
    public NivelConfiabilidad clasificarNivelConfiabilidad(int puntos) {
        if (puntos >= 300) {
            return NivelConfiabilidad.ORO;
        } else if (puntos >= 100) {
            return NivelConfiabilidad.PLATA;
        } else {
            return NivelConfiabilidad.BRONCE;
        }
    }

    @Override
    public boolean validarAcceso(Ciudadano ciudadano) {
        if (ciudadano == null) {
            return false;
        }
        return !ciudadano.isSuspendido();
    }
}