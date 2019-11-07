package com.outreach.zenhub;

import java.io.IOException;
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
import com.outreach.utils.HttpHelper;

public class LookupTickets {

    private static Logger log = Logger.getLogger(LookupTickets.class.getName());

    private JsonArray githubtickets;
    private Properties props;

    private String zenhub_issue_url;
    private String zenhub_auth_token;
    private String column;

    public LookupTickets(JsonArray githubtickets, Properties props) throws IllegalArgumentException {
        if (this.githubtickets == null)
            throw new IllegalArgumentException("Cannot have null set of github tickets");

        if (this.props == null)
            throw new IllegalArgumentException("Cannot have null set of CLI arguments");

        this.githubtickets = githubtickets;

        this.zenhub_issue_url = this.props.getProperty("zenhub_base_url");
        this.zenhub_auth_token = this.props.getProperty("zenhub_token");
        this.column = this.props.getProperty("filter_column");
    }

    public JsonArray removeUnwantedTickets() {
        JsonArray filterTickets = new JsonArray();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", this.zenhub_auth_token);
        
        try {
            for (JsonElement element : this.githubtickets) {
                int repo_id = this.getRepositoryID(element);
                int issue_number = this.getIssueNumber(element);
    
                String zenhub_uri = "/p1/repositories/" + repo_id + "/issues/" + issue_number;
                URL url = new URL(zenhub_issue_url + zenhub_uri);
    
                String zenhub_issue = HttpHelper.performGet(url, headers);
                if (zenhub_issue != null) {
                    JsonObject zenhub_issue_json = JsonParser.parseString(zenhub_issue).getAsJsonObject();
                    isTicketInDesiredColumn(zenhub_issue_json, filterTickets);
                }
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Caught an IOException in the filtering column process", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Caught an Exception in the filtering column process", e);
        }

        return filterTickets;
    }

    public void isTicketInDesiredColumn(JsonObject zenhub_issue_json, JsonArray filterTickets) {
        if (zenhub_issue_json != null && zenhub_issue_json.size() > 0 && filterTickets != null) {
            String pipeline = getPipelineName(zenhub_issue_json);

            if (isInCorrectPipeline(pipeline, this.column)) {
                filterTickets.add(zenhub_issue_json);
            }
        }
    }

    public String getPipelineName(JsonObject zenhub_issue_json) {
        if (zenhub_issue_json != null && zenhub_issue_json.size() > 0) {
            if (zenhub_issue_json.has("pipeline")) {
                return zenhub_issue_json.get("pipeline").getAsJsonObject().get("name").getAsString();
            }
        }

        return null;
    }

    public boolean isInCorrectPipeline(String ticket_pipeline, String desired_pipeline) {
        return ticket_pipeline.equalsIgnoreCase(desired_pipeline);
    }

    public int getRepositoryID(JsonElement githubTicket) {
        JsonObject ticket = githubTicket.getAsJsonObject();
        return ticket.get("repository").getAsJsonObject().get("id").getAsInt();
    }

    public int getIssueNumber(JsonElement githubTicket) {
        JsonObject ticket = githubTicket.getAsJsonObject();
        return ticket.get("number").getAsInt();
    }
}