package ar.com.leo.coa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author Leo
 */
// es la tabla: coa.login
@Entity
@Table(name = "login")
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
public class Usuario implements Serializable {

    // roles
    @Transient
    public static final int ALUMNO = 1;
    @Transient
    public static final int PROFESOR = 2;
    @Transient
    public static final int ESCUELA_SEDE = 3;
    @Transient
    public static final int ADMIN = 99;

    // auditoria
    @Transient
    private int revision;
    @Transient
    private Date revisionDate;
    @Transient
    private String revisionType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    @Length(min = 7, max = 30)
    @NotEmpty
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    @Length(min = 6, max = 25, message = "contra corta o larga")
    @NotEmpty
    private String password;

    @Column(name = "id_rol")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
//    @NotEmpty
    private Integer rol;

    @Column(name = "fecha_creacion")
    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "habilitado")
    private boolean habilitado;

    public Usuario() {
    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", email=" + email + ", password=" + password + ", rol=" + rol + ", fechaCreacion=" + fechaCreacion + ", habilitado=" + habilitado + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getRevisionType() {
        return revisionType;
    }

    public void setRevisionType(String revisionType) {
        this.revisionType = revisionType;
    }

    // Spring Security
    /**
     * Returns the authorities granted to the user. Cannot return
     * <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
//    @Override
//    public Collection<GrantedAuthority> getAuthorities() {
//        return list;
//    }
//
//    /**
//     * Set the authorities granted to the user.
//     *
//     * @param roles
//     */
//    public void setAuthorities(Collection<GrantedAuthority> roles) {
//        this.list = roles;
//    }
//
//    /**
//     * Regresa el identificador único de un usuario.
//     * @return 
//     */
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    /**
//     * Establece el identificador único de un usuario.
//     *
//     * @param username
//     */
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    /**
//     * Método que establece el status de autenticación de un usuario.
//     *
//     * @param status
//     */
//    public void setAuthentication(boolean status) {
//        this.status = status;
//    }
//
//    /**
//     * Indicates whether the user's account has expired. An expired account
//     * cannot be authenticated.
//     *
//     * @return <code>true</code> if the user's account is valid (ie
//     * non-expired), <code>false</code> if no longer valid (ie expired)
//     */
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    /**
//     * Indicates whether the user is locked or unlocked. A locked user cannot be
//     * authenticated.
//     *
//     * @return <code>true</code> if the user is not locked, <code>false</code>
//     * otherwise
//     */
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    /**
//     * Indicates whether the user's credentials (password) has expired. Expired
//     * credentials prevent authentication.
//     *
//     * @return <code>true</code> if the user's credentials are valid (ie
//     * non-expired), <code>false</code> if no longer valid (ie expired)
//     */
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    /**
//     * Indicates whether the user is enabled or disabled. A disabled user cannot
//     * be authenticated.
//     *
//     * @return <code>true</code> if the user is enabled, <code>false</code>
//     * otherwise
//     */
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
