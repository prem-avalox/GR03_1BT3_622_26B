package ec.edu.epn.alerfire.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "estaciones_bomberos")
public class EstacionBomberos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String codigo;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false)
    private boolean disponible = true;

    @Column(nullable = false)
    private double distanciaKmReferencia;

    @Embedded
    private Ubicacion ubicacion;

    public EstacionBomberos() {
        this.disponible = true;
    }

    public EstacionBomberos(String codigo, String nombre, boolean disponible, double distanciaKmReferencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.disponible = disponible;
        this.distanciaKmReferencia = distanciaKmReferencia;
    }

    public EstacionBomberos(String codigo, String nombre, boolean disponible, double distanciaKmReferencia, Ubicacion ubicacion) {
        this(codigo, nombre, disponible, distanciaKmReferencia);
        this.ubicacion = ubicacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public double getDistanciaKmReferencia() { return distanciaKmReferencia; }
    public void setDistanciaKmReferencia(double distanciaKmReferencia) { this.distanciaKmReferencia = distanciaKmReferencia; }

    public Ubicacion getUbicacion() { return ubicacion; }
    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }

    @Transient
    public int getEtaMinutos() {
        return new ec.edu.epn.alerfire.service.GestorDespacho().calcularTiempoArriboMinutos(this.distanciaKmReferencia);
    }

    @Transient
    public boolean isRespuestaExtendida() {
        return new ec.edu.epn.alerfire.service.GestorDespacho().esRespuestaExtendida(this.distanciaKmReferencia);
    }
}