package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.Ciudadano;
import ec.edu.epn.alerfire.model.NivelConfiabilidad;

/**
 * Contrato de interfaz del servicio de reputación y confiabilidad comunitaria.
 * Sigue el diseño TDD con interfaces para habilitar aislamiento mediante Mockito.
 */
public interface IServicioReputacion {
    int evaluarVeracidad(Ciudadano ciudadano, boolean esVeridico);
    NivelConfiabilidad clasificarNivelConfiabilidad(int puntos);
    boolean validarAcceso(Ciudadano ciudadano);
}
