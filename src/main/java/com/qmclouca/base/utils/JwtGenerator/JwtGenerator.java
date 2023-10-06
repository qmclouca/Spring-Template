package com.qmclouca.base.utils.JwtGenerator;

import com.qmclouca.base.models.Client;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtGenerator implements JwtGeneratorInterface {

    @Value("${jwt.secret}") // Inject the secret key from application.properties
    private String jwtSecret;

    @Value("${jwt.expirationMs}") // Inject the expiration time from application.properties
    private long jwtExpirationMs;

    @Override
    public Map<String, String> generateToken(Client client) {
        // Create claims for the JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("clientId", client.getId()); // Customize as needed

        // Calculate the expiration date
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpirationMs);

        // Generate the JWT token
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(client.getEmail()) // You can use email or any other identifier here
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        // Create a response map
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return response;
    }
}
