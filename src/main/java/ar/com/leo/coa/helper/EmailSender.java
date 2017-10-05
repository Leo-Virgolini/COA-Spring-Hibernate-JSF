package ar.com.leo.coa.helper;

import ar.com.leo.coa.model.Alumno;
import ar.com.leo.coa.model.Comision;
import ar.com.leo.coa.model.EscuelaSede;
import ar.com.leo.coa.model.Profesor;
import ar.com.leo.coa.model.Usuario;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

// E-mail Sender
@Service("emailSender")
public class EmailSender {

    @Autowired
    private MailSender javaMailService;

    @Autowired
    private DatesGenerator datesGenerator;

    //envía email al Usuario cuando se olvida la contraseña
    public void recuperarContraseña(Usuario usuario) throws MessagingException {

        // MimeMessage permite enviar html y adjuntos
        MimeMessage msg = ((JavaMailSenderImpl) javaMailService).createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");

        helper.setSubject("Plan COA-Fines: Recuperar Contraseña");
        helper.setTo(usuario.getEmail());
        helper.setSentDate(new Date());

        helper.setText("<html>"
                + "<body>"
                + "Solicitaste recuperar tu contraseña.<br/><br/>"
                + "<strong><u>Usuario</u>: " + usuario.getEmail() + "<br/>"
                + "<u>Contraseña</u>: " + usuario.getPassword() + "</strong> <br/><br/>"
                + "Plan COA-FINES™."
                + "</body>"
                + "</html>", true);

        ((JavaMailSenderImpl) javaMailService).send(msg);
    }

    //envía email al Alumno cuando se inscribe a una Comision
    public void inscripcionComision(Alumno alumno, Comision comisionSeleccionada) throws MessagingException {

        MimeMessage msg = ((JavaMailSenderImpl) javaMailService).createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");

        //obtengo las fechas de la Comision
        List<String> fechas = datesGenerator.obtenerFechas(comisionSeleccionada);

        String text = "<html>"
                + "<head>"
                + "<style>"
                + "p {font-style: italic;} "
                + "table, th, td {border: 1px solid black; text-align: center;} "
                + "td {color: blue; font-weight: bold;} "
                + "</style>"
                + "</head>"
                + "<body>"
                + alumno.getApellido() + ", " + alumno.getNombre() + " te has inscripto correctamente:<br/><br/>"
                + "<strong>-MATERIA: " + alumno.getMateriaAlumno().getMateriaComision().getNombre() + ", AÑO: " + alumno.getMateriaAlumno().getAnio() + "°.</strong>"
                + "<br/><br/><strong>-COMISIÓN:</strong><br/>"
                + "<table>"
                + "<tr> <th>N°</th> <th>Día</th> <th>Horario</th> <th>Escuela</th> <th>Localidad</th> <th>Dirección</th> </tr>"
                + "<tr> <td>" + comisionSeleccionada.getNumero() + "</td> <td>" + comisionSeleccionada.getDia() + "</td> <td>" + comisionSeleccionada.getHorario()
                + "</td> <td>" + comisionSeleccionada.getEscuelaSede().getNombre() + "</td> <td>" + comisionSeleccionada.getEscuelaSede().getLocalidad().getDescripcion() + "</td> <td>" + comisionSeleccionada.getEscuelaSede().getDireccion() + "</td> </tr>"
                + "</table>"
                + "<br/><br/><strong>-FECHAS DE CURSADA:</strong><br/>"
                + "<table>"
                + "<tr> <th>Clase 1</th> <th>Clase 2</th> <th>Clase 3</th> <th>Clase 4</th> <th>Clase 5</th> <th>Clase 6</th> <th>Clase 7</th> <th>Clase 8</th> </tr>"
                + "<tr> ";

        for (String fecha : fechas) {
            text = text.concat("<td>" + fecha + "</td>");
        }

        text = text + " </tr>"
                + "</table>"
                + "</body>"
                + "</html>"
                + "<br/><br/><br/><p>Plan COA-FINES™ 2017.</p>";

        helper.setSubject("Plan COA-Fines-Inscripción a Comisión N°: " + comisionSeleccionada.getNumero());
        helper.setTo(alumno.getEmail());
        helper.setText(text, true);

        ((JavaMailSenderImpl) javaMailService).send(msg);
    }

    //envía email al Alumno cuando se registra
    public void inscripcionAlumno(Alumno alumno) throws MessagingException {

        MimeMessage msg = ((JavaMailSenderImpl) javaMailService).createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");

        helper.setSubject("Plan COA-Fines: Registro Alumno");
        helper.setTo(alumno.getEmail());
        helper.setSentDate(new Date());

        helper.setText("<html>"
                + "<body>"
                + "Te has registrado correctamente.<br/><br/>"
                + "<strong><u>Usuario</u>: " + alumno.getEmail() + "<br/>"
                + "<u>Contraseña</u>: " + alumno.getPassword() + "</strong> <br/><br/>"
                + "Plan COA-FINES™."
                + "</body>"
                + "</html>", true);

        ((JavaMailSenderImpl) javaMailService).send(msg);
    }

    public void inscripcionEscuelaSede(EscuelaSede escuelaSede) throws MessagingException {

        MimeMessage msg = ((JavaMailSenderImpl) javaMailService).createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");

        helper.setSubject("Plan COA-Fines: Registro Escuela Sede");
        helper.setTo(escuelaSede.getEmail());
        helper.setSentDate(new Date());

        helper.setText("<html>"
                + "<body>"
                + "Te has registrado correctamente.<br/><br/>"
                + "<strong><u>Usuario</u>: " + escuelaSede.getEmail() + "<br/>"
                + "<u>Contraseña</u>: " + escuelaSede.getPassword() + "</strong> <br/><br/>"
                + "Plan COA-FINES™."
                + "</body>"
                + "</html>", true);

        ((JavaMailSenderImpl) javaMailService).send(msg);
    }

    public void inscripcionProfesor(Profesor profesor) throws MessagingException {

        MimeMessage msg = ((JavaMailSenderImpl) javaMailService).createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");

        helper.setSubject("Plan COA-Fines: Registro Profesor");
        helper.setTo(profesor.getEmail());
        helper.setSentDate(new Date());

        helper.setText("<html>"
                + "<body>"
                + "Te has registrado correctamente.<br/><br/>"
                + "<strong><u>Usuario</u>: " + profesor.getEmail() + "<br/>"
                + "<u>Contraseña</u>: " + profesor.getPassword() + "</strong> <br/><br/>"
                + "Plan COA-FINES™."
                + "</body>"
                + "</html>", true);

        ((JavaMailSenderImpl) javaMailService).send(msg);
    }

}
