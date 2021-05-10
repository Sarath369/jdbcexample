package com.example.demo.security;

import com.example.demo.entity.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import static com.example.demo.util.AppConstants.TOKEN_VALIDITY_SECONDS;
import static com.example.demo.util.AppConstants.REMEMBER_ME_TOKEN_VALIDITY_SECONDS;
import static com.example.demo.util.AppConstants.SECRET_KEY;

@Component
public class JwtTokenUtil implements Serializable {
    String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(final String token,
                                    final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(final Customer customer, final boolean rememberMe) {
        return doGenerateToken(customer.getEmail(),
                new SimpleGrantedAuthority(customer.getRoleString(customer.getRole())),
                rememberMe);
    }

    private Date getTokenValidity(final boolean rememberMe) {
        if (rememberMe) {
            return new Date(System.currentTimeMillis()
                    + REMEMBER_ME_TOKEN_VALIDITY_SECONDS * 1000);
        } else {
            return new Date(System.currentTimeMillis()
                    + TOKEN_VALIDITY_SECONDS * 1000);
        }
    }

    private String doGenerateToken(final String email,
                                   final GrantedAuthority authorities,
                                   final boolean rememberMe) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("scopes", authorities);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("jdbcexample")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getTokenValidity(rememberMe))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    Boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }



}
