/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud.model;

/**
 * JIRASubTask are never transferred to PPM, but they are retrieved from JIRA and used to compute aggregated effort of a Task.
 */
public class JIRASubTask extends JIRAIssue {

    private String parentKey;

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }
}
