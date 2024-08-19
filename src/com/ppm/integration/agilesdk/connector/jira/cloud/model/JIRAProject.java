
/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud.model;

import org.json.JSONException;
import org.json.JSONObject;

public class JIRAProject {

    private String key;

    private String name;
    
    private boolean isCompanyMangedProject = false;
    
    private long jiraProjectId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isCompanyMangedProject() {
		return isCompanyMangedProject;
	}

	public void setCompanyMangedProject(boolean isCompanyMangedProject) {
		this.isCompanyMangedProject = isCompanyMangedProject;
	}

	public long getJiraProjectId() {
		return jiraProjectId;
	}

	public void setJiraProjectId(long jiraProjectId) {
		this.jiraProjectId = jiraProjectId;
	}

    public static JIRAProject fromJSONObject(JSONObject obj) {
        try {
            JIRAProject project = new JIRAProject();
            project.setName(obj.getString("name"));
            project.setKey(obj.getString("key"));
            project.setCompanyMangedProject("classic".equals(obj.getString("style")));
            project.setJiraProjectId(obj.getLong("id"));
            return project;
        } catch (JSONException e) {
            throw new RuntimeException("Error while reading JSon defintiion of Project", e);
        }
    }

}
