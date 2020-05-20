
/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JIRAEpic extends JIRASubTaskableIssue {

    private List<JIRASubTaskableIssue> contents = new ArrayList<JIRASubTaskableIssue>();

    public List<JIRASubTaskableIssue> getContents() {
        return contents;
    }

    public void addContent(JIRASubTaskableIssue issue) {
        contents.add(issue);
    }

    // Sums up the Story Points from all the Epic contents, or use Epic SP if there's not content SP info.
    public int getAggregatedStoryPoints() {

        int aggSP = 0;
        int epicSP = getStoryPoints() == null ? 0 : getStoryPoints().intValue();

        for (JIRASubTaskableIssue content : contents) {
            aggSP += content.getStoryPoints() == null ? 0 : content.getStoryPoints();
        }

        if (aggSP == 0) {
            // Epic story points are used if Epic hasn't yet been broken down & sized
            aggSP = epicSP;
        }

        return aggSP;
    }

    public int getDoneStoryPoints() {

        int doneSP = 0;

        for (JIRASubTaskableIssue content : contents) {
            if (content.isDone()) {
                doneSP += content.getStoryPoints() == null ? 0 : content.getStoryPoints();
            }
        }

        return doneSP;

    }

    /**
     * @return an estimation of the finish date of this Epic, used for inserting Epics as Milestones.
     */
    public Date getEstimatedFinishDate(Map<String, JIRASprint> sprintsInfo) {

        Date epicFinishDate = getSprintFinishDate(this, sprintsInfo);

        if (epicFinishDate != null) {
            return epicFinishDate;
        }

        epicFinishDate = JIRAEntity.getDefaultStartDate();

        for (JIRAIssue issue : getContents()) {
            Date issueSprintEndDate = getSprintFinishDate(issue, sprintsInfo);

            if (issueSprintEndDate == null) {
                continue;
            }

            if (issueSprintEndDate.after(epicFinishDate)) {
                epicFinishDate = issueSprintEndDate;
            }
        }

        return epicFinishDate;
    }

    private Date getSprintFinishDate(JIRAIssue issue, Map<String, JIRASprint> sprintsInfo) {
        JIRASprint sprint = sprintsInfo != null ? sprintsInfo.get(issue.getSprintId()) : null;

        if (sprint == null) {
            return null;
        }

        return sprint.getEndDateAsDate();
    }
}