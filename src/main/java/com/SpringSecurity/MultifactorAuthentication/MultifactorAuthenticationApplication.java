package com.SpringSecurity.MultifactorAuthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.SpringSecurity.MultifactorAuthentication.Repositories")
public class MultifactorAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultifactorAuthenticationApplication.class, args);
    }

}
