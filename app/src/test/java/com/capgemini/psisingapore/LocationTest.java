package com.capgemini.psisingapore;

import com.capgemini.psisingapore.data.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

public class LocationTest {

    private Location location;
    @Before
    public void setUp() throws Exception {
        location = new Location("East", 10, 100);
    }

    @After
    public void tearDown() throws Exception {
        location = null;
    }

    @Test
    public void getName() throws Exception {
        assertNotNull(location.getName());
        assertEquals("East", location.getName());
    }

    @Test
    public void getLatitude() throws Exception {
        assertNotNull(location.getLatitude());
        assertEquals(10.0, location.getLatitude());
    }
    @Test
    public void getLatitudeTest() throws Exception {
        assertNotNull(location.getLatitude());
        assertNotSame(104, location.getLatitude());
    }

    @Test
    public void getLongitude() throws Exception {
        assertNotNull(location.getLongitude());
        assertEquals(100.0, location.getLongitude());
    }

    @Test
    public void getLongitudeTestWithWrongValue() throws Exception {
        assertNotNull(location.getLongitude());
        assertNotSame(1, location.getLongitude());
    }

    @Test
    public void getAppInfo() throws Exception {
        location.setAppInfo("healthy");
        assertNotNull(location.getAppInfo());
        assertEquals("healthy", location.getAppInfo());
    }

    @Test
    public void getAppInfoTestWithWrongValue() throws Exception {
        location.setAppInfo("heal");
        assertNotNull(location.getAppInfo());
        assertNotSame("healthy", location.getAppInfo());
    }

    @Test
    public void getNo2OnehourMax() {
        location.setNo2OnehourMax("key", 12);
        HashMap<String, Integer> mapno2 = new HashMap<>();
        mapno2.put("key", 12);
        assertEquals(mapno2, location.getNo2OnehourMax());
    }

    @Test

    public void getNo2OnehourMaxWithWrongVal() {
        location.setNo2OnehourMax("key", 19);
        HashMap<String, Integer> mapno2 = new HashMap<>();
        mapno2.put("key", 12);
        assertNotSame(mapno2, location.getNo2OnehourMax());
    }

    @Test
    public void getO3EightHourMax() {
        location.setO3EightHourMax("key", 11);
        HashMap<String, Integer> mapno2 = new HashMap<>();
        mapno2.put("key", 11);
        assertEquals(mapno2, location.getO3EightHourMax());
    }

    @Test
    public void getO3EightHourMaxWithWrongVal() {
        location.setO3EightHourMax("key", 9);
        HashMap<String, Integer> mapno2 = new HashMap<>();
        mapno2.put("key", 11);
        assertNotSame(mapno2, location.getO3EightHourMax());
    }

    @Test
    public void getPsiTwentyFourHourly() {
        location.setPsiTwentyFourHourly("key", 9);
        HashMap<String, Integer> mapno2 = new HashMap<>();
        mapno2.put("key", 9);
        assertEquals(mapno2, location.getPsiTwentyFourHourly());
    }

    @Test
    public void getPsiTwentyFourHourlyWithWrongVal() {
        location.setPsiTwentyFourHourly("key", 7);
        HashMap<String, Integer> mapno2 = new HashMap<>();
        mapno2.put("key", 9);
        assertNotSame(mapno2, location.getPsiTwentyFourHourly());
    }

    @Test
    public void getSo2TwentyFourHourly() {
        location.setSo2TwentyFourHourly("key", 9);
        HashMap<String, Integer> mapno2 = new HashMap<>();
        mapno2.put("key", 9);
        assertEquals(mapno2, location.getSo2TwentyFourHourly());
    }

    @Test
    public void getSo2TwentyFourHourlyWithWrongVal() {
        location.setSo2TwentyFourHourly("key", 7);
        HashMap<String, Integer> mapno2 = new HashMap<>();
        mapno2.put("key", 9);
        assertNotSame(mapno2, location.getSo2TwentyFourHourly());
    }

}
