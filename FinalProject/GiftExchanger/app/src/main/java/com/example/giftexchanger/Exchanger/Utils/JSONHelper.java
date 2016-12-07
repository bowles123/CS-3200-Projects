package com.example.giftexchanger.Exchanger.Utils;

import android.os.Environment;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 12/1/2016.
 */

public class JSONHelper {
    private static final String FILE_NAME = "PreviousParticipants.json";
    public static File FILE_PATH = Environment.getExternalStorageDirectory();
    private static Map<String, Map<String, String>> previous;
    private static final Gson GSON = new Gson();
    private static final int MAX_MAP_SIZE = 3;

    public static Map<String, Map<String, String>> ReadInPrevious() {
        Map<String, Map<String, String>> map = null;

        try {
            String line, jsonString = "";
            Reader reader = new FileReader(FILE_PATH + "/" + FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(reader);

            while ((line = bufferedReader.readLine()) != null) {
                jsonString += line;
            }
            map = GSON.fromJson(jsonString, Map.class);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        previous = map;
        return map;
    }

    public static boolean WriteOutAssignments(ArrayList<String> assigns, int year) {
        HashMap<String, String> assignments = new HashMap<>();
        FileWriter fileWriter;

        if (previous == null) {
            previous = new HashMap<>();
        } else if (previous.size() == MAX_MAP_SIZE) {
            previous.remove(year - MAX_MAP_SIZE);
        }

        for (String assign : assigns) {
            String[] people = assign.split(" -> ");
            people[1] = people[1].substring(0, people[1].length() - 1);
            assignments.put(people[0], people[1]);
        }

        previous.put(Integer.toString(year), assignments);
        String jsonString = GSON.toJson(previous);

        try {
            fileWriter = new FileWriter(FILE_PATH + "/" + FILE_NAME);
            fileWriter.write(jsonString);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
