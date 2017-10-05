package ar.com.leo.coa.repository;

import ar.com.leo.coa.model.Profesor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
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
public interface ProfesorRepository extends CrudRepository<Profesor, Long> {

    public List<Profesor> findByNombre(String nombre);

//    @Query("SELECT * FROM coaspring.profesores P, coaspring.login Log, coaspring.localidades L WHERE Log.id_login= ?1 AND P.id_login = Log.id_login AND L.Id_Localidad = P.Id_Localidad")
//    Profesor obtenerEntidad(Long id);
//    
//    List<Profesor> findByNombre(String nombre);
//    
//    List<Profesor> findByApellido(String apellido);
//
//    List<Profesor> findByNombreAndApellido(String nombre, String apellido);
//
//    List<Profesor> findByDni(Long dni);
//    
//    Profesor findByComision(Comision comision); //
//    
    @Modifying
    @Query("UPDATE Profesor p SET p.habilitado = false WHERE p.id = ?1")
    void deshabilitarProfesor(Long id);

    @Modifying
    @Query("UPDATE Profesor p SET p.habilitado = true WHERE p.id = ?1")
    void habilitarProfesor(Long id);

    @Query("SELECT p.dni FROM Profesor p WHERE p.dni = ?1")
    String validarDni(String dni);
}
