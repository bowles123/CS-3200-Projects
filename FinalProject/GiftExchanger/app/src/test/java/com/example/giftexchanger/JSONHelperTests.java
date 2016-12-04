package com.example.giftexchanger;

import com.example.giftexchanger.Exchanger.Utils.JSONHelper;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by User on 12/1/2016.
 */

public class JSONHelperTests {
    @Before
    public void setup() {
        JSONHelper.FILE_PATH = new File("C:/Users/User/Documents/CS 3200/GiftExchanger");
    }

    @Test
    public void saveAssignmentsTest() {
        ArrayList<String> assignments = new ArrayList<>();
        assignments.add("Derek -> Aaron.");
        assignments.add("Aaron -> Brian.");
        assignments.add("Brian -> Derek.");

        assertTrue(JSONHelper.WriteOutAssignments(assignments, 2002));
    }

    @Test
    public void getPreviousAssignmentsTest() {
        Map<String, Map<String, String>> previousAssignments = JSONHelper.ReadInPrevious();
        Map<String, String> assignments;

        assertNotNull(previousAssignments);
        assertEquals(1, previousAssignments.size());
        assignments = previousAssignments.get("2002");
        assertNotNull(assignments);
        assertEquals(3, assignments.size());

        assertEquals("Brian", assignments.get("Aaron"));
        assertEquals("Derek", assignments.get("Brian"));
        assertEquals("Aaron", assignments.get("Derek"));
    }
}
