package ar.com.leo.coa.service;

import ar.com.leo.coa.model.Usuario;
import ar.com.leo.coa.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Repository, @Service y @Controller son especializaciones de @Component para casos concretos (persistencia, servicios y presentación).
//The @Service annotation is also a specialization of the component annotation. It doesn’t currently provide any additional behavior over the @Component annotation
@Service("loginService")
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Transactional(readOnly = true)
    public Usuario autenticarUsuario(String email, String password) {

        // obtengo el rol
//        Integer rol = loginRepository.obtenerRol(email, password);
//        
//        if(rol == 1){
//            
//        } else if(rol == 2){
//            
//        } else{
//            
//        }
        return loginRepository.autenticarUsuario(email, password);
    }

    @Transactional(readOnly = true)
    public Usuario recuperarContraseña(String email) {
        return loginRepository.recuperarContraseña(email);
    }

    @Transactional(readOnly = true)
    public boolean validarEmail(String email) {
        //si el email que ingresaste ya existe: true, sino false
        return email.equals(loginRepository.validarEmail(email));
    }

}
