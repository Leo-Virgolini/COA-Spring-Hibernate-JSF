package ar.com.leo.coa.initializer;

import ar.com.leo.coa.conf.SpringConfig;
import ar.com.leo.coa.listener.SimpleSessionListener;
import java.util.EnumSet;
import javax.servlet.FilterRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.DelegatingFilterProxy;


// reemplazo del web.xml
public class WebAppInitializer implements WebApplicationInitializer {

    // cuando se levante la app
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringConfig.class); // registro la clase donde esta la config de Spring

//      rootContext.setServletContext(servletContext);   
        servletContext.addListener(new ContextLoaderListener(rootContext)); // listener del contexto
        servletContext.addListener(new RequestContextListener()); // listener de requests
//        servletContext.addListener(new SimpleSessionListener()); // listener de sessions
  

//      Agrego al ServletContext un Filter de Spring Security. Toma la configuracion de SecurityConfig.
//        FilterRegistration.Dynamic securityFilter = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
//        securityFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*"); // todos los requests pasan por este filtro.
        // DispatcherType.REQUEST, DispatcherType.FORWARD: mandatory to allow the managed bean to forward the request to the filter

        /* Faces Servlet: solo anda en el web.xml */
//        ServletRegistration.Dynamic facesServlet = servletContext.addServlet("Faces Servlet", FacesServlet.class);
//        facesServlet.setLoadOnStartup(1);
//        facesServlet.addMapping("/");
//        facesServlet.addMapping("/faces/*");
//        facesServlet.addMapping("*.xhtml");
//        facesServlet.addMapping("*.jsf");
//        facesServlet.addMapping("*.faces");
        
        /* Spring Dispatcher Servlet */
//        Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
//        dynamic.setLoadOnStartup(1);
//        dynamic.addMapping("/rest/*");

    }

}
