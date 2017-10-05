package ar.com.leo.coa.listener;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Web application lifecycle listener.
 *
 * @author Leo
 */

//
//@WebListener
//@PropertySource("classpath:coa.properties")
//public class DatesServletListener implements ServletContextListener {
//
//    @Resource
//    private Environment env;
//
//    private static final String FECHA_INICIO = "coa.fechaInicio";
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        ServletContext servletContext = sce.getServletContext();
//
//        env.getProperty(FECHA_INICIO);
//        servletContext.setInitParameter("fechaInicio", env.getProperty(FECHA_INICIO));
//        
//        
//         try {
//              Properties p = new Properties();
//              InputStream istream = ctx.getResourceAsStream("/WEB-INF/classes/version.properties");
//              p.load(istream);
//              Properties sysProps = System.getProperties();
//              sysProps.putAll(p);
//          } catch (IOException e) {
//              logger.error("Error reading " + "version.properties");
//          }
//    }
//
//    
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//
//    }
//}
