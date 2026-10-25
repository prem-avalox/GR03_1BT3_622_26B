package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.Ciudadano;
import ec.edu.epn.alerfire.model.EstadoIncendio;
import ec.edu.epn.alerfire.model.EstacionBomberos;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.model.Ubicacion;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class GestorDespachoTest {

    private GestorDespacho gestor;
    private Incendio conatoReportado;
    private EstacionBomberos estacionDisponible;
    private EstacionBomberos estacionOcupada;

    @Before
    public void setUp() {
        gestor = new GestorDespacho();
        Ciudadano ciudadano = new Ciudadano("Juan Pérez", "juan@epn.edu.ec", "1234", "0999999999");
        Ubicacion ubicacionConato = new Ubicacion(-0.180653, -78.467834, "Sector Parque Metropolitano");
        conatoReportado = new Incendio("Conato de incendio en ladera con humo visible", ubicacionConato, ciudadano);
        conatoReportado.setEstado(EstadoIncendio.REPORTADO);

        estacionDisponible = new EstacionBomberos("EST-A", "Estación A", true, 2.0);
        estacionOcupada = new EstacionBomberos("EST-B", "Estación B", false, 4.0);
    }

    @Test
    public void given_available_station_and_reported_fire_when_assign_then_fire_in_process_and_station_busy() {
        // Given: conatoReportado en estado REPORTADO y estacionDisponible (disponible = true) en setUp()

        // When: el comandante asigna la estación disponible al conato
        boolean asignado = gestor.asignarEstacion(conatoReportado, estacionDisponible);

        // Then: la operación es exitosa, el conato pasa a EN_PROCESO_DE_ATENCION y la estación queda ocupada
        assertTrue(asignado);
        assertEquals(EstadoIncendio.EN_PROCESO_DE_ATENCION, conatoReportado.getEstado());
        assertEquals(estacionDisponible, conatoReportado.getEstacionAsignada());
        assertFalse(estacionDisponible.isDisponible());
    }
}