package ar.com.leo.coa.mb;

import ar.com.leo.coa.helper.EmailSender;
import ar.com.leo.coa.model.Alumno;
import ar.com.leo.coa.model.MateriaComision;
import ar.com.leo.coa.model.MateriaProfesor;
import ar.com.leo.coa.model.Profesor;
import ar.com.leo.coa.service.AlumnoService;
import ar.com.leo.coa.service.ProfesorService;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.dao.DataAccessException;

/**
 *
 * @author Leo
 */
@ManagedBean
@ViewScoped
public class RegisterManagedBean implements Serializable {

    private Alumno alumno;
    private Profesor profesor;

    // altaProfesor: las materias que va a dar el profesor para el p:selectCheckBoxMenu
    private List<MateriaComision> materiasComision;

    @ManagedProperty("#{emailSender}") // Setter required.
    private EmailSender emailSender;

    private FacesMessage facesMessage;

    @ManagedProperty("#{alumnoService}")
    private AlumnoService alumnoService; // Setter required.

    @ManagedProperty("#{profesorService}")
    private ProfesorService profesorService; // Setter required.

    @ManagedProperty("#{sessionManagedBean}")
    private SessionManagedBean sessionManagedBean; // Setter required.

    @PostConstruct
    public void init() {

        System.out.println("register INIT post construct");

        alumno = new Alumno();
        profesor = new Profesor();
    }

    public String registrarAlumno() {

        try {

            //creo un nuevo alumno a la bd con los datos ingresados en el formulario
            alumno = alumnoService.altaAlumno(alumno);

            //envio de email
            emailSender.inscripcionAlumno(alumno);

            // seteo al Alumno directamente al sessionManagedBean
            sessionManagedBean.setAlumno(alumno);

            //seteo el atributo alumno al request   
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("alumno", sessionManagedBean);
//            httpServletRequest.getSession().setAttribute("sessionAlumno", alumno);
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Alumno", "Te has registrado exitosamente.");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        } catch (DataAccessException ex) {
            // si falla el insert
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro Alumno", "Error en el registro, vuelve a registrarte.");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

            return "";

        } catch (MessagingException ex) {
            // si se falla el envio del email
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Registro Alumno", "Error en el envío del e-mail.");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

            return "";
        }

        return "/login.xhtml?faces-redirect=true";
    }

    public String registrarProfesor() {

        try {

            //ingreso un nuevo profesor a la bd con los datos ingresados en el formulario 
            // seteo las materiasComision(selectCheckBoxMenu) en las materiasProfesor
            for (MateriaComision materiaComision : materiasComision) {
                MateriaProfesor materiaProfesor = new MateriaProfesor();
                materiaProfesor.setMateriaComision(materiaComision);
                materiaProfesor.setProfesor(profesor);
                profesor.getMateriasProfesor().add(materiaProfesor);
            }

            profesor = profesorService.altaProfesor(profesor);

            emailSender.inscripcionProfesor(profesor);

            //seteo el atributo profesor
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("profesor", profesor);
//            httpServletRequest.getSession().setAttribute("sessionProfesor", profesor);
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta Profesor", "El Profesor ha sido registrado exitosamente: " + profesor.getApellido());
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

            return "/admin/altaProfesor.xhtml?faces-redirect=true";

        } catch (DataAccessException ex) {
            System.out.println(ex);
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro Profesor", "Error en el registro, vuelve a intentarlo.");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

            return "";
        } catch (MessagingException ex) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Registro Profesor", "Error en el envío del e-mail.");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

            return "";
        }
    }

//    public void clearEscuela() {
//        if (this.alumno.getEscuelaOrigen().getLocalidad().getDistrito().getProvincia().getId() != 5) {
//            this.alumno.getEscuelaOrigen().getLocalidad().setId(null);
//        } else {
//            this.alumno.getEscuelaOrigen().getLocalidad().setId(5222); // una Localidad de Ciudad de Buenos Aires
//        }
//    }
    public void clearEscuela() {
        this.alumno.setEscuelaOrigen(null);
    }

    public void setAlumnoService(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    public void setProfesorService(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public FacesMessage getFacesMessage() {
        return facesMessage;
    }

    public void setFacesMessage(FacesMessage facesMessage) {
        this.facesMessage = facesMessage;
    }

    public void setSessionManagedBean(SessionManagedBean sessionManagedBean) {
        this.sessionManagedBean = sessionManagedBean;
    }

    public List<MateriaComision> getMateriasComision() {
        return materiasComision;
    }

    public void setMateriasComision(List<MateriaComision> materiasComision) {
        this.materiasComision = materiasComision;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

}
