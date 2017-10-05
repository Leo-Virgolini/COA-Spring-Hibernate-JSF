package ar.com.leo.coa.mb;

import ar.com.leo.coa.model.Comision;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@ManagedBean
@ViewScoped
public class ScheduleView implements Serializable {

    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar.getTime();
    }

    public Calendar inicioClases() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 2, 1); // 01/03/2017 Miercoles   

//        String fechaInicio = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("fechaInicio"); //obtengo la fecha del contexto, que DatesServletListener agregó
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            calendar.setTime(sdf.parse(fechaInicio));
//        } catch (ParseException ex) {
//            System.out.println("ERROR FECHA: " + fechaInicio);
//        }
        return calendar;
    }

    public ScheduleModel obtenerFecha(Comision comision) {

        String horario = comision.getHorario();

        switch (comision.getDia()) {

            case "LUNES":
                eventModel.clear();
                for (int i = 0; i < 50; i += 7) {
                    eventModel.addEvent(new DefaultScheduleEvent("Número comisión: " + comision.getNumero() + " - Materia: " + comision.getMateriaComision().getNombre() + " - Escuela: " + comision.getEscuelaSede().getNombre(),
                            lunes(i, obtenerHoraInicio(horario), obtenerMinutoInicio(horario)),
                            lunes(i, obtenerHoraFin(horario), obtenerMinutoFin(horario))));
                }
                break;

            case "MARTES":
                eventModel.clear();
                for (int i = 0; i < 50; i += 7) {
                    eventModel.addEvent(new DefaultScheduleEvent("Número comisión: " + comision.getNumero() + " - Materia: " + comision.getMateriaComision().getNombre() + " - Escuela: " + comision.getEscuelaSede().getNombre(),
                            martes(i, obtenerHoraInicio(horario), obtenerMinutoInicio(horario)),
                            martes(i, obtenerHoraFin(horario), obtenerMinutoFin(horario))));
                }
                break;

            case "MIERCOLES":
                eventModel.clear();
                for (int i = 0; i < 50; i += 7) {
                    eventModel.addEvent(new DefaultScheduleEvent("Número comisión: " + comision.getNumero() + " - Materia: " + comision.getMateriaComision().getNombre() + " - Escuela: " + comision.getEscuelaSede().getNombre(),
                            miercoles(i, obtenerHoraInicio(horario), obtenerMinutoInicio(horario)),
                            miercoles(i, obtenerHoraFin(horario), obtenerMinutoFin(horario))));
                }
                break;

            case "JUEVES":
                eventModel.clear();
                for (int i = 0; i < 50; i += 7) {
                    eventModel.addEvent(new DefaultScheduleEvent("Número comisión: " + comision.getNumero() + " - Materia: " + comision.getMateriaComision().getNombre() + " - Escuela: " + comision.getEscuelaSede().getNombre(),
                            jueves(i, obtenerHoraInicio(horario), obtenerMinutoInicio(horario)),
                            jueves(i, obtenerHoraFin(horario), obtenerMinutoFin(horario))));
                }
                break;

            case "VIERNES":
                eventModel.clear();
                for (int i = 0; i < 50; i += 7) {
                    eventModel.addEvent(new DefaultScheduleEvent("Número comisión: " + comision.getNumero() + " - Materia: " + comision.getMateriaComision().getNombre() + " - Escuela: " + comision.getEscuelaSede().getNombre(),
                            viernes(i, obtenerHoraInicio(horario), obtenerMinutoInicio(horario)),
                            viernes(i, obtenerHoraFin(horario), obtenerMinutoFin(horario))));
                }
                break;

            case "SÁBADO":
                eventModel.clear();
                for (int i = 0; i < 50; i += 7) {
                    eventModel.addEvent(new DefaultScheduleEvent("Número comisión: " + comision.getNumero() + " - Materia: " + comision.getMateriaComision().getNombre() + " - Escuela: " + comision.getEscuelaSede().getNombre(),
                            sabado(i, obtenerHoraInicio(horario), obtenerMinutoInicio(horario)),
                            sabado(i, obtenerHoraFin(horario), obtenerMinutoFin(horario))));
                }
                break;
        }

        return eventModel;
    }

    private int obtenerHoraInicio(String horario) {
        return Integer.valueOf(horario.substring(0, horario.indexOf(':')));
    }

    private int obtenerMinutoInicio(String horario) {
        return Integer.valueOf(horario.substring(horario.indexOf(':') + 1, horario.indexOf('-')));
    }

    private int obtenerHoraFin(String horario) {
        return Integer.valueOf(horario.substring(horario.indexOf('-') + 1, horario.indexOf(':', 5)));
    }

    private int obtenerMinutoFin(String horario) {
        return Integer.valueOf(horario.substring(horario.indexOf(':', 5) + 1, horario.length()));
    }

    private Date lunes(int i, int hora, int minutos) {
        Calendar t = (Calendar) inicioClases().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + i + 5);
        t.set(Calendar.HOUR_OF_DAY, hora);
        t.set(Calendar.MINUTE, minutos);

        return t.getTime();
    }

    private Date martes(int i, int hora, int minutos) {
        Calendar t = (Calendar) inicioClases().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + i + 6);
        t.set(Calendar.HOUR_OF_DAY, hora);
        t.set(Calendar.MINUTE, minutos);

        return t.getTime();
    }

    private Date miercoles(int i, int hora, int minutos) {
        Calendar t = (Calendar) inicioClases().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + i);
        t.set(Calendar.HOUR_OF_DAY, hora);
        t.set(Calendar.MINUTE, minutos);

        return t.getTime();
    }

    private Date jueves(int i, int hora, int minutos) {
        Calendar t = (Calendar) inicioClases().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + i + 1);
        t.set(Calendar.HOUR_OF_DAY, hora);
        t.set(Calendar.MINUTE, minutos);

        return t.getTime();
    }

    private Date viernes(int i, int hora, int minutos) {
        Calendar t = (Calendar) inicioClases().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + i + 2);
        t.set(Calendar.HOUR_OF_DAY, hora);
        t.set(Calendar.MINUTE, minutos);

        return t.getTime();
    }

    private Date sabado(int i, int hora, int minutos) {
        Calendar t = (Calendar) inicioClases().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + i + 3);
        t.set(Calendar.HOUR_OF_DAY, hora);
        t.set(Calendar.MINUTE, minutos);

        return t.getTime();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }

        event = new DefaultScheduleEvent();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
