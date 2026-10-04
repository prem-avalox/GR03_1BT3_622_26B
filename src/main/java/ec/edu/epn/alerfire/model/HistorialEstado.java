package ec.edu.epn.alerfire.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_estados")
public class HistorialEstado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private EstadoIncendio estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoIncendio estadoNuevo;

    @Column(length = 255)
    private String observacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bombero_id")
    private Bombero bombero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incendio_id", nullable = false)
    private Incendio incendio;

    public HistorialEstado() {
        this.fechaHora = LocalDateTime.now();
    }

    public HistorialEstado(EstadoIncendio estadoAnterior, EstadoIncendio estadoNuevo, Bombero bombero, Incendio incendio, String observacion) {
        this();
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.bombero = bombero;
        this.incendio = incendio;
        this.observacion = observacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoIncendio getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(EstadoIncendio estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public EstadoIncendio getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(EstadoIncendio estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Bombero getBombero() {
        return bombero;
    }

    public void setBombero(Bombero bombero) {
        this.bombero = bombero;
    }

    public Incendio getIncendio() {
        return incendio;
    }

    public void setIncendio(Incendio incendio) {
        this.incendio = incendio;
    }
}
