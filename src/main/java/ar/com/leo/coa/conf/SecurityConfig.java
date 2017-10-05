package ar.com.leo.coa.conf;

import ar.com.leo.filter.CustomAuthenticationSuccessHandler;
import ar.com.leo.filter.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //con Spring 4 uso un builder. Con Spring 3 xml.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // AuthenticationManagerBuilder se pasa por @Autowired, y es un objeto ya creado por Spring Security
        // Builder
        auth // objeto principal que permite configurar
                .jdbcAuthentication() // de donde voy a sacar la autenticacion (bd, txt, ldap)
                .dataSource(dataSource) // donde esta la bd
                .usersByUsernameQuery("SELECT email, password, habilitado FROM login WHERE email= ?") // para conseguir el usuario, password y enabled
                .authoritiesByUsernameQuery("SELECT L.email, LR.descripcion FROM login L, login_roles LR WHERE L.id_rol = LR.id_rol AND L.email = ?"); // para conseguir el rol
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //.csrf() is optional, enabled by default, if using WebSecurityConfigurerAdapter constructor
        // Have to disable it for POST methods:
        // http://stackoverflow.com/a/20608149/1199132
        http.csrf().disable();

        // Logout and redirection:
        // http://stackoverflow.com/a/24987207/1199132
        http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID", "remember-me")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(customLogoutSuccessHandler);

        http
                .authorizeRequests()
                // Some filters enabling url regex:
                // http://stackoverflow.com/a/8911284/1199132
                //                .regexMatchers(
                //                        "\\A/page1.xhtml\\?param1=true\\Z",
                //                        "\\A/page2.xhtml.*")
                //                .permitAll()
                //Permit access for all to error and denied views
                .antMatchers("/WEB-INF/errorpages/general.xhtml", "/WEB-INF/errorpages/accessDenied.xhtml", "/WEB-INF/errorpages/expired.xhtml", "/login.xhtml", "/alumno/altaAlumno.xhtml", "/javax.faces.resources/**")
                .permitAll()
                // Only access with admin role
                .antMatchers("/admin/**")
                .hasRole("ADMIN")
                //Permit access only for some roles
                .antMatchers("/alumno/**")
                .hasRole("ALUMNO")
                //Permit access only for some roles
                .antMatchers("/profesor/**")
                .hasRole("PROFESOR")
                //Permit access only for some roles
                .antMatchers("/escuelaSede/**")
                .hasRole("ESCUELA_SEDE")
                //If user doesn't have permission, forward him to login page
                .and()
                .formLogin()
                .loginPage("/login.xhtml")
                .usernameParameter("login:email") //el parametro para username en la vista
                .passwordParameter("login:pass") //el parametro para password en la vista
                .loginProcessingUrl("/login") // action url de SpringSecurity
                .successHandler(customAuthenticationSuccessHandler)
                .and()
                .rememberMe()
                .rememberMeParameter("login:recordar_input") // Primefaces adds '_input' suffix to remember-me parameter
                .and()
                .exceptionHandling()
                .accessDeniedPage("/WEB-INF/errorpages/accessDenied.xhtml");
//                .and()
//                .csrf(); //error con panels de primefaces

//        // otro Builder para configurar
//        http
//                .authorizeRequests()
//                .antMatchers("/alumno/**") // que URL que voy a segurizar: todas las que empiezen en /alumno/
//                .access("hasRole('ROLE_ALUMNO')") // que usuarios pueden acceder
//                .and()
//                .formLogin() // login configuration
//                .defaultSuccessUrl("/alumno/homeAlumno.xhtml") // cuando logeo correctamente
//                .loginPage("/login")
//                .failureUrl("/login?error")
//                .usernameParameter("login:email") //el parametro para username en la vista
//                .passwordParameter("login:pass") //el parametro para password en la vista
//                .and()
//                .logout() //logout configuration
//                .logoutSuccessUrl("/login?logout")
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
//                .and()
//                .csrf();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); //To change body of generated methods, choose Tools | Templates.
    }

}
