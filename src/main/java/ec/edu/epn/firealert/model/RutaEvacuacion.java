package ec.edu.epn.firealert.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rutas_evacuacion")
public class RutaEvacuacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 300)
    private String descripcion;

    @Column(nullable = false)
    private double distanciaKm;

    @Column(nullable = false)
    private double nivelRiesgo; // 0.0 (Bajo) a 10.0 (Crítico)

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitud", column = @Column(name = "origen_lat")),
        @AttributeOverride(name = "longitud", column = @Column(name = "origen_lng")),
        @AttributeOverride(name = "direccion", column = @Column(name = "origen_dir"))
    })
    private Ubicacion origen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "zona_segura_id")
    private ZonaSegura zonaSeguraDestino;

    @Lob
    @Column(name = "trazado_json")
    private String trazadoJson; // Coordenadas en formato JSON: [[lat1, lng1], [lat2, lng2], ...]

    public RutaEvacuacion() {
    }

    public RutaEvacuacion(String nombre, String descripcion, double distanciaKm, double nivelRiesgo, Ubicacion origen, ZonaSegura zonaSeguraDestino, String trazadoJson) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.distanciaKm = distanciaKm;
        this.nivelRiesgo = nivelRiesgo;
        this.origen = origen;
        this.zonaSeguraDestino = zonaSeguraDestino;
        this.trazadoJson = trazadoJson;
    }

    /**
     * Retorna la lista de puntos/ubicaciones que componen el trazado de la ruta.
     */
    public List<Ubicacion> obtenerTrazado() {
        List<Ubicacion> puntos = new ArrayList<>();
        if (origen != null) {
            puntos.add(origen);
        }
        if (zonaSeguraDestino != null && zonaSeguraDestino.getUbicacion() != null) {
            puntos.add(zonaSeguraDestino.getUbicacion());
        }
        return puntos;
    }

    public String getNivelRiesgoTexto() {
        if (nivelRiesgo <= 2.5) return "Bajo";
        if (nivelRiesgo <= 5.5) return "Moderado";
        if (nivelRiesgo <= 7.5) return "Alto";
        return "Crítico";
    }

    public String getNivelRiesgoBadge() {
        if (nivelRiesgo <= 2.5) return "badge bg-success";
        if (nivelRiesgo <= 5.5) return "badge bg-warning text-dark";
        if (nivelRiesgo <= 7.5) return "badge bg-danger";
        return "badge bg-dark";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public double getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(double nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public Ubicacion getOrigen() {
        return origen;
    }

    public void setOrigen(Ubicacion origen) {
        this.origen = origen;
    }

    public ZonaSegura getZonaSeguraDestino() {
        return zonaSeguraDestino;
    }

    public void setZonaSeguraDestino(ZonaSegura zonaSeguraDestino) {
        this.zonaSeguraDestino = zonaSeguraDestino;
    }

    public String getTrazadoJson() {
        return trazadoJson;
    }

    public void setTrazadoJson(String trazadoJson) {
        this.trazadoJson = trazadoJson;
    }
}
