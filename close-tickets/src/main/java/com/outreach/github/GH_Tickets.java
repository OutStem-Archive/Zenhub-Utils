package com.outreach.github;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.outreach.utils.FileUtils;
import com.outreach.utils.HttpHelper;

import org.apache.http.client.ClientProtocolException;

public class GH_Tickets {

    private static Logger log = Logger.getLogger(GH_Tickets.class.getName());
    private Properties props;
    private String url;
    private String token;
    private String org;
    private String gh_type;

    public GH_Tickets(Properties props) {
        if (props == null)
            throw new IllegalArgumentException("Cannot pass in a null properties object");

        this.props = props;
        this.url = this.props.getProperty("github_base_url");
        this.token = this.props.getProperty("github_token");
        this.org = this.props.getProperty("github_org");
        this.gh_type = this.props.getProperty("github_type");
    }

    public JsonArray getAllGithubTickets() throws IOException {
        int page = 0;

        if (this.token == null || this.token.isEmpty()) {
            log.log(Level.SEVERE, "No Github token has been set please set the appropriate token value");
            throw new IllegalArgumentException("No Github token set in the config");
        }

        JsonArray tickets = new JsonArray();
        JsonArray all_tickets = new JsonArray();

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + this.token);
        headers.put("Content-Type", "application/json");

        do {
            page = page + 1;
            String uri = getGithubTicketsURI() + page;

            URL request_url = new URL(this.url + uri);
            Map<String, Object> payload = HttpHelper.performGet(request_url, headers);

            if (payload != null && HttpHelper.wasRequestSuccessful(payload)) {
                String body = (String) payload.get(HttpHelper.BODY);
                tickets = JsonParser.parseReader(new StringReader(body)).getAsJsonArray();
            } else {
                log.info("Failed to retrieve valid data from the HTTP request to Github");
                break;
            }

            // Copy all the tickets over to the array
            all_tickets.addAll(tickets);
        } while (tickets.size() > 0);

        FileUtils.writeFile("github_tickets.json", all_tickets.toString());

        return all_tickets;
    }

    public String getGithubTicketsURI() {
        if (this.gh_type.equals("user")) {
            return "/user/issues?filter=assigned&state=open&page=";
        } else {
            return "/orgs/" + this.org + "/issues?filter=all&state=open&page=";
        }
    }

    public String getGithubMoveTicketsURI() {
        if (this.gh_type.equals("user")) {
            return "/user/issues?filter=assigned&state=open&page=";
        } else {
            return "/orgs/" + this.org + "/issues?filter=all&state=open&page=";
        }
    }

    public JsonArray moveTickets(JsonArray gh_tickets)
            throws IllegalArgumentException, ClientProtocolException, IOException {
        
        FileUtils.writeFile("closed_github_tickets.json", gh_tickets.toString());

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + this.token);
        headers.put("Content-Type", "application/json");

        JsonObject closed = new JsonObject();
        closed.addProperty("state", "closed");
            
        JsonArray moved_gh_tickets = new JsonArray();

        if (gh_tickets != null && gh_tickets.size() > 0) {
            for (JsonElement gh_ticket : gh_tickets) {
                String repo_name = this.getRepoName(gh_ticket);
                int issue_number = this.getIssueNumber(gh_ticket);
                URL url = new URL(this.url + "/repos/" + this.org + "/" + repo_name + "/issues/" + issue_number);

                Map<String, Object> moved_tickets = HttpHelper.performPost(url, headers, closed.toString());
                JsonObject body = JsonParser.parseString((String) moved_tickets.get(HttpHelper.BODY)).getAsJsonObject();
                moved_gh_tickets.add(body);
            }
        }

        return moved_gh_tickets;
    }

    public int getIssueNumber(JsonElement githubTicket) {
        JsonObject ticket = githubTicket.getAsJsonObject();
        return ticket.get("number").getAsInt();
    }

    public String getRepoName(JsonElement gh_ticket) throws IllegalArgumentException {
        if (gh_ticket == null)
            throw new IllegalArgumentException("Cannot submit a null github ticket for processing");

        JsonObject gh_ticket_json = gh_ticket.getAsJsonObject();

        if (gh_ticket_json.has("repository")) {
            JsonObject repository = gh_ticket_json.get("repository").getAsJsonObject();

            if (repository.has("name")) {
                return repository.get("name").getAsString();
            }
        }
        return null;
    }
}