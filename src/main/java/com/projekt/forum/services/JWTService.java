package com.projekt.forum.services;

import com.projekt.forum.utility.DateUtility;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private final String key = "bnb4LS2elBwtfXtLEZN2a1Yo69XKstLD";
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
        return Jwts.builder().setClaims(stringClaimsMap)
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(Date.from(now.toInstant().plusSeconds(10*60)))
                .signWith(getSecretKey())
                .compact();
    }


}
