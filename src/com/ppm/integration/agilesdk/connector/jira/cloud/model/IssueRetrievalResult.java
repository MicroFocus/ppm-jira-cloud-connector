/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IssueRetrievalResult {

    private List<JSONObject> issues = new ArrayList<>();

    private  String  nextPageToken;

    public IssueRetrievalResult(String nextPageToken) {
      this.nextPageToken = nextPageToken;
    }

    public List<JSONObject> getIssues() {
        return issues;
    }

    public void addIssue(JSONObject issue) {
        issues.add(issue);
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

}
