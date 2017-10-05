package ar.com.leo.coa.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Leo
 */
@Entity
@Table(name = "provincias")
public class Provincia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "descripcion")
    @NotEmpty
    private String descripcion;

    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL) // pointing Distrito's address field
//    @Column(name = "id_distrito") // inverse=true
    private List<Distrito> distritos;

    // relacion con escuelas_origen (EscuelaOrigen)
//    @OneToMany(mappedBy = "provincia") // pointing EscuelaOrigen's provincia field
//    @Column(name = "id_escuelaOrigen") // inverse=true
//    private List<EscuelaOrigen> escuelasOrigen;
    
    public Provincia() {
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Provincia) && (id != null)
                ? id.equals(((Provincia) other).id)
                : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Provincia[%d, %s]", id, descripcion);
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

    public List<Distrito> getDistritos() {
        return distritos;
    }

    public void setDistritos(List<Distrito> distritos) {
        this.distritos = distritos;
    }

}
