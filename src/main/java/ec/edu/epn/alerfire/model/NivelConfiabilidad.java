package ec.edu.epn.alerfire.model;

/**
 * Rangos de confiabilidad comunitaria del ciudadano en AlerFire (CU6).
 */
public enum NivelConfiabilidad {
    BRONCE("Nivel Bronce: Confiabilidad Inicial (< 100 pts)"),
    PLATA("Nivel Plata: Ciudadano Comprometido (100 - 299 pts)"),
    ORO("Nivel Oro: Guardián Comunitario Certificado (>= 300 pts)");

    private final String descripcion;

    NivelConfiabilidad(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
