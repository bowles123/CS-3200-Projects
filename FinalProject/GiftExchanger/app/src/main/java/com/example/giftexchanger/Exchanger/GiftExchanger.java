package com.example.giftexchanger.Exchanger;

import com.example.giftexchanger.Exchanger.Utils.JSONHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

/**
 * Created by User on 11/29/2016.
 */

public class GiftExchanger {
    private ArrayList<String> participants;
    private ArrayList<String> assignments;
    private int numParticipants;
    private int year;
    private Map<String, Map<String, String>> previousAssignments;

    private void saveAssignments() { JSONHelper.WriteOutAssignments(assignments, year); }
    private void getPreviousAssignments() { previousAssignments = JSONHelper.ReadInPrevious(); }

    public Map<String, Map<String, String>> getPrevious() { return previousAssignments; }
    public ArrayList<String> getParticipants() { return participants; }
    public int getNumParticipants() { return numParticipants; }
    public int getYear() { return year; }

    public GiftExchanger(ArrayList<String> people) {
        year = Calendar.getInstance().get(Calendar.YEAR);
        numParticipants = people.size();
        participants = people;
        assignments = new ArrayList<>();
        getPreviousAssignments();
    }

    private void shuffle() {
        Random rand = new Random();
        int n = participants.size();

        while(n > 1) {
            n--;
            int k = rand.nextInt(n + 1);
            String value = participants.get(k);
            participants.set(k, participants.get(n));
            participants.set(n, value);
        }
    }

    private void assign(ArrayList<String> people) {
        int done = 0;
        String one, two;
        ArrayList<String> tempParticipants = new ArrayList<>(participants);

        while(participants.size() > 0 && done <= numParticipants) {
            one = participants.get(0);
            participants.remove(one);
            two = people.get(0);
            people.remove(two);

            while (one == two)
            {
                people.add(two);
                two = people.get(0);
                people.remove(two);
            }
            assignments.add(one + " -> " + two + ".");
            done++;

            if (participants.size() == 0) {
                for (String assignment : assignments) {
                    String[] ppl = assignment.split(" -> ");
                    if (!checkValid(ppl[0], ppl[1].replace(".", ""))) {
                        participants = new ArrayList<>(tempParticipants);
                        assignments = new ArrayList<>();
                        done = 0;
                        break;
                    }
                }
            }
        }
    }

    private boolean checkValid(String person1, String person2) {
        if (previousAssignments == null) return true;

        int yearAgo = year - 1, twoAgo = year - 2, threeAgo = year - 3;
        Map<String, String> last = previousAssignments.get(yearAgo), two = previousAssignments.get(twoAgo),
                three = previousAssignments.get(threeAgo);

        if ((last == null || !last.get(person1).equals(person2)) && (two == null || !two.get(person1).equals(person2))
                && (three == null || !three.get(person1).equals(person2))) {
            return true;
        }
        return false;
    }

    public String exchange() {
        ArrayList<String> people = new ArrayList<>(participants);
        String exchanges = "";

        for (int i = 0; i < participants.size(); i++) {
            people.set(i, participants.get(i));
        }

        shuffle();
        assign(people);

        for (String exchange : assignments) {
            exchanges += exchange + "\n";
        }

        saveAssignments();
        return exchanges;
    }
}
