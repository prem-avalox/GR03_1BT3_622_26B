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

    public static final int LIMITE_FALSAS_ALARMAS = 3;
    public static final int PENALIZACION_FALSA_ALARMA = 100;
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
        // TODO: Implementar el resto de la lógica de evaluación de CU5.
        // Martín: +50 puntos si el reporte es verídico.
        // Adayely: IllegalStateException si el ciudadano ya está suspendido.
        // María Fernanda: actualizar la base de datos si ciudadanoDAO != null.

        if (!esVeridico) {
            int nuevoPuntaje = Math.max(
                    0,
                    ciudadano.getPuntosReputacion() - PENALIZACION_FALSA_ALARMA
            );
            ciudadano.setPuntosReputacion(nuevoPuntaje);

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
        // TODO: Validar que el ciudadano no sea nulo y no esté suspendido.
        return false;
    }
}