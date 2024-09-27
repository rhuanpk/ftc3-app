package com.fiaptech2024.fastfood.application.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {


    @Value("${auth.url}")
    private String authUrl;

    private final RestTemplate restTemplate;

    public AuthenticationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String authenticate(String nome, String cpf, String email) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nome", nome);
        requestBody.put("cpf", cpf);
        requestBody.put("email", email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(authUrl, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String tokenJson = response.getBody();
                return extractSubjectFromToken(tokenJson);
            } else {
                throw new RuntimeException("Erro ao autenticar usuário");
            }
    }

    private String extractSubjectFromToken(String tokenJson) {

        try {
            JSONObject jsonObject = new JSONObject(tokenJson);
            String token = jsonObject.getString("token");

            if (token.split("\\.").length != 3) {
                throw new RuntimeException("Token não é um JWT válido");
            }
            if (token.startsWith("Bearer ")) {
                token = token.substring("Bearer ".length());
            }

            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao decodificar o token: " + e.getMessage());
        }
    }
}