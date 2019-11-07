package com.outreach.github;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Properties;

import com.google.gson.JsonArray;

import org.junit.Test;

public class GetTicketsTest {

    @Test(expected = IllegalArgumentException.class)
    public void getAllTickets() throws IOException {
        Properties props = new Properties();
        props.setProperty("github_base_url", "https://api.github.com");
        GetTickets.getAllGithubTickets(props);
    }

}