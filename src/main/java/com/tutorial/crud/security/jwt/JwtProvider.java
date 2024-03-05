package com.tutorial.crud.security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.tutorial.crud.security.dto.JwtDto;
import com.tutorial.crud.security.entity.UsuarioPrincipal;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.text.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private int expiration;
    
    /**
    * @param authentication
    * @return
    */
    public String generateToken(Authentication authentication){
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        List<String> roles = usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(usuarioPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }
    
    /**
    * @param token
    * @return
    */
    public String getNombreUsuarioFromToken(String token){
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }
    
    
    /**
    * @param token
    * @return
    */
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        }catch(MalformedJwtException e){
            logger.error("Token mal formado");
        }catch(UnsupportedJwtException e){
            logger.error("Token no soportado");
        }catch(ExpiredJwtException e){
            logger.error("Token expirado");
        }catch(IllegalArgumentException e){
            logger.error("Token vacio");
        }catch(SignatureException e){
            logger.error("Error en la firma");
        }
        return false;
    }
    
    
    /**
    * @param jwtDto
    * @return
    * throws ParseException
    */
    public String refreshToken(JwtDto jwtDto) throws ParseException{
        try{
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(jwtDto.getToken());
        }catch(ExpiredJwtException e){
            JWT jwt = JWTParser.parse(jwtDto.getToken());
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            String nombreUsuario = claims.getSubject();
            List<String> roles = (List<String>)claims.getClaim("roles");
            
            return Jwts.builder()
                    .setSubject(nombreUsuario)
                    .claim("roles", roles)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + expiration))
                    .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                    .compact();
        }
        return null;
    }
}
