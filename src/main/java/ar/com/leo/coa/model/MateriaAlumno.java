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
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "materias_x_alumno") //materias que adeuda el Alumno
public class MateriaAlumno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    // relacion con materias_total (MateriaComision)
    @ManyToOne
    @JoinColumn(name = "id_materia_comision")  // inverse = false
    @NotNull
    private MateriaComision materiaComision;

    @Column(name = "anio")
    @NotBlank(message = "Ingresa el año de la materia.")
    private String anio;

    @Column(name = "nota")
    private Integer nota;

    // relacion con comisiones (Comision)
    @ManyToOne
    @JoinColumn(name = "numero_comision")  // inverse = false
    private Comision comision;

    // relacion con materias_x_alumno (MateriaAlumno)
    @ManyToOne
    @JoinColumn(name = "id_alumno")  // inverse = false
    @NotNull
    private Alumno alumno;

    // asistencia alumno de una comision de una materia
    @Column(name = "asistencia1")
    private boolean asistencia1;

    @Column(name = "asistencia2")
    private boolean asistencia2;

    @Column(name = "asistencia3")
    private boolean asistencia3;

    @Column(name = "asistencia4")
    private boolean asistencia4;

    @Column(name = "asistencia5")
    private boolean asistencia5;

    @Column(name = "asistencia6")
    private boolean asistencia6;

    @Column(name = "asistencia7")
    private boolean asistencia7;

    @Column(name = "asistencia8")
    private boolean asistencia8;

    public MateriaAlumno() {
        materiaComision = new MateriaComision();
        comision = new Comision();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final MateriaAlumno other = (MateriaAlumno) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.materiaComision, other.materiaComision)) {
            return false;
        }
        if (!Objects.equals(this.anio, other.anio)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Materia{" + "id=" + id + ", materiaComision=" + materiaComision + ", anio=" + anio + ", nota=" + nota + ", comision=" + comision + ", alumno=" + alumno + ", asistencia1=" + asistencia1 + ", asistencia2=" + asistencia2 + ", asistencia3=" + asistencia3 + ", asistencia4=" + asistencia4 + ", asistencia5=" + asistencia5 + ", asistencia6=" + asistencia6 + ", asistencia7=" + asistencia7 + ", asistencia8=" + asistencia8 + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Comision getComision() {
        return comision;
    }

    public void setComision(Comision comision) {
        this.comision = comision;
    }

    public MateriaComision getMateriaComision() {
        return materiaComision;
    }

    public void setMateriaComision(MateriaComision materiaComision) {
        this.materiaComision = materiaComision;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public boolean isAsistencia1() {
        return asistencia1;
    }

    public void setAsistencia1(boolean asistencia1) {
        this.asistencia1 = asistencia1;
    }

    public boolean isAsistencia2() {
        return asistencia2;
    }

    public void setAsistencia2(boolean asistencia2) {
        this.asistencia2 = asistencia2;
    }

    public boolean isAsistencia3() {
        return asistencia3;
    }

    public void setAsistencia3(boolean asistencia3) {
        this.asistencia3 = asistencia3;
    }

    public boolean isAsistencia4() {
        return asistencia4;
    }

    public void setAsistencia4(boolean asistencia4) {
        this.asistencia4 = asistencia4;
    }

    public boolean isAsistencia5() {
        return asistencia5;
    }

    public void setAsistencia5(boolean asistencia5) {
        this.asistencia5 = asistencia5;
    }

    public boolean isAsistencia6() {
        return asistencia6;
    }

    public void setAsistencia6(boolean asistencia6) {
        this.asistencia6 = asistencia6;
    }

    public boolean isAsistencia7() {
        return asistencia7;
    }

    public void setAsistencia7(boolean asistencia7) {
        this.asistencia7 = asistencia7;
    }

    public boolean isAsistencia8() {
        return asistencia8;
    }

    public void setAsistencia8(boolean asistencia8) {
        this.asistencia8 = asistencia8;
    }

}
