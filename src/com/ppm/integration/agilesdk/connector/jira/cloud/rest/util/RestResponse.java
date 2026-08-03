/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud.rest.util;

import org.springframework.http.ResponseEntity;

/**
 * Adapter wrapper replacing Apache Wink's ClientResponse with Spring ResponseEntity.
 * Provides backward compatibility for code transitioning from Wink to Spring.
 */
public class RestResponse {
    
    private ResponseEntity<String> responseEntity;
    
    public RestResponse(ResponseEntity<String> responseEntity) {
        this.responseEntity = responseEntity;
    }
    
    /**
     * Get HTTP status code
     */
    public int getStatusCode() {
        return responseEntity.getStatusCode().value();
    }
    
    /**
     * Get response body as String
     */
    public String getEntity(Class<String> type) {
        return responseEntity.getBody();
    }
    
    /**
     * Get underlying ResponseEntity for advanced use cases
     */
    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }
}

