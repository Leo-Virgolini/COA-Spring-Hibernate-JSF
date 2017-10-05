package ar.com.leo.coa.repository;

import ar.com.leo.coa.model.Comision;
import ar.com.leo.coa.model.EscuelaSede;
import ar.com.leo.coa.model.MateriaAlumno;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/*
 getBy , readBy , findBy

 And	         findByLastnameAndFirstname	… where x.lastname = ?1 and x.firstname = ?2
 Or	        findByLastnameOrFirstname	… where x.lastname = ?1 or x.firstname = ?2
 Between	    findByStartDateBetween	… where x.startDate between 1? and ?2
 LessThan	findByAgeLessThan	… where x.age < ?1
 GreaterThan	findByAgeGreaterThan	… where x.age > ?1
 After	    findByStartDateAfter	… where x.startDate > ?1
 Before	    findByStartDateBefore	… where x.startDate < ?1
 IsNull	    findByAgeIsNull	… where x.age is null
 IsNotNull,NotNull	findByAge(Is)NotNull	… where x.age not null
 Like	    findByFirstnameLike	… where x.firstname like ?1
 NotLike	    findByFirstnameNotLike	… where x.firstname not like ?1
 StartingWith	findByFirstnameStartingWith	… where x.firstname like ?1 (parameter bound with appended %)
 EndingWith	  findByFirstnameEndingWith	… where x.firstname like ?1 (parameter bound with prepended %)
 Containing	findByFirstnameContaining	… where x.firstname like ?1 (parameter bound wrapped in %)
 OrderBy	findByAgeOrderByLastnameDesc	… where x.age = ?1 order by x.lastname desc
 Not	findByLastnameNot	… where x.lastname <> ?1
 In	findByAgeIn(Collection<Age> ages)	… where x.age in ?1
 NotIn	findByAgeNotIn(Collection<Age> age)	… where x.age not in ?1
 True	findByActiveTrue()	… where x.active = true
 False	findByActiveFalse()	… where x.active = false

 */
// SpringData
@Repository
public interface MateriaAlumnoRepository extends CrudRepository<MateriaAlumno, Long> {

    @Query("SELECT ma FROM MateriaAlumno ma WHERE ma.comision.numero = ?1")
    List<MateriaAlumno> obtenerAsistencia(Integer numeroComision);

    @Query("SELECT ma FROM MateriaAlumno ma WHERE ma.alumno.id = ?1")
    List<MateriaAlumno> obtenerMateriasAlumno(Long idAlumno);

    @Query("SELECT ma FROM MateriaAlumno ma WHERE ma.alumno.id = ?1 AND ma.comision.numero is not null")
    List<MateriaAlumno> obtenerMateriasInscriptasAlumno(Long idAlumno);

    @Query("SELECT ma FROM MateriaAlumno ma WHERE ma.alumno.id = ?1 AND ma.comision.numero is null")
    List<MateriaAlumno> obtenerMateriasNoInscriptasAlumno(Long idAlumno);

    @Query("SELECT ma FROM MateriaAlumno ma WHERE ma.materiaComision.nombre = ?1")
    List<MateriaAlumno> obtenerMaterias(String query);

    @Query("SELECT ma FROM MateriaAlumno ma WHERE ma.comision.numero = ?1 AND ma.alumno.id = ?2")
    List<MateriaAlumno> obtenerAsistencia(Integer numeroComision, Long idAlumno);

    @Query("SELECT ma FROM MateriaAlumno ma WHERE ma.comision.escuelaSede = ?1 AND ma.nota >= 4 ORDER BY ma.alumno.escuelaOrigen")
    List<MateriaAlumno> obtenerMateriasAlumnoAprobadasEscuelaSede(EscuelaSede escuelaSede);

    @Query("SELECT ma FROM MateriaAlumno ma WHERE ma.comision = ?1 AND ma.nota >= 4 ORDER BY ma.alumno.escuelaOrigen")
    List<MateriaAlumno> obtenerMateriasAlumnoAprobadasComision(Comision comision);

    // estadisticas
    @Query("SELECT count(*) FROM MateriaAlumno")
    Long obtenerCantidadMateriasAlumno();

    @Query("SELECT ma FROM MateriaAlumno ma ORDER BY ma.materiaComision.nombre")
    List<MateriaAlumno> obtenerMateriasAlumnoOrdenadas();

    @Query(value = "SELECT mt.nombre, count(ma.id_materia_comision) FROM materias_x_alumno ma INNER JOIN materias_total mt ON ma.id_materia_comision = mt.id GROUP BY ma.id_materia_comision",
            nativeQuery = true)
    List<Object[]> obtenerMateriasAlumnosYCantidad();

    @Query(value = "SELECT count(ma.id) FROM materias_x_alumno ma WHERE ma.nota >= 4",
            nativeQuery = true)
    Long obtenerCantidadMateriasAprobadas();

    @Query(value = "SELECT COUNT(*) FROM(SELECT COUNT(ma.id_alumno) FROM materias_x_alumno ma GROUP BY ma.id_alumno HAVING MIN(ma.nota) >= 4 AND count(ma.nota) = count(*)) src",
            nativeQuery = true)
    Long obtenerCantidadAlumnosAprobados();

}
