package ar.com.leo.coa.repository;

import ar.com.leo.coa.model.Comision;
import ar.com.leo.coa.model.EscuelaSede;
import ar.com.leo.coa.model.MateriaComision;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
 IsNotNull, NotNull	findByAge(Is)NotNull	… where x.age not null
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
public interface ComisionRepository extends CrudRepository<Comision, Integer> {

    List<Comision> findByMateriaComision(MateriaComision materiaComision);

    List<Comision> findByEscuelaSede(EscuelaSede escuelaSede);

    @Modifying
    @Query("UPDATE MateriaAlumno ma SET ma.comision.numero = ?1 WHERE ma.id = ?2")
    public void altaComisionAlumno(Integer numeroComision, Long idMateriaAlumno);

    @Query("SELECT c"
            + " FROM Comision c, MateriaAlumno ma"
            + " WHERE ma.alumno.id = ?1"
            + " AND c.numero = ma.comision.numero"
            + " AND ma.comision.numero is not null")
    public List<Comision> obtenerComisionesInscriptas(Long idAlumno);

    @Query("SELECT c"
            + " FROM Comision c, MateriaAlumno ma"
            + " WHERE ma.alumno.id = ?1"
            + " AND ma.comision.numero is null")
    public List<Comision> obtenerComisionesNoInscriptas(Long idAlumno);

    @Query("SELECT c"
            + " FROM Comision c"
            + " WHERE c.profesor.id = ?1 AND c.profesor.id is not null")
    public List<Comision> obtenerComisionesProfesor(Long idProfesor);

    // Muestra una lista de Comisiones que el Alumno se puede inscribir de acuerdo a las MateriaAlumno que adeude, y a las que no está inscripto.
    @Query("SELECT c"
            + " FROM Comision c"
            + " WHERE c.cantidad < 30"
            + " AND c.materiaComision.id = ?2"
            + " AND NOT EXISTS"
            + " (SELECT ma"
            + " FROM MateriaAlumno ma"
            + " WHERE ma.alumno.id = ?1"
            + " AND c.numero = ma.comision.numero"
            + " AND ma.comision.numero is not null)")
    public List<Comision> obtenerComisionesMateria(Long idAlumno, Long idMateriaComision);

    @Query("SELECT c"
            + " FROM Comision c"
            + " WHERE c.escuelaSede.id = ?1")
    public List<Comision> obtenerComisionesEscuelaSede(Long idEscuelaSede);

    @Query(value = "SELECT mt.nombre, count(c.id_materia_comision) FROM coatest.comisiones c INNER JOIN coatest.materias_total mt ON c.id_materia_comision = mt.id GROUP BY c.id_materia_comision;",
            nativeQuery = true)
    public List<Object[]> obtenerComisionesYCantidad();

    @Query("SELECT count(c.id) FROM Comision c")
    public Long obtenerCantidadComisiones();

}
