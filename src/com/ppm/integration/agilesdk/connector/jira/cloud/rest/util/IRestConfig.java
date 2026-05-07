
/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud.rest.util;

/**
 * Configuration interface for REST client settings.
 */
public interface IRestConfig {

    IRestConfig setProxy(String proxyHost, String proxyPort);

    void setBasicAuthorizationCredentials(String username, String password);

    String getBasicAuthorizationToken();

    String getProxyHost();

    int getProxyPort();
}
