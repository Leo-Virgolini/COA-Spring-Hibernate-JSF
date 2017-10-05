package ar.com.leo.coa.mb;

import ar.com.leo.coa.helper.EmailSender;
import ar.com.leo.coa.model.Alumno;
import ar.com.leo.coa.model.EscuelaSede;
import ar.com.leo.coa.model.Profesor;
import ar.com.leo.coa.model.Usuario;
import ar.com.leo.coa.service.LoginService;
import java.io.IOException;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Leo
 */
//@Component // Spring-managed.
//@Scope("request") // Spring-managed scope.
@ManagedBean
@RequestScoped
public class LoginManagedBean implements Serializable {

    @ManagedProperty("#{loginService}")
    private LoginService loginService; // Setter required.

    @ManagedProperty("#{emailSender}")
    private EmailSender emailSender; // Setter required.

    @ManagedProperty("#{sessionManagedBean}")
    private SessionManagedBean sessionManagedBean; // Setter required.

    @ManagedProperty("#{authenticationManagerBean}")
    private AuthenticationManager authenticationManagerBean; // Setter required.

//    @ManagedProperty("#{alumnoService}")
//    private AlumnoService alumnoService; // Setter required.
//
//    @ManagedProperty("#{profesorService}")
//    private ProfesorService profesorService; // Setter required.
    private Usuario usuario;
    private boolean recordar;

    private FacesMessage facesMessage;
    private HttpServletRequest httpServletRequest;
    private FacesContext facesContext;
    private ExternalContext context;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }

    public void login() throws ServletException, IOException {

        facesContext = FacesContext.getCurrentInstance();
        context = facesContext.getExternalContext();
        httpServletRequest = (HttpServletRequest) context.getRequest();
        RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("/login");
        dispatcher.forward(httpServletRequest, (ServletResponse) context.getResponse());
        facesContext.responseComplete();

        //mando un mensaje a la pagina destino
        facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso correcto.", null);
        facesContext.addMessage(null, facesMessage);
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
    }

    public void setAuthenticationManagerBean(AuthenticationManager authenticationManagerBean) {
        this.authenticationManagerBean = authenticationManagerBean;
    }

    public String doLogin() throws ServletException, IOException {

        Authentication authenticationRequestToken = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword());
        //authentication action
        try {
            Authentication authenticationResponseToken = authenticationManagerBean.authenticate(authenticationRequestToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationResponseToken);
            //ok, test if authenticated, if yes reroute
            if (authenticationResponseToken.isAuthenticated()) {
                //lookup authentication success url, or find redirect parameter from login bean
                System.out.println("user: " + authenticationResponseToken.getPrincipal().toString());
                //System.out.println("pass: " + authenticationResponseToken.getCredentials().toString());

                Usuario usuarioLogeado = loginService.autenticarUsuario(usuario.getEmail(), usuario.getPassword());
                System.out.println("USUARIO: " + usuarioLogeado);

                if (usuarioLogeado instanceof Alumno) {
                    sessionManagedBean.setAlumno((Alumno) usuarioLogeado);
                } else if (usuarioLogeado instanceof Profesor) {
                    sessionManagedBean.setProfesor((Profesor) usuarioLogeado);
                } else if (usuarioLogeado instanceof EscuelaSede) {
                    sessionManagedBean.setEscuelaSede((EscuelaSede) usuarioLogeado);
                } else if (usuarioLogeado instanceof Usuario) {
                    sessionManagedBean.setUsuario(usuarioLogeado);
                }

                //
                facesContext = FacesContext.getCurrentInstance();
                context = facesContext.getExternalContext();
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso correcto", "Acceso correcto.");
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

                httpServletRequest = (HttpServletRequest) context.getRequest();
                RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("/login");
                dispatcher.forward(httpServletRequest, (ServletResponse) context.getResponse());
                facesContext.responseComplete();
            }
        } catch (BadCredentialsException badCredentialsException) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario/contraseña incorrecto/s, vuelve a intentar.", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } catch (LockedException lockedException) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Cuenta bloqueada: contacta con el administrador.", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } catch (DisabledException disabledException) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Cuenta inhabilitada: contacta con el administrador.", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }

        return null;
    }

    public String autenticar() throws ServletException, IOException {

        // METODO 1
        //do any job with the associated values that you've got from the user, like persisting attempted login, etc.
//        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
//        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/login");
//        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
//        FacesContext.getCurrentInstance().responseComplete();
        // METODO 2
//        facesContext = FacesContext.getCurrentInstance();
//        httpServletRequest =(HttpServletRequest) facesContext.getExternalContext().getRequest();
//        facesContext.getExternalContext().redirect(httpServletRequest.getContextPath() + "/login");
        // JSF       
        facesContext = FacesContext.getCurrentInstance();

        usuario = loginService.autenticarUsuario(usuario.getEmail(), usuario.getPassword());

        //si el usuario es correcto en coa.login
        if (usuario != null) {

//         //saco todos los atributos que haya
//          httpServletRequest.getSession().removeAttribute("sessionUsuario");
//          httpServletRequest.getSession().removeAttribute("sessionAlumno");
//          httpServletRequest.getSession().removeAttribute("sessionProfesor");
//
//            // borro sesion: sessionManagedBean no funciona sin antes un redirect
//          facesContext.getExternalContext().invalidateSession();
            sessionManagedBean.setUsuario(null);
            sessionManagedBean.setAlumno(null);
            sessionManagedBean.setProfesor(null);

            System.out.println("ROL: " + usuario.getRol());

//          //si es alumno
            if (usuario.getRol() == Usuario.ALUMNO) {
//
                //obtengo el Alumno a partir del id_login de usuario
//                Alumno alumno = alumnoService.obtenerAlumno(usuario.getId());

                //seteo el atributo alumno al request
//                httpServletRequest.getSession().setAttribute("sessionAlumno", (Alumno) usuario);
                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ALUMNO", alumno);
                Alumno alumno = (Alumno) usuario;
                // seteo al Alumno directamente en el sessionManagedBean
                sessionManagedBean.setAlumno(alumno);

                //mando un mensaje a la pagina destino
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso correcto.", null);
                facesContext.addMessage(null, facesMessage);
                facesContext.getExternalContext().getFlash().setKeepMessages(true);

                return "/alumno/homeAlumno.xhtml?faces-redirect=true";

                //si es Profesor
            } else if (usuario.getRol() == Usuario.PROFESOR) {

                //obtengo el Profesor a partir del id_login de usuario
//                Profesor profesor = new ProfesorService().obtenerProfesor(usuario.getId());
                //seteo el atributo profesor al request
//                httpServletRequest.getSession().setAttribute("sessionProfesor", profesor);
                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PROFESOR", profesor);
                Profesor profesor = (Profesor) usuario;
                // seteo al Profesor directamente en el sessionManagedBean
                sessionManagedBean.setProfesor(profesor);

                //mando un mensaje a la pagina destino
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso correcto.", null);
                facesContext.addMessage(null, facesMessage);
                facesContext.getExternalContext().getFlash().setKeepMessages(true);

                return "/profesor/homeProfesor.xhtml?faces-redirect=true";

                //si es EscuelaSede
            } else if (usuario.getRol() == Usuario.ESCUELA_SEDE) {

                EscuelaSede escuelaSede = (EscuelaSede) usuario;
                // seteo la EscuelaSede directamente en el sessionManagedBean
                sessionManagedBean.setEscuelaSede(escuelaSede);

                //mando un mensaje a la pagina destino
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso correcto.", null);
                facesContext.addMessage(null, facesMessage);
                facesContext.getExternalContext().getFlash().setKeepMessages(true);

                return "/escuelaSede/homeEscuelaSede.xhtml?faces-redirect=true";

                //si es ADMIN
            } else if (usuario.getRol() == Usuario.ADMIN) {

                //seteo el atributo usuario al reguest
//            httpServletRequest.getSession().setAttribute("sessionUsuario", usuario);
                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("USUARIO", usuario);
                // seteo al Admin directamente en el sessionManagedBean
                sessionManagedBean.setUsuario(usuario);

                //mando un mensaje a la pagina destino
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso correcto.", null);
                facesContext.addMessage(null, facesMessage);
                facesContext.getExternalContext().getFlash().setKeepMessages(true);

                return "/admin/homeAdmin.xhtml?faces-redirect=true";
            }
            //si no existe el usuario
        } else {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrecto/a.", null);
            facesContext.addMessage(null, facesMessage);

            return null;
        }

        return "login.xhtml";
    }

    // manda un email con la contraseña
    public String recuperarContraseña() {

        usuario = loginService.recuperarContraseña(usuario.getEmail());

        //si el email existe
        if (usuario != null) {

            try {
                //envio contraseña al email
                emailSender.recuperarContraseña(usuario);

                //mando un mensaje a la pagina destino
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña enviada a tu casilla de correo.", null);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

                return "recuperarContrasenia.xhtml?faces-redirect=true";

            } catch (MessagingException me) {
                // simply log it and go on...
                System.err.println(me.getMessage());

                facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, vuelve a intentarlo.", null);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }

            return null; //vuelvo a la misma pag

        } else {
            //si no existe, mando un mensaje a la pagina destino
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ese e-mail no existe.", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

            return null; //vuelvo a la misma pag
        }

    }

    public void onLoad() {

        Boolean logout = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("logout");

        if (null != logout && logout.equals(true)) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesión cerrada correctamente.", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
    }

    public boolean getRecordar() {
        return recordar;
    }

    public void setRecordar(boolean recordar) {
        this.recordar = recordar;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void setSessionManagedBean(SessionManagedBean sessionManagedBean) {
        this.sessionManagedBean = sessionManagedBean;
    }

}
