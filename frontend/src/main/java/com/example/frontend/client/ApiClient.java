package com.example.frontend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.gateway.url}")
    private String gatewayUrl;

    public <T> T get(String path, Class<T> responseType, String token) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(headers(token));
            return restTemplate.exchange(gatewayUrl + path, HttpMethod.GET, entity, responseType).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ApiException(e.getStatusCode().value(), e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new ApiException(503, "Сервис недоступен: " + e.getMessage());
        }
    }

    public <T> T getList(String path, ParameterizedTypeReference<T> typeRef, String token) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(headers(token));
            return restTemplate.exchange(gatewayUrl + path, HttpMethod.GET, entity, typeRef).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ApiException(e.getStatusCode().value(), e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new ApiException(503, "Сервис недоступен: " + e.getMessage());
        }
    }

    public <T, R> T post(String path, R body, Class<T> responseType, String token) {
        try {
            HttpEntity<R> entity = new HttpEntity<>(body, headers(token));
            return restTemplate.exchange(gatewayUrl + path, HttpMethod.POST, entity, responseType).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ApiException(e.getStatusCode().value(), e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new ApiException(503, "Сервис недоступен: " + e.getMessage());
        }
    }

    public <T, R> T put(String path, R body, Class<T> responseType, String token) {
        try {
            HttpEntity<R> entity = new HttpEntity<>(body, headers(token));
            return restTemplate.exchange(gatewayUrl + path, HttpMethod.PUT, entity, responseType).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ApiException(e.getStatusCode().value(), e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new ApiException(503, "Сервис недоступен: " + e.getMessage());
        }
    }

    public void delete(String path, String token) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(headers(token));
            restTemplate.exchange(gatewayUrl + path, HttpMethod.DELETE, entity, Void.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ApiException(e.getStatusCode().value(), e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new ApiException(503, "Сервис недоступен: " + e.getMessage());
        }
    }

    private HttpHeaders headers(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) headers.setBearerAuth(token);
        return headers;
    }

    public <T> T delete_return(String path, Class<T> responseType, String token) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(headers(token));
            return restTemplate.exchange(gatewayUrl + path, HttpMethod.DELETE, entity, responseType).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ApiException(e.getStatusCode().value(), e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new ApiException(503, "Сервис недоступен: " + e.getMessage());
        }
    }
}