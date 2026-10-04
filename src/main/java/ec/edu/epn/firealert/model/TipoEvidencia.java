package ec.edu.epn.firealert.model;

public enum TipoEvidencia {
    FOTO("Fotografía"),
    VIDEO("Video");

    private final String descripcion;

    TipoEvidencia(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
