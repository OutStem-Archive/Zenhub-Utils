package com.outreach.zenhub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class MainTest
{

    public final String URI = "/close-tickets/src/test/resources/properties/properties.properties";

    @Test
    public void testImproperCLIConversion() {
        String[] args = {
            "entry_1",
            "entry_2",
            "entry_3"
        };

        Map<String, String> parsed_args = Main.getCLIMap(args);

        assertTrue(parsed_args.size() == 0);
    }

    @Test
    public void testProperCLIConversion() {
        String[] args = {
            "entry_1=entry_1",
            "entry_2=entry_2",
            "entry_3=entry_3"
        };

        Map<String, String> parsed_args = Main.getCLIMap(args);

        assertTrue(parsed_args.size() == 3);
        assertTrue(parsed_args.get("entry_3").equals("entry_3"));
        assertTrue(parsed_args.get("entry_2").equals("entry_2"));
        assertTrue(parsed_args.get("entry_1").equals("entry_1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullCLIConversion() {
        Main.getCLIMap(null);
    }

    @Test
    public void testReadProps() {
        String path = System.getProperty("user.dir") + URI ;
        Properties props = Main.getPropertiesFile(path);
        String github_base_url = props.getProperty("github_base_url");

        assertEquals("https://api.github.com", github_base_url);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testReadPropsNull() {
        Main.getPropertiesFile(null);
    }

    
}
