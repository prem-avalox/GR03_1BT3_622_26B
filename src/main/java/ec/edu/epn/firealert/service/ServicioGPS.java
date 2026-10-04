package ec.edu.epn.firealert.service;

import ec.edu.epn.firealert.model.Ubicacion;

public class ServicioGPS {

    /**
     * Obtiene la ubicación actual del usuario o dispositivo según el diagrama de secuencia.
     * Retorna por defecto coordenadas de referencia en Quito (ej. sector Iñaquito / La Carolina).
     */
    public Ubicacion obtenerUbicacion() {
        return new Ubicacion(-0.180653, -78.467838, "Av. Amazonas y República, Quito, Ecuador");
    }

    public Ubicacion obtenerUbicacion(Double lat, Double lng, String direccion) {
        if (lat == null || lng == null) {
            return obtenerUbicacion();
        }
        String dir = (direccion != null && !direccion.trim().isEmpty())
                ? direccion
                : String.format("Lat: %.5f, Lng: %.5f", lat, lng);
        return new Ubicacion(lat, lng, dir);
    }
}
