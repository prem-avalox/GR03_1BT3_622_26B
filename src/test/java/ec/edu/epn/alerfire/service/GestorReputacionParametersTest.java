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
        // TODO: Agregar combinaciones de puntajes y niveles esperados
        return objects;
    }

    public GestorReputacionParametersTest(int puntos, NivelConfiabilidad esperado) {
        this.puntos = puntos;
        this.esperado = esperado;
    }

    @Test
    public void given_parameters_when_clasificar_nivel_then_ok() {
        // TODO: Implementar prueba parametrizada (Given-When-Then)
    }
}
