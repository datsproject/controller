package com.datsddos.controller.app.service.rest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;

@Service
public class RestService implements IRestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public ResponseEntity<String> getUrlWithHeaders(String url, Map<String, String> headersMap) {
        HttpHeaders headers = new HttpHeaders();
        for (String headerName : headersMap.keySet()) {
            String headerValue = headersMap.get(headerName);
            headers.add(headerName, headerValue);
        }

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        return this.restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    @Override
    public ResponseEntity<String> getPostsWithQueryParams(String url, Map<String, String> urlParams, String... xApiKey) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        for (Map.Entry<String, String> entry : urlParams.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        if (xApiKey != null) {
            headers.set("x-api-key", xApiKey[0]);
        }

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        return this.restTemplate.exchange(builder.build().toUriString(), HttpMethod.POST, entity, String.class);
    }

    @Override
    public ResponseEntity<String> getPostsWithFormUrlEncodedParams(String url, LinkedMultiValueMap<String, String> formUrlEncodedParams) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formUrlEncodedParams, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return responseEntity;
    }

    @Override
    public ResponseEntity<String> getPostsWithJsonBody(String url, String requestJson, String... xApiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (xApiKey != null) {
            headers.set("x-api-key", xApiKey[0]);
        }

        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return responseEntity;
    }

    @Override
    public ResponseEntity<String> getGetWithHeader(String url, String header) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", header);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, String.class);

        return response;
    }
}
