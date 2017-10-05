package ar.com.leo.coa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Leo
 */
@Entity
@Table(name = "escuelas_sede")
@PrimaryKeyJoinColumn(name = "id")
public class EscuelaSede extends Usuario implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", unique = true, nullable = false)
//    private Integer id;
    @Column(name = "nombre")
    @NotBlank(message = "Ingresa el nombre.")
    private String nombre;

    @Column(name = "direccion")
    @NotBlank(message = "Ingresa la dirección.")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_localidad") // inverse = false
    private Localidad localidad;

    @Transient
    private Provincia provincia;

    @OneToMany(mappedBy = "escuelaSede") // pointing Comision's escuelaSede's field
//    @Column(name = "numero_comision") // inverse=true
    private List<Comision> comisiones;

    public EscuelaSede() {
        localidad = new Localidad();
        comisiones = new ArrayList<Comision>();
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

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
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

}
