package com.projekt.forum.configuration;

import com.projekt.forum.entity.UserEntity;
import com.projekt.forum.handlers.CustomUrlAuthenticationFailureHandler;
import com.projekt.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.transaction.annotation.Transactional;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired UserRepository userRepository;

    @Bean
    public CustomUrlAuthenticationFailureHandler customUrlAuthenticationFailureHandler(){
        return new CustomUrlAuthenticationFailureHandler();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider, CustomUrlAuthenticationFailureHandler customUrlAuthenticationFailureHandler) throws Exception {
//        http    .csrf().disable()
//                .authorizeHttpRequests().anyRequest().hasRole("USER")
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(
//                        jwtAuthenticationFilter
//                )
//        ;

            http.logout(logout->logout
                            .logoutSuccessUrl("/")
                            .permitAll()
                    );
//            http.logout(logout->{
//                logout.logoutUrl("/logout");
//
//            });
            http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {authorizationManagerRequestMatcherRegistry
                    //.requestMatchers("/").authenticated()
                    .anyRequest().permitAll();
            });

            http.formLogin(httpSecurityFormLoginConfigurer -> {
                httpSecurityFormLoginConfigurer
                        .loginPage("/login")
                        //.failureHandler(customUrlAuthenticationFailureHandler)

                ;
            });

            http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    @Transactional()
    public AuthenticationProvider authenticationProvider(PasswordEncoder encoder){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        InMemoryUserDetailsManager  userDetailsService = new InMemoryUserDetailsManager();

        System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123"));
        for (UserEntity user:this.userRepository.joinUsersWithRole()){
            userDetailsService.createUser(user);

        }
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean()
//    public InMemoryUserDetailsManager userDetailsManager(){
//
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        UserDetails user = User.withUsername("bruh").password(encoder.encode("moment")).roles("USER").build();
//
//        return new InMemoryUserDetailsManager(user);
//
//    }


}
