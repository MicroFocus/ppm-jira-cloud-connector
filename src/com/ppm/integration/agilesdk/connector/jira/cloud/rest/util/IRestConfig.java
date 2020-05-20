
/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud.rest.util;

import org.apache.wink.client.ClientConfig;

public interface IRestConfig {

    ClientConfig setProxy(String proxyHost, String proxyPort);

    void setBasicAuthorizationCredentials(String username, String password);

    String getBasicAuthorizationToken();

    ClientConfig getClientConfig();
}
