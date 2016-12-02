package com.example.giftexchanger;

import com.example.giftexchanger.Exchanger.Utils.JSONHelper;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by User on 12/1/2016.
 */

public class JSONHelperTests {
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
        HashMap<String, HashMap<String, String>> previousAssignments = JSONHelper.ReadInPrevious();
        HashMap<String, String> assignments;

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
