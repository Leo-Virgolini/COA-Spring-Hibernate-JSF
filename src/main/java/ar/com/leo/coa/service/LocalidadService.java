package ar.com.leo.coa.service;

import ar.com.leo.coa.model.Localidad;
import ar.com.leo.coa.model.Provincia;
import ar.com.leo.coa.repository.LocalidadRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Repository, @Service y @Controller son especializaciones de @Component para casos concretos (persistencia, servicios y presentación).
//The @Service annotation is also a specialization of the component annotation. It doesn’t currently provide any additional behavior over the @Component annotation
@Service("localidadService")
public class LocalidadService {

    @Autowired
    private LocalidadRepository localidadRepository;

    @Transactional(readOnly = true)
    public List<Localidad> obtenerLocalidades() {

        List<Localidad> localidades = new ArrayList<Localidad>();

        for (Localidad unaLocalidad : localidadRepository.findAll()) {
            localidades.add(unaLocalidad);
        }

        return localidades;
    }

    @Transactional(readOnly = true)
    public List<Localidad> obtenerLocalidades(Provincia provincia) {

        List<Localidad> localidades = new ArrayList<Localidad>();

        for (Localidad unaLocalidad : localidadRepository.obtenerPorIdProvincia(provincia.getId())) {
            localidades.add(unaLocalidad);
        }

        return localidades;
    }

    @Transactional(readOnly = true)
    public List<Localidad> obtenerPorIdProvincia(Integer idProvincia) {

        List<Localidad> localidades = new ArrayList<Localidad>();

        for (Localidad unaLocalidad : localidadRepository.obtenerPorIdProvincia(idProvincia)) {
            localidades.add(unaLocalidad);
        }

        return localidades;
    }

}
