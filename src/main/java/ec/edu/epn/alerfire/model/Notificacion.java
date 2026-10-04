package ec.edu.epn.alerfire.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(length = 50)
    private String tipoAlerta; // ej: "NUEVO_INCENDIO", "CAMBIO_ESTADO", "EVACUACION"

    @Column(nullable = false)
    private boolean enviada;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incendio_id")
    private Incendio incendio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receptor_id")
    private Usuario receptor;

    public Notificacion() {
        this.fechaHora = LocalDateTime.now();
        this.enviada = false;
    }

    public Notificacion(String mensaje, String tipoAlerta, Incendio incendio, Usuario receptor) {
        this();
        this.mensaje = mensaje;
        this.tipoAlerta = tipoAlerta;
        this.incendio = incendio;
        this.receptor = receptor;
    }

    /**
     * Simula el envío de la notificación (push / SMS / broadcast a ciudadanos cercanos) según el diagrama de secuencia.
     */
    public boolean enviar() {
        this.enviada = true;
        System.out.println("[ALERTA BROADCAST] " + this.tipoAlerta + ": " + this.mensaje);
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public boolean isEnviada() {
        return enviada;
    }

    public void setEnviada(boolean enviada) {
        this.enviada = enviada;
    }

    public Incendio getIncendio() {
        return incendio;
    }

    public void setIncendio(Incendio incendio) {
        this.incendio = incendio;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }
}
