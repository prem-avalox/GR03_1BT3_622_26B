package ec.edu.epn.alerfire.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ciudadanos")
public class Ciudadano extends Usuario {

    @Column(length = 20)
    private String telefono;

    @Column(name = "puntos_reputacion")
    private int puntosReputacion = 100;

    @Column(name = "reportes_falsos")
    private int reportesFalsos = 0;

    @Column(name = "suspendido")
    private boolean suspendido = false;

    public Ciudadano() {
        super();
    }

    public Ciudadano(String nombre, String email, String password, String telefono) {
        super(nombre, email, password);
        this.telefono = telefono;
        this.puntosReputacion = 100;
        this.reportesFalsos = 0;
        this.suspendido = false;
    }

    public Ciudadano(String nombre, String email, String password, String telefono, int puntosReputacion) {
        super(nombre, email, password);
        this.telefono = telefono;
        this.puntosReputacion = puntosReputacion;
        this.reportesFalsos = 0;
        this.suspendido = false;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getPuntosReputacion() {
        return puntosReputacion;
    }

    public void setPuntosReputacion(int puntosReputacion) {
        this.puntosReputacion = puntosReputacion;
    }

    public int getReportesFalsos() {
        return reportesFalsos;
    }

    public void setReportesFalsos(int reportesFalsos) {
        this.reportesFalsos = reportesFalsos;
    }

    public boolean isSuspendido() {
        return suspendido;
    }

    public void setSuspendido(boolean suspendido) {
        this.suspendido = suspendido;
    }
}
