package ec.edu.epn.alerfire.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class GestorDespachoParametersTest {

    private final double distanciaKm;
    private final int minutosEsperados;
    private final boolean extendidaEsperada;
    private GestorDespacho gestor;

    public GestorDespachoParametersTest(double distanciaKm, int minutosEsperados, boolean extendidaEsperada) {
        this.distanciaKm = distanciaKm;
        this.minutosEsperados = minutosEsperados;
        this.extendidaEsperada = extendidaEsperada;
    }

    @Parameters(name = "Caso {index}: distancia={0} km -> ETA={1} min (Extendida={2})")
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                // Escenario 2: Proximidad inmediata (<= 0.5 km) -> ETA mínimo 3 min
                {0.0, 3, false},
                {0.5, 3, false},
                // Escenario 1: Distancia urbana media (<= 15 min)
                {2.0, 5, false},
                {4.0, 8, false},
                {5.0, 10, false},
                {8.0, 14, false},
                // Escenario 3: Distancia lejana (> 15 min -> Respuesta Extendida)
                {10.0, 17, true},
                {12.0, 20, true}
        });
    }

    @Before
    public void setUp() {
        gestor = new GestorDespacho();
    }

    @Test
    public void given_distance_in_km_when_calculate_eta_then_return_expected_arrival_minutes() {
        int etaObtenido = gestor.calcularTiempoArriboMinutos(distanciaKm);
        boolean esExtendida = gestor.esRespuestaExtendida(distanciaKm);

        assertEquals(minutosEsperados, etaObtenido);
        assertEquals(extendidaEsperada, esExtendida);
    }
}