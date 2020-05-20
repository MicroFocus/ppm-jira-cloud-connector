
/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud.rest.util.exception;

import java.lang.Thread.UncaughtExceptionHandler;

import com.ppm.integration.agilesdk.provider.Providers;
import org.apache.wink.client.ClientRuntimeException;

import com.ppm.integration.IntegrationException;
import com.ppm.integration.agilesdk.connector.jira.cloud.JIRACloudIntegrationConnector;

public class JIRAConnectivityExceptionHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        uncaughtException(t, e, JIRACloudIntegrationConnector.class);
    }

    public void uncaughtException(Thread t, Throwable e, Class cls) {
        if (e instanceof ClientRuntimeException) {
            handleClientRuntimeException((ClientRuntimeException)e, cls);
        } else if (e instanceof RestRequestException) {
            handleClientException((RestRequestException)e, cls);
        } else {
            throw IntegrationException.build(cls).setErrorCode("PPM_INT_JIRA_ERR_202").setMessage("ERROR_UNKNOWN_ERROR",
                    e.getMessage());
        }
    }

    private void handleClientException(RestRequestException e, Class cls) {
        switch (e.getStatusCode()) {
            case 404:
                throw IntegrationException.build(cls).setErrorCode("PPM_INT_JIRA_ERR_" + e.getStatusCode())
                        .setMessage("ERROR_DOMAIN_NOT_FOUND");
            case 502:
                throw IntegrationException.build(cls).setErrorCode("PPM_INT_JIRA_ERR_" + e.getStatusCode())
                        .setMessage("ERROR_BAD_GETWAY");
            case 400:
                throw IntegrationException.build(cls).setErrorCode("PPM_INT_JIRA_ERR_" + e.getStatusCode())
                        .setMessage("ERROR_BAD_REQUEST");
            case 401:
                String error_message_auth = Providers.getLocalizationProvider(JIRACloudIntegrationConnector.class).getConnectorText("ERROR_AUTHENTICATION_FAILED");
                throw IntegrationException.build(cls).setErrorCode("PPM_INT_JIRA_ERR_" + e.getStatusCode())
                        .setMessage(error_message_auth);
            default:
                throw IntegrationException.build(cls).setErrorCode("PPM_INT_JIRA_ERR_202")
                        .setMessage("ERROR_CONNECTIVITY_ERROR", e.getMessage());
        }

    }

    private void handleClientRuntimeException(Exception e, Class cls) {
        Throwable t = extractException(e);

        if (t instanceof java.security.cert.CertificateException) {

            IntegrationException.build(cls).setErrorCode("PPM_INT_JIRA_ERR_202")
                    .setMessage("ERROR_CERTIFICATE_EXCEPTION");
        }

        if (t instanceof java.net.UnknownHostException) {
            throw IntegrationException.build(cls).setErrorCode("PPM_INT_JIRA_ERR_202")
                    .setMessage("ERROR_UNKNOWN_HOST_EXCEPTION");
        }

        if (t instanceof java.net.ConnectException) {
            throw IntegrationException.build(cls).setErrorCode("PPM_INT_JIRA_ERR_202")
                    .setMessage("ERROR_CONNECT_EXCEPTION");
        }
        if (t instanceof java.net.NoRouteToHostException) {
            throw IntegrationException.build(cls).setErrorCode("PPM_INT_JIRA_ERR_202")
                    .setMessage("ERROR_NO_ROUTE_EXCEPTION");
        }
        if (t instanceof java.lang.IllegalArgumentException) {

            throw IntegrationException.build(cls).setErrorCode("PPM_INT_JIRA_ERR_202")
                    .setMessage("ERROR_ILLEGAL_ARGUMENT__EXCEPTION");
        }
        throw IntegrationException.build(cls).setErrorCode("PPM_INT_JIRA_ERR_202")
                .setMessage("ERROR_CONNECTIVITY_ERROR", e.getMessage());

    }

    @SuppressWarnings("unchecked")
    protected <T extends Throwable> T extractException(Exception e, Class<T> clazz) {

        Throwable t = e;
        while (!clazz.isInstance(t) && t != null) {
            t = t.getCause();
        }

        return (T)t;
    }

    protected <T extends Throwable> T extractException(Exception e) {

        Throwable t = e;

        while (t.getCause() != null) {
            t = t.getCause();

        }
        return (T)t;
    }

}