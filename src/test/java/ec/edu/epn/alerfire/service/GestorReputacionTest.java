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
        // TODO: Martín Dávalos debe implementar el test aquí (Given-When-Then)
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
    @Test
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
        // TODO: María Fernanda Alquinga debe implementar el test con Mockito aquí
    }
}
