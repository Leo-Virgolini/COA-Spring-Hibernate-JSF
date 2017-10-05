package ar.com.leo.coa.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Leo
 */
@Entity
@Table(name = "localidades")
public class Localidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    // relacion con provincias (Provincia)
//    @ManyToOne
//    @JoinColumn(name = "id_provincia")  // inverse = false
//    @NotEmpty
//    private Provincia provincia;
    
    // relacion con distritos (Distrito)
    @ManyToOne
    @JoinColumn(name = "id_distrito")  // inverse = false
    private Distrito distrito;

    @Column(name = "descripcion")
    @NotBlank
    private String descripcion;

    // relacion con escuelas_sede (EscuelaSede)
    @OneToMany(mappedBy = "localidad") // pointing EscuelaSede's localidad field
//    @Column(name = "id_escuela_sede") // inverse=true
    private List<EscuelaSede> escuelasSede;

    // relacion con escuelas_origen (EscuelaOrigen)
//    @OneToMany(mappedBy = "localidad") // pointing EscuelaOrigen's localidad field
//    @Column(name = "id_escuelaOrigen") // inverse=true
//    private List<EscuelaOrigen> escuelasOrigen;
    
    // relacion con alumnos (Alumno)
    @OneToMany(mappedBy = "localidad")  // pointing Alumnos's localidad field
//    @Column(name = "id_alumno")    // inverse=true
    private List<Alumno> alumnos;

    // relacion con profesores (Profesor)
    @OneToMany(mappedBy = "localidad")  // pointing Profesore's localidad field
//    @Column(name = "id_profesor")    // inverse=true
    private List<Profesor> profesores;

    public Localidad() {
        distrito = new Distrito();
    }

    public Localidad(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.distrito);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Localidad other = (Localidad) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.distrito, other.distrito)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Localidad{" + "id=" + id + ", distrito=" + distrito + ", descripcion=" + descripcion + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public List<EscuelaSede> getEscuelasSede() {
        return escuelasSede;
    }

    public void setEscuelasSede(List<EscuelaSede> escuelasSede) {
        this.escuelasSede = escuelasSede;
    }

//    public List<EscuelaOrigen> getEscuelasOrigen() {
//        return escuelasOrigen;
//    }
//
//    public void setEscuelasOrigen(List<EscuelaOrigen> escuelasOrigen) {
//        this.escuelasOrigen = escuelasOrigen;
//    }
    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

}
