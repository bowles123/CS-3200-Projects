package com.example.giftexchanger;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by User on 11/29/2016.
 */

public class GiftExchanger {
    private ArrayList<String> participants;
    private ArrayList<String> assignments;
    private int numParticipants;

    public ArrayList<String> getParticipants() { return participants; }
    public int getNumParticipants() { return numParticipants; }

    public GiftExchanger(ArrayList<String> people) {
        numParticipants = people.size();
        participants = people;
        assignments = new ArrayList<>();
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
        }
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
        return exchanges;
    }
}
