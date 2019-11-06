package com.outreach.zenhub;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Main {

    private static Logger log = Logger.getLogger(Main.class.getName());

    // This is the main class for the CLI
    public static void main(String[] args) {
        
    }

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

}