package ar.com.leo.coa.mb;

import ar.com.leo.coa.helper.EmailSender;
import ar.com.leo.coa.model.Alumno;
import ar.com.leo.coa.model.Comision;
import ar.com.leo.coa.model.MateriaAlumno;
import ar.com.leo.coa.model.Profesor;
import ar.com.leo.coa.service.ComisionService;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.RowEditEvent;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Leo
 */
@ManagedBean
@ViewScoped
public class ComisionesManagedBean implements Serializable {

    @ManagedProperty("#{emailSender}") // Setter required.
    private EmailSender emailSender;

    @ManagedProperty("#{comisionService}")
    private ComisionService comisionService; // Setter required.

    @ManagedProperty("#{sessionManagedBean}")
    private SessionManagedBean sessionManagedBean; // +setter required

    private FacesContext facesContext;
    private HttpServletRequest httpServletRequest;
    private Alumno alumno;
    private Profesor profesor;

    private String horarioInicio;
    private String horarioFin;

    private Comision comision;
    private List<Comision> comisiones;
    private List<Comision> comisionesFiltradas;
    //son las comisiones a las que está inscripto un alumno
    private List<Comision> comisionesAlumno;
    //son las comisiones que tiene a cargo un profesor.
    private List<Comision> comisionesProfesor;

    //cuando el alumno se da de alta/baja de una comision.
    private Comision comisionSeleccionada;

    @PostConstruct
    public void init() {

        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        String path = httpServletRequest.getServletPath();

        System.out.println("Path ComisionesManagedBean: " + path);

        if (path != null) //segun la página, elijo qué comisiones mostrar
        {
            switch (path) {

                case "/alumno/altaComision.xhtml":
                    //alta comision
                    //obtengo el sessionAttribute alumno proveniente del LoginManagedBean
//                    alumno = (Alumno) httpServletRequest.getSession().getAttribute("sessionAlumno");
//                    alumno = sessionManagedBean.getAlumno();
//                    comisiones = this.obtenerComisionesNoInscriptas(alumno.getId());
                    break;

                case "/alumno/bajaComision.xhtml":
                    //baja comision
                    //obtengo el sessionAttribute alumno proveniente del LoginManagedBean
                    alumno = (Alumno) httpServletRequest.getSession().getAttribute("sessionAlumno");
                    comisiones = this.obtenerComisionesInscriptas(alumno.getId());
                    break;

                case "/alumno/gestionComisiones.xhtml":
                    //obtengo el sessionAttribute alumno proveniente del LoginManagedBean
//                    alumno = (Alumno) httpServletRequest.getSession().getAttribute("sessionAlumno");
                    //todas las comisiones inscriptas del alumno
                    comisionesAlumno = this.obtenerComisionesInscriptas(sessionManagedBean.getAlumno().getId());
                    break;

                case "/profesor/gestionComisiones.xhtml":
                    //obtengo el sessionAttribute profesor proveniente del LoginManagedBean
//                    profesor = (Profesor) httpServletRequest.getSession().getAttribute("sessionProfesor");

                    //todas las comisiones del profesor
                    comisionesProfesor = this.obtenerComisionesProfesor(sessionManagedBean.getProfesor().getId());
                    break;

                case "/profesor/actaVolante.xhtml":
                    //obtengo el sessionAttribute profesor proveniente del LoginManagedBean
//                    profesor = (Profesor) httpServletRequest.getSession().getAttribute("sessionProfesor");

                    //todas las comisiones del profesor
                    comisiones = this.obtenerComisionesProfesor(sessionManagedBean.getProfesor().getId());
                    break;

                case "/escuelaSede/gestionComisiones.xhtml":
                    comisiones = this.obtenerComisionesEscuelaSede(sessionManagedBean.getEscuelaSede().getId());
                    break;

                case "/admin/gestionComisiones.xhtml":
                    //todas
                    comisiones = this.obtenerComisiones();
                    break;

                case "/admin/altaComision.xhtml":
                    //crear comision con admin
                    comision = new Comision();
                    comisiones = this.obtenerComisiones();
                    break;

                case "/admin/gestionAlumnos.xhtml":
                    //editar la comision de la materia de un alumno
                    comisiones = this.obtenerComisiones();
                    break;

                case "/admin/actaVolante.xhtml":
                    //todas
                    comisiones = this.obtenerComisiones();
                    break;
            }
        }

    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void setComisionService(ComisionService comisionService) {
        this.comisionService = comisionService;
    }

    public Comision getComision() {
        return comision;
    }

    public void setComision(Comision comision) {
        this.comision = comision;
    }

    public List<Comision> getComisiones() {
        return comisiones;
    }

    public List<Comision> getComisionesFiltradas() {
        return comisionesFiltradas;
    }

    public void setComisionesFiltradas(List<Comision> comisionesFiltradas) {
        this.comisionesFiltradas = comisionesFiltradas;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Comision getComisionSeleccionada() {
        return comisionSeleccionada;
    }

    public void setComisionSeleccionada(Comision comisionSeleccionada) {
        this.comisionSeleccionada = comisionSeleccionada;
    }

    public List<Comision> getComisionesAlumno() {
        return comisionesAlumno;
    }

    public List<Comision> getComisionesProfesor() {
        return comisionesProfesor;
    }

    //alumno se inscribe en una comision a partir de una MateriaAlumno que adeuda
    public String altaComisionAlmuno(MateriaAlumno materiaAlumno) {

        if (comisionSeleccionada != null) {

            try {

                //ingresa el numero_comision en materias_x_alumno 
                comisionService.altaComisionAlumno(comisionSeleccionada, materiaAlumno);

                alumno = sessionManagedBean.getAlumno();
                alumno.setMateriaAlumno(materiaAlumno);

                //envio de EMAIL con datos de la comision
//                String fechaInicio = context.getExternalContext().getInitParameter("fechaInicio");
                emailSender.inscripcionComision(alumno, comisionSeleccionada);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Inscripción: se envío un e-mail con los datos.", "Te has inscripto correctamente a la comisión N°: " + comisionSeleccionada.getNumero());
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().addMessage(null, message);

                //actualizar tabla
                comisiones = null;

                return "/alumno/altaComision.xhtml?faces-redirect=true";

            } catch (DataAccessException ex) { //si falla el update
                //mensaje al growl
                System.out.println(ex);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alta Comisión Alumno", "¡Error: no se pudo inscribir correctamente!");
                FacesContext.getCurrentInstance().addMessage(null, message);

                return "";

            } catch (MessagingException ex) { // si falla el envio del mail
                System.err.println(ex.getMessage());

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alta Comisión Alumno", "¡Error: no se pudo enviar el e-mail correctamente!");
                FacesContext.getCurrentInstance().addMessage(null, message);

                return "";
            }
        } else { //si no hay ninguna comision seleccionada
            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Inscripción", "¡Selecciona alguna comisión!");
            FacesContext.getCurrentInstance().addMessage(null, message);

            return "";
        }
    }

    //alumno se da de baja de una comision
    public void bajaComisionAlmuno() {

        if (comisionSeleccionada != null) {

            try {
                //darse de baja de comision seleccionada en coa.comisiones_x_alumno
                comisionService.bajaComisionAlumno(comisionSeleccionada, alumno);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Baja", "Te has dado de baja correctamente de la comisión número: " + comisionSeleccionada.getNumero());
                FacesContext.getCurrentInstance().addMessage(null, message);

                //mostrar tabla actualizada
                comisiones = this.obtenerComisionesInscriptas(alumno.getId());

            } catch (DataAccessException ex) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la baja de comisión");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

        } else { //si no hay ninguna comision seleccionada
            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "¡Selecciona alguna comisión!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    //admin borrra comision
    public void bajaComision(Comision comisionSeleccionada) {

        if (comisionSeleccionada.getNumero() != 0) {

            try { //al eliminar un alumno que es foreign key en otra tabla--> tira SQLException

                //dar de baja comision seleccionada 
                comisionService.bajaComision(comisionSeleccionada);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Baja Comisión", "Comisión número: " + comisionSeleccionada.getNumero());
                FacesContext.getCurrentInstance().addMessage(null, message);

                //mostrar tabla actualizada
                comisiones = this.obtenerComisiones();

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "¡Error: no puedes eliminar una Comisión que está en otra tabla!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else { //si no hay ninguna comision seleccionada
            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Baja Comisión", "¡Error!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public String altaComision() {

        if (comision != null) {

            try {

                comision.setCantidad(0);
                //alta de comision
                comisionService.altaComision(comision);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta Comisión", "Comisión N°: " + comision.getNumero() + " creada.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

                //mostrar tabla actualizada
                comisiones = this.obtenerComisiones();

                return "/admin/altaComision.xhtml?faces-redirect=true";

            } catch (DataAccessException ex) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en alta de comisión.");
                FacesContext.getCurrentInstance().addMessage(null, message);

                return "";
            }

        } else { //si no hay ninguna comision
            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alta", "¡Error!");
            FacesContext.getCurrentInstance().addMessage(null, message);

            return "";
        }
    }

    //muestra las comisiones a las que el alumno esta inscripto
//    public List<Comision> obtenerComisionesInscriptas(Alumno alumno) {
//
//        return comisionService.obtenerComisionesInscriptas(alumno.getId());
//    }
    //comisiones pertenecientes al id del alumno
    public List<Comision> obtenerComisionesInscriptas(Long idAlumno) {

        //muestra las comisiones a las que el alumno esta inscripto
        return comisionesAlumno = comisionService.obtenerComisionesInscriptas(idAlumno);
    }

    //comisiones del id del profesor
    public List<Comision> obtenerComisionesProfesor(Long idProfesor) {

        //muestra las comisiones que el profesor tiene a cargo
        return comisionesProfesor = comisionService.obtenerComisionesProfesor(idProfesor);
    }

    public List<Comision> obtenerComisionesNoInscriptas(Long idAlumno) {

        //muestra las comisiones a las que el alumno NO esta inscripto
        return comisionService.obtenerComisionesNoInscriptas(idAlumno);
    }

    public List<Comision> obtenerComisiones() {

        //muestra todas las comisiones
        return comisionService.obtenerComisiones();
    }

    //obtiene las comisiones disponibles para que el alumno se inscriba segun el id de la materia que debe
    public List<Comision> obtenerComisionesMateria(Long idMateriaComision) {

        System.out.println("idMateriaComision: " + idMateriaComision);
        System.out.println("idAlumno: " + sessionManagedBean.getAlumno().getId());

//        SessionManagedBean sessionManagedBean = (SessionManagedBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionManagedBean");
        if (idMateriaComision != 0) {
            return comisiones = comisionService.obtenerComisionesMateria(sessionManagedBean.getAlumno().getId(), idMateriaComision);
        } else {
            return null;
        }
    }

    // ESCUELA SEDE
    //muestra las comisiones de la EscuelaSede
    private List<Comision> obtenerComisionesEscuelaSede(Long idEscuelaSede) {
        return comisiones = comisionService.obtenerComisionesEscuelaSede(idEscuelaSede);
    }

    //metodo del evento RowEdit
    public void onRowEdit(RowEditEvent event) {

        //obtengo el Profesor al cual editee
        Comision comisionSeleccionada = (Comision) event.getObject();

        //si hay algun profesor con ID distinta a 0
        if (comisionSeleccionada.getNumero() != 0) {

            try {
                comisionService.modificarComision(comisionSeleccionada);

                //mostrar tabla actualizada
                comisiones = this.obtenerComisiones();

                //mensaje al growl
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Comisión Modificada", "ID: " + comisionSeleccionada.getNumero());
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (DataAccessException ex) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en modificar comisión.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en Modificación", "ID: " + comisionSeleccionada.getNumero());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event) {
        //mensaje al growl
        FacesMessage msg = new FacesMessage("Modificación Cancelada", "ID: " + ((Comision) event.getObject()).getNumero() + " " + ((Comision) event.getObject()).getMateriaComision().getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void setSessionManagedBean(SessionManagedBean sessionManagedBean) {
        this.sessionManagedBean = sessionManagedBean;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFin() {
        return horarioFin;
    }

    public void setHorarioFin(String horarioFin) {
        this.horarioFin = horarioFin;
    }

}
