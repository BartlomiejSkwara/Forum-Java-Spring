package com.projekt.forum.configuration;

import com.projekt.forum.handlers.CustomUrlAuthenticationFailureHandler;
import com.projekt.forum.repositories.UserRepository;
import com.projekt.forum.services.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final UserRepository userRepository;
    private final JpaUserDetailsService userDetailsService;

    public SecurityConfiguration(UserRepository userRepository, JpaUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
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
//        http    .csrf().disable()
//                .authorizeHttpRequests().anyRequest().hasRole("USER")
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(
//                        jwtAuthenticationFilter
//                )
//        ;


            http.logout(logout->logout
                            .logoutSuccessUrl("/logout")
                            .permitAll()
                    );
//            http.logout(logout->{
//                logout.logoutUrl("/logout");
//
//            });
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

//            http.httpBasic(httpSecurityHttpBasicConfigurer -> {httpSecurityHttpBasicConfigurer.})

            http.userDetailsService(userDetailsService);

            http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
//    @Bean
//    @Transactional()
//    public AuthenticationProvider authenticationProvider(PasswordEncoder encoder){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setHideUserNotFoundExceptions(false);

//        InMemoryUserDetailsManager  userDetailsService = new InMemoryUserDetailsManager();
//        for (CustomUserDetails user:this.userRepository.joinUsersWithRole()){
//            userDetailsService.createUser(user);
//        }
//        provider.setUserDetailsService(userDetailsService);

//        return provider;
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



}
