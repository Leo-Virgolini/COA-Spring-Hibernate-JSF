package ar.com.leo.coa.conf;

import java.util.Properties;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author Leo
 */

// Config del EmailSender
@Configuration
@PropertySource("classpath:application.properties")
public class SpringMailConfig {

    @Resource
    private Environment env;

    private static final String MAIL_HOST = "mail.host";
    private static final String MAIL_PORT = "mail.port";
    private static final String MAIL_USERNAME = "mail.username";
    private static final String MAIL_PASSWORD = "mail.password";

    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_DEBUG = "mail.debug";

    @Bean
    public JavaMailSender javaMailService() {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(env.getProperty(MAIL_HOST));
        javaMailSender.setPort(Integer.parseInt(env.getProperty(MAIL_PORT)));
        javaMailSender.setUsername(env.getProperty(MAIL_USERNAME));
        javaMailSender.setPassword(env.getProperty(MAIL_PASSWORD));

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {

        Properties properties = new Properties();

        properties.setProperty(MAIL_TRANSPORT_PROTOCOL, env.getProperty(MAIL_TRANSPORT_PROTOCOL));
        properties.setProperty(MAIL_SMTP_AUTH, env.getProperty(MAIL_SMTP_AUTH));
        properties.setProperty(MAIL_SMTP_STARTTLS_ENABLE, env.getProperty(MAIL_SMTP_STARTTLS_ENABLE));
        properties.setProperty(MAIL_DEBUG, env.getProperty(MAIL_DEBUG));

        return properties;
    }

}
