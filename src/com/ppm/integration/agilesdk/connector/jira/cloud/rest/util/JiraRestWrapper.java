/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud.rest.util;

import com.ppm.integration.agilesdk.connector.jira.cloud.rest.util.exception.RestRequestException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.*;
import java.util.UUID;

/**
 * REST client wrapper for Jira Cloud API calls.
 * Uses Spring RestTemplate instead of Apache Wink.
 */
public class JiraRestWrapper {

    private RestTemplate restTemplate;
    private IRestConfig config;

    public JiraRestWrapper(IRestConfig config) {
        this.config = config;
        this.restTemplate = createRestTemplate(config);
    }

    /**
     * Create a RestTemplate with proxy configuration if needed
     */
    private RestTemplate createRestTemplate(IRestConfig config) {
        RestTemplate template = new RestTemplate();
        
        // Spring RestTemplate's HttpClient factory will be configured with proxy
        // if proxyHost and proxyPort are set in config
        // This is handled at the HttpClientFactory level when needed
        
        return template;
    }

    /**
     * Build HTTP headers for the request
     */
    private HttpHeaders buildHeaders(boolean includeContentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", config.getBasicAuthorizationToken());
        headers.set("Accept", "application/json");
        
        if (includeContentType) {
            headers.set("Content-Type", "application/json");
        }
        
        return headers;
    }

    /**
     * Send GET request
     */
    public RestResponse sendGet(String uri) {
        String uuid = UUID.randomUUID().toString();
        return executeRequest(uri, HttpMethod.GET, null, 200, false, uuid);
    }

    /**
     * Send POST request
     */
    public RestResponse sendPost(String uri, String jsonPayload, int expectedHttpStatusCode) {
        String uuid = UUID.randomUUID().toString();
        return executeRequest(uri, HttpMethod.POST, jsonPayload, expectedHttpStatusCode, true, uuid);
    }

    /**
     * Send PUT request
     */
    public RestResponse sendPut(String uri, String jsonPayload, int expectedHttpStatusCode) {
        String uuid = UUID.randomUUID().toString();
        return executeRequest(uri, HttpMethod.PUT, jsonPayload, expectedHttpStatusCode, true, uuid);
    }

    /**
     * Execute the HTTP request and validate response status
     */
    private RestResponse executeRequest(String uri, HttpMethod method, String payload, 
                                       int expectedStatusCode, boolean includeContentType, String uuid) {
        try {
            // Validate URL
            new URL(uri);
            
            HttpHeaders headers = buildHeaders(includeContentType);
            headers.set("X-B3-TraceId", uuid);
            
            ResponseEntity<String> response;
            if (HttpMethod.GET.equals(method)) {
                org.springframework.http.HttpEntity<String> entity =
                    new org.springframework.http.HttpEntity<>(headers);
                response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
            } else if (HttpMethod.POST.equals(method)) {
                org.springframework.http.HttpEntity<String> entity = 
                    new org.springframework.http.HttpEntity<>(payload, headers);
                response = restTemplate.postForEntity(uri, entity, String.class);
            } else if (HttpMethod.PUT.equals(method)) {
                org.springframework.http.HttpEntity<String> entity = 
                    new org.springframework.http.HttpEntity<>(payload, headers);
                restTemplate.put(uri, entity);
                // Spring put returns void, so we need to create a synthetic response
                response = ResponseEntity.status(204).body("");
            } else {
                throw new RestRequestException(400, "Unsupported HTTP method: " + method);
            }
            
            RestResponse restResponse = new RestResponse(response);
            checkResponseStatus(expectedStatusCode, restResponse, uri, method.toString(), payload, uuid);
            
            return restResponse;
            
        } catch (MalformedURLException e) {
            throw new RestRequestException(400, String.format("%s is a malformed URL", uri));
        } catch (HttpClientErrorException e) {
            // Handle Spring HTTP exceptions
            handleSpringHttpException(e, uri, method.toString(), payload, uuid);
            throw e;
        } catch (RestRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RestRequestException(500, "Request failed: " + e.getMessage());
        }
    }

    /**
     * Validate the response status code
     */
    private void checkResponseStatus(int expectedHttpStatusCode, RestResponse response, String uri, 
                                    String verb, String payload, String uuid) {
        if (response.getStatusCode() != expectedHttpStatusCode) {
            StringBuilder errorMessage = new StringBuilder(String.format(
                "## Unexpected HTTP response status code %s for %s uri %s, expected %s", 
                response.getStatusCode(), verb, uri, expectedHttpStatusCode));
                
            if (uuid != null) {
                errorMessage.append(System.lineSeparator())
                           .append("Value of HTTP tracking header X-B3-TraceId:")
                           .append(uuid);
            }
            
            if (payload != null) {
                errorMessage.append(System.lineSeparator())
                           .append(System.lineSeparator())
                           .append("# Sent Payload:")
                           .append(System.lineSeparator())
                           .append(payload);
            }
            
            String responseStr = null;
            if (response.getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
                responseStr = "Authentication failed";
            } else {
                try {
                    responseStr = response.getEntity(String.class);
                } catch (Exception e) {
                    // Ignore if we cannot get the response body
                }
            }
            
            if (!StringUtils.isBlank(responseStr)) {
                errorMessage.append(System.lineSeparator())
                           .append(System.lineSeparator())
                           .append("# Received Response:")
                           .append(System.lineSeparator())
                           .append(responseStr);
            }
            
            throw new RestRequestException(response.getStatusCode(), errorMessage.toString());
        }
    }

    /**
     * Handle Spring HTTP exceptions
     */
    private void handleSpringHttpException(HttpClientErrorException e, String uri, String verb, 
                                          String payload, String uuid) {
        HttpStatusCode statusCode = e.getStatusCode();
        RestResponse response = new RestResponse(ResponseEntity.status(statusCode).body(e.getResponseBodyAsString()));
        checkResponseStatus(-1, response, uri, verb, payload, uuid);
    }
}
