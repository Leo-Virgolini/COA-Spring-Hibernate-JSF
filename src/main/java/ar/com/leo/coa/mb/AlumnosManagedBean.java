package ar.com.leo.coa.mb;

import ar.com.leo.coa.model.Alumno;
import ar.com.leo.coa.model.Comision;
import ar.com.leo.coa.service.AlumnoService;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.RowEditEvent;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Leo
 */
@ManagedBean
@ViewScoped
public class AlumnosManagedBean implements Serializable {

    @ManagedProperty("#{alumnoService}")
    private AlumnoService alumnoService; // Setter required.

    private FacesContext facesContext;
    private HttpServletRequest httpServletRequest;

    private Alumno alumno;
    private List<Alumno> alumnos;
    private List<Alumno> alumnosFiltrados;
    private List<Alumno> alumnosComision;
    private Alumno alumnoSeleccionado;
    private List<Alumno> auditoriaAlumno;

    private String busqueda;

    @PostConstruct
    public void init() {

        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        String path = httpServletRequest.getServletPath();

        System.out.println("Path AlumnosManagedBean: " + path);

        alumno = new Alumno();

        if (path != null) {

            switch (path) {
                case "/admin/gestionAlumnos.xhtml":
                    alumnos = this.obtenerAlumnos();
                    break;

                default:
                    break;
            }
        }
    }

    private boolean isPostBack() {
        return FacesContext.getCurrentInstance().isPostback();
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public void setAlumnoService(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Alumno getAlumnoSeleccionado() {
        return alumnoSeleccionado;
    }

    public void setAlumnoSeleccionado(Alumno alumnoSeleccionado) {
        this.alumnoSeleccionado = alumnoSeleccionado;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public List<Alumno> getAlumnosFiltrados() {
        return alumnosFiltrados;
    }

    public void setAlumnosFiltrados(List<Alumno> alumnosFiltrados) {
        this.alumnosFiltrados = alumnosFiltrados;
    }

    public List<Alumno> getAlumnosComision() {
        return alumnosComision;
    }

    public List<Alumno> getAuditoriaAlumno() {
        return auditoriaAlumno;
    }

    public void setAuditoriaAlumno(List<Alumno> auditoriaAlumno) {
        this.auditoriaAlumno = auditoriaAlumno;
    }

    public List<Alumno> obtenerAlumnos() {
        return alumnoService.obtenerAlumnos();
    }

    public List<Alumno> obtenerAuditoriaAlumno(Long idAlumno) {
        return auditoriaAlumno = alumnoService.obtenerAuditoriaAlumno(idAlumno);
    }

    //obtengo los alumnos de una comision a partir de su numero
    public List<Alumno> obtenerAlumnos(Integer numeroComision) {
        return alumnosComision = alumnoService.obtenerPorNumeroComision(numeroComision);
    }

    //obtengo los alumnos por apellido, nombre o DNI
    public List<Alumno> obtenerPorNombreApellidoDni() {
        if (!busqueda.equals("")) {
            return alumnos = alumnoService.obtenerPorNombreApellidoDni(busqueda);
        } else {
            return alumnos = null;
        }
    }

    //obtengo los alumnos por apellido
    public List<Alumno> obtenerAlumnosApellido(String apellido) {

        return alumnosComision = alumnoService.obtenerPorApellido(apellido);
    }

    //obtengo los alumnos por DNI
    public List<Alumno> obtenerAlumnosDNI(String dni) {

        return alumnosComision = alumnoService.obtenerPorDNI(dni);
    }

    //obtengo un alumno para mostrar sus datos
    public Alumno obtenerAlumnoID(Long id) {

        return alumno = alumnoService.obtenerPorId(id);
    }

    //el admin borra un alumno
    public void eliminarAlumno(Alumno alumnoSeleccionado) {

        if (alumnoSeleccionado != null) {

            try { //al eliminar un alumno que es foreign key en otra tabla--> tira SQLException
                alumnoService.borrarAlumno(alumnoSeleccionado);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Baja Alumno", "ID: " + alumnoSeleccionado.getId());
                FacesContext.getCurrentInstance().addMessage(null, message);

                //mostrar tabla actualizada
                alumnos = this.obtenerAlumnos();

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "¡Error: no puedes eliminar un Alumno que está en otra tabla!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la eliminación");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //el admin habilita/deshabilita un Alumno
    public void habilitacionAlumno(Alumno alumnoSeleccionado) {

        if (alumnoSeleccionado != null) {

            // deshabilitar
            if (alumnoSeleccionado.isHabilitado() == true) {

                try {
                    alumnoService.deshabilitarAlumno(alumnoSeleccionado);

                    //mensaje al growl
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deshabilitar Alumno", "ID: " + alumnoSeleccionado.getId() + " Alumno: " + alumnoSeleccionado.getApellido());
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    //mostrar tabla actualizada
                    alumnos = this.obtenerAlumnos();

                } catch (DataAccessException ex) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error: en deshabilitación del Alumno.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                // habilitar
            } else {

                try {
                    alumnoService.habilitarAlumno(alumnoSeleccionado);

                    //mensaje al growl
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Habilitar Alumno", "ID: " + alumnoSeleccionado.getId() + " Alumno: " + alumnoSeleccionado.getApellido());
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    //mostrar tabla actualizada
                    alumnos = this.obtenerAlumnos();

                } catch (DataAccessException ex) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error: en habilitación del Alumno.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la habilitación/deshabilitación");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //el alumno modifica sus datos
    public void modificarmeAlumno(Alumno alumnoSeleccionado) {

        //si hay algun alumno con ID distinta a 0
        if (alumnoSeleccionado.getId() != 0) {

            try {
                alumnoService.modificarAlumno(alumnoSeleccionado);

                //mensaje al growl
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tus datos han sido modificados.", null);
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la modificación");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la modificación");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //alumno se borra de una comision
    public void bajaComisionAlumno(Comision comisionSeleccionada, Alumno alumno) {
        try {
            alumno.setComision(comisionSeleccionada);
            alumnoService.borrarComisionAlumno(alumno);

        } catch (DataAccessException ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la baja de comisión");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //metodo del evento RowEdit de Admin
    public void onRowEdit(RowEditEvent event) {

        //obtengo el Alumno al cual editee
        Alumno alumnoSeleccionado = (Alumno) event.getObject();

        //si hay algun alumno con ID distinta a 0
        if (alumnoSeleccionado.getId() != 0) {

            try {
                alumnoService.modificarAlumno(alumnoSeleccionado);

                //mensaje al growl
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alumno Modificado", "ID: " + alumnoSeleccionado.getId() + " Apellido: " + alumnoSeleccionado.getApellido());
                FacesContext.getCurrentInstance().addMessage(null, msg);

                // mostrar tabla actualizada
                alumnos = this.obtenerAlumnos();
                
            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la modificación");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la modificación");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        //mensaje al growl
        FacesMessage msg = new FacesMessage("Modificación cancelada", "ID: " + ((Alumno) event.getObject()).getId() + " Apellido: " + ((Alumno) event.getObject()).getApellido());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
