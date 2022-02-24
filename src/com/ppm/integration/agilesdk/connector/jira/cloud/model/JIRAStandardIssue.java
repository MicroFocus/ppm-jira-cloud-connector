/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud.model;

/**
 * This is a standard Jira Issue (story, bug, task, feature, etc.), i.e. not an Epic and not a sub-task.
 */
public class JIRAStandardIssue extends JIRASubTaskableIssue {
	private String parentKey;
	
	private String parentIssueType;

	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getParentIssueType() {
		return parentIssueType;
	}

	public void setParentIssueType(String parentIssueType) {
		this.parentIssueType = parentIssueType;
	}
	
	
}
