package ec.edu.epn.firealert.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "incendios")
public class Incendio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoIncendio estado;

    @Embedded
    private Ubicacion ubicacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ciudadano_id")
    private Ciudadano ciudadano;

    @OneToMany(mappedBy = "incendio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Evidencia> evidencias = new ArrayList<>();

    @OneToMany(mappedBy = "incendio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("fechaHora DESC")
    private List<HistorialEstado> historial = new ArrayList<>();

    public Incendio() {
        this.fechaHora = LocalDateTime.now();
        this.estado = EstadoIncendio.REPORTADO;
    }

    public Incendio(String descripcion, Ubicacion ubicacion, Ciudadano ciudadano) {
        this();
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.ciudadano = ciudadano;
    }

    /**
     * Valida que el reporte cumpla con los requisitos mínimos según el diagrama de secuencia.
     */
    public boolean validarReporte() {
        if (descripcion == null || descripcion.trim().length() < 5) {
            return false;
        }
        if (ubicacion == null || (ubicacion.getLatitud() == 0.0 && ubicacion.getLongitud() == 0.0)) {
            return false;
        }
        return true;
    }

    /**
     * Cambia el estado del incendio y registra la transición.
     */
    public void cambiarEstado(EstadoIncendio nuevoEstado) {
        if (nuevoEstado != null) {
            this.estado = nuevoEstado;
        }
    }

    /**
     * Retorna una síntesis detallada del incendio y su estado actual.
     */
    public String obtenerDetalles() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fecha = fechaHora != null ? fechaHora.format(formatter) : "Sin fecha";
        String dir = ubicacion != null ? ubicacion.getDireccion() : "Ubicación no especificada";
        return String.format("Incendio #%d - Estado: %s | Ubicación: %s | Reportado: %s",
                id != null ? id : 0,
                estado != null ? estado.getDescripcion() : "Desconocido",
                dir,
                fecha);
    }

    public void agregarEvidencia(Evidencia evidencia) {
        evidencias.add(evidencia);
        evidencia.setIncendio(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoIncendio getEstado() {
        return estado;
    }

    public void setEstado(EstadoIncendio estado) {
        this.estado = estado;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }

    public List<Evidencia> getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(List<Evidencia> evidencias) {
        this.evidencias = evidencias;
    }

    public List<HistorialEstado> getHistorial() {
        return historial;
    }

    public void setHistorial(List<HistorialEstado> historial) {
        this.historial = historial;
    }
}
