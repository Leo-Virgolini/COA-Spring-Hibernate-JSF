package ar.com.leo.coa.repository;

import ar.com.leo.coa.model.Alumno;
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
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {

    //obtengo el Alumno a partir del id_login de usuario
//    @Query("SELECT * FROM coaspring.alumnos A, coa.login Log, coaspring.provincias P, coaspring.localidades L WHERE A.id_login = Log.id_login AND Log.id_login=?1")
//    Alumno obtenerEntidad(Long id);
    List<Alumno> findByNombre(String nombre);

    List<Alumno> findByApellido(String apellido);

    List<Alumno> findByNombreAndApellido(String nombre, String apellido);

    List<Alumno> findByDni(String dni);

    @Query("SELECT ma.alumno FROM MateriaAlumno ma WHERE ma.comision.numero = ?1")
    List<Alumno> obtenerPorNumeroComision(Integer numeroComision);

    @Query("SELECT a FROM Alumno a WHERE a.apellido LIKE %?1% OR a.nombre LIKE %?1% OR a.dni LIKE %?1%")
    List<Alumno> obtenerPorNombreApellidoDni(String busqueda);

    @Modifying
    @Query("UPDATE Alumno a SET a.habilitado = true WHERE a.id = ?1")
    void habilitarAlumno(Long idAlumno);

    @Modifying
    @Query("UPDATE Alumno a SET a.habilitado = false WHERE a.id = ?1")
    void deshabilitarAlumno(Long idAlumno);

    // buscar si el dni que ingreso ya existe
    @Query("SELECT a.dni FROM Alumno a WHERE a.dni = ?1")
    String validarDni(String dni);

    @Query("SELECT count(*) FROM Alumno")
    Long obtenerCantidadAlumnos();

    @Query("SELECT count(*) FROM Alumno a WHERE a.sexo = 'm'")
    Long obtenerAlumnosMasculinos();

}
