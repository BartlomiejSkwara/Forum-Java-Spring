package com.projekt.forum.configuration;

import com.projekt.forum.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    public JWTAuthFilter(JWTService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        final String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader==null || !tokenHeader.contains("Bearer "))
        {
            ///error 403
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }else {
            final String jwtToken  = tokenHeader.substring(7);
            final String username = jwtService.retrieveUsernameClaim(jwtToken);
            SecurityContext securityContext = SecurityContextHolder.getContext();

            if (username!= null || securityContext.getAuthentication() == null){

            }
            else
            if (username==null || securityContext.getAuthentication() == null){
                //error 403
            }
            else {
                if (username.equals(securityContext.getAuthentication().getName())){
                    //finish cała autentykacja skończona :>
                }
                else {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    //a co jak nie ma :<<

                    if(jwtService.isTokenValid(userDetails,jwtToken)){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        securityContext.setAuthentication(usernamePasswordAuthenticationToken);
                    }
                    else {
                        //error 403
                    }

                }

            }
        }



        filterChain.doFilter(request,response);
    }
}
