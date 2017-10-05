package ar.com.leo.coa.helper;

import ar.com.leo.coa.model.Alumno;
import ar.com.leo.coa.model.Comision;
import ar.com.leo.coa.model.EscuelaOrigen;
import ar.com.leo.coa.model.EscuelaSede;
import ar.com.leo.coa.model.MateriaAlumno;
import ar.com.leo.coa.model.Profesor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Service;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.variable.TableVariable;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variable;
import pl.jsolve.templ4docx.variable.Variables;

/**
 *
 * @author Leo
 */
@Service("documentGenerator")
public class DocumentGenerator {

    public void generarActaResumen(ArrayList<MateriaAlumno> materiasAlumno, String direccionOrigenResumen, String direccionDestinoResumen) throws IOException {

        // hacer un acta por Escuela Origen
        EscuelaSede escuelaSede = materiasAlumno.get(0).getComision().getEscuelaSede();
        //creo carpeta donde voy a guardar las actas segun la escuela sede
        Files.createDirectories(Paths.get(direccionDestinoResumen + escuelaSede.getNombre()));

        // archivo origen
        Docx docx = new Docx(direccionOrigenResumen);
        Variables var = new Variables();
        TableVariable tableVariable = new TableVariable();

        int numero = 1;

        List<Variable> numeroColumnVariables = new ArrayList<Variable>();
        List<Variable> apellidoNombreColumnVariables = new ArrayList<Variable>();
        List<Variable> dniColumnVariables = new ArrayList<Variable>();
        List<Variable> materiaColumnVariables = new ArrayList<Variable>();
        List<Variable> anioColumnVariables = new ArrayList<Variable>();
        List<Variable> resolucionColumnVariables = new ArrayList<Variable>();
        List<Variable> notaColumnVariables = new ArrayList<Variable>();
        List<Variable> fechaColumnVariables = new ArrayList<Variable>();
        List<Variable> letraColumnVariables = new ArrayList<Variable>();
        List<Variable> conceptoColumnVariables = new ArrayList<Variable>();

        var.addTextVariable(new TextVariable("${escuelaSede}", escuelaSede.getNombre()));
        var.addTextVariable(new TextVariable("${direccionEscuelaSede}", escuelaSede.getDireccion()));

        EscuelaOrigen escuelaOrigen = materiasAlumno.get(0).getAlumno().getEscuelaOrigen();

        Iterator<MateriaAlumno> it = materiasAlumno.iterator();

        while (it.hasNext()) {

            MateriaAlumno materiaAlumno = it.next();

            // si las Escuelas Origen son distintas generar nueva acta resumen
            if (materiaAlumno.getAlumno().getEscuelaOrigen().getId() != escuelaOrigen.getId()) {

                System.out.println("DISTINTAS");
                System.out.println("Escuela Origen anterior: " + materiaAlumno.getAlumno().getEscuelaOrigen().getNombre() + " " + materiaAlumno.getAlumno().getEscuelaOrigen().getId());
                System.out.println("Escuela Origen actual: " + escuelaOrigen.getNombre() + " " + escuelaOrigen.getId());

                tableVariable.addVariable(numeroColumnVariables);
                tableVariable.addVariable(apellidoNombreColumnVariables);
                tableVariable.addVariable(dniColumnVariables);
                tableVariable.addVariable(materiaColumnVariables);
                tableVariable.addVariable(anioColumnVariables);
                tableVariable.addVariable(resolucionColumnVariables);
                tableVariable.addVariable(notaColumnVariables);
                tableVariable.addVariable(letraColumnVariables);
                tableVariable.addVariable(conceptoColumnVariables);
                tableVariable.addVariable(fechaColumnVariables);

                var.addTableVariable(tableVariable);
                docx.fillTemplate(var);

                // guardo
                //archivo destino
                docx.save(direccionDestinoResumen + escuelaSede.getNombre() + "\\Acta Resumen-" + escuelaOrigen.getNombre() + ".docx");

                // creo nuevo archivo
                docx = new Docx(direccionOrigenResumen);
                var = new Variables();
                tableVariable = new TableVariable();
                numero = 1;
            }

            escuelaOrigen = materiaAlumno.getAlumno().getEscuelaOrigen();

            var.addTextVariable(new TextVariable("${provincia}", escuelaOrigen.getLocalidad().getDistrito().getProvincia().getDescripcion()));
            var.addTextVariable(new TextVariable("${localidad}", escuelaOrigen.getLocalidad().getDescripcion()));
            var.addTextVariable(new TextVariable("${distritoEscuelaOrigen}", escuelaOrigen.getLocalidad().getDistrito().getDescripcion()));
            var.addTextVariable(new TextVariable("${escuelaOrigen}", escuelaOrigen.getNombre()));
            var.addTextVariable(new TextVariable("${direccionEscuelaOrigen}", escuelaOrigen.getDireccion()));

            numeroColumnVariables.add(new TextVariable("${numero}", "" + numero));
            apellidoNombreColumnVariables.add(new TextVariable("${apellidoNombre}", materiaAlumno.getAlumno().getApellido() + ", " + materiaAlumno.getAlumno().getNombre()));
            dniColumnVariables.add(new TextVariable("${dni}", materiaAlumno.getAlumno().getDni()));
            materiaColumnVariables.add(new TextVariable("${materia}", materiaAlumno.getMateriaComision().getNombre()));
            anioColumnVariables.add(new TextVariable("${anio}", materiaAlumno.getAnio()));
            resolucionColumnVariables.add(new TextVariable("${resolucion}", materiaAlumno.getAlumno().getResolucion()));
            notaColumnVariables.add(new TextVariable("${nota}", "" + materiaAlumno.getNota()));

            switch (materiaAlumno.getNota()) {
                case 4:
                    letraColumnVariables.add(new TextVariable("${letra}", "CUATRO"));
                    break;
                case 5:
                    letraColumnVariables.add(new TextVariable("${letra}", "CINCO"));
                    break;
                case 6:
                    letraColumnVariables.add(new TextVariable("${letra}", "SEIS"));
                    break;
                case 7:
                    letraColumnVariables.add(new TextVariable("${letra}", "SIETE"));
                    break;
                case 8:
                    letraColumnVariables.add(new TextVariable("${letra}", "OCHO"));
                    break;
                case 9:
                    letraColumnVariables.add(new TextVariable("${letra}", "NUEVE"));
                    break;
                case 10:
                    letraColumnVariables.add(new TextVariable("${letra}", "DIEZ"));
                    break;
                default:
                    letraColumnVariables.add(new TextVariable("${letra}", ""));
            }

            conceptoColumnVariables.add(new TextVariable("${concepto}", "APROBADO"));
            fechaColumnVariables.add(new TextVariable("${fecha}", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));

            numero += 1;
        }

    }

    public void generarActasVolante(ArrayList<MateriaAlumno> materiasAlumno, String direccionOrigenVolante, String direccionDestinoVolante) throws IOException {

        Profesor profesor = materiasAlumno.get(0).getComision().getProfesor();
        Comision comision = materiasAlumno.get(0).getComision();
        EscuelaSede escuelaSede = materiasAlumno.get(0).getComision().getEscuelaSede();

        Docx docx;
        Variables var;

        //creo carpeta donde voy a guardar las actas segun el profesor y el numero de comision
        direccionDestinoVolante += "COM. " + comision.getNumero() + "-" + profesor.getApellido() + ", " + profesor.getNombre();
        Files.createDirectories(Paths.get(direccionDestinoVolante));

        if (Files.exists(Paths.get(direccionDestinoVolante))) {
            System.out.println("existe");
            System.out.println(Paths.get(direccionDestinoVolante));
        }

        for (MateriaAlumno materiaAlumno : materiasAlumno) {

            // archivo origen
            docx = new Docx(direccionOrigenVolante);
            var = new Variables();

            Alumno alumno = materiaAlumno.getAlumno();
            EscuelaOrigen escuelaOrigen = alumno.getEscuelaOrigen();

            var.addTextVariable(new TextVariable("${escuelaSede}", escuelaSede.getNombre().toUpperCase()));
            var.addTextVariable(new TextVariable("${direccionEscuelaSede}", escuelaSede.getDireccion()));
            var.addTextVariable(new TextVariable("${provincia}", escuelaOrigen.getLocalidad().getDistrito().getProvincia().getDescripcion()));
            var.addTextVariable(new TextVariable("${localidadEscuelaOrigen}", escuelaOrigen.getLocalidad().getDescripcion()));
            var.addTextVariable(new TextVariable("${localidadEscuelaSede}", escuelaSede.getLocalidad().getDescripcion()));
            var.addTextVariable(new TextVariable("${distritoEscuelaOrigen}", escuelaOrigen.getLocalidad().getDistrito().getDescripcion()));
            var.addTextVariable(new TextVariable("${escuelaOrigen}", escuelaOrigen.getNombre()));
            var.addTextVariable(new TextVariable("${direccionEscuelaOrigen}", escuelaOrigen.getDireccion()));

            if (escuelaOrigen.getLocalidad().getDistrito().getProvincia().getId() == 5) { //si es CABA imprime el distrito
                var.addTextVariable(new TextVariable("${distrito}", escuelaOrigen.getLocalidad().getDistrito().getDescripcion()));
            } else {
                var.addTextVariable(new TextVariable("${distrito}", ""));
            }

            var.addTextVariable(new TextVariable("${apellidoNombre}", alumno.getApellido() + ", " + alumno.getNombre()));
            var.addTextVariable(new TextVariable("${dni}", alumno.getDni()));
            var.addTextVariable(new TextVariable("${materia}", materiaAlumno.getMateriaComision().getNombre()));
            var.addTextVariable(new TextVariable("${anio}", materiaAlumno.getAnio()));
            var.addTextVariable(new TextVariable("${resolucion}", alumno.getResolucion()));
            var.addTextVariable(new TextVariable("${modalidad}", alumno.getModalidad()));
            var.addTextVariable(new TextVariable("${nota}", "" + materiaAlumno.getNota()));
            var.addTextVariable(new TextVariable("${profesor}", profesor.getApellido().toUpperCase() + ", " + profesor.getNombre().toUpperCase()));

            switch (materiaAlumno.getNota()) {
                case 4:
                    var.addTextVariable(new TextVariable("${letra}", "CUATRO"));
                    break;
                case 5:
                    var.addTextVariable(new TextVariable("${letra}", "CINCO"));
                    break;
                case 6:
                    var.addTextVariable(new TextVariable("${letra}", "SEIS"));
                    break;
                case 7:
                    var.addTextVariable(new TextVariable("${letra}", "SIETE"));
                    break;
                case 8:
                    var.addTextVariable(new TextVariable("${letra}", "OCHO"));
                    break;
                case 9:
                    var.addTextVariable(new TextVariable("${letra}", "NUEVE"));
                    break;
                case 10:
                    var.addTextVariable(new TextVariable("${letra}", "DIEZ"));
                    break;
                default:
                    var.addTextVariable(new TextVariable("${letra}", ""));
            }

            var.addTextVariable(new TextVariable("${concepto}", "APROBADO"));
            var.addTextVariable(new TextVariable("${fecha}", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));

            docx.fillTemplate(var);

            //archivo destino
            docx.save(direccionDestinoVolante + "\\Acta Volante-" + alumno.getApellido() + ", " + alumno.getNombre() + ".docx");
        }

    }

}
