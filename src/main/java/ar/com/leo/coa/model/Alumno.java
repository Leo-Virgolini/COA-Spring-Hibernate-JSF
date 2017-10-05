package ar.com.leo.coa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 *
 * @author Leo
 */
@Entity
@Table(name = "alumnos")
@PrimaryKeyJoinColumn(name = "id")
@Audited
public class Alumno extends Usuario implements Serializable {

    //materia que inscribe
    @Transient
    private MateriaAlumno materiaAlumno; // +getter +setter   

    //comision a inscribir
    @Transient
    private Comision comision;

    @Column(name = "nombre")
    @Pattern(regexp = "^[a-zA-Zá-úñÑ ]+$", message = "Ingresa tu nombre correctamente.")
    @Length(min = 3, max = 30, message = "Nombre demasiado corto/largo.")
    @NotBlank(message = "Ingresa tu nombre.")
    private String nombre;

    @Column(name = "apellido")
    @Pattern(regexp = "^[a-zA-Zá-úñÑ ]+$", message = "Ingresa tu apellido correctamente.")
    @Length(min = 3, max = 30, message = "Apellido demasiado corto/largo.")
    @NotBlank(message = "Ingresa tu apellido.")
    private String apellido;

    @Column(name = "dni")
    @Pattern(regexp = "^[0-9]+$", message = "Ingresa tu DNI correctamente.")
    @Length(min = 8, max = 8, message = "DNI demasiado corto/largo.")
    @NotBlank(message = "Ingresa tu DNI.")
    private String dni;

//    @Pattern(regexp="^[^(?:(?:31(/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(/)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(/)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$]+$",
//             message = "Ingresa tu fecha de nacimiento correctamente.")
//    @Length(min = 9, max = 9, message = "Ingresa tu fecha de nacimiento correctamente.")
    @Column(name = "fecha_nacimiento")
//    @Past(message = "Ingresa una fecha correcta.")
    @Temporal(value = TemporalType.DATE)
    @NotNull(message = "Ingresa tu fecha de nacimiento.")
    private Date fechaNacimiento;

    @Column(name = "sexo")
//    @Pattern(regexp="^[M|F]+$", message = "Ingresa tu sexo correctamente.")
//    @NotEmpty(message = "Ingresa tu sexo.")
    private char sexo;

    @Transient
    private Integer edad;

    @Column(name = "telefono")
    @Pattern(regexp = "^[0-9]+$", message = "Ingresa tu teléfono correctamente.")
    @Length(min = 8, max = 10, message = "Teléfono demasiado corto/largo.")
    @NotBlank(message = "Ingresa tu teléfono.")
    private String telefono;

    @Column(name = "celular")
    @Pattern(regexp = "^[0-9)(-]+$", message = "Ingresa tu celular correctamente.")
    @Length(min = 16, max = 16, message = "Ingresa tu celular correctamente.")
    @NotBlank(message = "Ingresa tu celular.")
    private String celular;

    @ManyToOne
    @JoinColumn(name = "id_localidad") // inverse = false
    @NotNull(message = "Selecciona tu localidad.")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Localidad localidad;

    @Column(name = "direccion")
    @Pattern(regexp = "^[a-zA-Zá-ú0-9.ñÑ ]+$", message = "Ingresa tu dirección correctamente.")
    @Length(min = 3, max = 25, message = "Dirección demasiado corta/larga.")
    @NotBlank(message = "Ingresa tu dirección.")
    private String direccion;

    @Column(name = "trabaja")
    private boolean trabaja;

    // relacion con materias_x_alumno (MateriaAlumno)
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL) // pointing MateriaAlumno alumno's field. CASCADE para guardar las materias cuando guardo un Alumno
//    @Column(name = "id_materia_alumno") // inverse=true
    @NotAudited
    private List<MateriaAlumno> materiasAlumno;

    //escuela origen del alumno   
    @ManyToOne
    @JoinColumn(name = "id_escuela_origen") // inverse = false
    @NotNull(message = "Selecciona tu escuela.")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private EscuelaOrigen escuelaOrigen;

    @Column(name = "resolucion")
    @Pattern(regexp = "^[0-9-/ ]+$", message = "Ingresa la resolución correctamente.")
    @Length(min = 4, max = 15, message = "Resolución demasiado corta/larga.")
    @NotBlank(message = "Ingresa la resolución.")
    private String resolucion;

    @Column(name = "modalidad")
    @Pattern(regexp = "^[a-zA-Zá-úñÑ. ]+$", message = "Ingresa tu modalidad correctamente.")
    @Length(min = 5, max = 20, message = "Modalidad demasiado corta/larga.")
    @NotBlank(message = "Ingresa la modalidad.")
    private String modalidad;

    @Column(name = "confirmado")
    private boolean confirmado;

    //cantidad de materias que adeuda
//    @Column(name = "cantidadMaterias")
//    @NumberFormat(style = NumberFormat.Style.NUMBER)
//    @NotEmpty(message = "Ingresa la cantidad de materias que adeudas.")
    @Transient
    private Integer cantidadMaterias;

    public Alumno() {
        escuelaOrigen = new EscuelaOrigen();
        localidad = new Localidad();
        materiaAlumno = new MateriaAlumno();
    }

    @Override
    public String toString() {
        return "Alumno{" + "nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", fechaNacimiento=" + fechaNacimiento + ", sexo=" + sexo + ", edad=" + edad + ", telefono=" + telefono + ", celular=" + celular + ", localidad=" + localidad + ", direccion=" + direccion + ", trabaja=" + trabaja + ", escuelaOrigen=" + escuelaOrigen + ", resolucion=" + resolucion + ", modalidad=" + modalidad + ", confirmado=" + confirmado + ", habilitado=" + this.isHabilitado() + '}';
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEdad() {

        LocalDate birthdate = new LocalDate(fechaNacimiento); //Birth date
        LocalDate now = new LocalDate();  //Today's date
        Period period = new Period(birthdate, now, PeriodType.years());

        return period.getYears();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public EscuelaOrigen getEscuelaOrigen() {
        return escuelaOrigen;
    }

    public void setEscuelaOrigen(EscuelaOrigen escuelaOrigen) {
        this.escuelaOrigen = escuelaOrigen;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public MateriaAlumno getMateriaAlumno() {
        return materiaAlumno;
    }

    public void setMateriaAlumno(MateriaAlumno materiaAlumno) {
        this.materiaAlumno = materiaAlumno;
    }

    public boolean isTrabaja() {
        return trabaja;
    }

    public void setTrabaja(boolean trabaja) {
        this.trabaja = trabaja;
    }

    public Integer getCantidadMaterias() {
        return cantidadMaterias;
    }

    public void setCantidadMaterias(Integer cantidadMaterias) {
        this.cantidadMaterias = cantidadMaterias;
    }

    public List<MateriaAlumno> getMateriasAlumno() {
        return materiasAlumno;
    }

    public void setMateriasAlumno(List<MateriaAlumno> materiasAlumno) {
        this.materiasAlumno = materiasAlumno;
    }

    public Comision getComision() {
        return comision;
    }

    public void setComision(Comision comision) {
        this.comision = comision;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

}
