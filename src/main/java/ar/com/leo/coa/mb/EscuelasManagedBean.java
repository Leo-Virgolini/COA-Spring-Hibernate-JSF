package ar.com.leo.coa.mb;

import ar.com.leo.coa.helper.EmailSender;
import ar.com.leo.coa.model.EscuelaOrigen;
import ar.com.leo.coa.model.EscuelaSede;
import ar.com.leo.coa.model.Localidad;
import ar.com.leo.coa.service.EscuelaService;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Leo
 */
@ManagedBean
@ViewScoped
public class EscuelasManagedBean implements Serializable {

    @ManagedProperty("#{escuelaService}")
    private EscuelaService escuelaService; // Setter required.

    @ManagedProperty("#{emailSender}") // Setter required.
    private EmailSender emailSender;

    private EscuelaOrigen escuelaOrigen;
    private List<EscuelaOrigen> escuelasOrigen;

    private FacesContext facesContext;
    private HttpServletRequest httpServletRequest;

    private EscuelaSede escuelaSede;
    private List<EscuelaSede> escuelasSede;
    private List<EscuelaSede> escuelasFiltradas;

    //lazy pagination
    private LazyDataModel<EscuelaOrigen> lazyModel;

    @PostConstruct
    public void init() {

        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        String path = httpServletRequest.getServletPath();

        System.out.println("Path EscuelasManagedBean: " + path);

        escuelaSede = new EscuelaSede();

        if (path != null) {
            switch (path) {

                case "/admin/gestionEscuelasSede.xhtml":
                    escuelasSede = this.obtenerEscuelasSede();
                    break;

                case "/admin/gestionEscuelasOrigen.xhtml":
                    escuelasOrigen = this.obtenerEscuelasOrigen();
//                    lazyModel = new LazyEscuelaOrigenDataModel(escuelaService);
                    break;

                case "/admin/gestionComisiones.xhtml":
                    escuelasSede = this.obtenerEscuelasSede();
                    break;

                case "/admin/altaComision.xhtml":
                    escuelasSede = this.obtenerEscuelasSede();
                    break;

                case "/admin/altaEscuelaSede.xhtml":
                    escuelasSede = this.obtenerEscuelasSede();
                    break;

                case "/admin/actaResumen.xhtml":
                    escuelasSede = this.obtenerEscuelasSede();
                    break;
            }
        }
    }

    public LazyDataModel<EscuelaOrigen> getLazyModel() {
        return lazyModel;
    }

    public void setEscuelaService(EscuelaService escuelaService) {
        this.escuelaService = escuelaService;
    }

    public EscuelaSede obtenerEscuelaSede(Long id) {

        //busca escuela sede por id
        return escuelaSede = escuelaService.obtenerEscuelaSede(id);
    }

    //metodo del evento RowEdit para EscuelasSede
    public void modificarEscuelaSede(RowEditEvent event) {

        //obtengo la Escuela a la cual editee
        EscuelaSede escuelaSeleccionada = (EscuelaSede) event.getObject();

        //si hay alguna escuela con ID distinta a 0
        if (escuelaSeleccionada.getId() != 0) {

            try {
                escuelaService.modificarEscuelaSede(escuelaSeleccionada);

                //mostrar tabla actualizada
                escuelasSede = this.obtenerEscuelasSede();

                //mensaje al growl
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Escuela Modificada", "ID: " + escuelaSeleccionada.getId());
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en Modificación", "ID: " + escuelaSeleccionada.getId());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en Modificación", "ID: " + escuelaSeleccionada.getId());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void cancelarModificarEscuelaSede(RowEditEvent event) {
        //mensaje al growl
        FacesMessage msg = new FacesMessage("Moficación Cancelada", "ID: " + ((EscuelaSede) event.getObject()).getId() + " " + ((EscuelaSede) event.getObject()).getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //metodo del evento RowEdit para EscuelasOrigen
    public void modificarEscuelaOrigen(RowEditEvent event) {

        //obtengo la Escuela a la cual editee
        EscuelaOrigen escuelaSeleccionada = (EscuelaOrigen) event.getObject();

        //si hay alguna escuela con ID distinta a 0
        if (escuelaSeleccionada.getId() != 0) {

            try {
                escuelaService.modificarEscuelaOrigen(escuelaSeleccionada);

                //mostrar tabla actualizada
                escuelasOrigen = this.obtenerEscuelasOrigen();

                //mensaje al growl
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Escuela Modificada", "ID: " + escuelaSeleccionada.getId());
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en Modificación", "ID: " + escuelaSeleccionada.getId());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en Modificación", "ID: " + escuelaSeleccionada.getId());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void cancelarModificarEscuelaOrigen(RowEditEvent event) {
        //mensaje al growl
        FacesMessage msg = new FacesMessage("Moficación Cancelada", "ID: " + ((EscuelaOrigen) event.getObject()).getId() + " " + ((EscuelaOrigen) event.getObject()).getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    // baja escuela sede por admin
    public void bajaEscuelaSede(EscuelaSede escuelaSeleccionada) {

        if (escuelaSeleccionada != null) {

            try { //al eliminar una escuela que es foreign key en otra tabla--> tira SQLException

                //dar de baja escuela seleccionada 
                escuelaService.bajaEscuelaSede(escuelaSeleccionada);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Baja Escuela", "Escuela: " + escuelaSeleccionada.getNombre());
                FacesContext.getCurrentInstance().addMessage(null, message);

                //mostrar tabla actualizada
                escuelasSede = this.obtenerEscuelasSede();

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "¡Error: no puedes eliminar una Escuela que está en otra tabla!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else { //si no hay ninguna escuela seleccionada
            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Baja Escuela", "¡Error!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    // alta escuela sede por admin
    public String altaEscuelaSede() {

        if (escuelaSede != null) {

            try {

                //alta de escuela sede
                escuelaService.altaEscuelaSede(escuelaSede);

                emailSender.inscripcionEscuelaSede(escuelaSede);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta Escuela", "Escuela: " + escuelaSede.getNombre());
                FacesContext.getCurrentInstance().addMessage(null, message);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

                return "/admin/altaEscuelaSede.xhtml?faces-redirect=true";

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Alta Escuela");
                FacesContext.getCurrentInstance().addMessage(null, msg);

                return "";
            } catch (MessagingException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Error envío de e-mail.");
                FacesContext.getCurrentInstance().addMessage(null, msg);

                return "";
            }

        } else { //si no hay ninguna escuela
            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alta Escuela", "¡Error!");
            FacesContext.getCurrentInstance().addMessage(null, message);

            return "";
        }
    }

    //el admin habilita/deshabilita una Escuela Sede
    public void habilitacionEscuelaSede(EscuelaSede escuelaSedeSeleccionada) {

        if (escuelaSedeSeleccionada != null) {

            // deshabilitar
            if (escuelaSedeSeleccionada.isHabilitado() == true) {

                try {
                    escuelaService.deshabilitarEscuelaSede(escuelaSedeSeleccionada);

                    //mensaje al growl
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deshabilitar Escuela Sede", "ID: " + escuelaSedeSeleccionada.getId() + " Nombre: " + escuelaSedeSeleccionada.getNombre());
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    //mostrar tabla actualizada
                    escuelasSede = this.obtenerEscuelasSede();

                } catch (DataAccessException ex) {
                    System.out.println(ex);
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error: en deshabilitación de la Escuela Sede.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
                // habilitar
            } else {

                try {
                    escuelaService.habilitarEscuelaSede(escuelaSedeSeleccionada);

                    //mensaje al growl
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Habilitar Escuela Sede", "ID: " + escuelaSedeSeleccionada.getId() + " Nombre: " + escuelaSedeSeleccionada.getNombre());
                    FacesContext.getCurrentInstance().addMessage(null, message);

                    //mostrar tabla actualizada
                    escuelasSede = this.obtenerEscuelasSede();

                } catch (DataAccessException ex) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error: en habilitación de la Escuela Sede.");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la habilitación/deshabilitación");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    // baja escuela origen por admin
    public void bajaEscuelaOrigen(EscuelaOrigen escuelaSeleccionada) {

        if (escuelaSeleccionada != null) {

            try { //al eliminar una escuela que es foreign key en otra tabla--> tira SQLException

                //dar de baja escuela seleccionada 
                escuelaService.bajaEscuelaOrigen(escuelaSeleccionada);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Baja Escuela", "Escuela: " + escuelaSeleccionada.getNombre());
                FacesContext.getCurrentInstance().addMessage(null, message);

                //mostrar tabla actualizada
                escuelasOrigen = this.obtenerEscuelasOrigen();

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "¡Error: no puedes eliminar una Escuela que está en otra tabla!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else { //si no hay ninguna escuela seleccionada
            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Baja Escuela", "¡Error!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public List<EscuelaSede> obtenerEscuelasSede() {

        //muestra las escuelas sede
        return escuelaService.obtenerEscuelasSede();
    }

    public List<EscuelaOrigen> obtenerEscuelasOrigen() {

        //muestra las escuelas origen
        return escuelaService.obtenerEscuelasOrigen();
    }

    public EscuelaOrigen obtenerEscuelaOrigen(Integer id) {

        //busca escuela origen por id
        return escuelaOrigen = escuelaService.obtenerEscuelaOrigen(id);
    }

    //muestra las escuelas origen segun lo que ingresa
    public List<EscuelaOrigen> completeText(String query) {

        facesContext = FacesContext.getCurrentInstance();
        Localidad localidad = (Localidad) UIComponent.getCurrentComponent(facesContext).getAttributes().get("localidad");

        List<EscuelaOrigen> escuelasOrigen = null;

        // si es Ciudad de Buenos Aires: busco todas las Escuelas
        if (localidad.getId() == 5222) {

            escuelasOrigen = escuelaService.obtenerEscuelasOrigenProvincia(query, 5);
            this.escuelasOrigen = escuelasOrigen;

            return escuelasOrigen;

            // sino por la Localidad seleccionada
        } else {

            if (localidad.getId() != null) {
                escuelasOrigen = escuelaService.obtenerEscuelasOrigenLocalidad(query, localidad.getId());

                this.escuelasOrigen = escuelasOrigen;
            }

            return escuelasOrigen;
        }
    }

    public void clearEscuelas() {
        this.escuelasOrigen = null;
    }

    public EscuelaSede getEscuelaSede() {
        return escuelaSede;
    }

    public void setEscuelaSede(EscuelaSede escuelaSede) {
        this.escuelaSede = escuelaSede;
    }

    public List<EscuelaSede> getEscuelasSede() {
        return escuelasSede;
    }

    public List<EscuelaSede> getEscuelasFiltradas() {
        return escuelasFiltradas;
    }

    public void setEscuelasFiltradas(List<EscuelaSede> escuelasFiltradas) {
        this.escuelasFiltradas = escuelasFiltradas;
    }

    public EscuelaOrigen getEscuelaOrigen() {
        return escuelaOrigen;
    }

    public void setEscuelaOrigen(EscuelaOrigen escuelaOrigen) {
        this.escuelaOrigen = escuelaOrigen;
    }

    public List<EscuelaOrigen> getEscuelasOrigen() {
        return escuelasOrigen;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

}
