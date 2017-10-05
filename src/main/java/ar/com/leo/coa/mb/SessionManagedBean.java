package ar.com.leo.coa.mb;

import ar.com.leo.coa.model.Alumno;
import ar.com.leo.coa.model.EscuelaSede;
import ar.com.leo.coa.model.Profesor;
import ar.com.leo.coa.model.Usuario;
import java.io.IOException;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Leo
 */
@ManagedBean
@SessionScoped
public class SessionManagedBean implements Serializable {

    private Usuario usuario;
    private Alumno alumno;
    private Profesor profesor;
    private EscuelaSede escuelaSede;

    private FacesContext facesContext;
    private HttpServletRequest httpServletRequest;
    private FacesMessage facesMessage;
    private ExternalContext context;

    public SessionManagedBean() {
        System.out.println("SessionManagedBean Constructur");
    }

    @PostConstruct
    public void init() {

        System.out.println("SessionManagedBean PostConstructor init");

//        facesContext = FacesContext.getCurrentInstance();
//        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
//
//        //le asigno a la variable usuario, el Usuario procedente del Attribute sessionUsuario de LoginManagedBean
//        if (httpServletRequest.getSession().getAttribute("sessionUsuario") != null) {
//            System.out.println("entro admin");
//            usuario = (Usuario) httpServletRequest.getSession().getAttribute("sessionUsuario");
//        }
//
//        //le asigno a la variable alumno, el Alumno procedente del Attribute sessionAlumno de LoginManagedBean o de RegisterManagedBean
//        if (httpServletRequest.getSession().getAttribute("alumno") != null) {
//            System.out.println("entro alumno");
//            alumno = (Alumno) httpServletRequest.getSession().getAttribute("alumno");
//        } else {
//            //le asigno a la variable profesor, el Profesor procedente del Attribute sessionProfesor de LoginManagedBean
//            if (httpServletRequest.getSession().getAttribute("sessionProfesor") != null) {
//                System.out.println("entro profesor");
//                profesor = (Profesor) httpServletRequest.getSession().getAttribute("sessionProfesor");
//            }
//        }
    }

    public void logout() throws ServletException, IOException {

        context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/logout");
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void doLogout() throws ServletException, IOException {

        facesContext = FacesContext.getCurrentInstance();
        context = facesContext.getExternalContext();
//        facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesión cerrada correctamente.", null);
//        facesContext.addMessage(null, facesMessage);
//        context.getFlash().setKeepMessages(true);
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/logout");
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        facesContext.responseComplete();
    }

    public String cerrarSesion() {

        //saco todos los atributos que haya
//        httpServletRequest.getSession().removeAttribute("sessionUsuario");
//        httpServletRequest.getSession().removeAttribute("sessionAlumno");
//        httpServletRequest.getSession().removeAttribute("sessionProfesor");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesión cerrada correctamente.", null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

        return "/login.xhtml?faces-redirect=true";
    }

    // antes de que la sesion se destruya
    @PreDestroy
    public void sessionDestroyed() {

        if (alumno != null) {
            System.out.println("Alumno: " + this.getAlumno().getNombre());
        } else if (profesor != null) {
            System.out.println("Profesor: " + this.getProfesor().getNombre());
        } else if (escuelaSede != null) {
            System.out.println("Escuela Sede: " + this.getEscuelaSede().getNombre());
        } else {
            System.out.println("Admin: " + this.getUsuario().getEmail());
        }

        /*Do some bussiness logic such as save audit logs to db, 
         save logout history, and so on.*/
    }

    public void onLoad() {

        Boolean login = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("login");

        if (null != login && login.equals(true)) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso correcto.", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EscuelaSede getEscuelaSede() {
        return escuelaSede;
    }

    public void setEscuelaSede(EscuelaSede escuelaSede) {
        this.escuelaSede = escuelaSede;
    }

}
