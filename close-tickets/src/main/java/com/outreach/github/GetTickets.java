package com.outreach.github;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.outreach.utils.HttpHelper;

public class GetTickets {

    private static Logger log = Logger.getLogger(GetTickets.class.getName());

    public static JsonArray getAllGithubTickets(Properties props) throws IOException {
        Integer page = 0;
        String url = props.getProperty("github_base_url");
        String token = props.getProperty("github_token");

        if (token == null || token.isEmpty()) {
            log.log(Level.SEVERE, "No Github token has been set please set the appropriate token value");
            throw new IllegalArgumentException("No Github token set in the config");
        }

        JsonArray tickets = new JsonArray();
        JsonArray all_tickets = new JsonArray();

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "Basic " + token);
        headers.put("Content-Type", "application/json");
        
        do {
            page = page + 1;    
            String uri = "/orgs/AES-Outreach/issues?filter=all&state=open&page=" + page;

            URL request_url = new URL(url + uri);
            String payload = HttpHelper.performGet(request_url, headers);

            if (payload != null) {
                tickets = JsonParser.parseReader(new StringReader(payload)).getAsJsonArray();
            } else {
                log.info("Failed to retrieve valid data from the HTTP request to Github");
                break;
            }
            
            // Copy all the tickets over to the array
            all_tickets.addAll(tickets);
        } while (tickets.size() > 0);

        return all_tickets;
    }
}