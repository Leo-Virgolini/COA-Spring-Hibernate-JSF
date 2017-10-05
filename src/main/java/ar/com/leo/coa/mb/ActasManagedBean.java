package ar.com.leo.coa.mb;

import ar.com.leo.coa.helper.DocumentGenerator;
import ar.com.leo.coa.model.Comision;
import ar.com.leo.coa.model.EscuelaSede;
import ar.com.leo.coa.model.MateriaAlumno;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import pl.jsolve.templ4docx.exception.OpenDocxException;

/**
 *
 * @author Leo
 */
@ManagedBean
@ViewScoped
public class ActasManagedBean implements Serializable {

    @ManagedProperty("#{documentGenerator}")
    private DocumentGenerator documentGenerator; // Setter required.

    private FacesContext facesContext;
    private HttpServletRequest httpServletRequest;

    // direcciones de los archivos
    private String direccionOrigenResumen;
    private String direccionDestinoResumen;

    private String direccionOrigenVolante;
    private String direccionDestinoVolante;

    @PostConstruct
    public void init() {

        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        String path = httpServletRequest.getServletPath();
        System.out.println("Path MateriasManagedBean: " + path);

        // valores iniciales
        direccionOrigenResumen = "C:\\Users\\Leo\\Desktop\\acta resumen.docx";
        direccionDestinoResumen = "C:\\Users\\Leo\\Desktop\\";

        direccionOrigenVolante = "C:\\Users\\Leo\\Desktop\\acta volante.docx";
        direccionDestinoVolante = "C:\\Users\\Leo\\Desktop\\";
    }

    public String generarActaResumen(ArrayList<MateriaAlumno> materiasAlumno) {

        try {

            // si hay alumnos aprobados
            if (materiasAlumno != null && !materiasAlumno.isEmpty()) {
                //generar acta resumen .docx
                documentGenerator.generarActaResumen(materiasAlumno, direccionOrigenResumen, direccionDestinoResumen);

                EscuelaSede escuelaSede = materiasAlumno.get(0).getComision().getEscuelaSede();

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Generar Acta Resumen", "Has generado correctamente el Acta Resumen de la Escuela Sede: " + escuelaSede.getNombre());
                FacesContext.getCurrentInstance().addMessage(null, message);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

                return "";
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Generar Actas Resumen", "Esta escuela sede no tiene alumnos aprobados.");
                FacesContext.getCurrentInstance().addMessage(null, message);

                return "";
            }

        } catch (OpenDocxException ode) { // si la direccion del documento no existe
            //mensaje al growl
            System.out.println(ode);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Generar Acta Resumen", "¡Error: ingresa la dirección origen del documento correctamente!");
            FacesContext.getCurrentInstance().addMessage(null, message);

            return "";

        } catch (Exception ex) { //si falla
            //mensaje al growl
            System.out.println(ex);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Generar Acta Resumen", "¡Error: no se pudo generar el Acta Resumen!");
            FacesContext.getCurrentInstance().addMessage(null, message);

            return "";
        }
    }

    public String generarActasVolante(ArrayList<MateriaAlumno> materiasAlumno) {

        try {
            // si hay alumnos aprobados
            if (materiasAlumno != null && !materiasAlumno.isEmpty()) {

                //generar acta volante .docx
                documentGenerator.generarActasVolante(materiasAlumno, direccionOrigenVolante, direccionDestinoVolante);

                Comision comision = materiasAlumno.get(0).getComision();

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Generar Acta Volante", "Has generado correctamente las Actas Volante de la Comisión: " + comision.getNumero());
                FacesContext.getCurrentInstance().addMessage(null, message);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

                return "";
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Generar Actas Volante", "Esta comisión no tiene alumnos aprobados.");
                FacesContext.getCurrentInstance().addMessage(null, message);

                return "";
            }

        } catch (OpenDocxException ode) { // si la direccion del documento no existe
            //mensaje al growl
            System.out.println(ode);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Generar Acta Volante", "¡Error: ingresa la dirección origen del documento correctamente!");
            FacesContext.getCurrentInstance().addMessage(null, message);

            return "";

        } catch (Exception ex) { //si falla
            //mensaje al growl
            System.out.println(ex);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Generar Actas Volante", "¡Error: no se pudieron generar las Actas Volante!");
            FacesContext.getCurrentInstance().addMessage(null, message);

            return "";
        }
    }

    public void setDocumentGenerator(DocumentGenerator documentGenerator) {
        this.documentGenerator = documentGenerator;
    }

    public String getDireccionOrigenResumen() {
        return direccionOrigenResumen;
    }

    public void setDireccionOrigenResumen(String direccionOrigenResumen) {
        this.direccionOrigenResumen = direccionOrigenResumen;
    }

    public String getDireccionDestinoResumen() {
        return direccionDestinoResumen;
    }

    public void setDireccionDestinoResumen(String direccionDestinoResumen) {
        this.direccionDestinoResumen = direccionDestinoResumen;
    }

    public String getDireccionOrigenVolante() {
        return direccionOrigenVolante;
    }

    public void setDireccionOrigenVolante(String direccionOrigenVolante) {
        this.direccionOrigenVolante = direccionOrigenVolante;
    }

    public String getDireccionDestinoVolante() {
        return direccionDestinoVolante;
    }

    public void setDireccionDestinoVolante(String direccionDestinoVolante) {
        this.direccionDestinoVolante = direccionDestinoVolante;
    }

}
