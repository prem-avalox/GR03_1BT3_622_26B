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

    private final ICiudadanoDAO ciudadanoDAO;
    public static final int PENALIZACION_FALSA_ALARMA = 100;

    public GestorReputacion() {
        this.ciudadanoDAO = null;
    }

    public GestorReputacion(ICiudadanoDAO ciudadanoDAO) {
        this.ciudadanoDAO = ciudadanoDAO;
    }

    @Override
    public int evaluarVeracidad(Ciudadano ciudadano, boolean esVeridico) {
        // TODO: Implementar lógica de evaluación de veracidad y reputación (CU5)
        // Martín: +50 puntos si reporte es verídico.
        // Nayia: -100 puntos si es falsa alarma (con piso en cero).
        // Anthony: Suspender preventivamente si acumula 3 falsas alarmas.
        // Adayely: Lanzar IllegalStateException si el ciudadano ya está suspendido.
        // María Fernanda: Actualizar en base de datos si ciudadanoDAO != null.
        if (!esVeridico) {
            int nuevoPuntaje = Math.max(
                    0,
                    ciudadano.getPuntosReputacion() - PENALIZACION_FALSA_ALARMA
            );
            ciudadano.setPuntosReputacion(nuevoPuntaje);
        }

        return ciudadano.getPuntosReputacion();
    }

    @Override
    public NivelConfiabilidad clasificarNivelConfiabilidad(int puntos) {
        // TODO: Implementar clasificación por rangos (CU6)
        // Bronce: < 100 puntos
        // Plata: 100 a 299 puntos
        // Oro: >= 300 puntos
        return null;
    }

    @Override
    public boolean validarAcceso(Ciudadano ciudadano) {
        // TODO: Validar que el ciudadano no sea nulo y no se encuentre suspendido.
        return false;
    }
}
