package ar.com.leo.coa.mb;

import ar.com.leo.coa.model.Profesor;
import ar.com.leo.coa.service.ProfesorService;

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
public class ProfesoresManagedBean implements Serializable {

    @ManagedProperty("#{profesorService}")
    private ProfesorService profesorService; // Setter required.

    private FacesContext facesContext;
    private HttpServletRequest httpServletRequest;

    private Profesor profesor;
    private List<Profesor> profesores;
    private List<Profesor> profesoresFiltrados;
    private Profesor profesorSeleccionado;
    private List<Profesor> auditoriaProfesor;

    @PostConstruct
    public void init() {

        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        String path = httpServletRequest.getServletPath();

        System.out.println("Path ProfesoresManagedBean: " + path);

        if (path != null) //segun la página, elijo que profesores mostrar
        {
            switch (path) {

                case "/admin/gestionProfesores.xhtml":
                    profesores = this.obtenerProfesores();
                    break;

                case "/admin/gestionComisiones.xhtml":
                    profesores = this.obtenerProfesores();
                    break;

                case "/admin/altaComision.xhtml":
                    profesores = this.obtenerProfesores();
                    break;

                case "/admin/altaMateriaProfesor.xhtml":
                    profesores = this.obtenerProfesores();
                    profesor = new Profesor();
                    break;

                default:
                    break;
            }
        }
    }

    public void setProfesorService(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    public List<Profesor> obtenerProfesores() {
        return profesorService.obtenerProfesores();
    }

    public List<Profesor> obtenerAuditoriaProfesor(Long idProfesor) {
        return auditoriaProfesor = profesorService.obtenerAuditoriaProfesor(idProfesor);
    }

    public Profesor obtenerProfesor(Long id) {
        return profesor = profesorService.obtenerProfesor(id);
    }

    public void eliminarProfesor(Profesor profesorSeleccionado) {

        if (profesorSeleccionado != null) {

            try { // al eliminar un profesor que es foreign key en otra tabla--> tira SQLException

                profesorService.eliminarProfesor(profesorSeleccionado);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Baja Profesor", "Profesor ID: " + profesorSeleccionado.getId());
                FacesContext.getCurrentInstance().addMessage(null, message);

                //muestro la tabla actualizada
                profesores = this.obtenerProfesores();

            } catch (DataAccessException ex) {
                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Baja Profesor", "¡Error: no puedes eliminar un profesor si está en otra tabla!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Baja Profesor", "¡Error!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    //el admin habilita/deshabilita un Profesor
    public void habilitacionProfesor(Profesor profesorSeleccionado) {

        if (profesorSeleccionado != null) {

            // deshabilitar
            if (profesorSeleccionado.isHabilitado() == true) {

                try {
                    profesorService.deshabilitarProfesor(profesorSeleccionado);

                    //mensaje al growl
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deshabilitar Profesor", "ID: " + profesorSeleccionado.getId() + " Profesor: " + profesorSeleccionado.getApellido());
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    //mostrar tabla actualizada
                    profesores = this.obtenerProfesores();

                } catch (DataAccessException ex) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error: en deshabilitación del Profesor.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                // habilitar
            } else {

                try {
                    profesorService.habilitarProfesor(profesorSeleccionado);

                    //mensaje al growl
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Habilitar Profesor", "ID: " + profesorSeleccionado.getId() + " Profesor: " + profesorSeleccionado.getApellido());
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    //mostrar tabla actualizada
                    profesores = this.obtenerProfesores();

                } catch (DataAccessException ex) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error: en habilitación del Profesor.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la habilitación/deshabilitación");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //metodo del evento RowEdit
    public void onRowEdit(RowEditEvent event) {

        //obtengo el Profesor al cual quiero editar
        Profesor profesorSeleccionado = (Profesor) event.getObject();

        //si hay algun profesor con ID distinta a 0
        if (profesorSeleccionado.getId() != 0) {

            try {

                this.modificarProfesor(profesorSeleccionado);

                //mensaje al growl
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Profesor Modificado", "ID: " + profesorSeleccionado.getId() + " Apellido: " + profesorSeleccionado.getApellido());
                FacesContext.getCurrentInstance().addMessage(null, msg);

                //mostrar tabla actualizada
                profesores = this.obtenerProfesores();

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
        FacesMessage msg = new FacesMessage("Modificación Cancelada", "ID: " + ((Profesor) event.getObject()).getId() + " " + ((Profesor) event.getObject()).getApellido());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    // el Admin modifica al Profesor
    public void modificarProfesor(Profesor profesorSeleccionado) {

        profesorService.modificarProfesor(profesorSeleccionado);
    }

    // el mismo Profesor se modifica
    public void modificarmeProfesor(Profesor profesorSeleccionado) {

        //si hay algun profesor con ID distinta a 0
        if (profesorSeleccionado.getId() != 0) {

            try {
                profesorService.modificarProfesor(profesorSeleccionado);

                //mensaje al growl
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tus datos han sido modificados.", null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la Modificación");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la Modificación");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public List<Profesor> getProfesoresFiltrados() {
        return profesoresFiltrados;
    }

    public void setProfesoresFiltrados(List<Profesor> profesoresFiltrados) {
        this.profesoresFiltrados = profesoresFiltrados;
    }

    public Profesor getProfesorSeleccionado() {
        return profesorSeleccionado;
    }

    public void setProfesorSeleccionado(Profesor profesorSeleccionado) {
        this.profesorSeleccionado = profesorSeleccionado;
    }

    public List<Profesor> getAuditoriaProfesor() {
        return auditoriaProfesor;
    }

    public void setAuditoriaProfesor(List<Profesor> auditoriaProfesor) {
        this.auditoriaProfesor = auditoriaProfesor;
    }

}
