package dev.jam.accountservice.config;

import dev.jam.accountservice.service.IUserService;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Dans cette class de configuration une specification des bean qu'on est besoin
 */
@Configuration
public class AppConfig {
    private final IUserService userService;
//    @Value("server.http.port")
//    private int httpPort;

    public AppConfig(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Implementing AuthenticationProvider class to define
     * the used UserDetailsService implementation and the password encoder
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setUserDetailsService(userService);
        daoAuthProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthProvider;
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (TomcatServletWebServerFactory factory) -> {
            // also listen on http
            final Connector connector = new Connector();
            connector.setPort(8086);
            factory.addAdditionalTomcatConnectors(connector);
        };
    }


}
