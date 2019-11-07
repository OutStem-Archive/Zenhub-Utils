package com.outreach.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class HttpHelperTest {
    
    @Test
    public void testHttpHelperWasNotSuccessful() {
        assertTrue(!HttpHelper.wasRequestSuccessful(null));
    }

    @Test
    public void testHttpHelperWasSuccessful() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(HttpHelper.RESPONSE_CODE, 222);
        assertTrue(HttpHelper.wasRequestSuccessful(result));
    }

    @Test
    public void testHttpHelperWasUnSuccessful() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(HttpHelper.RESPONSE_CODE, 122);
        assertTrue(!HttpHelper.wasRequestSuccessful(result));
    }

    @Test
    public void testHttpHelperWasUnSuccessfulSmallNumber() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(HttpHelper.RESPONSE_CODE, 12);
        assertTrue(!HttpHelper.wasRequestSuccessful(result));
    }
}