package ar.com.leo.coa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "materias_total") //materia propia de la Comision
public class MateriaComision implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "nombre")
    @NotBlank(message = "Ingresa el nombre de la materia.")
    private String nombre;

    // relacion con comisiones (Comision)
    @OneToMany(mappedBy = "materiaComision")  // pointing Comision's materiaComision field
//    @Column(name = "numero_comision")    // inverse=true
    private List<Comision> comisiones;

    // relacion con materias_x_profesores (Profesor)
//    @ManyToMany(mappedBy="materiasProfesor")  // map info is in Profesor's class
//    private List<Profesor> profesores;
//    
    // relacion con materias_x_alumno (MateriaAlumno)
    @OneToMany(mappedBy = "materiaComision") // pointing MateriaAlumno's materiaComision field
//    @Column(name = "id_materia") // inverse=true
    private List<MateriaAlumno> materiasAlumno;

    public MateriaComision() {
        comisiones = new ArrayList<Comision>();
        materiasAlumno = new ArrayList<MateriaAlumno>();
    }

    @Override
    public String toString() {
        return "MateriaComision{" + "id=" + id + ", nombre=" + nombre + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final MateriaComision other = (MateriaComision) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
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

    public List<Comision> getComisiones() {
        return comisiones;
    }

    public void setComisiones(List<Comision> comisiones) {
        this.comisiones = comisiones;
    }

    public List<MateriaAlumno> getMateriasAlumno() {
        return materiasAlumno;
    }

    public void setMateriasAlumno(List<MateriaAlumno> materiasAlumno) {
        this.materiasAlumno = materiasAlumno;
    }

}
