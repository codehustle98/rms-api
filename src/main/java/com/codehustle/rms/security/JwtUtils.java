package com.codehustle.rms.security;

import com.codehustle.rms.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.aspectj.weaver.IClassFileProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class JwtUtils {

    private static final String AUTH_SECRET = "$$$$<rms>@<Adm!n>|<sEcReT>/|\\8192873231###$$$$";

    private static final String ISSUER = "codehustle";

    private static Claims getAllClaimsFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(AUTH_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    private static <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public static String getUsernameFromToken(String token){
        return getClaimFromToken(token,Claims::getSubject);
    }

    public static Date getTokenExpirationTime(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getTokenExpirationTime(token);
        return expiration.before(new Date());
    }

    public static Boolean isTokenValid(String token){
        final String userEmail = getUsernameFromToken(token);
        return userEmail != null && !isTokenExpired(token);
    }

    public static List<GrantedAuthority> getAuthorities(String token){
        List<GrantedAuthority> authorities = new ArrayList<>();
        return getClaimFromToken(token,claims -> {
            List<Map<String,String>> roles = (List<Map<String,String>>) claims.get("roles");
            for (Map<String, String> role:roles){
                authorities.add(new SimpleGrantedAuthority(role.get("authority")));
            }
            return authorities;
        });
    }

    public static String generateToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        return Jwts.builder()
                .claim("roles",authorities)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setIssuer(ISSUER)
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
                .signWith(SignatureAlgorithm.HS512, AUTH_SECRET).compact();
    }

    public static String generateRefreshToken(String subject, Collection<? extends GrantedAuthority> authorities){
        return Jwts.builder()
                .claim("roles",authorities)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setIssuer(ISSUER)
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))
                .signWith(SignatureAlgorithm.HS512, AUTH_SECRET).compact();
    }
}
