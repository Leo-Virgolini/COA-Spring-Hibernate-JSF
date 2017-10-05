package ar.com.leo.coa.converters;

import ar.com.leo.coa.model.MateriaComision;
import ar.com.leo.coa.service.MateriaService;
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

//@FacesConverter("materiasConverter") // no anda con Spring
@ManagedBean
@RequestScoped
public class MateriasConverter implements Converter {

    @ManagedProperty("#{materiaService}")
    private MateriaService materiaService; // setter required

    public void setMateriaService(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

        if (value != null && value.trim().length() > 0) {
            try {
                return materiaService.obtenerMateriaComision(Long.parseLong(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de conversión", "No es una materia válida."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {

        if (object != null) {
            if (((MateriaComision) object).getId() == null) {
                return null;
            }
            return String.valueOf(((MateriaComision) object).getId());
        } else {
            return null;
        }
    }
}
