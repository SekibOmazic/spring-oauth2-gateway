package com.comsysto.dcc.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EntityScan(
    basePackageClasses = {
        Jsr310JpaConverters.class
    },
    value = "com.comsysto.dcc.auth"
)
@SpringBootApplication
@SessionAttributes("authorizationRequest")
@EnableResourceServer
public class AuthserverApplication extends WebMvcConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/confirm_access").setViewName("authorize");
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthserverApplication.class, args);
    }

}
