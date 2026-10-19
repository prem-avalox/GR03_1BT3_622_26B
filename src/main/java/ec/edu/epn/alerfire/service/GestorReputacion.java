package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.Ciudadano;
import ec.edu.epn.alerfire.model.NivelConfiabilidad;

/**
 * Gestor de negocio para el cálculo de reputación cívica, evaluación de reportes
 * y rangos de confiabilidad en AlerFire.
 * 
 * Casos de Uso del Incremento:
 * - CU5: Evaluar veracidad del reporte y asignar reputación.
 * - CU6: Consultar perfil de reputación y confiabilidad.
 * 
 * ESTRUCTURA BASE PARA TRABAJO EN EQUIPO (TDD):
 * Cada integrante debe escribir primero su prueba en GestorReputacionTest (Fase RED),
 * verificar que falle, y luego implementar aquí el código mínimo necesario (Fase GREEN).
 */
public class GestorReputacion implements IServicioReputacion {

    public static final int LIMITE_FALSAS_ALARMAS = 3;
    public static final int UMBRAL_NIVEL_PLATA = 100;
    public static final int UMBRAL_NIVEL_ORO = 300;

    private final ICiudadanoDAO ciudadanoDAO;

    public GestorReputacion() {
        this.ciudadanoDAO = null;
    }

    public GestorReputacion(ICiudadanoDAO ciudadanoDAO) {
        this.ciudadanoDAO = ciudadanoDAO;
    }

    @Override
    public int evaluarVeracidad(Ciudadano ciudadano, boolean esVeridico) {
        if (!esVeridico) {
            int faltas = ciudadano.getReportesFalsos() + 1;
            ciudadano.setReportesFalsos(faltas);
            if (faltas >= LIMITE_FALSAS_ALARMAS) {
                ciudadano.setSuspendido(true);
            }
        }
        return ciudadano.getPuntosReputacion();
    }

    @Override
    public NivelConfiabilidad clasificarNivelConfiabilidad(int puntos) {
        if (puntos >= UMBRAL_NIVEL_ORO) {
            return NivelConfiabilidad.ORO;
        } else if (puntos >= UMBRAL_NIVEL_PLATA) {
            return NivelConfiabilidad.PLATA;
        } else {
            return NivelConfiabilidad.BRONCE;
        }
    }

    @Override
    public boolean validarAcceso(Ciudadano ciudadano) {
        // TODO: Validar que el ciudadano no sea nulo y no se encuentre suspendido.
        return false;
    }
}
