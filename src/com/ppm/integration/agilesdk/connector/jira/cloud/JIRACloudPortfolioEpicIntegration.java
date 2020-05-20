/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud;

import com.ppm.integration.agilesdk.ValueSet;
import com.ppm.integration.agilesdk.connector.jira.cloud.model.JIRAEpic;
import com.ppm.integration.agilesdk.connector.jira.cloud.model.JIRAIssue;
import com.ppm.integration.agilesdk.connector.jira.cloud.model.JIRAIssueType;
import com.ppm.integration.agilesdk.connector.jira.cloud.model.JIRASubTaskableIssue;
import com.ppm.integration.agilesdk.connector.jira.cloud.service.JIRAService;
import com.ppm.integration.agilesdk.epic.PortfolioEpicCreationInfo;
import com.ppm.integration.agilesdk.epic.PortfolioEpicIntegration;
import com.ppm.integration.agilesdk.epic.PortfolioEpicSyncInfo;
import com.ppm.integration.agilesdk.provider.Providers;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @see PortfolioEpicIntegration
 * @since PPM 9.42
 */
public class JIRACloudPortfolioEpicIntegration extends PortfolioEpicIntegration {

    // All operations for Portfolio Epics are using the admin account as users are not prompted for account info upon sync.


    @Override public String createEpicInAgileProject(PortfolioEpicCreationInfo epicInfo, String agileProjectValue,
            ValueSet instanceConfigurationParameters)
    {
        return JIRAServiceProvider.get(instanceConfigurationParameters).createEpic(agileProjectValue, epicInfo.getEpicName(), epicInfo.getEpicDescription());
    }

    @Override public PortfolioEpicSyncInfo getPortfolioEpicSyncInfo(String epicId, String agileProjectValue,
            ValueSet instanceConfigurationParameters)
    {
        if (StringUtils.isBlank(epicId)) {
            return null;
        }

        JIRAService service = JIRAServiceProvider.get(instanceConfigurationParameters);

        // We want to retrieve the epic and all of its contents to be able to compute aggregated story points & percent SP complete
        // That means retrieve all issue types except Sub-Tasks.

        List<JIRAIssueType> jiraIssueTypes =  service.getProjectIssueTypes(agileProjectValue);

        Set<String> issueTypes = new HashSet<String>();
        for (JIRAIssueType jiraIssueType : jiraIssueTypes) {
            if (!jiraIssueType.isSubTask()) {
                issueTypes.add(jiraIssueType.getName());
            }
        }

        List<JIRASubTaskableIssue> issues = null;

        try {
            issues = service.getAllEpicIssues(agileProjectValue, issueTypes, epicId);
        } catch (Exception e) {
            String errorEpicName = Providers.getLocalizationProvider(JIRACloudIntegrationConnector.class).getConnectorText("ERROR_EPIC_CANNOT_BE_RETRIEVED");
            // If there's an error when retrieving the Epic, it may mean that the Epic was deleted, that the JIRA server is down or that JIRA user doesn't have access to it anymore.
            PortfolioEpicSyncInfo epicSyncInfoError = new PortfolioEpicSyncInfo();
            epicSyncInfoError.setEpicName(errorEpicName);
            epicSyncInfoError.setDoneStoryPoints(0);
            epicSyncInfoError.setTotalStoryPoints(0);
            return epicSyncInfoError;
        }

        JIRAEpic jiraEpic = null;

        for (JIRAIssue issue: issues) {
            if (epicId.equalsIgnoreCase(issue.getKey())) {
                jiraEpic = (JIRAEpic) issue;
                break;
            }
        }

        if (jiraEpic == null) {
            // The Epic must have been deleted in Jira, or there's some problem to get it
            return null;
        }

        PortfolioEpicSyncInfo epicSyncInfo = new PortfolioEpicSyncInfo();
        epicSyncInfo.setEpicName(jiraEpic.getName());
        epicSyncInfo.setDoneStoryPoints(jiraEpic.getDoneStoryPoints());
        epicSyncInfo.setTotalStoryPoints(jiraEpic.getAggregatedStoryPoints());

        return epicSyncInfo;
    }

    @Override public String getEpicURI(String epicId, String agileProjectValue) {
        // The epic ID is actually the Issue Key
        return "/browse/"+epicId;
    }
}
