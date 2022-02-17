
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

/**
 * Main Connector class file for Jira Cloud connector.
 * Note that the Jira Cloud version is purely informative - there is no version for Jira Cloud.
 */
public class JIRACloudIntegrationConnector extends IntegrationConnector {

    @Override
    public String getExternalApplicationName() {
        return "Atlassian JIRA Cloud";
    }

    @Override
    public String getExternalApplicationVersionIndication() {
        return "2020+";
    }

    @Override
    public String getConnectorVersion() {
        return "3.1";
    }

    @Override
    public String getTargetApplicationIcon() {
        return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAeCAYAAAA/xX6fAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAA" +
                "AAJcEhZcwAADsMAAA7DAcdvqGQAAAR9SURBVEhL1ZZZaJxVFMfPvfe73zdNEwOGNhgtNs1SJwStUBW3igqJWyM+tAUfgtIHbfFFpaTYJpPpm" +
                "LQqiEIbpQnBJ+sCCn2oICr0rT7kQSo1rTFSmzYmmkmmWabzbdf/t0yWyUwyEVH8wbede+7533PuMkP/C6oOpiqIFAs/1wQPn0VjtF6qiehGZ3" +
                "2XeozU2kXXJCh3n7+rrLzsLU03XiGXPt56VO1cq2jRztqe8w84jtW+ORptlkYE/ZSCedglahuy2JcUZ3hdnaIyNPb88Lhjm91QaUJ24SCZ96x" +
                "GgEStVM9RTBUVa1WnkheGnnIZTyChh7gQOf7M+96KW7xeorxFiK7oULFvdCcXvFNI7R4hpVBuvqr5olFS1F2vqabVRAs2Vh1ItXCNd0hNu1v" +
                "quqbpOkJzch0n9FgMRBndgeXzXo1GO1YSzdtQ3W49rQnerulyG4Q0TeoEUfJEk2O/h165MM4U1ePWXyfV9kKrd5lgXcLdIaQ4KHRjWzYzTUp" +
                "cgWhmbo7SMzOhdw4MEG2G8BfVCdWYT3SJYN2b7r14dKLX/Rpq6Ytlr1BUaBpNJ8dblFKXgl65oDejKo3RN9GEqs09keYFa7vcBkz8Ebg/gnEK" +
                "b6xe8Gwps5fUI81TkwNnnAg9jAyGwu45eKmyjQ6n77cco02LRf0XZHYrxE7go8Xz9VtCkAnZlkW2mVG26TaPlpV/m93kW7rcRqHoNFKq9p3zom" +
                "YyFqu7HGf+5PPa910DMfdB59lcMQ/PhOIqYax7YvRK+XeLT5ThQ/xHzmivIvVbaMoDK9U1NdQQU7r3xfUkU4g5i0wsvz0PvqgQekMDidAUsEuJ" +
                "TNosU45rhJY8+Ctnal0Vagj4hTgzWYT1org9aLzu++QCRWRy2hH0/J3vuOt9G8Qqb/njSctM96Lklfn3p3JRvYGMzRoHXmJ+QvMlrI25tzFJr2" +
                "EcrYiP37v8oBIH0jb1p0Z+fVBGIn1Syo3BwjL8lYzjL+tp4/Y1zorWwTf4RGCjhRIlz8avVz4aG0T+OkTrkFWQSQ4YTJMmiKUmJjohviE0+3i" +
                "j904jvNjw+9S2aP/PHfzPoDVg2SKJJtzbbUb7sXlfRPQlARczOT5GM1NT4VZZOIlwLNnS0D/CnHcMHuajofs8y06an9r5ZVfRcQylF6tvyegWc9PN" +
                "FZghF9vF9C8reFq2bZ+8kRGdg4eDbZDLMkGPX9r5FUzxCZT2A0zaZGheAkfpIutL58WsjGlZZuYkkXV0pJtdQ/H8VZlLXkGPi3F+zbHpOM79dzFX" +
                "06F5AWyVSEkJISOyIeaYZj+ZdOzq2xVXC4l5FBT0GI7zcWlSD2J3IdN0aF4ADVj4KKNzSlnqSLJv04piHisKelyI8yS2Sw88D2FOzdDs7Q+6MTvj" +
                "QPIrxc229CdRlDHY3CuxqqDHxTY+PRehD7GkX0VMf4crpDabSp1TBu2lz+8r9CO5jKIEPUZe52nNYn14fdmTS8/ODVuG8wyd2l5wJf8j7PpMiZqY" +
                "vXtDbKw0NP0L/I1/3P8RRH8B2Hi5cEDZi1MAAAAASUVORK5CYII=";
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
                new PlainText(JIRAConstants.KEY_JIRA_EPIC_TYPE_NAME, "JIRA_EPIC_TYPE_NAME", "Epic", false),
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
