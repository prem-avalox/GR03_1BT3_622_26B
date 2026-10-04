package ec.edu.epn.alerfire.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Ubicacion implements Serializable {

    private double latitud;
    private double longitud;
    private String direccion;

    public Ubicacion() {
    }

    public Ubicacion(double latitud, double longitud, String direccion) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
    }

    /**
     * Calcula la distancia en kilómetros entre esta ubicación y otra utilizando la fórmula de Haversine.
     */
    public double distancia(Ubicacion otra) {
        if (otra == null) return 0.0;
        final int R = 6371; // Radio de la Tierra en km
        double latDist = Math.toRadians(otra.latitud - this.latitud);
        double lonDist = Math.toRadians(otra.longitud - this.longitud);
        double a = Math.sin(latDist / 2) * Math.sin(latDist / 2)
                + Math.cos(Math.toRadians(this.latitud)) * Math.cos(Math.toRadians(otra.latitud))
                * Math.sin(lonDist / 2) * Math.sin(lonDist / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round(R * c * 100.0) / 100.0;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ubicacion ubicacion = (Ubicacion) o;
        return Double.compare(ubicacion.latitud, latitud) == 0 &&
               Double.compare(ubicacion.longitud, longitud) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitud, longitud);
    }

    @Override
    public String toString() {
        return direccion != null && !direccion.isEmpty() ? direccion : (latitud + ", " + longitud);
    }
}
