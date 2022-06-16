
/*
 * © Copyright 2019 - 2020 Micro Focus or one of its affiliates.
 */

package com.ppm.integration.agilesdk.connector.jira.cloud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ppm.integration.agilesdk.FunctionIntegration;
import com.ppm.integration.agilesdk.IntegrationConnector;
import com.ppm.integration.agilesdk.ValueSet;
import com.ppm.integration.agilesdk.connector.jira.cloud.model.JIRAProject;
import com.ppm.integration.agilesdk.model.AgileProject;
import com.ppm.integration.agilesdk.ui.*;

import org.apache.log4j.Logger;

/**
 * Main Connector class file for Jira Cloud connector.
 * Note that the Jira Cloud version is purely informative - there is no version for Jira Cloud.
 */
public class JIRACloudIntegrationConnector extends IntegrationConnector {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public String getExternalApplicationName() {
        return "Atlassian JIRA Cloud";
    }

    @Override
    public String getExternalApplicationVersionIndication() {
        return "2022+";
    }

    @Override
    public String getConnectorVersion() {
        return "4.0";
    }

    @Override
    public String getTargetApplicationIcon() {
    	return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAF6WlUWHRYT" + 
                "Uw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1" + 
    			"wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNi4wLWMwMDYgNzkuMTY0NzUzLCAyMDIxL" + 
                "zAyLzE1LTExOjUyOjEzICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5" + 
    			"bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8i" + 
                "IHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL" + 
    			"3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly" + 
                "9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgMjIuMyA" + 
    			"oTWFjaW50b3NoKSIgeG1wOkNyZWF0ZURhdGU9IjIwMjItMDQtMjBUMTc6MTM6NDcrMDg6MDAiIHhtcDpNb2RpZnlEYXRlPSIyMDIyLTA0LTIw" + 
                "VDE3OjE2OjE0KzA4OjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDIyLTA0LTIwVDE3OjE2OjE0KzA4OjAwIiBkYzpmb3JtYXQ9ImltYWdlL3BuZyI" + 
    			"gcGhvdG9zaG9wOkNvbG9yTW9kZT0iMyIgcGhvdG9zaG9wOklDQ1Byb2ZpbGU9InNSR0IgSUVDNjE5NjYtMi4xIiB4bXBNTTpJbnN0YW5jZUlEPS" + 
                "J4bXAuaWlkOjExYjU5NmJlLTI1YzctNDI1Ny1hZmIyLTZjNDI5MDgyYzcwNSIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo3ZDZiMTVhZC1lMG" + 
    			"Y0LTQ2NWItOWRmOC02NzBmODVhMTY4YzgiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo3ZDZiMTVhZC1lMGY0LTQ2NWItOWRmOC" + 
                "02NzBmODVhMTY4YzgiPiA8eG1wTU06SGlzdG9yeT4gPHJkZjpTZXE+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjcmVhdGVkIiBzdEV2dDppbnN0YW" + 
    			"5jZUlEPSJ4bXAuaWlkOjdkNmIxNWFkLWUwZjQtNDY1Yi05ZGY4LTY3MGY4NWExNjhjOCIgc3RFdnQ6d2hlbj0iMjAyMi0wNC0yMFQxNzoxMzo0Ny" + 
                "swODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIDIyLjMgKE1hY2ludG9zaCkiLz4gPHJkZjpsaSBzdEV2dDphY3Rpb24" + 
    			"9InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjExYjU5NmJlLTI1YzctNDI1Ny1hZmIyLTZjNDI5MDgyYzcwNSIgc3RFdnQ6d2hlbj0i" + 
                "MjAyMi0wNC0yMFQxNzoxNjoxNCswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIDIyLjMgKE1hY2ludG9zaCkiIHN0R" + 
    			"XZ0OmNoYW5nZWQ9Ii8iLz4gPC9yZGY6U2VxPiA8L3htcE1NOkhpc3Rvcnk+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXR" + 
                "hPiA8P3hwYWNrZXQgZW5kPSJyIj8+KJIRAgAAAlpJREFUOI11krtrFHEQxz/z2/3t627VJJqkESzSqFhYqYVC8EFA8Ynio9J/x9bWygcG8QFaCEqwE" + 
    			"LRQEDQIwRcEC3OeUW9vb+929zcWGpF4TjnMfGa+M1+ZuuigAEEY5H1+fP1CZ7lNXVWsS9bvTzZtXN+b5jbP6EsbdIvARqgvwfgUGIaHEZHdyeTa2ckJ" + 
                "uR7+4ChKOLRwSM6iulfV3Q3WJCO9Dmx4wrWg4iD8C1kNCBBmjLU3xTOjVIp6UIyKN265amEGCP4HsBgO2CCYjZvNtX4QUuQ5AqhCH+Jxn1mr7AfsaoB" + 
    			"B2OOH9kacNuMoSYiiiKzd6g6KuuV5oCr0UwknPuudoGBawVsBGGAHqtd8zzSCKCJqNEjWjXV61ffDvXJxl420DagaKBA7+VrvhX2m1cMzwDZxckVEJl" +
                "TBGCGKwy5eegoxc4i8W/7CIc+nBYBCz0gwOqMP7QjHjF9IidXOiiZVwBhZ0zDh1ObtZO2YTquXuro2IoCA1lBnFLZJaYA3wGmcvvwDcSTGchWRM8A+z" + 
    			"2/fyjv5WD1wGAN+qu9Nk50Y7htAURaMcgr0BfIL4hxNgctBUN/tZd20yDIZDDI0rhaKRTni+rzCUK98QYG3KGfV6dO/5MRpOtZAHd0sp0rj+aVHcqL8z" + 
                "jwGt9oHCiwA51B9LPrrHkES4NmIfrn0+tuDuZPV197879qhTgT4AJyvPX3sAFcrZeUWXdk67brZm99nHupEUKhLcCUfq5oLSc7LwVKel657XNXNI//O8/9" + 
    			"uNiFMbhW0BhU+eMrJj5/6Y27ZPR+yKQA/Afh0Cs6cpogTAAAAAElFTkSuQmCC";
    }

    @Override
    public List<Field> getDriverConfigurationFields() {
        return Arrays.asList(new Field[] {new PlainText(JIRAConstants.KEY_BASE_URL, "BASE_URL", "", true),
                new PlainText(JIRAConstants.KEY_PROXY_HOST, "PROXY_HOST", "", false),
                new PlainText(JIRAConstants.KEY_PROXY_PORT, "PROXY_PORT", "", false),
                new LineBreaker(),
                new LabelText("", "ADMIN_INFO_FOR_EPIC_AND_AGILE_DATA", "block", false),
                new PlainText(JIRAConstants.KEY_ADMIN_USERNAME, "ADMIN_USERNAME", "", true),
                new PasswordText(JIRAConstants.KEY_ADMIN_PASSWORD, "ADMIN_PASSWORD", "", true),
                new LineBreaker(),
                new LabelText(JIRAConstants.LABEL_REQUEST_AGILE_OPTIONS, "REQUEST_AGILE_OPTIONS","Request Mapping (Request-Agile):", false),
                new CheckBox(JIRAConstants.KEY_ALLOW_WILDCARD_PROJECT_MAPPING, "KEY_ALLOW_WILDCARD_PROJECT_MAPPING", false),
                new LineBreaker(),
                new LabelText(JIRAConstants.LABEL_WORK_PLAN_OPTIONS, "WORK_PLAN_OPTIONS",
                        "User Data Options:", true),
                getUserDataDDL(JIRAConstants.SELECT_USER_DATA_STORY_POINTS, "USER_DATA_STORY_POINTS"),
                getUserDataDDL(JIRAConstants.SELECT_USER_DATA_AGGREGATED_STORY_POINTS, "USER_DATA_AGGREGATED_STORY_POINTS"),
                new PlainText(JIRAConstants.KEY_JIRA_EPIC_TYPE_NAME, "JIRA_EPIC_TYPE_NAME", JIRAConstants.DEFAULT_JIRA_EPIC_TYPE_NAME, false),
                new LineBreaker(),
                new CheckBox(JIRAConstants.KEY_USE_ADMIN_PASSWORD_TO_MAP_TASKS, "KEY_USE_ADMIN_PASSWORD_TO_MAP_TASKS", false),
                new CheckBox(JIRAConstants.KEY_IMPORT_ASSIGNED_USERS_TO_TASKS, "KEY_IMPORT_ASSIGNED_USERS_TO_TASKS", true),
                new LineBreaker(),
                new PlainText(JIRAConstants.KEY_WORK_PLAN_ISSUE_TYPES_ALLOW_LIST, "WORK_PLAN_ISSUE_TYPES_ALLOW_LIST", "", false),
                new PlainText(JIRAConstants.KEY_WORK_PLAN_ISSUE_TYPES_CHECKED_LIST, "WORK_PLAN_ISSUE_TYPES_CHECKED_LIST", "", false),
                new LineBreaker(),
                new LabelText("", "STATUS_MAPPING_LABEL", "block", false),
                new LineBreaker(),
                //new PlainText(JIRAConstants.KEY_TASK_STATUS_IN_PLANNING, "STATUS_IN_PLANNING_LABEL", "", false),
                new PlainText(JIRAConstants.KEY_TASK_STATUS_READY, "STATUS_READY_LABEL", "To Do;Open;Reopened", false),
                //new PlainText(JIRAConstants.KEY_TASK_STATUS_ACTIVE, "STATUS_ACTIVE_LABEL", "", false),
                new PlainText(JIRAConstants.KEY_TASK_STATUS_IN_PROGRESS, "STATUS_IN_PROGRESS_LABEL", "In Progress", false),
                new PlainText(JIRAConstants.KEY_TASK_STATUS_COMPLETED, "STATUS_COMPLETED_LABEL", "Done;Closed;Resolved", false),
                //new PlainText(JIRAConstants.KEY_TASK_STATUS_PENDING_PREDECESSOR, "STATUS_PENDING_PREDECESSOR_LABEL", "", false),
                new PlainText(JIRAConstants.KEY_TASK_STATUS_CANCELLED, "STATUS_CANCELLED_LABEL", "", false),
                //new PlainText(JIRAConstants.KEY_TASK_STATUS_ON_HOLD, "STATUS_ON_HOLD_LABEL", "", false),
                new PlainText(JIRAConstants.KEY_TASK_STATUS_UNKNOWN, "STATUS_UNKNOWN_LABEL", "", false)
        });
    }

    @Override
    public List<AgileProject> getAgileProjects(ValueSet instanceConfigurationParameters) {
        List<JIRAProject> jiraProjects = JIRAServiceProvider.get(instanceConfigurationParameters).useAdminAccount().getProjects();
        List<AgileProject> agileProjects = new ArrayList<AgileProject>(jiraProjects.size());

        // Adding * project for project-independent fields mapping at the begging of the list
        if ("true".equals(instanceConfigurationParameters.get(JIRAConstants.KEY_ALLOW_WILDCARD_PROJECT_MAPPING))) {
            AgileProject agileProject = new AgileProject();
            agileProject.setDisplayName("*");
            agileProject.setValue("*");
            agileProjects.add(agileProject);
        }

        for (JIRAProject jiraProject : jiraProjects) {
            AgileProject agileProject = new AgileProject();
            agileProject.setDisplayName(jiraProject.getName());
            agileProject.setValue(jiraProject.getKey());
            agileProjects.add(agileProject);
        }

        return agileProjects;
    }

    @Override
    public List<FunctionIntegration> getIntegrations() {
        return Arrays.asList(new FunctionIntegration[] {new JIRACloudWorkPlanIntegration(), new JIRACloudTimeSheetIntegration()});
    }

    @Override
    public List<String> getIntegrationClasses() {
        return Arrays.asList(new String[] {"com.ppm.integration.agilesdk.connector.jira.cloud.JIRACloudWorkPlanIntegration","com.ppm.integration.agilesdk.connector.jira.cloud.JIRACloudTimeSheetIntegration", "com.ppm.integration.agilesdk.connector.jira.cloud.JIRACloudPortfolioEpicIntegration", "com.ppm.integration.agilesdk.connector.jira.cloud.JIRACloudAgileDataIntegration", "com.ppm.integration.agilesdk.connector.jira.cloud.JIRACloudRequestIntegration"});
    }

    @Override
    /** @since 10.0.3 */
    public String testConnection(ValueSet instanceConfigurationParameters) {
        // Overriding this method as making a call to /rest/api/2/myself is faster then retrieving the whole list of projects.
        try {
            String myselfInfo = JIRAServiceProvider.get(instanceConfigurationParameters).useAdminAccount().getMyselfInfo();
            logger.debug("Test Connection successful. Returned myself info:");
            logger.debug(myselfInfo);
        } catch (Exception e) {
            logger.error("Error when testing connectivity", e);
            return e.getMessage();
        }

        return null;
    }

    private DynamicDropdown getUserDataDDL(String elementName,
                                           String labelKey) {

        DynamicDropdown udDDL = new DynamicDropdown(elementName, labelKey, "0", "", false) {

            @Override public List<String> getDependencies() {
                return new ArrayList<String>();
            }

            @Override public List<Option> getDynamicalOptions(ValueSet values) {
                List<Option> options = new ArrayList<Option>();
                options.add(new Option("0", "Do not sync"));

                for (int i = 1 ; i <= 20 ; i++) {
                    options.add(new Option(String.valueOf(i), "USER_DATA"+i));
                }

                return options;
            }
        };

        return udDDL;
    }

}
