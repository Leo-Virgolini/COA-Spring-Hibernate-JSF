package ar.com.leo.coa.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "materias_x_profesor") //materias que puede dar el Profesor
public class MateriaProfesor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    // relacion con materias_total (MateriaComision)
    @ManyToOne
    @JoinColumn(name = "id_materia_comision") // inverse = false
    private MateriaComision materiaComision;

//    @Column(name = "anio")
//    @NotBlank(message = "Ingresa el año de la materia.")
//    private String anio;
    
    @Column(name = "nota")
    private Double nota;

    // relacion con comisiones (Comision)
//    @ManyToOne
//    @JoinColumn(name = "numero_comision")  // inverse = false
//    private Comision comision;
    
    // relacion con profesores
    @ManyToOne // 
    @JoinColumn(name = "id_profesor") // inverse = false
    @NotNull
    private Profesor profesor;

    public MateriaProfesor() {
        materiaComision = new MateriaComision();
        profesor = new Profesor();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.materiaComision);
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
        final MateriaProfesor other = (MateriaProfesor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.materiaComision, other.materiaComision)) {
            return false;
        }
        if (!Objects.equals(this.nota, other.nota)) {
            return false;
        }
        if (!Objects.equals(this.profesor, other.profesor)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MateriaProfesor{" + "id=" + id + ", materiaComision=" + materiaComision + ", nota=" + nota + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getAnio() {
//        return anio;
//    }
//
//    public void setAnio(String anio) {
//        this.anio = anio;
//    }
    
    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public MateriaComision getMateriaComision() {
        return materiaComision;
    }

    public void setMateriaComision(MateriaComision materiaComision) {
        this.materiaComision = materiaComision;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

}
