package com.projekt.forum.configuration;


import com.projekt.forum.dataTypes.AlertManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.projekt.forum.repositories")
public class ApplicationConfiguration {
    @Bean()
    AlertManager alertManager(){
        return new AlertManager();
    }
}
