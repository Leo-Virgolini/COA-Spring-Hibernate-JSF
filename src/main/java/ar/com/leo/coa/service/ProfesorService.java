package ar.com.leo.coa.service;

import ar.com.leo.coa.model.Profesor;
import ar.com.leo.coa.model.Usuario;
import ar.com.leo.coa.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

//@Repository, @Service y @Controller son especializaciones de @Component para casos concretos (persistencia, servicios y presentación).
//The @Service annotation is also a specialization of the component annotation. It doesn’t currently provide any additional behavior over the @Component annotation
@Service("profesorService")
public class ProfesorService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Transactional(readOnly = true)
    public Profesor obtenerProfesor(Long id) {
        return profesorRepository.findOne(id);
    }

    // Auditoria
    @Transactional(readOnly = true)
    public List<Profesor> obtenerAuditoriaProfesor(Long idProfesor) {

        List<Profesor> auditoriaProfesor = new ArrayList<Profesor>();

        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        AuditQuery aq = auditReader.createQuery().forRevisionsOfEntity(Profesor.class, false, true)
                .add(AuditEntity.id().eq(idProfesor));

        List<Object[]> results = aq.getResultList();

        for (Object[] o : results) {

            Profesor profesor = (Profesor) o[0];
            DefaultRevisionEntity revision = (DefaultRevisionEntity) o[1];
            RevisionType revisionType = (RevisionType) o[2];

            profesor.setRevision(revision.getId());
            profesor.setRevisionDate(revision.getRevisionDate());
            profesor.setRevisionType(revisionType.toString());

            auditoriaProfesor.add(profesor);

            System.out.println("Profesor [" + profesor + "] at revision [" + revision.getId() + "] at date [" + revision.getRevisionDate() + "] rev type [" + revisionType.toString() + "].");
        }

        return auditoriaProfesor;
    }

    @Transactional
    public Profesor altaProfesor(Profesor profesor) {
        //le seteo al profesor el rol=2 profesor
        profesor.setRol(Usuario.PROFESOR);

        return profesorRepository.save(profesor);
    }

    @Transactional(readOnly = true)
    public Profesor obtenerPorId(Long id) {
        return profesorRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Profesor> obtenerPorNombre(String nombre) {
        return profesorRepository.findByNombre(nombre);
    }

    @Transactional(readOnly = true)
    public List<Profesor> obtenerProfesores() {

        List<Profesor> profesores = new ArrayList<Profesor>();

        for (Profesor profesor : profesorRepository.findAll()) {
            profesores.add(profesor);
        }

        return profesores;
    }

    // tiene que ser logica
    @Transactional
    public void eliminarProfesor(Profesor profesorSeleccionado) {
        profesorRepository.delete(profesorSeleccionado.getId());
    }

    @Transactional
    public void modificarProfesor(Profesor profesorSeleccionado) {
        profesorRepository.save(profesorSeleccionado);
    }

    @Transactional
    public void deshabilitarProfesor(Profesor profesorSeleccionado) {
        profesorRepository.deshabilitarProfesor(profesorSeleccionado.getId());
    }

    @Transactional
    public void habilitarProfesor(Profesor profesorSeleccionado) {
        profesorRepository.habilitarProfesor(profesorSeleccionado.getId());
    }

    @Transactional(readOnly = true)
    public boolean validarDni(String dni) {
        return dni.equals(profesorRepository.validarDni(dni));
    }

}
