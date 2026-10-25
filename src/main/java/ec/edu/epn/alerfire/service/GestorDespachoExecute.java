package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.EstacionBomberos;
import ec.edu.epn.alerfire.model.Incendio;

public class GestorDespachoExecute {

    public static void main(String[] args) {
        System.out.println("== AlerFire: GestorDespacho Execute (Incremento XP) ==");

        GestorDespacho gestor = new GestorDespacho();
        Incendio conato = new Incendio();
        EstacionBomberos estacionA = new EstacionBomberos("B1", "Estacion Operativa A", true, 5.0);

        // HU-03: Estimacion de Tiempo de Arribo (ETA)
        int etaMinutos = gestor.calcularTiempoArriboMinutos(estacionA.getDistanciaKmReferencia());
        boolean extendida = gestor.esRespuestaExtendida(estacionA.getDistanciaKmReferencia());
        System.out.println("[HU-03] Distancia: " + estacionA.getDistanciaKmReferencia() + " km -> ETA estimado: "
                + etaMinutos + " min (Respuesta Extendida: " + extendida + ")");

        // HU-01: Despacho de Estacion Disponible
        boolean despachado = gestor.asignarEstacion(conato, estacionA);
        System.out.println("[HU-01] Despacho exitoso: " + despachado
                + " | Estado Conato: " + conato.getEstado()
                + " | Estacion Disponible: " + estacionA.isDisponible());

        // HU-02: Liquidacion de Emergencia y Liberacion Automatica
        boolean finalizado = gestor.finalizarAtencion(conato);
        System.out.println("[HU-02] Liquidacion exitosa: " + finalizado
                + " | Estado Final Conato: " + conato.getEstado()
                + " | Estacion Liberada (Disponible): " + estacionA.isDisponible());

        System.out.println("== Ejecucion de Incremento XP completada con exito ==");
    }
}
