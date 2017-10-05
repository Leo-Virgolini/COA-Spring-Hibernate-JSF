package ar.com.leo.coa.mb;

import ar.com.leo.coa.service.LoginService;
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
//@FacesValidator("uniqueEmailValidator") // no anda con Spring
@ManagedBean
@RequestScoped
public class UniqueEmailValidator implements Validator, ClientValidator {

    @ManagedProperty("#{loginService}")
    private LoginService loginService; // Setter required.

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value == null) {
            return; // Let required="true" handle, if any.
        }

        String email = (String) value; // You need to cast it to the same type as returned by Converter, if any.

        if (loginService.validarEmail(email.trim().toLowerCase())) {
//            context.addMessage(component.getClientId(context), facesMessage);
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Este e-mail ya está en uso.", null);
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
        return "uniqueEmailValidator";
    }

}
