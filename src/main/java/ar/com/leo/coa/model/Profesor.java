package ar.com.leo.coa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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
@Table(name = "profesores")
@PrimaryKeyJoinColumn(name = "id")
@Audited
public class Profesor extends Usuario implements Serializable {

    //profesor
    @Column(name = "nombre")
    @Pattern(regexp = "^[a-zA-Zá-úñ ]+$", message = "Ingresa tu nombre correctamente.")
    @Length(min = 3, max = 30, message = "Nombre demasiado corto/largo.")
    @NotBlank(message = "Ingresa tu nombre.")
    private String nombre;

    @Column(name = "apellido")
    @Pattern(regexp = "^[a-zA-Zá-úñ ]+$", message = "Ingresa tu apellido correctamente.")
    @Length(min = 3, max = 30, message = "Apellido demasiado corto/largo.")
    @NotBlank(message = "Ingresa tu apellido.")
    private String apellido;

    @Column(name = "dni")
    @Pattern(regexp = "^[0-9]+$", message = "Ingresa tu DNI correctamente.")
    @Length(min = 8, max = 8, message = "DNI demasiado corto/largo.")
    @NotBlank(message = "Ingresa tu DNI.")
    private String dni;

    @Column(name = "sexo")
//    @Pattern(regexp="^[M|F]+$", message = "Ingresa tu sexo correctamente.")
//    @NotEmpty(message = "Ingresa tu sexo.")
    private char sexo;

    //    @Pattern(regexp="^[^(?:(?:31(/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(/)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(/)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$]+$",
//             message = "Ingresa tu fecha de nacimiento correctamente.")
//    @Length(min = 9, max = 9, message = "Ingresa tu fecha de nacimiento correctamente.")
    @Column(name = "fecha_nacimiento")
//    @Past(message = "Ingresa una fecha correcta.")
    @Temporal(value = TemporalType.DATE)
    @NotNull(message = "Ingresa tu fecha de nacimiento.")
    private Date fechaNacimiento;

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
    @Pattern(regexp = "^[a-zA-Z0-9.ñ ]+$", message = "Ingresa tu dirección correctamente.")
    @Length(min = 3, max = 25, message = "Dirección demasiado corta/larga.")
    @NotBlank(message = "Ingresa tu dirección.")
    private String direccion;

//    @ManyToMany
//    @JoinTable(name = "profesores_x_materias",
//            joinColumns = {
//                @JoinColumn(name = "id_profesor", unique = true)
//            },
//            inverseJoinColumns = {
//                @JoinColumn(name = "id_materia_profesor")
//            }
//    )
//    private List<MateriaProfesor> materiasProfesor;
    
    //relacion con materias_x_profesor (MateriaProfesor)
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)  // pointing MateriaProfesor profesor's field. EAGER para obtener sus materias. CASCADE para guardar las materias cuando guardo un Profesor
//    @Column(name = "id_materia_profesor")   // inverse=true
    @NotAudited
    private List<MateriaProfesor> materiasProfesor;

    @OneToMany(mappedBy = "profesor")  // pointing Comision's profesor field
//    @Column(name = "numero_comision")  // inverse=true
    @NotAudited
    private List<Comision> comisiones;

    public Profesor() {
        this.localidad = new Localidad();
        this.materiasProfesor = new ArrayList<MateriaProfesor>();
        this.comisiones = new ArrayList<Comision>();
    }

    @Override
    public String toString() {
        return "Profesor{" + "nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", sexo=" + sexo + ", fechaNacimiento=" + fechaNacimiento + ", edad=" + edad + ", telefono=" + telefono + ", celular=" + celular + ", localidad=" + localidad + ", direccion=" + direccion + '}';
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

        LocalDate birthdate = new org.joda.time.LocalDate(fechaNacimiento); //Birth date
        LocalDate now = new org.joda.time.LocalDate();  //Today's date
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public List<Comision> getComisiones() {
        return comisiones;
    }

    public void setComisiones(List<Comision> comisiones) {
        this.comisiones = comisiones;
    }

    public List<MateriaProfesor> getMateriasProfesor() {
        return materiasProfesor;
    }

    public void setMateriasProfesor(List<MateriaProfesor> materiasProfesor) {
        this.materiasProfesor = materiasProfesor;
    }

}
