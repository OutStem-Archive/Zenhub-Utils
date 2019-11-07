package com.outreach.zenhub;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.outreach.github.GH_Tickets;

public class Main {

    private static Logger log = Logger.getLogger(Main.class.getName());

    // This is the main class for the CLI
    public static void main(String[] args) {

        log.info("Initializing..");

        Map<String, String> parsed_args = Main.getCLIMap(args);
        Properties props = Main.getPropertiesFile(parsed_args.get("properties_file"));
        JsonArray gh_tickets = Main.getGitHubTickets(props);
        JsonArray fitered_tickets = Main.filterForPipelineTickets(gh_tickets, props);

        log.info("Complete");
    }

    public static JsonArray getGitHubTickets(Properties props) {
        log.info("Retrieving all tickets from Github");
        JsonArray result = null;
        try {
            result = GH_Tickets.getAllGithubTickets(props);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to retrieve Github tickets", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Caught an exception when trying to retrieve Github tickets", e);
        }
        log.info("Retrieval completed successfully");
        return result;
    }

    public static JsonArray filterForPipelineTickets(JsonArray gh_tickets, Properties props) {
        log.info("Filtering out the tickets that are not in the desired pipeline");
        JsonArray filter_gh_tickets = null;
        try {
            LookupTickets lookup = new LookupTickets(gh_tickets, props);
            filter_gh_tickets = lookup.removeUnwantedTickets();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Caught an IOException when filtering through Github tickets with Zenhub API", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Caught an Exception when filtering through Github tickets with Zenhub API", e);
        }
        log.info("Filter completed successfully");
        return filter_gh_tickets;
    }

    /**
     * Parse the CLI args to a map in order to be able to retrieve them faster The
     * CLI args must be in the following format entry_1=val_1, entry_2=val_2, ...,
     * entry_n=val_n
     * 
     * @param args The command line arguments
     * @return A map representation of the CLI args
     */
    public static Map<String, String> getCLIMap(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("The arguments are empty, provide the correct CLI params");
        }

        if (args.length == 0) {
            throw new IllegalArgumentException("The arguments are empty, provide the correct CLI params");
        }

        List<String> cli_args = Arrays.asList(args);
        Map<String, String> cli_arg_map = new HashMap<String, String>();

        cli_args.stream().forEach(arg -> {
            String[] parsed_arg = arg.split("=");

            if (parsed_arg.length == 2) {
                cli_arg_map.put(parsed_arg[0], parsed_arg[1]);
            } else {
                log.warning("Invalid arg " + arg + " skipping");
            }
        });

        return cli_arg_map;
    }

    public static Properties getPropertiesFile(String path_to_file) {
        if (path_to_file == null || path_to_file.isEmpty()) {
            throw new IllegalArgumentException("The path is empty, please provide a valid path");
        }

        Properties prop = new Properties();

        try (InputStream input = new FileInputStream(path_to_file)) {
            // load a properties file
            prop.load(input);

        } catch (IOException ex) {
            log.log(Level.SEVERE, "Caught an IOException when trying to read in the properties", ex);
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Caught an Exception when trying to read in the properties", ex);
        }

        return prop;
    }

}