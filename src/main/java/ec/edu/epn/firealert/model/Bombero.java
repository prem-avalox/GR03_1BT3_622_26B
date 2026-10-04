package ec.edu.epn.firealert.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bomberos")
public class Bombero extends Usuario {

    @Column(length = 50)
    private String cargo;

    @Column(length = 100)
    private String estacion;

    public Bombero() {
        super();
    }

    public Bombero(String nombre, String email, String password, String cargo, String estacion) {
        super(nombre, email, password);
        this.cargo = cargo;
        this.estacion = estacion;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }
}
