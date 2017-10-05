package ar.com.leo.coa.service;

import ar.com.leo.coa.model.Alumno;
import ar.com.leo.coa.model.Comision;
import ar.com.leo.coa.model.EscuelaSede;
import ar.com.leo.coa.model.MateriaAlumno;
import ar.com.leo.coa.model.MateriaComision;
import ar.com.leo.coa.repository.ComisionRepository;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@Repository, @Service y @Controller son especializaciones de @Component para casos concretos (persistencia, servicios y presentación).
//The @Service annotation is also a specialization of the component annotation. It doesn’t currently provide any additional behavior over the @Component annotation
@Service("comisionService")
public class ComisionService {

    @Autowired
    private ComisionRepository comisionRepository;

    @Transactional(readOnly = true)
    public Comision obtenerComision(Integer numeroComision) {
        return comisionRepository.findOne(numeroComision);
    }

    @Transactional
    public Comision salvarComision(Comision comision) {
        return comisionRepository.save(comision);
    }

    @Transactional
    public void borrarComision(Comision comision) {
        comisionRepository.delete(comision);
    }

    @Transactional
    public void modificarComision(Comision comision) {
        comisionRepository.save(comision);
    }

    @Transactional(readOnly = true)
    public List<Comision> obtenerComisiones() {

        List<Comision> comisiones = new ArrayList<Comision>();
        for (Comision comision : comisionRepository.findAll()) {
            comisiones.add(comision);
        }

        return comisiones;
    }

    @Transactional(readOnly = true)
    public List<Comision> obtenerPorMateriaComision(MateriaComision materiaComision) {
        return comisionRepository.findByMateriaComision(materiaComision);
    }

    @Transactional(readOnly = true)
    public List<Comision> obtenerPorEscuelaSede(EscuelaSede escuelaSede) {
        return comisionRepository.findByEscuelaSede(escuelaSede);
    }

    // el alumno se inscribe a una Comision a partir de una materia adeudada
    @Transactional
    public void altaComisionAlumno(Comision comisionSeleccionada, MateriaAlumno materiaAlumno) {
        // UPDATE en la tabla materias_x_alumno: numero_comision WHERE id_materiasXalumno
        comisionRepository.altaComisionAlumno(comisionSeleccionada.getNumero(), materiaAlumno.getId());
    }

    // Alumno se da de baja de una Comision
    @Transactional
    public void bajaComisionAlumno(Comision comisionSeleccionada, Alumno alumno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // admin da de baja una Comision
    @Transactional
    public void bajaComision(Comision comisionSeleccionada) {
        comisionRepository.delete(comisionSeleccionada);
    }

    // admin crea una Comision
    @Transactional
    public void altaComision(Comision comision) {
        comisionRepository.save(comision);
    }

    @Transactional(readOnly = true)
    public List<Comision> obtenerComisionesInscriptas(Long id) {
        return comisionRepository.obtenerComisionesInscriptas(id);
    }

    @Transactional(readOnly = true)
    public List<Comision> obtenerComisionesNoInscriptas(Long id) {
        return comisionRepository.obtenerComisionesNoInscriptas(id);
    }

    @Transactional(readOnly = true)
    public List<Comision> obtenerComisionesProfesor(Long id) {
        return comisionRepository.obtenerComisionesProfesor(id);
    }

    @Transactional(readOnly = true)
    public List<Comision> obtenerComisionesMateria(Long idAlumno, Long idMateriaComision) {
        return comisionRepository.obtenerComisionesMateria(idAlumno, idMateriaComision);
    }

    @Transactional(readOnly = true)
    public List<Comision> obtenerComisionesEscuelaSede(Long idEscuelaSede) {
        return comisionRepository.obtenerComisionesEscuelaSede(idEscuelaSede);
    }

    @Transactional(readOnly = true)
    public HashMap<String, Long> obtenerComisionesYCantidad() {

        HashMap<String, Long> comisiones = new HashMap<String, Long>();

        for (Object[] unaEscuela : comisionRepository.obtenerComisionesYCantidad()) {
            comisiones.put((String) unaEscuela[0], ((BigInteger) unaEscuela[1]).longValue());
        }

        return comisiones;
    }

    @Transactional(readOnly = true)
    public Long obtenerCantidadComisiones() {
        return comisionRepository.obtenerCantidadComisiones();
    }

}
