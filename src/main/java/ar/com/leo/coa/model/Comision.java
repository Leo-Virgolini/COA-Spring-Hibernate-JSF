/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.leo.coa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "comisiones")
public class Comision implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_comision", unique = true, nullable = false)
    private Integer numero;

    // relacion con materias_total (MateriaComision)
    @ManyToOne // id de materias_total
    @JoinColumn(name = "id_materia_comision") // inverse = false
    @NotNull(message = "Selecciona una materia de comisión.")
    private MateriaComision materiaComision;

    // id de materias_x_alumno
    @OneToMany(mappedBy = "comision", fetch = FetchType.EAGER)  // pointing MateriaAlumno's comision field. EAGER para obtener todas las MateriaAlumno de una Comision, en gestionComisiones
//    @Column(name = "id_materia_alumno")    // inverse=true
    private List<MateriaAlumno> materiasAlumno;

    @Column(name = "dia")
    @NotBlank
    private String dia;

    @Column(name = "horario")
    @NotBlank
    private String horario;

    @ManyToOne
    @JoinColumn(name = "id_escuela_sede")  // inverse = false
    @NotNull(message = "Selecciona una escuela sede.")
    private EscuelaSede escuelaSede;

    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_profesor") // inverse = false
    @NotNull(message = "Selecciona un profesor.")
    private Profesor profesor;

    public Comision() {
        materiasAlumno = new ArrayList<MateriaAlumno>();
        materiaComision = new MateriaComision();
        escuelaSede = new EscuelaSede();
        profesor = new Profesor();
    }

    @Column(name = "estado")
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public EscuelaSede getEscuelaSede() {
        return escuelaSede;
    }

    public void setEscuelaSede(EscuelaSede escuelaSede) {
        this.escuelaSede = escuelaSede;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public MateriaComision getMateriaComision() {
        return materiaComision;
    }

    public void setMateriaComision(MateriaComision materiaComision) {
        this.materiaComision = materiaComision;
    }

    public List<MateriaAlumno> getMateriasAlumno() {
        return materiasAlumno;
    }

    public void setMateriasAlumno(List<MateriaAlumno> materiasAlumno) {
        this.materiasAlumno = materiasAlumno;
    }

}
