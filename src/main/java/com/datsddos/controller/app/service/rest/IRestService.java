package com.datsddos.controller.app.service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Map;

public interface IRestService {
    ResponseEntity<String> getUrlWithHeaders(String url, Map<String, String> headersMap);

    ResponseEntity<String> getPostsWithQueryParams(String url, Map<String, String> urlParams, String... xApiKey);

    ResponseEntity<String> getPostsWithFormUrlEncodedParams(String url, LinkedMultiValueMap<String, String> formUrlEncodedParams);

    ResponseEntity<String> getPostsWithJsonBody(String url, String requestJson, String... xApiKey);

    ResponseEntity<String> getGetWithHeader(String url, String header);
}

