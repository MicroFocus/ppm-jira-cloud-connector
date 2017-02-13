package com.ppm.integration.agilesdk.connector.jira.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ppm.integration.agilesdk.pm.ExternalTask;

public class JIRAEpic extends JIRAIssue {

	public JIRAEpic(String issueName, String type, String key, String statusName, String scheduledStartDate,
			String scheduledFinishDate, String scheduledDuration, Long scheduledEffort, String actualStart,
			String percentComplete, String actualFinish, String predecessors, String role, String resources,
			String createdDate, String updatedDate, List<JIRAIssue> subTasks, String epicLink) {
		super(issueName, type, key, statusName, scheduledStartDate, scheduledFinishDate, scheduledDuration,
				scheduledEffort, actualStart, percentComplete, actualFinish, predecessors, role, resources, createdDate,
				updatedDate, subTasks, epicLink);
	}

	@Override
	public Date getScheduledStart() {

		return checkDate(this.getCreatedDate());
	}

	@Override
	public Date getScheduledFinish() {
		return checkDate(this.getUpdatedDate());
	}

	@Override
	public List<ExternalTask> getChildren() {
		List<ExternalTask> ets = new ArrayList<>();
		for (JIRAIssue issue : this.getSubTasks()) {
			ets.add((ExternalTask) issue);
		}
		return ets;
	}
}
