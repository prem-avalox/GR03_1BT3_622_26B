package ec.edu.epn.firealert.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "evidencias")
public class Evidencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String archivoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoEvidencia tipo;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incendio_id", nullable = false)
    private Incendio incendio;

    public Evidencia() {
        this.fecha = LocalDateTime.now();
        this.tipo = TipoEvidencia.FOTO;
    }

    public Evidencia(String archivoUrl, TipoEvidencia tipo) {
        this();
        this.archivoUrl = archivoUrl;
        this.tipo = tipo;
    }

    /**
     * Valida que la evidencia tenga un formato y ruta/URL válidos.
     */
    public boolean validar() {
        if (archivoUrl == null || archivoUrl.trim().isEmpty()) {
            return false;
        }
        String urlLower = archivoUrl.toLowerCase();
        return urlLower.endsWith(".jpg") || urlLower.endsWith(".jpeg") ||
               urlLower.endsWith(".png") || urlLower.endsWith(".webp") ||
               urlLower.endsWith(".mp4") || urlLower.startsWith("http://") ||
               urlLower.startsWith("https://") || urlLower.startsWith("/uploads/");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArchivoUrl() {
        return archivoUrl;
    }

    public void setArchivoUrl(String archivoUrl) {
        this.archivoUrl = archivoUrl;
    }

    public TipoEvidencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoEvidencia tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Incendio getIncendio() {
        return incendio;
    }

    public void setIncendio(Incendio incendio) {
        this.incendio = incendio;
    }
}
