package com.projekt.forum.configuration;

import com.projekt.forum.dataTypes.Alert;
import com.projekt.forum.dataTypes.AlertManager;
import com.projekt.forum.services.JWTService;
import com.projekt.forum.utility.RequestUtility;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final AlertManager alertManager;
    private final UserDetailsService userDetailsService;

    public JWTAuthFilter(JWTService jwtService, AlertManager alertManager, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.alertManager = alertManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        if(request.getCookies()==null){
            filterChain.doFilter(request,response);
            return;
        }

        Optional<Cookie> jwtCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("jwt")).findFirst();
        if (jwtCookie.isEmpty()){
            filterChain.doFilter(request,response);
            return;
        }

        final String jwtToken = jwtCookie.get().getValue();
        if(jwtToken==null || jwtToken.isEmpty())
        {
            filterChain.doFilter(request,response);
            return;

        }
        //final String jwtToken  = tokenHeader.substring(7);



        try {
            final String username = jwtService.retrieveUsernameClaim(jwtToken);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            if (username!= null && securityContext.getAuthentication() == null){// gdy nie ma username w tokenie i niezalogowany
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(userDetails, jwtToken)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(usernamePasswordAuthenticationToken);


                    String newJwtToken = jwtService.generateJWT(userDetails);
                    jwtService.addTokenToResponse(response,newJwtToken);


                }
            }


        }catch (ExpiredJwtException | SignatureException expiredJwtException){
            ///Obsługa wygaśnięcia tokenu lub złej sygnatury

            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            jwtService.removeTokenCookie(response);
            alertManager.addAlert(new Alert("Zostałeś automatycznie wylogowany ponieważ twój token JWT wygasł :<", Alert.AlertType.DANGER));

            //request.getRequestURI()
            //request.setAttribute(RequestUrl);
            //response.sendRedirect("/");
            //RequestUtility.setupAjaxRedirectionHeaders(response);


        }

        filterChain.doFilter(request,response);


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/css") || path.startsWith("/images") || path.startsWith("/js");
    }




}
