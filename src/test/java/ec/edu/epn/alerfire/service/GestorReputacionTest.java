package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.Ciudadano;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Suite de Pruebas Unitarias para GestorReputacion
*/
public class GestorReputacionTest {

    private GestorReputacion gestor;
    private Ciudadano ciudadano;

    @Before
    public void setUp() {
        gestor = new GestorReputacion();
        ciudadano = new Ciudadano("Carlos Mendoza", "carlos.mendoza@epn.edu.ec", "segura123", "0991234567", 100);
    }

    @After
    public void tearDown() {
        gestor = null;
        ciudadano = null;
    }

    // =========================================================================
    // TEST 1 (Unitario): Martín Dávalos
    // Requerimiento CU5: Reporte verídico otorga +50 puntos de reputación.
    // =========================================================================
    @Test
    public void given_veridical_report_when_evaluate_then_reputation_increased() {
        // Given: ciudadano con 100 puntos iniciales (proveniente de setUp)
        int puntosEsperados = 150;

        // When: el bombero certifica que el reporte fue verídico (esVeridico = true)
        int resultado = gestor.evaluarVeracidad(ciudadano, true);

        // Then: el ciudadano incrementa su puntaje en +50
        assertEquals(puntosEsperados, resultado);
    }

    // =========================================================================
    // TEST 2 (Unitario): Nayia Cóndor
    // Requerimiento CU5: Falsa alarma penaliza -100 puntos con piso en cero.
    // =========================================================================
    @Test
    public void given_false_alarm_when_evaluate_then_reputation_decreased_with_floor_zero() {
        ciudadano.setPuntosReputacion(60);

        gestor.evaluarVeracidad(ciudadano, false);

        assertEquals(0, ciudadano.getPuntosReputacion());
    }

    // =========================================================================
    // TEST 3 (Unitario): Anthony Minayo
    // Requerimiento CU5: Al acumular 3 falsas alarmas se suspende preventivamente.
    // =========================================================================
    @Test
    public void given_three_false_alarms_when_evaluate_then_citizen_suspended() {
        // Given: ciudadano con 2 falsas alarmas registradas previamente
        ciudadano.setReportesFalsos(2);

        // When: el bombero certifica una nueva falsa alarma (la 3ra)
        gestor.evaluarVeracidad(ciudadano, false);

        // Then: acumula 3 faltas y su estado de suspensión pasa a true
        assertEquals(3, ciudadano.getReportesFalsos());
        assertTrue(ciudadano.isSuspendido());
    }

    // =========================================================================
    // TEST 4 (Unitario - Excepciones): Adayely Rochina
    // Requerimiento CU5: Lanzar excepción si el ciudadano ya está suspendido.
    // =========================================================================
    @Test(expected = IllegalStateException.class)
    public void given_suspended_citizen_when_evaluate_then_throws_exception() {
        // Given: un ciudadano que ya se encuentra suspendido preventivamente
        ciudadano.setSuspendido(true);

        // When: el bombero intenta certificar un nuevo reporte para este ciudadano
        gestor.evaluarVeracidad(ciudadano, true);

        // Then: el sistema lanza IllegalStateException (manejado por el expected del @Test)
    }

    // =========================================================================
    // TEST 5 (Con Mocks - Mockito): María Fernanda Alquinga
    // Requerimiento CU5: Aislamiento de base de datos simulando ICiudadanoDAO.
    // =========================================================================
    @Test
    public void given_mock_dao_when_evaluar_veracidad_then_dao_updated_ok() {
        // Given: creación del mock para aislar la capa de persistencia
        ICiudadanoDAO mockDAO = org.mockito.Mockito.mock(ICiudadanoDAO.class);
        GestorReputacion gestorConPersistencia = new GestorReputacion(mockDAO);

        // When: se evalúa un reporte verídico
        int resultado = gestorConPersistencia.evaluarVeracidad(ciudadano, true);

        // Then: valida cálculo y comprueba que el mock haya sido invocado con verify
        assertEquals(150, resultado);
        org.mockito.Mockito.verify(mockDAO, org.mockito.Mockito.times(1)).actualizar(ciudadano);
    }

    // =========================================================================
    // TEST 6 (Con Mocks - Mockito - Verificación never): María Fernanda Alquinga
    // Requerimiento CU5 / Rúbrica: Al menos 2 pruebas con Mocks.
    // =========================================================================
    @Test
    public void given_mock_dao_when_citizen_suspended_then_dao_never_updated() {
        // Given: creación del mock y ciudadano que se encuentra suspendido
        ICiudadanoDAO mockDAO = org.mockito.Mockito.mock(ICiudadanoDAO.class);
        GestorReputacion gestorConPersistencia = new GestorReputacion(mockDAO);
        ciudadano.setSuspendido(true);

        // When: se intenta evaluar el reporte (se espera fallo por regla de suspensión)
        try {
            gestorConPersistencia.evaluarVeracidad(ciudadano, true);
            fail("Se esperaba IllegalStateException por ciudadano suspendido");
        } catch (IllegalStateException e) {
            // Then: verifica que el DAO jamás fue invocado ante un error de validación
            org.mockito.Mockito.verify(mockDAO, org.mockito.Mockito.never()).actualizar(ciudadano);
        }
    }
}
