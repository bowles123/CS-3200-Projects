package com.example.giftexchanger;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class GiftExchangerTest {
    private GiftExchanger exchanger;

    @Before
    public void setup() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Derek", "Aaron", "Brian", "Jason", "Philip", "Corey"));
        exchanger = new GiftExchanger(list);
    }

    @Test
    public void exchangeTest() {
        ArrayList<String> test = new ArrayList<>(exchanger.getParticipants());
        String assigns;
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
}