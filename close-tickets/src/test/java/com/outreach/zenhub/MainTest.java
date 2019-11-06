package com.outreach.zenhub;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class MainTest
{
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
}
