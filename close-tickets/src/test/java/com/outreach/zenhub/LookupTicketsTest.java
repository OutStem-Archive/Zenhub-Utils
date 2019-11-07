package com.outreach.zenhub;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Before;
import org.junit.Test;

public class LookupTicketsTest {

    LookupTickets lookupTickets;
    JsonObject gh_ticket;
    JsonObject zenhub_ticket;
    JsonObject zenhub_ticket_review;
    JsonObject zenhub_ticket_broken;

    @Before
    public void setup() {
        JsonArray gh_tickets = new JsonArray();
        Properties props = new Properties();
        props.setProperty("filter_column", "QA");

        this.lookupTickets = new LookupTickets(gh_tickets, props);

        String zenhub_ticket = "{\r\n    \"plus_ones\": [],\r\n    \"pipeline\": {\r\n        \"name\": \"QA\",\r\n        \"pipeline_id\": \"5c8fdfced06a4e6d359ed122\",\r\n        \"workspace_id\": \"5c8fdfced06a4e6d359ed124\"\r\n    },\r\n    \"pipelines\": [\r\n        {\r\n            \"name\": \"Review\",\r\n            \"pipeline_id\": \"5c8fdfced06a4e6d359ed122\",\r\n            \"workspace_id\": \"5c8fdfced06a4e6d359ed124\"\r\n        }\r\n    ],\r\n    \"is_epic\": false\r\n}";
        this.zenhub_ticket = JsonParser.parseString(zenhub_ticket).getAsJsonObject();

        String zenhub_ticket_review = "{\r\n    \"plus_ones\": [],\r\n    \"pipeline\": {\r\n        \"name\": \"Review\",\r\n        \"pipeline_id\": \"5c8fdfced06a4e6d359ed122\",\r\n        \"workspace_id\": \"5c8fdfced06a4e6d359ed124\"\r\n    },\r\n    \"pipelines\": [\r\n        {\r\n            \"name\": \"Review\",\r\n            \"pipeline_id\": \"5c8fdfced06a4e6d359ed122\",\r\n            \"workspace_id\": \"5c8fdfced06a4e6d359ed124\"\r\n        }\r\n    ],\r\n    \"is_epic\": false\r\n}";
        this.zenhub_ticket_review = JsonParser.parseString(zenhub_ticket_review).getAsJsonObject();

        String zenhub_ticket_broken = "{\r\n    \"plus_ones\": [],\r\n    \"pipeline\": {\r\n    },\r\n    \"pipelines\": [\r\n        {\r\n            \"name\": \"Review\",\r\n            \"pipeline_id\": \"5c8fdfced06a4e6d359ed122\",\r\n            \"workspace_id\": \"5c8fdfced06a4e6d359ed124\"\r\n        }\r\n    ],\r\n    \"is_epic\": false\r\n}";
        this.zenhub_ticket_broken = JsonParser.parseString(zenhub_ticket_broken).getAsJsonObject();

        String gh_ticket = "{\r\n        \"url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/issues/98\",\r\n        \"repository_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access\",\r\n        \"labels_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/issues/98/labels{/name}\",\r\n        \"comments_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/issues/98/comments\",\r\n        \"events_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/issues/98/events\",\r\n        \"html_url\": \"https://github.com/AES-Outreach/Outreach-Data-Access/pull/98\",\r\n        \"id\": 518056423,\r\n        \"node_id\": \"MDExOlB1bGxSZXF1ZXN0MzM3MDEzMjc3\",\r\n        \"number\": 98,\r\n        \"title\": \"Feature/get siso registrations\",\r\n        \"user\": {\r\n            \"login\": \"ebivibe\",\r\n            \"id\": 33612255,\r\n            \"node_id\": \"MDQ6VXNlcjMzNjEyMjU1\",\r\n            \"avatar_url\": \"https://avatars2.githubusercontent.com/u/33612255?v=4\",\r\n            \"gravatar_id\": \"\",\r\n            \"url\": \"https://api.github.com/users/ebivibe\",\r\n            \"html_url\": \"https://github.com/ebivibe\",\r\n            \"followers_url\": \"https://api.github.com/users/ebivibe/followers\",\r\n            \"following_url\": \"https://api.github.com/users/ebivibe/following{/other_user}\",\r\n            \"gists_url\": \"https://api.github.com/users/ebivibe/gists{/gist_id}\",\r\n            \"starred_url\": \"https://api.github.com/users/ebivibe/starred{/owner}{/repo}\",\r\n            \"subscriptions_url\": \"https://api.github.com/users/ebivibe/subscriptions\",\r\n            \"organizations_url\": \"https://api.github.com/users/ebivibe/orgs\",\r\n            \"repos_url\": \"https://api.github.com/users/ebivibe/repos\",\r\n            \"events_url\": \"https://api.github.com/users/ebivibe/events{/privacy}\",\r\n            \"received_events_url\": \"https://api.github.com/users/ebivibe/received_events\",\r\n            \"type\": \"User\",\r\n            \"site_admin\": false\r\n        },\r\n        \"labels\": [],\r\n        \"state\": \"open\",\r\n        \"locked\": false,\r\n        \"assignee\": {\r\n            \"login\": \"ebivibe\",\r\n            \"id\": 33612255,\r\n            \"node_id\": \"MDQ6VXNlcjMzNjEyMjU1\",\r\n            \"avatar_url\": \"https://avatars2.githubusercontent.com/u/33612255?v=4\",\r\n            \"gravatar_id\": \"\",\r\n            \"url\": \"https://api.github.com/users/ebivibe\",\r\n            \"html_url\": \"https://github.com/ebivibe\",\r\n            \"followers_url\": \"https://api.github.com/users/ebivibe/followers\",\r\n            \"following_url\": \"https://api.github.com/users/ebivibe/following{/other_user}\",\r\n            \"gists_url\": \"https://api.github.com/users/ebivibe/gists{/gist_id}\",\r\n            \"starred_url\": \"https://api.github.com/users/ebivibe/starred{/owner}{/repo}\",\r\n            \"subscriptions_url\": \"https://api.github.com/users/ebivibe/subscriptions\",\r\n            \"organizations_url\": \"https://api.github.com/users/ebivibe/orgs\",\r\n            \"repos_url\": \"https://api.github.com/users/ebivibe/repos\",\r\n            \"events_url\": \"https://api.github.com/users/ebivibe/events{/privacy}\",\r\n            \"received_events_url\": \"https://api.github.com/users/ebivibe/received_events\",\r\n            \"type\": \"User\",\r\n            \"site_admin\": false\r\n        },\r\n        \"assignees\": [\r\n            {\r\n                \"login\": \"ebivibe\",\r\n                \"id\": 33612255,\r\n                \"node_id\": \"MDQ6VXNlcjMzNjEyMjU1\",\r\n                \"avatar_url\": \"https://avatars2.githubusercontent.com/u/33612255?v=4\",\r\n                \"gravatar_id\": \"\",\r\n                \"url\": \"https://api.github.com/users/ebivibe\",\r\n                \"html_url\": \"https://github.com/ebivibe\",\r\n                \"followers_url\": \"https://api.github.com/users/ebivibe/followers\",\r\n                \"following_url\": \"https://api.github.com/users/ebivibe/following{/other_user}\",\r\n                \"gists_url\": \"https://api.github.com/users/ebivibe/gists{/gist_id}\",\r\n                \"starred_url\": \"https://api.github.com/users/ebivibe/starred{/owner}{/repo}\",\r\n                \"subscriptions_url\": \"https://api.github.com/users/ebivibe/subscriptions\",\r\n                \"organizations_url\": \"https://api.github.com/users/ebivibe/orgs\",\r\n                \"repos_url\": \"https://api.github.com/users/ebivibe/repos\",\r\n                \"events_url\": \"https://api.github.com/users/ebivibe/events{/privacy}\",\r\n                \"received_events_url\": \"https://api.github.com/users/ebivibe/received_events\",\r\n                \"type\": \"User\",\r\n                \"site_admin\": false\r\n            }\r\n        ],\r\n        \"milestone\": null,\r\n        \"comments\": 0,\r\n        \"created_at\": \"2019-11-05T21:38:53Z\",\r\n        \"updated_at\": \"2019-11-07T15:52:19Z\",\r\n        \"closed_at\": null,\r\n        \"author_association\": \"CONTRIBUTOR\",\r\n        \"repository\": {\r\n            \"id\": 207885028,\r\n            \"node_id\": \"MDEwOlJlcG9zaXRvcnkyMDc4ODUwMjg=\",\r\n            \"name\": \"Outreach-Data-Access\",\r\n            \"full_name\": \"AES-Outreach/Outreach-Data-Access\",\r\n            \"private\": true,\r\n            \"owner\": {\r\n                \"login\": \"AES-Outreach\",\r\n                \"id\": 37001002,\r\n                \"node_id\": \"MDEyOk9yZ2FuaXphdGlvbjM3MDAxMDAy\",\r\n                \"avatar_url\": \"https://avatars1.githubusercontent.com/u/37001002?v=4\",\r\n                \"gravatar_id\": \"\",\r\n                \"url\": \"https://api.github.com/users/AES-Outreach\",\r\n                \"html_url\": \"https://github.com/AES-Outreach\",\r\n                \"followers_url\": \"https://api.github.com/users/AES-Outreach/followers\",\r\n                \"following_url\": \"https://api.github.com/users/AES-Outreach/following{/other_user}\",\r\n                \"gists_url\": \"https://api.github.com/users/AES-Outreach/gists{/gist_id}\",\r\n                \"starred_url\": \"https://api.github.com/users/AES-Outreach/starred{/owner}{/repo}\",\r\n                \"subscriptions_url\": \"https://api.github.com/users/AES-Outreach/subscriptions\",\r\n                \"organizations_url\": \"https://api.github.com/users/AES-Outreach/orgs\",\r\n                \"repos_url\": \"https://api.github.com/users/AES-Outreach/repos\",\r\n                \"events_url\": \"https://api.github.com/users/AES-Outreach/events{/privacy}\",\r\n                \"received_events_url\": \"https://api.github.com/users/AES-Outreach/received_events\",\r\n                \"type\": \"Organization\",\r\n                \"site_admin\": false\r\n            },\r\n            \"html_url\": \"https://github.com/AES-Outreach/Outreach-Data-Access\",\r\n            \"description\": \"A repository used to manage the POJO's that interact with the database that Outstem uses\",\r\n            \"fork\": false,\r\n            \"url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access\",\r\n            \"forks_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/forks\",\r\n            \"keys_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/keys{/key_id}\",\r\n            \"collaborators_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/collaborators{/collaborator}\",\r\n            \"teams_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/teams\",\r\n            \"hooks_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/hooks\",\r\n            \"issue_events_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/issues/events{/number}\",\r\n            \"events_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/events\",\r\n            \"assignees_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/assignees{/user}\",\r\n            \"branches_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/branches{/branch}\",\r\n            \"tags_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/tags\",\r\n            \"blobs_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/git/blobs{/sha}\",\r\n            \"git_tags_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/git/tags{/sha}\",\r\n            \"git_refs_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/git/refs{/sha}\",\r\n            \"trees_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/git/trees{/sha}\",\r\n            \"statuses_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/statuses/{sha}\",\r\n            \"languages_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/languages\",\r\n            \"stargazers_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/stargazers\",\r\n            \"contributors_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/contributors\",\r\n            \"subscribers_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/subscribers\",\r\n            \"subscription_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/subscription\",\r\n            \"commits_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/commits{/sha}\",\r\n            \"git_commits_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/git/commits{/sha}\",\r\n            \"comments_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/comments{/number}\",\r\n            \"issue_comment_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/issues/comments{/number}\",\r\n            \"contents_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/contents/{+path}\",\r\n            \"compare_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/compare/{base}...{head}\",\r\n            \"merges_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/merges\",\r\n            \"archive_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/{archive_format}{/ref}\",\r\n            \"downloads_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/downloads\",\r\n            \"issues_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/issues{/number}\",\r\n            \"pulls_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/pulls{/number}\",\r\n            \"milestones_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/milestones{/number}\",\r\n            \"notifications_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/notifications{?since,all,participating}\",\r\n            \"labels_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/labels{/name}\",\r\n            \"releases_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/releases{/id}\",\r\n            \"deployments_url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/deployments\",\r\n            \"created_at\": \"2019-09-11T18:57:00Z\",\r\n            \"updated_at\": \"2019-11-07T14:29:31Z\",\r\n            \"pushed_at\": \"2019-11-07T16:50:58Z\",\r\n            \"git_url\": \"git://github.com/AES-Outreach/Outreach-Data-Access.git\",\r\n            \"ssh_url\": \"git@github.com:AES-Outreach/Outreach-Data-Access.git\",\r\n            \"clone_url\": \"https://github.com/AES-Outreach/Outreach-Data-Access.git\",\r\n            \"svn_url\": \"https://github.com/AES-Outreach/Outreach-Data-Access\",\r\n            \"homepage\": \"\",\r\n            \"size\": 935,\r\n            \"stargazers_count\": 0,\r\n            \"watchers_count\": 0,\r\n            \"language\": \"Java\",\r\n            \"has_issues\": true,\r\n            \"has_projects\": true,\r\n            \"has_downloads\": true,\r\n            \"has_wiki\": true,\r\n            \"has_pages\": false,\r\n            \"forks_count\": 0,\r\n            \"mirror_url\": null,\r\n            \"archived\": false,\r\n            \"disabled\": false,\r\n            \"open_issues_count\": 5,\r\n            \"license\": {\r\n                \"key\": \"apache-2.0\",\r\n                \"name\": \"Apache License 2.0\",\r\n                \"spdx_id\": \"Apache-2.0\",\r\n                \"url\": \"https://api.github.com/licenses/apache-2.0\",\r\n                \"node_id\": \"MDc6TGljZW5zZTI=\"\r\n            },\r\n            \"forks\": 0,\r\n            \"open_issues\": 5,\r\n            \"watchers\": 0,\r\n            \"default_branch\": \"master\"\r\n        },\r\n        \"pull_request\": {\r\n            \"url\": \"https://api.github.com/repos/AES-Outreach/Outreach-Data-Access/pulls/98\",\r\n            \"html_url\": \"https://github.com/AES-Outreach/Outreach-Data-Access/pull/98\",\r\n            \"diff_url\": \"https://github.com/AES-Outreach/Outreach-Data-Access/pull/98.diff\",\r\n            \"patch_url\": \"https://github.com/AES-Outreach/Outreach-Data-Access/pull/98.patch\"\r\n        },\r\n        \"body\": \"## Purpose\\r\\nAdded queries for getting registrations, programs, and collections for the sign in sign out app\\r\\n\\r\\n## Acceptance Criteria\\r\\n- [ ] The user should be able to query the registrations by team, program type, term, program region, and language\\r\\n\\r\\n\"\r\n    }";
        this.gh_ticket = JsonParser.parseString(gh_ticket).getAsJsonObject();
    }

    @Test
    public void testgetIssueNumber() {
        assertEquals(98, this.lookupTickets.getIssueNumber(this.gh_ticket));
    }

    @Test
    public void testgetRepositoryNumber() {
        assertEquals(207885028, this.lookupTickets.getRepositoryID(this.gh_ticket));
    }

    @Test(expected = NullPointerException.class)
    public void testgetRepositoryNumberNull() {
        this.lookupTickets.getRepositoryID(null);
    }

    @Test(expected = NullPointerException.class)
    public void testgetIssueNumberNull() {
        this.lookupTickets.getIssueNumber(null);
    }

    @Test
    public void testGetPipelineName() {
        assertEquals("QA", this.lookupTickets.getPipelineName(this.zenhub_ticket));
    }

    @Test
    public void testGetPipelineNameNull() {
        assertEquals(null,this.lookupTickets.getPipelineName(null));
    }

    @Test
    public void testGetPipelineNoPipeline() {
        assertEquals(null,this.lookupTickets.getPipelineName(this.zenhub_ticket_broken));
    }

    @Test
    public void testFilterColumnQA() {
        JsonArray filteredTicket = new JsonArray();
        this.lookupTickets.isTicketInDesiredColumn(this.zenhub_ticket, new JsonObject(), filteredTicket);

        assertEquals(1, filteredTicket.size());
    }

    @Test
    public void testFilterColumnReview() {
        JsonArray filteredTicket = new JsonArray();
        this.lookupTickets.isTicketInDesiredColumn(this.zenhub_ticket_review, new JsonObject(),filteredTicket);

        assertEquals(0, filteredTicket.size());
    }

}