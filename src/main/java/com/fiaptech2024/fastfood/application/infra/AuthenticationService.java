package com.fiaptech2024.fastfood.application.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiaptech2024.fastfood.core.applications.exception.RegraDeNegocioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {


    @Value("${auth.url}")
    private String authUrl;

    private final RestTemplate restTemplate;

    public AuthenticationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void authenticate(String nome, String cpf, String email) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("nome", nome);
        requestBody.put("cpf", cpf);
        requestBody.put("email", email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(authUrl, request, String.class);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new RegraDeNegocioException("Falha na autenticação: resposta inesperada do servidor");
            }
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro de cliente ao autenticar: " + e.getMessage());
        }
    }
}