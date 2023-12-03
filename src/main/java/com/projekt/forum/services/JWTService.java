package com.projekt.forum.services;

import com.projekt.forum.dataTypes.CustomUserDetails;
import com.projekt.forum.entity.UserEntity;
import com.projekt.forum.utility.DateUtility;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private final String key = "K2AIkrQ937S1r5mE/tC0j8HGi0KXplERAaqgDHXm6T0uhHlQ4o4Jy532CAvlajip";
    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public String retrieveUsernameClaim(String jwtToken){
        return retrieveSpecificClaim(jwtToken,Claims::getSubject);
    }
    public <T> T retrieveSpecificClaim(String jwtToken, Function<Claims,T> claimsResolver) {
        final Claims claims = retrieveAllClaims(jwtToken);
        return claimsResolver.apply(claims);

    }

    public Claims retrieveAllClaims(String jwtToken){
        return Jwts.parser().verifyWith(getSecretKey()).build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public boolean isTokenValid(UserDetails userDetails, String jwtToken) {
        return (userDetails.getUsername().equals(retrieveUsernameClaim(jwtToken)) && isTokenNonExpired(jwtToken));
    }

    private boolean isTokenNonExpired(String jwtToken) {
        return (retrieveExpirationDateClaim(jwtToken).after(DateUtility.getCurrentDate()));
    }

    private Date retrieveExpirationDateClaim(String jwtToken) {
        return  retrieveSpecificClaim(jwtToken,Claims::getExpiration);
    }

    public String generateJWT(Map<String,Object> stringClaimsMap, UserDetails userDetails){
        Date now = DateUtility.getCurrentDate();
        JwtBuilder jwtBuilder = Jwts.builder();

        if (stringClaimsMap != null){
            jwtBuilder.setClaims(stringClaimsMap);
        }

        return jwtBuilder
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(Date.from(now.toInstant().plusSeconds(10*60)))
                .signWith(getSecretKey())
                .compact();

    }
    public String generateJWT(UserDetails userDetails){
        return  generateJWT(null, userDetails);
    }
    public String generateJWT(UserEntity userEntity){
        return  generateJWT(null, new CustomUserDetails(userEntity));
    }

    public void addTokenToResponse(HttpServletResponse httpServletResponse, String token){
        //httpServletResponse.addHeader("Authorization","Bearer ".concat(token));
        //httpServletResponse.addHeader(HttpHeaders.SET_COOKIE,"jwt="+token+"; HttpOnly; Secure; SameSite=Strict");
        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE,"jwt="+token+"; HttpOnly; SameSite=Strict");

    }

}
