package com.nttd.automation.common.integration.jira;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.Issue.FluentCreate;
import net.rcarz.jiraclient.JiraClient;

public class JiraServiceProvider {

	private JiraClient jira;
	private String project;

	public JiraServiceProvider(String jiraUrl, String username, String password, String project) {
		BasicCredentials creds;
		try {
			creds = new BasicCredentials(username, password);
			jira = new JiraClient(jiraUrl, creds);
		} catch (Exception ex) {

		}
		this.project = project;
	}

	public void createJiraIssue(String issueType, String summary, String description, String reporterName) {
		try {
			FluentCreate newIssueFluentCreate = jira.createIssue(project, issueType);
			newIssueFluentCreate.field(Field.SUMMARY, summary);
			newIssueFluentCreate.field(Field.DESCRIPTION, description);
			newIssueFluentCreate.field(Field.REPORTER, reporterName);
			Issue newIssue = newIssueFluentCreate.execute();
			System.out.println("New issue created. Jira ID : " + newIssue);
		} catch (Exception ex) {

		}
	}

}