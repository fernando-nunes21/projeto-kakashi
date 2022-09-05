package com.kakashi.projetokakashi.web_api.security

import com.google.api.client.util.Value
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

@Component
class JwtUtils {

    private final String secret = "a36861c1-bd9d-487d-a161-84b992a3e31d"
    private final long expiration = 999999

    String generateToken (String id) {
        return Jwts.builder()
                .setSubject(id)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    Boolean isValidToken(String token) {
        Claims claims = getClaims(token)
        if (claims.getSubject() == null || claims.getExpiration() == null || new Date().after(claims.getExpiration())){
            return false
        }

        return true
    }

    Claims getClaims(String token) {
        try {
           return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
        } catch (Exception ex) {
            //TODO - CRIAR AS EXCEPTIONS CORRETAS para RETORNO CORRETO
            throw new Exception("Token Inválido = ${ex}")
        }
    }

    String getSubjectToken(String token) {
        return getClaims(token).getSubject()
    }
}
