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

/**
 * Created by User on 12/1/2016.
 */

public class JSONHelper {
    private static final String FILE_NAME = "PreviousParticipants.txt";
    private static final File FILE_PATH = Environment.getExternalStorageDirectory();
    private static final Gson GSON = new Gson();

    public static HashMap<String, HashMap<String, String>> ReadInPrevious() {
        HashMap<String, HashMap<String, String>> map = null;

        try {
            String line, jsonString = "";
            Reader reader = new FileReader(FILE_PATH + "/" + FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(reader);

            while ((line = bufferedReader.readLine()) != null) {
                jsonString += line;
            }
            map = GSON.fromJson(jsonString, HashMap.class);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static boolean WriteOutAssignments(ArrayList<String> assigns, int year) {
        HashMap<String, HashMap<String, String>> finalMap = new HashMap<>();
        HashMap<String, String> assignments = new HashMap<>();
        FileWriter fileWriter;

        for (String assign : assigns) {
            String[] people = assign.split(" -> ");
            people[1] = people[1].substring(0, people[1].length() - 1);
            assignments.put(people[0], people[1]);
        }

        finalMap.put(Integer.toString(year), assignments);
        String jsonString = GSON.toJson(assignments);

        try {
            fileWriter = new FileWriter(FILE_PATH + "/" + FILE_NAME, true);
            fileWriter.write(jsonString);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
