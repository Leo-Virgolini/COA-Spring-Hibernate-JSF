package ar.com.leo.coa.mb;

import ar.com.leo.coa.model.Alumno;
import ar.com.leo.coa.model.Comision;
import ar.com.leo.coa.model.EscuelaSede;
import ar.com.leo.coa.model.MateriaAlumno;
import ar.com.leo.coa.model.MateriaComision;
import ar.com.leo.coa.model.MateriaProfesor;
import ar.com.leo.coa.model.Profesor;
import ar.com.leo.coa.service.MateriaService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.RowEditEvent;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Leo 
 */
@ManagedBean
@ViewScoped
public class MateriasManagedBean implements Serializable {

    @ManagedProperty("#{materiaService}")
    private MateriaService materiaService; // Setter required.

    @ManagedProperty("#{sessionManagedBean}")
    private SessionManagedBean sessionManagedBean; // +setter required

    private FacesContext facesContext;
    private HttpServletRequest httpServletRequest;

    //materias que adeuda el alumno
    private MateriaAlumno materiaAlumno;
    private List<MateriaAlumno> materiasAlumno;

    //materias que da el profesor
    private MateriaProfesor materiaProfesor;
    private List<MateriaProfesor> materiasProfesor;

    //materias propias que forman una comision
    private MateriaComision materiaComision;
    private List<MateriaComision> materiasComision;

    private List<MateriaComision> materiasFiltradas;

    //lista de cantidad de materias
    final private List<SelectItem> cantidadMaterias = new ArrayList<SelectItem>(); // +getter (no setter necessary)

    /**
     * Creates a new instance of MateriasManagedBean
     */
    @PostConstruct
    public void init() {

        facesContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();

//        String path1 = httpServletRequest.getRequestURI();
//        String path2 = httpServletRequest.getPathInfo();
//        String path3 = httpServletRequest.getRequestURI();
        String path = httpServletRequest.getServletPath();
//        String path5 = httpServletRequest.getContextPath();

//        System.out.println("getRequestURI: " + path1);
//        System.out.println("getPathInfo: " + path2);
//        System.out.println("getRequestURI: " + path3);
        System.out.println("Path MateriasManagedBean: " + path);
//        System.out.println("getContextPath: " + path5);

        if (path != null) //segun la página, elijo que materias mostrar
        {
            switch (path) {

                case "/escuelaSede/actaResumen.xhtml":
                    materiasAlumno = this.obtenerMateriasAlumnoAprobadasEscuelaSede(sessionManagedBean.getEscuelaSede());
                    break;

                case "/admin/gestionComisiones.xhtml":
                    materiasComision = this.obtenerMateriasComision();
                    break;

                case "/admin/gestionProfesores.xhtml":
                    materiasComision = this.obtenerMateriasComision();
                    materiaProfesor = new MateriaProfesor();
                    break;

                case "/admin/altaMateriaProfesor.xhtml":
                    materiasComision = this.obtenerMateriasComision();
                    materiaProfesor = new MateriaProfesor();
                    break;

                case "/admin/gestionAlumnos.xhtml":
                    materiasComision = this.obtenerMateriasComision();
                    materiaAlumno = new MateriaAlumno();
                    break;

                case "/admin/buscarAlumnos.xhtml":
                    materiasComision = this.obtenerMateriasComision();
                    materiaAlumno = new MateriaAlumno();
                    break;

                case "/admin/altaComision.xhtml":
                    materiasComision = this.obtenerMateriasComision();
                    break;

                case "/admin/altaMateriaComision.xhtml":
                    materiasComision = this.obtenerMateriasComision();
                    materiaComision = new MateriaComision();
                    break;

                case "/alumno/altaComision.xhtml":
                    materiasAlumno = this.obtenerMateriasNoInscriptasAlumno(sessionManagedBean.getAlumno());
                    break;

                case "/alumno/altaMateria.xhtml":
                    materiasAlumno = this.obtenerMateriasAlumno(sessionManagedBean.getAlumno().getId());
                    materiaAlumno = new MateriaAlumno();
                    break;

                default:
                    materiasComision = this.obtenerMateriasComision();
                    break;
            }
        }

//        for (int i = 1; i < 15; i++) {
//            cantidadMaterias.add(new SelectItem("" + i));
//        }
    }

    public void setMateriaService(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    //obtiene todas las Materias Alumno
    public List<MateriaAlumno> obtenerMateriasAlumno() {

        return materiaService.obtenerMateriasAlumno();
    }

    //obtiene todas las materias de materias_comision
    public List<MateriaComision> obtenerMateriasComision() {

        return materiaService.obtenerMateriasComision();
    }

    //obtiene las materias aprobadas del alumno de una EscuelaSede, ordenadas por Escuela Origen
    public List<MateriaAlumno> obtenerMateriasAlumnoAprobadasEscuelaSede(EscuelaSede escuelaSede) {

        return materiasAlumno = materiaService.obtenerMateriasAlumnoAprobadasEscuelaSede(escuelaSede);
    }

    //obtiene las materias aprobadas del alumno de una Comision
    public List<MateriaAlumno> obtenerMateriasAlumnoAprobadasComision(Comision comision) {

        return materiasAlumno = materiaService.obtenerMateriasAlumnoAprobadasComision(comision);
    }

    //obtiene TODAS las materias que debe el alumno
    public List<MateriaAlumno> obtenerMateriasAlumno(Long idAlumno) {

//        facesContext = FacesContext.getCurrentInstance();
//        httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        //obtengo el sessionAttribute alumno proveniente del LoginManagedBean
//        Alumno alumno = (Alumno) httpServletRequest.getSession().getAttribute("alumno");
        if (materiaAlumno != null) { // para que el Admin pueda crear MateriasAlumno
            Alumno alumno = new Alumno(); //creo un Alumno y lo seteo con su id en materiaAlumno para poder persistirlo en la bd
            alumno.setId(idAlumno);
            materiaAlumno.setAlumno(alumno);
        }

        return materiasAlumno = materiaService.obtenerMateriasAlumno(idAlumno);
    }

    //obtiene las materias a las que debe el alumno y que aun no inscribio a comision
    public List<MateriaAlumno> obtenerMateriasNoInscriptasAlumno(Alumno alumno) {

        //obtengo el sessionAttribute alumno proveniente del LoginManagedBean
//        Alumno alumno = (Alumno) httpServletRequest.getSession().getAttribute("sessionAlumno");
        return materiasAlumno = materiaService.obtenerMateriasNoInscriptasAlumno(alumno.getId());
    }

    //obtiene 1 materia que debe el alumno
    public MateriaAlumno obtenerMateriaAlumno(Long idMateriaAlumno) {
        return materiaAlumno = materiaService.obtenerMateriaAlumno(idMateriaAlumno);
    }

    public String altaMateriaComision() {

        if (materiaComision != null) {

            try {
                materiaComision.setNombre(materiaComision.getNombre().toUpperCase());
                //alta de materia
                materiaService.altaMateriaComision(materiaComision);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta Materia", "Materia: " + materiaComision.getNombre());
                FacesContext.getCurrentInstance().addMessage(null, message);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

                return "/admin/altaMateriaComision.xhtml?faces-redirect=true";

            } catch (DataAccessException ex) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alta Materia", "¡Error!");
                FacesContext.getCurrentInstance().addMessage(null, message);

                return "";
            }

        } else { //si no hay ninguna materia
            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alta Materia", "¡Error!");
            FacesContext.getCurrentInstance().addMessage(null, message);

            return "";
        }
    }

    // modifico MateriaComision
    public void onRowEdit(RowEditEvent event) {

        //obtengo la materia al cual editee
        MateriaComision materiaSeleccionada = (MateriaComision) event.getObject();

        //si hay alguna materia con ID distinta a 0
        if (materiaSeleccionada.getId() != 0) {

            try {
                materiaService.modificarMateriaComision(materiaSeleccionada);

                //mostrar tabla actualizada
                this.obtenerMateriasComision();

                //mensaje al growl
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Materia Modificada", "ID: " + materiaSeleccionada.getId());
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en Modificación", "Vuelve a intentarlo");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en Modificación", "ID: " + materiaSeleccionada.getId());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        //mensaje al growl
        FacesMessage msg = new FacesMessage("Modificación Cancelada", "ID: " + ((MateriaComision) event.getObject()).getId() + " " + ((MateriaComision) event.getObject()).getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    // modifico MateriaAlumno
    public void materiaAlumnoEdit(RowEditEvent event) {

        //obtengo la materia que quiero editar
        MateriaAlumno materiaSeleccionada = (MateriaAlumno) event.getObject();

        //si hay alguna materia con ID distinta a 0
        if (materiaSeleccionada.getId() != 0) {

            try {
                materiaService.modificarMateriaAlumno(materiaSeleccionada);

                //mostrar tabla actualizada
                materiasAlumno = this.obtenerMateriasAlumno(materiaSeleccionada.getAlumno().getId());

                //mensaje al growl
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Materia Modificada", "ID: " + materiaSeleccionada.getId());
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en Modificación", "Vuelve a intentarlo");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en Modificación", "ID: " + materiaSeleccionada.getId());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void materiaAlumnoCancel(RowEditEvent event) {
        //mensaje al growl
        FacesMessage msg = new FacesMessage("Modificación Cancelada", "ID: " + ((MateriaAlumno) event.getObject()).getId() + " " + ((MateriaAlumno) event.getObject()).getMateriaComision().getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    // modifico MateriaProfesor
    public void materiaProfesorEdit(RowEditEvent event) {

        //obtengo la materia al cual editee
        MateriaProfesor materiaSeleccionada = (MateriaProfesor) event.getObject();

        //si hay alguna materia con ID distinta a 0
        if (materiaSeleccionada.getId() != 0) {

            try {
                materiaService.modificarMateriaProfesor(materiaSeleccionada);

                //mensaje al growl
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Materia Modificada", "ID: " + materiaSeleccionada.getId());
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en Modificación", "Vuelve a intentarlo");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en Modificación", "ID: " + materiaSeleccionada.getId());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void materiaProfesorCancel(RowEditEvent event) {
        //mensaje al growl
        FacesMessage msg = new FacesMessage("Modificación Cancelada", "ID: " + ((MateriaProfesor) event.getObject()).getId() + " " + ((MateriaProfesor) event.getObject()).getMateriaComision().getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void bajaMateriaComision(MateriaComision materiaSeleccionada) {

        if (materiaSeleccionada != null) {

            try { //al eliminar una materia que es foreign key en otra tabla--> tira SQLException

                //dar de baja materia seleccionada 
                materiaService.bajaMateriaComision(materiaSeleccionada);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Baja Materia", "Materia: " + materiaSeleccionada.getNombre());
                FacesContext.getCurrentInstance().addMessage(null, message);

                //mostrar tabla actualizada
                materiasComision = this.obtenerMateriasComision();

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "¡Error: no puedes eliminar una Materia que está en otra tabla!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else { //si no hay ninguna materia seleccionada
            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Baja Materia", "¡Error!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    //ingresa la materia del alumno a materias_x_alumno
    public String altaMateriaAlumno(Alumno alumno) {

        if (materiaAlumno != null) {

            try {
                //alta de materia del Alumno
                MateriaAlumno materiaRegistrada = materiaService.altaMateriaAlumno(materiaAlumno, alumno);

                //mensaje confirmacion
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta Materia", "Has registrado la materia: " + materiaRegistrada.getMateriaComision().getNombre() + " " + materiaRegistrada.getAnio());
                FacesContext.getCurrentInstance().addMessage(null, message);

                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getFlash().setKeepMessages(true);

                return "/alumno/altaMateria.xhtml?faces-redirect=true";

            } catch (DataAccessException ex) {
                //mensaje error
                System.out.println(ex);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alta Materia", "Error en el registro, vuelve a intentarlo.");
                FacesContext.getCurrentInstance().addMessage(null, message);

                return "";
            }
        } else { //si no hay ninguna materia
            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alta Materia de Alumno", "¡Error!");
            FacesContext.getCurrentInstance().addMessage(null, message);

            return "";
        }

    }

    //admin inscribe una MateriaAlumno (a una Comision)
    public String altaMateriaAlumno() {

        try {

            //alta de materia del Alumno
            materiaService.altaMateriaAlumno(materiaAlumno);

            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Inscripción", "Has inscripto correctamente la materia: " + materiaAlumno.getMateriaComision().getNombre());
            FacesContext.getCurrentInstance().addMessage(null, message);

            //actualizar tabla
            materiasAlumno = this.obtenerMateriasAlumno(materiaAlumno.getAlumno().getId());

            //reseteo el id de la materiaAlumno para poder realizar otra alta
            materiaAlumno.setId(null);

            return "";

        } catch (DataAccessException ex) { //si falla el update
            //mensaje al growl
            System.out.println(ex);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alta Comisión Alumno", "¡Error: no se pudo inscribir correctamente!");
            FacesContext.getCurrentInstance().addMessage(null, message);

            return "";
        }
    }

    //el admin borra una Materia de un alumno
    public void bajaMateriaAlumno(MateriaAlumno materiaAlumno) {

        if (materiaAlumno != null) {

            try { //al eliminar una materia de alumno que es foreign key en otra tabla--> tira SQLException
                materiaService.eliminarMateriaAlumno(materiaAlumno);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Baja Materia de alumno", "ID: " + materiaAlumno.getId() + ", Nombre: " + materiaAlumno.getMateriaComision().getNombre() + " " + materiaAlumno.getAnio());
                FacesContext.getCurrentInstance().addMessage(null, message);

                //mostrar tabla actualizada
                materiasAlumno = this.obtenerMateriasAlumno(materiaAlumno.getAlumno().getId());

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "¡Error: no puedes eliminar una Materia de alumno que está en otra tabla!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la eliminación");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //admin inscribe una MateriaProfesor
    public String altaMateriaProfesor() {

        try {

            //alta de materia de Profesor
            materiaService.altaMateriaProfesor(materiaProfesor);

            //mensaje al growl
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Inscripción", "Has inscripto correctamente la materia: " + materiaProfesor.getMateriaComision().getNombre()
                    + " al profesor ID: " + materiaProfesor.getProfesor().getId());
            FacesContext.getCurrentInstance().addMessage(null, message);

            //actualizar tabla
            materiasProfesor = this.obtenerMateriasProfesor(materiaProfesor.getProfesor().getId());

            //reseteo el id de la materiaProfesor para poder realizar otra alta
            materiaProfesor.setId(null);

            return "";

        } catch (DataAccessException ex) { //si falla el update
            //mensaje al growl
            System.out.println(ex);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alta Materia Profesor", "¡Error: no se pudo inscribir correctamente!");
            FacesContext.getCurrentInstance().addMessage(null, message);

            return "";
        }
    }

    //el admin borra una Materia de un profesor
    public void bajaMateriaProfesor(MateriaProfesor materiaProfesor) {

        if (materiaProfesor != null) {

            try { //al eliminar una materia de profesor que es foreign key en otra tabla--> tira SQLException
                materiaService.eliminarMateriaProfesor(materiaProfesor);

                //mensaje al growl
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Baja Materia de profesor", "ID: " + materiaProfesor.getId() + ", Nombre: " + materiaProfesor.getMateriaComision().getNombre());
                FacesContext.getCurrentInstance().addMessage(null, message);

                //mostrar tabla actualizada
                materiasProfesor = this.obtenerMateriasProfesor(materiaProfesor.getProfesor().getId());

            } catch (DataAccessException ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "¡Error: no puedes eliminar una Materia de profesor que está en otra tabla!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la eliminación");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //muestra las materias disponibles segun lo que ingresa
    public List<MateriaComision> completeText(String query) {

        List<MateriaComision> materiasComision = new ArrayList<MateriaComision>();
        materiasComision = materiaService.obtenerMateriasComision(query);

        for (MateriaComision unaMateria : materiasComision) {
            System.out.println(unaMateria.getId() + " " + unaMateria.getNombre());
        }

        return materiasComision;
    }

    // asistencia de la comision
    public List<MateriaAlumno> obtenerAsistencia(Integer numeroComision) {
        return materiaService.obtenerAsistencia(numeroComision);
    }

    // asistencia de una comision de un alumno
    public List<MateriaAlumno> obtenerAsistenciaAlumno(Integer numeroComision, Long idAlumno) {
        return materiaService.obtenerAsistencia(numeroComision, idAlumno);
    }

    //materias del id del profesor
    public List<MateriaProfesor> obtenerMateriasProfesor(Long idProfesor) {

        if (materiaProfesor != null) {
            Profesor profesor = new Profesor(); //creo un Profesor y lo seteo con su id en materiaProfesor para poder crear nuevas materias
            profesor.setId(idProfesor);
            materiaProfesor.setProfesor(profesor);
        }

        return materiasProfesor = materiaService.obtenerMateriasProfesor(idProfesor);
    }

    public MateriaAlumno getMateriaAlumno() {
        return materiaAlumno;
    }

    public void setMateriaAlumno(MateriaAlumno materiaAlumno) {
        this.materiaAlumno = materiaAlumno;
    }

    public MateriaComision getMateriaComision() {
        return materiaComision;
    }

    public void setMateriaComision(MateriaComision materiaComision) {
        this.materiaComision = materiaComision;
    }

    public List<MateriaComision> getMateriasComision() {
        return materiasComision;
    }

    public void setMateriasComision(List<MateriaComision> materiasComision) {
        this.materiasComision = materiasComision;
    }

    public List<SelectItem> getCantidadMaterias() {
        return cantidadMaterias;
    }

    public List<MateriaComision> getMateriasFiltradas() {
        return materiasFiltradas;
    }

    public void setMateriasFiltradas(List<MateriaComision> materiasFiltradas) {
        this.materiasFiltradas = materiasFiltradas;
    }

    public List<MateriaProfesor> getMateriasProfesor() {
        return materiasProfesor;
    }

    public void setMateriasProfesor(List<MateriaProfesor> materiasProfesor) {
        this.materiasProfesor = materiasProfesor;
    }

    public MateriaProfesor getMateriaProfesor() {
        return materiaProfesor;
    }

    public void setMateriaProfesor(MateriaProfesor materiaProfesor) {
        this.materiaProfesor = materiaProfesor;
    }

    public List<MateriaAlumno> getMateriasAlumno() {
        return materiasAlumno;
    }

    public void setMateriasAlumno(List<MateriaAlumno> materiasAlumno) {
        this.materiasAlumno = materiasAlumno;
    }

    public void setSessionManagedBean(SessionManagedBean sessionManagedBean) {
        this.sessionManagedBean = sessionManagedBean;
    }

}
