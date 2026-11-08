package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.Ciudadano;
import ec.edu.epn.alerfire.model.NivelConfiabilidad;

/**
 * Gestor de negocio para el cálculo de reputación cívica, evaluación de reportes
 * y rangos de confiabilidad en AlerFire.
 *
 * Casos de Uso:
 * - CU5: Evaluar veracidad del reporte y asignar reputación.
 * - CU6: Consultar perfil de reputación y confiabilidad.
 */
public class GestorReputacion implements IServicioReputacion {

    public static final int PUNTOS_REPORTE_VERIDICO = 50;
    public static final int PENALIZACION_FALSA_ALARMA = 100;
    public static final int LIMITE_FALSAS_ALARMAS = 3;
    public static final int UMBRAL_NIVEL_ORO = 300;
    public static final int UMBRAL_NIVEL_PLATA = 100;

    private final ICiudadanoDAO ciudadanoDAO;

    public GestorReputacion() {
        this.ciudadanoDAO = null;
    }

    public GestorReputacion(ICiudadanoDAO ciudadanoDAO) {
        this.ciudadanoDAO = ciudadanoDAO;
    }

   @Override
    public int evaluarVeracidad(Ciudadano ciudadano, boolean esVeridico) {
        // [FASE REFACTOR - Martín Dávalos] Aplicación de Replace Magic Literal with Symbolic Constant
        if (esVeridico) {
            ciudadano.setPuntosReputacion(ciudadano.getPuntosReputacion() + PUNTOS_REPORTE_VERIDICO);
        }
        return ciudadano.getPuntosReputacion();
    }
    
    @Override
    public NivelConfiabilidad clasificarNivelConfiabilidad(int puntos) {
        // FASE REFACTOR (TDD): Retornos tempranos y uso de constantes semánticas
        if (puntos >= UMBRAL_NIVEL_ORO) {
            return NivelConfiabilidad.ORO;
        }
        if (puntos >= UMBRAL_NIVEL_PLATA) {
            return NivelConfiabilidad.PLATA;
        }
        return NivelConfiabilidad.BRONCE;
    }

    @Override
    public boolean validarAcceso(Ciudadano ciudadano) {
        if (ciudadano == null || ciudadano.isSuspendido()) {
            return false;
        }
        return true;
    }
}
