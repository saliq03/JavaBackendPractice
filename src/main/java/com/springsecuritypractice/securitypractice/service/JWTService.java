
package com.springsecuritypractice.securitypractice.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

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

    private Key getKeY() {
        byte[] keyBytes=Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
