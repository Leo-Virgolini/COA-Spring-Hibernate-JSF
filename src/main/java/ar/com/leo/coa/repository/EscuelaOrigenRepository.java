package ar.com.leo.coa.repository;

import ar.com.leo.coa.model.EscuelaOrigen;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface EscuelaOrigenRepository extends JpaRepository<EscuelaOrigen, Integer> {

    List<EscuelaOrigen> findByNombre(String nombre);

    List<EscuelaOrigen> findByNombreStartingWith(String nombre);

    List<EscuelaOrigen> findByNombreContaining(String nombre);

    @Query("SELECT eo FROM EscuelaOrigen eo WHERE eo.nombre LIKE %?1% AND eo.localidad.id = ?2")
    List<EscuelaOrigen> obtenerPorNombreYLocalidad(String query, Integer idLocalidad);

    @Query("SELECT eo FROM EscuelaOrigen eo WHERE eo.nombre LIKE %?1% AND eo.localidad.distrito.provincia.id = ?2")
    List<EscuelaOrigen> obtenerPorNombreYProvincia(String query, int idProvincia);

    @Query("SELECT eo FROM EscuelaOrigen eo ORDER BY eo.localidad.distrito.provincia.descripcion, eo.localidad.distrito.descripcion, eo.localidad.descripcion")
    List<EscuelaOrigen> obtenerEscuelasOrigen();

    @Query("SELECT a.escuelaOrigen FROM Alumno a WHERE a.escuelaOrigen.id is not null ORDER BY a.escuelaOrigen.id")
    List<EscuelaOrigen> obtenerEscuelasOrigenOrdenadas();

    @Query(value = "SELECT eo.nombre, count(a.id_escuela_origen) FROM coatest.escuelas_origen eo INNER JOIN alumnos a ON eo.id = a.id_escuela_origen GROUP BY a.id_escuela_origen",
            nativeQuery = true)
    List<Object[]> obtenerEscuelasOrigenYCantidad();

    @Query(value = "SELECT l.descripcion, count(eo.id_localidad) FROM coatest.escuelas_origen eo, localidades l, alumnos a WHERE eo.id_localidad = l.id AND eo.id = a.id_escuela_origen GROUP BY eo.id_localidad",
            nativeQuery = true)
    List<Object[]> obtenerLocalidadesYCantidad();

    @Query(value = "SELECT d.descripcion, count(l.id_distrito) FROM coatest.escuelas_origen eo, localidades l, distritos d, alumnos a WHERE eo.id_localidad = l.id AND eo.id = a.id_escuela_origen AND d.id = l.id_distrito GROUP BY l.id_distrito",
            nativeQuery = true)
    List<Object[]> obtenerDistritosYCantidad();

    @Query(value = "SELECT p.descripcion, count(d.id_provincia) FROM coatest.escuelas_origen eo, localidades l, distritos d, provincias p, alumnos a WHERE eo.id_localidad = l.id AND eo.id = a.id_escuela_origen AND d.id = l.id_distrito AND p.id = d.id_provincia GROUP BY d.id_provincia",
            nativeQuery = true)
    List<Object[]> obtenerProvinciasYCantidad();

    @Query(value = "SELECT count(a.id_escuela_origen) FROM coatest.escuelas_origen eo, alumnos a, distritos d, localidades l WHERE eo.id = a.id_escuela_origen AND l.id = eo.id_localidad AND l.id_distrito = d.id AND (l.id_distrito = 4690 OR l.id_distrito = 4724 OR l.id_distrito = 4671 OR l.id_distrito = 4679)",
            nativeQuery = true)
    Long obtenerCantidadRegion6();

    @Query(value = "SELECT d.descripcion, count(l.id_distrito) FROM coatest.escuelas_origen eo, localidades l, distritos d, alumnos a WHERE eo.id_localidad = l.id AND eo.id = a.id_escuela_origen AND d.id = l.id_distrito AND (l.id_distrito = 4690 OR l.id_distrito = 4724 OR l.id_distrito = 4671 OR l.id_distrito = 4679) GROUP BY l.id_distrito",
            nativeQuery = true)
    List<Object[]> obtenerCantidadDistritosRegion6();

    @Query(value = "SELECT count(a.id_escuela_origen) FROM coatest.alumnos a",
            nativeQuery = true)
    Long obtenerCantidadEscuelasOrigen();

}
