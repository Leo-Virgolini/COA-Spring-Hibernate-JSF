package ar.com.leo.coa.service;

import ar.com.leo.coa.model.Provincia;
import ar.com.leo.coa.repository.ProvinciaRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Repository, @Service y @Controller son especializaciones de @Component para casos concretos (persistencia, servicios y presentación).
//The @Service annotation is also a specialization of the component annotation. It doesn’t currently provide any additional behavior over the @Component annotation
@Service("provinciaService")
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Transactional(readOnly = true)
    public Provincia obtenerProvincia(Integer id) {
        return provinciaRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Provincia> obtenerProvincias() {

        List<Provincia> provincias = new ArrayList<Provincia>();

        for (Provincia unaProvincia : provinciaRepository.findAll()) {
            provincias.add(unaProvincia);
        }

        return provincias;
    }

}
