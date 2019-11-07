package com.outreach.github;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

public class GetTicketsTest {

    @Test(expected = IllegalArgumentException.class)
    public void getAllTickets() throws IOException {
        Properties props = new Properties();
        props.setProperty("github_base_url", "https://api.github.com");
        GH_Tickets.getAllGithubTickets(props);
    }

}