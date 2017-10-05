package ar.com.leo.coa.service;

import ar.com.leo.coa.model.Alumno;
import ar.com.leo.coa.model.Comision;
import ar.com.leo.coa.model.EscuelaSede;
import ar.com.leo.coa.model.MateriaAlumno;
import ar.com.leo.coa.model.MateriaComision;
import ar.com.leo.coa.model.MateriaProfesor;
import ar.com.leo.coa.repository.MateriaComisionRepository;
import ar.com.leo.coa.repository.MateriaAlumnoRepository;
import ar.com.leo.coa.repository.MateriaProfesorRepository;
import java.math.BigInteger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Repository, @Service y @Controller son especializaciones de @Component para casos concretos (persistencia, servicios y presentación).
//The @Service annotation is also a specialization of the component annotation. It doesn’t currently provide any additional behavior over the @Component annotation
@Service("materiaService")
public class MateriaService {

    @Autowired
    private MateriaAlumnoRepository materiaAlumnoRepository;

    @Autowired
    private MateriaProfesorRepository materiaProfesorRepository;

    @Autowired
    private MateriaComisionRepository materiaComisionRepository;

    // MATERIAS COMISION
    @Transactional(readOnly = true)
    public MateriaComision obtenerMateriaComision(Long idMateriaComision) {

        return materiaComisionRepository.findOne(idMateriaComision);
    }

    @Transactional(readOnly = true)
    public List<MateriaComision> obtenerMateriasComision() {

        ArrayList<MateriaComision> materias = new ArrayList<MateriaComision>();

        for (MateriaComision unaMateria : materiaComisionRepository.findAll()) {
            materias.add(unaMateria);
        }

        return materias;
    }

    @Transactional(readOnly = true)
    public List<MateriaComision> obtenerMateriasComision(String query) {
        return materiaComisionRepository.obtenerMateriasComision(query);
    }

    // admin crea una MateriaComision
    @Transactional
    public void altaMateriaComision(MateriaComision materiaComision) {
        materiaComisionRepository.save(materiaComision);
    }

    @Transactional
    public void modificarMateriaComision(MateriaComision materiaComisionSeleccionada) {
        materiaComisionRepository.save(materiaComisionSeleccionada);
    }

    @Transactional
    public void bajaMateriaComision(MateriaComision materiaComSeleccionada) {
        materiaComisionRepository.delete(materiaComSeleccionada);
    }

    // MATERIAS ALUMNO
    @Transactional(readOnly = true)
    public List<MateriaAlumno> obtenerMateriasAlumno() {

        ArrayList<MateriaAlumno> materias = new ArrayList<MateriaAlumno>();

        for (MateriaAlumno unaMateria : materiaAlumnoRepository.findAll()) {
            materias.add(unaMateria);
        }

        return materias;
    }

    @Transactional(readOnly = true)
    public List<MateriaAlumno> obtenerMateriasAlumnoOrdenadas() {

        ArrayList<MateriaAlumno> materias = new ArrayList<MateriaAlumno>();

        for (MateriaAlumno unaMateria : materiaAlumnoRepository.obtenerMateriasAlumnoOrdenadas()) {
            materias.add(unaMateria);
        }

        return materias;
    }

    @Transactional(readOnly = true)
    public HashMap<String, Long> obtenerMateriasAlumnosYCantidad() {

        HashMap<String, Long> materias = new HashMap<String, Long>();

        for (Object[] unaMateria : materiaAlumnoRepository.obtenerMateriasAlumnosYCantidad()) {
            materias.put((String) unaMateria[0], ((BigInteger) unaMateria[1]).longValue());
        }

        return materias;
    }

    @Transactional(readOnly = true)
    public List<MateriaAlumno> obtenerMateriasAlumno(Long idAlumno) {
        return materiaAlumnoRepository.obtenerMateriasAlumno(idAlumno);
    }

    @Transactional(readOnly = true)
    public List<MateriaAlumno> obtenerMateriasNoInscriptasAlumno(Long idAlumno) {
        return materiaAlumnoRepository.obtenerMateriasNoInscriptasAlumno(idAlumno);
    }

    // alumno ingresa su materia adeudada
    @Transactional
    public MateriaAlumno altaMateriaAlumno(MateriaAlumno materiaAlumno, Alumno alumno) {

        // le agrego al objeto MateriaAlumno el Alumno para poder persistirlo en la bd
        materiaAlumno.setAlumno(alumno);
        materiaAlumno.setComision(null);

        return materiaAlumnoRepository.save(materiaAlumno); // guardo en la tabla materias_x_alumno: el id_materia y id_alumno
    }

    @Transactional(readOnly = true)
    public List<MateriaAlumno> obtenerMaterias(String query) {
        return materiaAlumnoRepository.obtenerMaterias(query);
    }

    @Transactional(readOnly = true)
    public List<MateriaAlumno> obtenerAsistencia(Integer numeroComision) {
        return materiaAlumnoRepository.obtenerAsistencia(numeroComision);
    }

    @Transactional
    public void modificarMateriaAlumno(MateriaAlumno materiaAlumnoSeleccionada) {
        materiaAlumnoRepository.save(materiaAlumnoSeleccionada);
    }

    @Transactional
    public void altaMateriaAlumno(MateriaAlumno materiaAlumno) {
        materiaAlumnoRepository.save(materiaAlumno);
    }

    @Transactional
    public void eliminarMateriaAlumno(MateriaAlumno materiaAlumno) {
        materiaAlumnoRepository.delete(materiaAlumno.getId());
    }

    @Transactional(readOnly = true)
    public List<MateriaAlumno> obtenerAsistencia(Integer numeroComision, Long idAlumno) {
        return materiaAlumnoRepository.obtenerAsistencia(numeroComision, idAlumno);
    }

    // Escuelas Origen ordenadas
    @Transactional(readOnly = true)
    public List<MateriaAlumno> obtenerMateriasAlumnoAprobadasEscuelaSede(EscuelaSede escuelaSede) {
        return materiaAlumnoRepository.obtenerMateriasAlumnoAprobadasEscuelaSede(escuelaSede);
    }

    @Transactional(readOnly = true)
    public List<MateriaAlumno> obtenerMateriasAlumnoAprobadasComision(Comision comision) {
        return materiaAlumnoRepository.obtenerMateriasAlumnoAprobadasComision(comision);
    }

    @Transactional(readOnly = true)
    public MateriaAlumno obtenerMateriaAlumno(Long idMateriaAlumno) {
        return materiaAlumnoRepository.findOne(idMateriaAlumno);
    }

    // MATERIAS PROFESOR
    // materias que da el Profesor
    @Transactional(readOnly = true)
    public List<MateriaProfesor> obtenerMateriasProfesor(Long idProfesor) {
        return materiaProfesorRepository.obtenerMateriasProfesor(idProfesor);
    }

    @Transactional
    public void agregarMateriasProfesor(List<MateriaProfesor> materiasProfesor) {
        materiaProfesorRepository.save(materiasProfesor);
    }

    @Transactional
    public void modificarMateriaProfesor(MateriaProfesor materiaProfesorSeleccionada) {
        materiaProfesorRepository.save(materiaProfesorSeleccionada);
    }

    @Transactional
    public void eliminarMateriaProfesor(MateriaProfesor materiaProfesor) {
        materiaProfesorRepository.delete(materiaProfesor.getId());
    }

    @Transactional
    public void altaMateriaProfesor(MateriaProfesor materiaProfesor) {
        materiaProfesorRepository.save(materiaProfesor);
    }

    // ESTADISTICAS
    @Transactional(readOnly = true)
    public Long obtenerCantidadMateriasAlumno() {
        return materiaAlumnoRepository.obtenerCantidadMateriasAlumno();
    }

    @Transactional(readOnly = true)
    public Long obtenerCantidadMateriasAprobadas() {
        return materiaAlumnoRepository.obtenerCantidadMateriasAprobadas();
    }

    @Transactional(readOnly = true)
    public Long obtenerCantidadAlumnosAprobados() {
        return materiaAlumnoRepository.obtenerCantidadAlumnosAprobados();
    }

}
