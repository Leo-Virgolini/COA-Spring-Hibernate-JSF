
package ar.com.leo.coa.mb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.validate.ClientValidator;

/**
 *
 * @author Leo
 */
@FacesValidator("birthDateValidator")
public class BirthDateValidator implements Validator, ClientValidator {

    private static final String BIRTH_DATE_PATTERN = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$"
            + "|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)"
            + "(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private final Pattern pattern;
    private Matcher matcher;

    public BirthDateValidator() {
        pattern = Pattern.compile(BIRTH_DATE_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value == null) {
            return; // Let required="true" handle, if any.
        }

        Date fechaNacimiento = (Date) value; // You need to cast it to the same type as returned by Converter, if any.

        String strDate = DATE_FORMAT.format(fechaNacimiento);

        matcher = pattern.matcher(strDate);

        // fecha limite para que un alumno tenga 18 años: fecha actual - 18 años
        Calendar fechaLimite = Calendar.getInstance(); // this would default to now
        fechaLimite.add(Calendar.DAY_OF_YEAR, -(365 * 18));

        // averiguo si nacio antes de 1916 o después del año limite
        if (!matcher.matches() || fechaNacimiento.after(fechaLimite.getTime()) || fechaNacimiento.before(new Date("1916/01/01"))) {

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese una fecha de nacimiento correcta.", "Ingrese una fecha de nacimiento correcta.");
            throw new ValidatorException(msg);
        }

    }

    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }

    @Override
    public String getValidatorId() {
        return "birthDateValidator";
    }

}
