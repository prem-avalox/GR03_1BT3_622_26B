package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.Ciudadano;

/**
 * Contrato de persistencia para ciudadanos, utilizado para aislamiento
 * de base de datos durante pruebas unitarias con Mockito.
 */
public interface ICiudadanoDAO {
    Ciudadano buscarPorId(Long id);
    void actualizar(Ciudadano ciudadano);
}
