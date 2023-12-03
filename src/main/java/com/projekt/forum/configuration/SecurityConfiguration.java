package com.projekt.forum.configuration;

import com.projekt.forum.handlers.CustomUrlAuthenticationFailureHandler;
import com.projekt.forum.repositories.UserRepository;
import com.projekt.forum.services.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JWTAuthFilter jwtAuthFilter;
    private final UserRepository userRepository;
    private final AuthenticationProvider authProvider;
    private final JpaUserDetailsService userDetailsService;

    public SecurityConfiguration(JWTAuthFilter jwtAuthFilter, UserRepository userRepository, AuthenticationProvider authProvider, JpaUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userRepository = userRepository;
        this.authProvider = authProvider;
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public CustomUrlAuthenticationFailureHandler customUrlAuthenticationFailureHandler(){
        CustomUrlAuthenticationFailureHandler customUrlAuthenticationFailureHandler = new CustomUrlAuthenticationFailureHandler();
        customUrlAuthenticationFailureHandler.setDefaultFailureUrl("/login?error");

        return customUrlAuthenticationFailureHandler;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http/*, AuthenticationProvider authenticationProvider*/, CustomUrlAuthenticationFailureHandler customUrlAuthenticationFailureHandler) throws Exception {

            http.csrf(httpSecurityCsrfConfigurer ->
                    httpSecurityCsrfConfigurer.disable()
            );

            http.sessionManagement(httpSecuritySessionManagementConfigurer ->
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
            http.authenticationProvider(authProvider);
            http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

            http.logout(logout->logout
                            .logoutSuccessUrl("/logout")
                            .permitAll()
                    );

            http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {authorizationManagerRequestMatcherRegistry
                    .requestMatchers("/deleteCategory/**").hasRole("admin")
                    .requestMatchers("/addCategory").hasRole("admin")
                    .requestMatchers("/editCategory/**").hasRole("admin")
                    .requestMatchers("/postMessage/**").hasAnyRole("admin","user")
                    .requestMatchers("/deleteThread/**").hasAnyRole("admin","user")
                    .requestMatchers("/addThread/**").hasAnyRole("admin","user")

                    .anyRequest().permitAll();
            });

            http.formLogin(httpSecurityFormLoginConfigurer -> {
                httpSecurityFormLoginConfigurer
                        .loginPage("/login")
                        .failureHandler(customUrlAuthenticationFailureHandler)
                        .successForwardUrl("/loginSuccess")

                ;
            });

            http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer
                    .ignoringRequestMatchers("/category/{categoryUrl}")
                    .ignoringRequestMatchers("/thread/{categoryUrl}/{threadId}")
                    .ignoringRequestMatchers("/register")

            );




            http.httpBasic(Customizer.withDefaults());

        return http.build();
    }



}
