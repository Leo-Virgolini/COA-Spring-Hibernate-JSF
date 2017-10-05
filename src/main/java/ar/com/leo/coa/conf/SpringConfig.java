package ar.com.leo.coa.conf;

import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan({"ar.com.leo.coa.service", "ar.com.leo.coa.repository", "ar.com.leo.coa.converters", "ar.com.leo.coa.helper", "ar.com.leo.filter", "ar.com.leo.coa.mb"})
@Import({HibernateConfig.class, SpringMailConfig.class, SecurityConfig.class})
public class SpringConfig {


//    @Bean(name = "messageSource")
//    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
//        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
//        reloadableResourceBundleMessageSource.setBasename("/WEB-INF/MyMessage");
//        
//        return reloadableResourceBundleMessageSource;
//    }

}
