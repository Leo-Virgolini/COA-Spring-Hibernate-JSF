package ar.com.leo.coa.service;

import ar.com.leo.coa.model.EscuelaOrigen;
import ar.com.leo.coa.model.EscuelaSede;
import ar.com.leo.coa.model.Usuario;
import ar.com.leo.coa.repository.EscuelaOrigenRepository;
import ar.com.leo.coa.repository.EscuelaSedeRepository;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

//@Repository, @Service y @Controller son especializaciones de @Component para casos concretos (persistencia, servicios y presentación).
//The @Service annotation is also a specialization of the component annotation. It doesn’t currently provide any additional behavior over the @Component annotation
@Service("escuelaService")
public class EscuelaService {

    @Autowired
    private EscuelaSedeRepository escuelaSedeRepository;

    @Autowired
    private EscuelaOrigenRepository escuelaOrigenRepository;

    @Transactional(readOnly = true)
    public EscuelaSede obtenerEscuelaSede(Long id) {
        return escuelaSedeRepository.findOne(id);
    }

    @Transactional
    public EscuelaSede altaEscuelaSede(EscuelaSede escuelaSede) {

        escuelaSede.setEmail((escuelaSede.getEmail().toLowerCase()));
        escuelaSede.setRol(Usuario.ESCUELA_SEDE);

        return escuelaSedeRepository.save(escuelaSede);
    }

    @Transactional
    public void bajaEscuelaSede(EscuelaSede escuelaSede) {
        escuelaSedeRepository.delete(escuelaSede);
    }

    @Transactional
    public void modificarEscuelaSede(EscuelaSede escuelaSede) {
        escuelaSedeRepository.save(escuelaSede);
    }

    @Transactional(readOnly = true)
    public List<EscuelaSede> obtenerEscuelasSede() {

        List<EscuelaSede> escuelasSede = new ArrayList<EscuelaSede>();
        for (EscuelaSede unaEscuelaSede : escuelaSedeRepository.findAll()) {
            escuelasSede.add(unaEscuelaSede);
        }

        return escuelasSede;
    }

    @Transactional(readOnly = true)
    public EscuelaOrigen obtenerEscuelaOrigen(Integer id) {
        return escuelaOrigenRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<EscuelaOrigen> obtenerEscuelasOrigenLocalidad(String query, Integer idLocalidad) {

        List<EscuelaOrigen> escuelasOrigen = new ArrayList<EscuelaOrigen>();
        for (EscuelaOrigen unaEscuelaOrigen : escuelaOrigenRepository.obtenerPorNombreYLocalidad(query, idLocalidad)) {
            escuelasOrigen.add(unaEscuelaOrigen);
        }

        return escuelasOrigen;
    }

    @Transactional(readOnly = true)
    public List<EscuelaOrigen> obtenerEscuelasOrigenProvincia(String query, int idProvincia) {

        List<EscuelaOrigen> escuelasOrigen = new ArrayList<EscuelaOrigen>();
        for (EscuelaOrigen unaEscuelaOrigen : escuelaOrigenRepository.obtenerPorNombreYProvincia(query, idProvincia)) {
            escuelasOrigen.add(unaEscuelaOrigen);
        }

        return escuelasOrigen;
    }

    @Transactional(readOnly = true)
    public List<EscuelaOrigen> obtenerEscuelasOrigenOrdenadas() {

        List<EscuelaOrigen> escuelasOrigen = new ArrayList<EscuelaOrigen>();
        for (EscuelaOrigen unaEscuelaOrigen : escuelaOrigenRepository.obtenerEscuelasOrigenOrdenadas()) {
            escuelasOrigen.add(unaEscuelaOrigen);
        }

        return escuelasOrigen;
    }

    @Transactional(readOnly = true)
    public List<EscuelaOrigen> obtenerEscuelasOrigen() {

        List<EscuelaOrigen> escuelasOrigen = new ArrayList<EscuelaOrigen>();
        for (EscuelaOrigen unaEscuelaOrigen : escuelaOrigenRepository.obtenerEscuelasOrigen()) {
            escuelasOrigen.add(unaEscuelaOrigen);
        }

        return escuelasOrigen;
    }

    // Paginacion
    @Transactional(readOnly = true)
    public List<EscuelaOrigen> obtenerEscuelasOrigen(Integer pageNumber, Integer pageSize, SortOrder sortOrder, String sortField) {

        Direction direction;
        if (sortOrder == sortOrder.ASCENDING) {
            direction = Direction.ASC;
        } else {
            direction = Direction.DESC;
        }

        PageRequest pageRequest = new PageRequest(pageNumber, pageSize, direction, "id");

        return escuelaOrigenRepository.findAll(pageRequest).getContent();
    }

    @Transactional
    public void modificarEscuelaOrigen(EscuelaOrigen escuelaSeleccionada) {
        escuelaOrigenRepository.save(escuelaSeleccionada);
    }

    @Transactional
    public void bajaEscuelaOrigen(EscuelaOrigen escuelaOrigen) {
        escuelaOrigenRepository.delete(escuelaOrigen);
    }

    @Transactional
    public void deshabilitarEscuelaSede(EscuelaSede escuelaSedeSeleccionada) {
        escuelaSedeRepository.deshabilitarEscuelaSede(escuelaSedeSeleccionada.getId());
    }

    @Transactional
    public void habilitarEscuelaSede(EscuelaSede escuelaSedeSeleccionada) {
        escuelaSedeRepository.habilitarEscuelaSede(escuelaSedeSeleccionada.getId());
    }

    @Transactional(readOnly = true)
    public HashMap<String, Long> obtenerEscuelasOrigenYCantidad() {

        HashMap<String, Long> escuelas = new HashMap<String, Long>();

        for (Object[] unaEscuela : escuelaOrigenRepository.obtenerEscuelasOrigenYCantidad()) {
            escuelas.put((String) unaEscuela[0], ((BigInteger) unaEscuela[1]).longValue());
        }

        return escuelas;
    }

    @Transactional(readOnly = true)
    public Long obtenerCantidadEscuelasOrigen() {
        return escuelaOrigenRepository.obtenerCantidadEscuelasOrigen();
    }

    @Transactional(readOnly = true)
    public HashMap<String, Long> obtenerLocalidadesYCantidad() {

        HashMap<String, Long> localidades = new HashMap<String, Long>();

        for (Object[] unaLocalidad : escuelaOrigenRepository.obtenerLocalidadesYCantidad()) {
            localidades.put((String) unaLocalidad[0], ((BigInteger) unaLocalidad[1]).longValue());
        }

        return localidades;
    }

    @Transactional(readOnly = true)
    public HashMap<String, Long> obtenerDistritosYCantidad() {

        HashMap<String, Long> distritos = new HashMap<String, Long>();

        for (Object[] unDistrito : escuelaOrigenRepository.obtenerDistritosYCantidad()) {
            distritos.put((String) unDistrito[0], ((BigInteger) unDistrito[1]).longValue());
        }

        return distritos;
    }

    @Transactional(readOnly = true)
    public HashMap<String, Long> obtenerProvinciasYCantidad() {
        HashMap<String, Long> provincias = new HashMap<String, Long>();

        for (Object[] unaProvincia : escuelaOrigenRepository.obtenerProvinciasYCantidad()) {
            provincias.put((String) unaProvincia[0], ((BigInteger) unaProvincia[1]).longValue());
        }

        return provincias;
    }

    public Long obtenerCantidadRegion6() {

        Long cantidad = escuelaOrigenRepository.obtenerCantidadRegion6();

        return cantidad;
    }

    public HashMap<String, Long> obtenerCantidadDistritosRegion6() {
        HashMap<String, Long> distritos = new HashMap<String, Long>();

        for (Object[] unDistrito : escuelaOrigenRepository.obtenerCantidadDistritosRegion6()) {
            distritos.put((String) unDistrito[0], ((BigInteger) unDistrito[1]).longValue());
        }

        return distritos;
    }

}
