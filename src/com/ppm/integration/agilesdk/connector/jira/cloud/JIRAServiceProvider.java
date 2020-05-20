/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud;

import com.ppm.integration.agilesdk.ValueSet;
import com.ppm.integration.agilesdk.connector.jira.cloud.service.JIRAService;
import com.ppm.integration.agilesdk.connector.jira.cloud.rest.util.IRestConfig;
import com.ppm.integration.agilesdk.connector.jira.cloud.rest.util.JIRARestConfig;
import com.ppm.integration.agilesdk.connector.jira.cloud.rest.util.JiraRestWrapper;
import com.ppm.integration.agilesdk.provider.Providers;
import com.ppm.integration.agilesdk.provider.UserProvider;

public class JIRAServiceProvider {

    public static UserProvider getUserProvider() {
        return Providers.getUserProvider(JIRACloudIntegrationConnector.class);
    }

    public static JIRAService get(ValueSet config) {
        IRestConfig adminRestConfig = new JIRARestConfig();
        adminRestConfig.setProxy(config.get(JIRAConstants.KEY_PROXY_HOST), config.get(JIRAConstants.KEY_PROXY_PORT));
        adminRestConfig.setBasicAuthorizationCredentials(config.get(JIRAConstants.KEY_ADMIN_USERNAME),
                config.get(JIRAConstants.KEY_ADMIN_PASSWORD));
        JiraRestWrapper adminWrapper = new JiraRestWrapper(adminRestConfig);

        IRestConfig userRestConfig = new JIRARestConfig();
        userRestConfig.setProxy(config.get(JIRAConstants.KEY_PROXY_HOST), config.get(JIRAConstants.KEY_PROXY_PORT));
        userRestConfig.setBasicAuthorizationCredentials(config.get(JIRAConstants.KEY_USERNAME),
                config.get(JIRAConstants.KEY_PASSWORD));
        JiraRestWrapper userWrapper = new JiraRestWrapper(userRestConfig);

        JIRAService service = new JIRAService(config.get(JIRAConstants.KEY_BASE_URL), adminWrapper, userWrapper);

        return service;
    }

    }
