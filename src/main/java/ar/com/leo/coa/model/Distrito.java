package ar.com.leo.coa.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
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
@Table(name = "distritos")
public class Distrito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    // relacion con provincias (Provincia)
    @ManyToOne
    @JoinColumn(name = "id_provincia")  // inverse = false
    private Provincia provincia;

    // relacion con localidades (Localidad)
    @OneToMany(mappedBy = "distrito", cascade = CascadeType.ALL) // pointing Localidad's distrito field
//    @Column(name = "id_localidad") // inverse=true
    private List<Localidad> localidades;

    @Column(name = "descripcion")
    @NotBlank
    private String descripcion;

    public Distrito() {
        provincia = new Provincia();
    }

    public Distrito(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Distrito{" + "id=" + id + ", provincia=" + provincia + ", descripcion=" + descripcion + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.descripcion);
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
        final Distrito other = (Distrito) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Localidad> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<Localidad> localidades) {
        this.localidades = localidades;
    }

}
