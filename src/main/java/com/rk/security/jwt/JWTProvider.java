package com.rk.security.jwt;

import com.rk.security.principle.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {
    @Value("${expired}")
    private Long EXPIRED;
    @Value("${secret_key}")
    private String SECRKET_KEY;
    private final Logger logger= LoggerFactory.getLogger(JWTEntryPoint.class);
    public String generateToken(UserPrinciple userPrinciple){
        return Jwts.builder().setSubject(userPrinciple.getUsername()).
                setIssuedAt(new Date()).
                setExpiration(new Date(new Date().getTime()+EXPIRED)).
                signWith(SignatureAlgorithm.HS256,SECRKET_KEY).
                compact();
    }
    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRKET_KEY).parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){
            logger.error("expired Token {}",e.getMessage());
        }catch (MalformedJwtException malformedJwtException){
            logger.error("invalid format {}",malformedJwtException.getMessage());
        }catch (UnsupportedJwtException unsupportedJwtException){
            logger.error("Unsupported token {}",unsupportedJwtException.getMessage());
        }catch (SignatureException signatureException){
            logger.error("Invalid Signature {}",signatureException.getMessage());
        }
        return false;
    }
    public String getUserNameToken(String token){
        return Jwts.parser().setSigningKey(SECRKET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
