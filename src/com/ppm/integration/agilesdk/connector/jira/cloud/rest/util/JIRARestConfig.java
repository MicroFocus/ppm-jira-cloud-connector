
/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud.rest.util;

import org.apache.commons.codec.binary.Base64;

/**
 * Spring-compatible REST configuration implementation.
 */
public class JIRARestConfig implements IRestConfig {
    
    private String proxyHost;
    private int proxyPort = -1;
    private String basicAuthenticationToken;

    public JIRARestConfig() {
        // Spring RestTemplate is stateless, config is applied per request
    }

    @Override
    public IRestConfig setProxy(String proxyHost, String proxyPort) {
        if (proxyHost != null && !proxyHost.isEmpty() && proxyPort != null && !proxyPort.isEmpty()) {
            this.proxyHost = proxyHost;
            this.proxyPort = Integer.parseInt(proxyPort);
        }
        return this;
    }

    @Override
    public String getBasicAuthorizationToken() {
        return basicAuthenticationToken;
    }

    @Override
    public void setBasicAuthorizationCredentials(String username, String password) {
        String basicToken = new String(Base64.encodeBase64((username + ":" + password).getBytes()));
        basicAuthenticationToken = RestConstants.BASIC_AUTHENTICATION_PREFIX + basicToken;
    }

    @Override
    public String getProxyHost() {
        return proxyHost;
    }

    @Override
    public int getProxyPort() {
        return proxyPort;
    }
}
