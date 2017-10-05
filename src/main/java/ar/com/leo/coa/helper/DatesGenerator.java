package ar.com.leo.coa.helper;

import ar.com.leo.coa.model.Comision;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Leo
 */
@Service("datesGenerator")
public class DatesGenerator {

    public List<String> obtenerFechas(Comision comision) {

        List<String> fechas = new ArrayList<String>();

        switch (comision.getDia()) {

            case "LUNES":
                for (int i = 0; i < 50; i += 7) {
                    fechas.add(lunes(i));
                }
                break;

            case "MARTES":
                for (int i = 0; i < 50; i += 7) {
                    fechas.add(martes(i));
                }
                break;

            case "MIERCOLES":
                for (int i = 0; i < 50; i += 7) {
                    fechas.add(miercoles(i));
                }
                break;

            case "JUEVES":
                for (int i = 0; i < 50; i += 7) {
                    fechas.add(jueves(i));
                }
                break;

            case "VIERNES":
                for (int i = 0; i < 50; i += 7) {
                    fechas.add(viernes(i));
                }
                break;

            case "SÁBADO":
                for (int i = 0; i < 50; i += 7) {
                    fechas.add(sabado(i));
                }
                break;
        }

        return fechas;
    }

    public Calendar inicioClases() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.MARCH, 1); // 01/03/2017 Miercoles

        return calendar;
    }

    private String lunes(int i) {

        Calendar t = (Calendar) inicioClases().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + i + 5);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

        return sdf.format(t.getTime());
    }

    private String martes(int i) {

        Calendar t = (Calendar) inicioClases().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + i + 6);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

        return sdf.format(t.getTime());
    }

    private String miercoles(int i) {

        Calendar t = (Calendar) inicioClases().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + i);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

        return sdf.format(t.getTime());
    }

    private String jueves(int i) {

        Calendar t = (Calendar) inicioClases().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + i + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

        return sdf.format(t.getTime());
    }

    private String viernes(int i) {

        Calendar t = (Calendar) inicioClases().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + i + 2);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

        return sdf.format(t.getTime());
    }

    private String sabado(int i) {

        Calendar t = (Calendar) inicioClases().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + i + 3);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

        return sdf.format(t.getTime());
    }

}
