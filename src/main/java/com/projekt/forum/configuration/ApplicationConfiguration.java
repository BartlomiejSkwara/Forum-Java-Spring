package com.projekt.forum.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.projekt.forum.repositories")
public class ApplicationConfiguration {
//    @Bean()
//    EntityManagerFactory entityManagerFactory(){
//
//    }
}
