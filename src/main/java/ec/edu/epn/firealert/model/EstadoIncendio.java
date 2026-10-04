package ec.edu.epn.firealert.model;

public enum EstadoIncendio {
    REPORTADO("Reportado", "badge bg-secondary"),
    EN_PROCESO_DE_ATENCION("En Proceso de Atención", "badge bg-warning text-dark"),
    NO_CONTROLADO("No Controlado", "badge bg-danger"),
    BAJO_CONTROL("Bajo Control", "badge bg-info text-dark"),
    EXTINTO("Extinto", "badge bg-success");

    private final String descripcion;
    private final String badgeClass;

    EstadoIncendio(String descripcion, String badgeClass) {
        this.descripcion = descripcion;
        this.badgeClass = badgeClass;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getBadgeClass() {
        return badgeClass;
    }
}
