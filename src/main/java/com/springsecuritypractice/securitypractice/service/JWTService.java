
package com.springsecuritypractice.securitypractice.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    // 256-bit base64 encoded key (example)
    String secretKey = "uWT1u5ZtSydTZQqRpXY0K3+IfWqIdZj6zNcH3n4FvGM=";


    public String generateToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        return Jwts
                  .builder()
                  .claims()
                  .add(claims)
                  .subject(userName)
                  .issuedAt(new Date(System.currentTimeMillis()))
                  .expiration(new Date(System.currentTimeMillis()+1000*60*60*24*7))
                  .and()
                  .signWith(getKeY())
                  .compact();
        


    }

    private SecretKey getKeY() {
        byte[] keyBytes=Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username=extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
               .verifyWith(getKeY())
               .build()
               .parseSignedClaims(token)
               .getPayload();         
    }

}
