package com.example.giftexchanger;

import com.example.giftexchanger.Exchanger.GiftExchanger;
import com.example.giftexchanger.Exchanger.Utils.JSONHelper;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

public class GiftExchangerTests {
    private GiftExchanger exchanger;

    @Before
    public void setup() {
        JSONHelper.FILE_PATH = (new File("C:/Users/User/Documents/CS 3200/GiftExchanger"));
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Derek", "Aaron", "Brian", "Jason", "Philip", "Corey"));
        exchanger = new GiftExchanger(list);
    }

    @Test
    public void exchangeWithoutPreviousTest() {
        ArrayList<String> test = new ArrayList<>(exchanger.getParticipants());
        String assigns;
        assertEquals(2016, exchanger.getYear());
        assertEquals(test.size(), exchanger.getNumParticipants());
        assertEquals(6, exchanger.getNumParticipants());

        assigns = exchanger.exchange();
        test = new ArrayList<>(exchanger.getParticipants());
        assertEquals(0, test.size());
        assertTrue(assigns.contains("Derek ->"));
        assertTrue(assigns.contains("Aaron ->"));
        assertTrue(assigns.contains("Brian ->"));
        assertTrue(assigns.contains("Jason ->"));
        assertTrue(assigns.contains("Philip ->"));
        assertTrue(assigns.contains("Corey ->"));

        assertTrue(assigns.contains("-> Derek"));
        assertTrue(assigns.contains("-> Aaron"));
        assertTrue(assigns.contains("-> Brian"));
        assertTrue(assigns.contains("-> Jason"));
        assertTrue(assigns.contains("-> Philip"));
        assertTrue(assigns.contains("-> Corey"));
    }

    @Test
    public void exchangeWithPreviousTest() {
        assertTrue(exchanger.getPrevious().size() > 0);
        String[] assigns = exchanger.exchange().split("\n");
        Map<String, String> year1, year2;

        year1 = exchanger.getPrevious().get(2002);
        year2 = exchanger.getPrevious().get(2016);

        for (String assign : assigns) {
            String[] people = assign.split(" -> ");
            assertFalse(year1.get(people[1]).equals(people[2]));
            assertFalse(year2.get(people[1]).equals(people[2]));
        }
    }
}