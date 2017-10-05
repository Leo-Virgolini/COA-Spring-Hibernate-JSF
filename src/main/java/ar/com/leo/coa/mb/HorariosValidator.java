package ar.com.leo.coa.mb;

import ar.com.leo.coa.model.Comision;
import ar.com.leo.coa.service.ComisionService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
//@FacesValidator("horariosValidator") // no anda con Spring
@ManagedBean
@RequestScoped
public class HorariosValidator implements Validator, ClientValidator {

    @ManagedProperty("#{comisionService}")
    private ComisionService comisionService; // Setter required.

    public void setComisionService(ComisionService comisionService) {
        this.comisionService = comisionService;
    }

    private String obtenerInicio(String horario) {
        return horario.substring(0, horario.indexOf('-'));
    }

    private String obtenerFin(String horario) {
        return horario.substring(horario.indexOf('-') + 1, horario.length());
    }

    private int obtenerHoraInicio(String horario) {
        return Integer.valueOf(horario.substring(0, horario.indexOf(':')));
    }

    private int obtenerMinutoInicio(String horario) {
        return Integer.valueOf(horario.substring(horario.indexOf(':') + 1, horario.indexOf('-'))); // no devuelve 00
    }

    private int obtenerHoraFin(String horario) {
        return Integer.valueOf(horario.substring(horario.indexOf('-') + 1, horario.indexOf(':', 5)));
    }

    private int obtenerMinutoFin(String horario) {
        return Integer.valueOf(horario.substring(horario.indexOf(':', 5) + 1, horario.length())); // no devuelve 00
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value == null) {
            return; // Let required="true" handle, if any.
        }

        String horario = (String) value; // You need to cast it to the same type as returned by Converter, if any.

        int horaInicioComision = obtenerHoraInicio(horario);
        int minutoInicioComision = obtenerMinutoInicio(horario);
        int horaFinComision = obtenerHoraFin(horario);
        int minutoFinComision = obtenerMinutoFin(horario);

        // si el horario de inicio es mayor al final, entre 3-4 horas
        if (horaInicioComision >= horaFinComision || horaInicioComision >= 24 || horaFinComision > 24 || minutoInicioComision >= 60
                || minutoFinComision >= 60 || (horaFinComision * 60 + minutoFinComision) - (horaInicioComision * 60 + minutoInicioComision) < 180
                || (horaFinComision * 60 + minutoFinComision) - (horaInicioComision * 60 + minutoInicioComision) > 240) {

            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Error: ingresa un horario correcto [HH:mm-HH:mm]. Entre 3-4 horas.");
            throw new ValidatorException(facesMessage);
        }

        Long idProfesor = (Long) UIComponent.getCurrentComponent(context).getAttributes().get("idProfesor");
        String dia = (String) UIComponent.getCurrentComponent(context).getAttributes().get("dia");

        List<Comision> comisionesProfesor = comisionService.obtenerComisionesProfesor(idProfesor);

        try {
            // inicio horario de Comision creada
            String inicio = obtenerInicio(horario);
            Date fechaInicioComision = new SimpleDateFormat("HH:mm").parse(inicio);

            // fin horario de Comision creada
            String fin = obtenerFin(horario);
            Date fechaFinComision = new SimpleDateFormat("HH:mm").parse(fin);

            for (Comision unaComision : comisionesProfesor) {

                // inicio horario de comisiones de profesor
                String inicioComision = obtenerInicio(unaComision.getHorario());
                Date fechaInicio = new SimpleDateFormat("HH:mm").parse(inicioComision);

                // fin horario de comisiones de profesor
                String finComision = obtenerFin(unaComision.getHorario());
                Date fechaFin = new SimpleDateFormat("HH:mm").parse(finComision);

                System.out.println("dia: " + dia);
                System.out.println("inicio: " + inicio + " fin: " + fin);
                System.out.println("dia Comision: " + unaComision.getDia());
                System.out.println("inicio Comision: " + inicioComision + " fin Comision: " + finComision);

                // verificar que el Profesor no esté inscripto en una Comisión en el mismo dia y horario
                if ((dia.equals(unaComision.getDia())) && isOverlapping(fechaInicioComision, fechaFinComision, fechaInicio, fechaFin)) {
                    System.out.println("Horario ya ocupado: " + inicioComision + "-" + finComision);
                    FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Error: el Profesor ya está inscripto en una comisión el mismo día y durante ese horario.");
                    throw new ValidatorException(facesMessage);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && start2.before(end1);
    }

    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }

    @Override
    public String getValidatorId() {
        return "horariosValidator";
    }

}
