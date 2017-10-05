package ar.com.leo.coa.mb;

import ar.com.leo.coa.service.AlumnoService;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.validate.ClientValidator;

/**
 *
 * @author Leo
 */
//@FacesValidator("uniqueDniValidator") // no anda con Spring
@ManagedBean
@RequestScoped
public class UniqueDniAlumnoValidator implements Validator, ClientValidator {

    @ManagedProperty("#{alumnoService}")
    private AlumnoService alumnoService; // Setter required.

    public void setAlumnoService(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value == null) {
            return; // Let required="true" handle, if any.
        }

        String dni = (String) value; // You need to cast it to the same type as returned by Converter, if any.

        if (alumnoService.validarDni(dni)) {
//            context.addMessage(component.getClientId(context), facesMessage);
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Este DNI ya está inscripto.", null);
            throw new ValidatorException(facesMessage);
        } else {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto.", null);
            context.addMessage(component.getClientId(context), facesMessage);
        }

    }

    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }

    @Override
    public String getValidatorId() {
        return "uniqueDniAlumnoValidator";
    }

}
