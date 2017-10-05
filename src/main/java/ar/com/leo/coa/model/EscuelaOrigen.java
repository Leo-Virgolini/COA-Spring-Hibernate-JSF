package ar.com.leo.coa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Leo
 */
@Entity
@Table(name = "escuelas_origen")
public class EscuelaOrigen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @NotNull
    private Integer id;

//    @Column(name = "privada")
//    private boolean privada;
    @OneToMany(mappedBy = "escuelaOrigen")  // pointing Alumno's escuelaOrigen field
//    @Column(name = "id_alumno") // inverse=true
    private List<Alumno> alumnos;

    @ManyToOne
    @JoinColumn(name = "id_localidad") // inverse = false
    private Localidad localidad;

//    @Column(name = "localidad")
//    private String localidad;
//    @ManyToOne
//    @JoinColumn(name = "id_provincia") // inverse = false
//    private Provincia provincia;
    
    @Column(name = "nombre")
    @NotBlank
    private String nombre;

    @Column(name = "sector")
    private String sector;

    @Column(name = "ambito")
    private String ambito;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "cp")
    private String cp;

    @Column(name = "telefono")
    private String telefono;

//    @Column(name = "distrito")
//    private String distrito;
    @Column(name = "email")
    private String email;

    public EscuelaOrigen() {
        localidad = new Localidad();
        alumnos = new ArrayList<Alumno>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

//    public boolean isPrivada() {
//        return privada;
//    }
//
//    public void setPrivada(boolean privada) {
//        this.privada = privada;
//    }
    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

//    public String getDistrito() {
//        return distrito;
//    }
//
//    public void setDistrito(String distrito) {
//        this.distrito = distrito;
//    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getLocalidad() {
//        return localidad;
//    }
//
//    public void setLocalidad(String localidad) {
//        this.localidad = localidad;
//    }
}
