package ar.com.leo.coa.repository;

import ar.com.leo.coa.model.Usuario;
import org.springframework.data.repository.CrudRepository;

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
public interface LoginRepository extends CrudRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    // devuelve un Usuario si las contraseñas coinciden, sino null
    @Query("SELECT u FROM Usuario u WHERE u.email = ?1 AND u.password = ?2")
    Usuario autenticarUsuario(String email, String password);

//    @Query(value = "SELECT u.email, u.password, u.id_rol, u.habilitado FROM Usuario u WHERE u.email = ?1 AND u.password = ?2", nativeQuery = true)
//    List<Object[]> autenticarUsuario(String email, String password);
    
    // devuelve un Usuario con el email y password
    @Query("SELECT new Usuario(u.email, u.password) FROM Usuario u WHERE u.email = ?1")
    Usuario recuperarContraseña(String email);

    // buscar si el email que ingreso ya existe
    @Query("SELECT u.email FROM Usuario u WHERE u.email = ?1")
    String validarEmail(String email);
    
}
