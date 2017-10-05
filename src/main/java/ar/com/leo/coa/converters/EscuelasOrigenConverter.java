package ar.com.leo.coa.converters;

import ar.com.leo.coa.model.EscuelaOrigen;
import ar.com.leo.coa.service.EscuelaService;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Leo
 */

//@FacesConverter("escuelasOrigenConverter") //no anda con Spring
@ManagedBean
@RequestScoped 
public class EscuelasOrigenConverter implements Converter {
    
    @ManagedProperty("#{escuelaService}")
    private EscuelaService escuelaService; // setter required

    
    public void setEscuelaService(EscuelaService escuelaService) {
        this.escuelaService = escuelaService;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        
        if (value != null && value.trim().length() > 0) {
            try {
                return escuelaService.obtenerEscuelaOrigen(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de conversión", "No es una escuela válida."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        
        if (object != null) {
            return String.valueOf(((EscuelaOrigen) object).getId());
        } else {
            return null;
        }
    }
}
