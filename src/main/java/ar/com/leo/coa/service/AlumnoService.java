package ar.com.leo.coa.service;

import ar.com.leo.coa.model.Alumno;
import ar.com.leo.coa.model.Usuario;
import ar.com.leo.coa.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang.WordUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

//@Repository, @Service y @Controller son especializaciones de @Component para casos concretos (persistencia, servicios y presentación).
//The @Service annotation is also a specialization of the component annotation. It doesn’t currently provide any additional behavior over the @Component annotation
@Service("alumnoService")
public class AlumnoService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private AlumnoRepository alumnoRepository;

//    @Autowired //Singleton?
//    private AuditReader auditReader;
    
    @Transactional
    public Alumno altaAlumno(Alumno alumno) {
        alumno.setRol(Usuario.ALUMNO);
        alumno.setFechaCreacion(new Date());
        alumno.setEmail((alumno.getEmail().toLowerCase()));
        alumno.setNombre(WordUtils.capitalizeFully(alumno.getNombre()));
        alumno.setApellido(WordUtils.capitalizeFully(alumno.getApellido()));
        alumno.setDireccion(WordUtils.capitalizeFully(alumno.getDireccion()));

        return alumnoRepository.save(alumno);
    }

    @Transactional
    public void borrarAlumno(Alumno alumno) {
        alumnoRepository.delete(alumno);
    }

    @Transactional
    public void modificarAlumno(Alumno alumno) {
        alumnoRepository.save(alumno);
    }

    @Transactional(readOnly = true)
    public List<Alumno> obtenerAlumnos() {

        List<Alumno> alumnos = new ArrayList<Alumno>();
        for (Alumno alumno : alumnoRepository.findAll()) {
            alumnos.add(alumno);
        }

        return alumnos;
    }

    // Auditoria
    @Transactional(readOnly = true)
    public List<Alumno> obtenerAuditoriaAlumno(Long idAlumno) {

        List<Alumno> auditoriaAlumno = new ArrayList<Alumno>();

        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        AuditQuery aq = auditReader.createQuery().forRevisionsOfEntity(Alumno.class, false, true)
                .add(AuditEntity.id().eq(idAlumno));

        List<Object[]> results = aq.getResultList();

        for (Object[] o : results) {

            Alumno alumno = (Alumno) o[0];
            DefaultRevisionEntity revision = (DefaultRevisionEntity) o[1];
            RevisionType revisionType = (RevisionType) o[2];

            alumno.setRevision(revision.getId());
            alumno.setRevisionDate(revision.getRevisionDate());
            alumno.setRevisionType(revisionType.toString());

            auditoriaAlumno.add(alumno);

            System.out.println("Alumno [" + alumno + "] at revision [" + revision.getId() + "] at date [" + revision.getRevisionDate() + "] rev type [" + revisionType.toString() + "].");
        }

        return auditoriaAlumno;
    }

    @Transactional(readOnly = true)
    public Alumno obtenerPorId(Long id) {

        return alumnoRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Alumno> obtenerPorNombre(String nombre) {

        return alumnoRepository.findByNombre(nombre);
    }

    @Transactional(readOnly = true)
    public List<Alumno> obtenerPorNumeroComision(Integer numeroComision) {
        return alumnoRepository.obtenerPorNumeroComision(numeroComision);
    }

    @Transactional(readOnly = true)
    public List<Alumno> obtenerPorApellido(String apellido) {
        return alumnoRepository.findByApellido(apellido);
    }

    @Transactional(readOnly = true)
    public List<Alumno> obtenerPorDNI(String dni) {
        return alumnoRepository.findByDni(dni);
    }

    @Transactional
    public void borrarComisionAlumno(Alumno alumno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Transactional(readOnly = true)
    public Alumno obtenerAlumno(Long id) {
        return alumnoRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Alumno> obtenerPorNombreApellidoDni(String busqueda) {
        return alumnoRepository.obtenerPorNombreApellidoDni(busqueda);
    }

    @Transactional
    public void habilitarAlumno(Alumno alumnoSeleccionado) {
        alumnoRepository.habilitarAlumno(alumnoSeleccionado.getId());
    }

    @Transactional
    public void deshabilitarAlumno(Alumno alumnoSeleccionado) {
        alumnoRepository.deshabilitarAlumno(alumnoSeleccionado.getId());
    }

    @Transactional(readOnly = true)
    public boolean validarDni(String dni) {
        //si el dni que ingresaste ya existe: true, sino false
        return dni.equals(alumnoRepository.validarDni(dni));
    }

    @Transactional(readOnly = true)
    public Long obtenerCantidadAlumnos() {
        return alumnoRepository.obtenerCantidadAlumnos();
    }

    @Transactional(readOnly = true)
    public Long obtenerAlumnosMasculinos() {
        return alumnoRepository.obtenerAlumnosMasculinos();
    }

}
