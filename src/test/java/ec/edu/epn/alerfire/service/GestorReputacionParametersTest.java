package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.NivelConfiabilidad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Prueba Parametrizada en JUnit 4 para el Caso de Uso CU6:
 * Consultar Perfil de Reputación y Confiabilidad Comunitaria.
 * 
 * ESTRUCTURA BASE PARA TRABAJO GRUPAL:
 * Esqueleto inicial con @RunWith(Parameterized.class).
 * Deben poblar la colección en parameters() e implementar la aserción en el test.
 */
@RunWith(Parameterized.class)
public class GestorReputacionParametersTest {

    private final int puntos;
    private final NivelConfiabilidad esperado;

    @Parameterized.Parameters
    public static Iterable<Object[]> parameters() {
        List<Object[]> objects = new ArrayList<>();
        // Partición de equivalencia y valores frontera para CU6:
        // Nivel Bronce: 0 a 99 puntos
        objects.add(new Object[]{0, NivelConfiabilidad.BRONCE});
        objects.add(new Object[]{50, NivelConfiabilidad.BRONCE});
        objects.add(new Object[]{99, NivelConfiabilidad.BRONCE});

        // Nivel Plata: 100 a 299 puntos
        objects.add(new Object[]{100, NivelConfiabilidad.PLATA});
        objects.add(new Object[]{250, NivelConfiabilidad.PLATA});
        objects.add(new Object[]{299, NivelConfiabilidad.PLATA});

        // Nivel Oro: 300 puntos o más
        objects.add(new Object[]{300, NivelConfiabilidad.ORO});
        objects.add(new Object[]{500, NivelConfiabilidad.ORO});
        return objects;
    }

    public GestorReputacionParametersTest(int puntos, NivelConfiabilidad esperado) {
        this.puntos = puntos;
        this.esperado = esperado;
    }

    @Test
    public void given_parameters_when_clasificar_nivel_then_ok() {
        // Given: una instancia de GestorReputacion
        GestorReputacion gestor = new GestorReputacion();

        // When: se evalúa la clasificación de confiabilidad con los puntos recibidos
        NivelConfiabilidad resultado = gestor.clasificarNivelConfiabilidad(this.puntos);

        // Then: el nivel clasificado coincide exactamente con el nivel esperado
        assertEquals(this.esperado, resultado);
    }
}
