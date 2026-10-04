package ec.edu.epn.firealert.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ciudadanos")
public class Ciudadano extends Usuario {

    @Column(length = 20)
    private String telefono;

    public Ciudadano() {
        super();
    }

    public Ciudadano(String nombre, String email, String password, String telefono) {
        super(nombre, email, password);
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
