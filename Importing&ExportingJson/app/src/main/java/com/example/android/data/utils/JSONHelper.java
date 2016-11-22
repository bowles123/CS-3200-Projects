package com.example.android.data.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.android.data.model.DataItem;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class JSONHelper {

    private static final String FILE_NAME = "menuitems.json";
    private static final String TAG = "JSONHelper";

    public static boolean exportToJSON(Context context, List<DataItem> dataItemList) {
        DataItems menuData = new DataItems();
        menuData.setDataItems(dataItemList);

        Gson gson = new Gson();
        String jsonString = gson.toJson(menuData);
        Log.i(TAG, "exportToJSON: " + jsonString);

        FileOutputStream fileoutputStream = null;
        File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);

        try {
            fileoutputStream = new FileOutputStream(file);
            fileoutputStream.write(jsonString.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileoutputStream != null) {
                try {
                    fileoutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public static List<DataItem> importFromJSON(Context context) {
        DataItems menuData = new DataItems();
        Gson gson = new Gson();
        Log.i(TAG, "ImportFromJson: " + FILE_NAME);

        try {
            String line, jsonString = "";
            Reader reader = new FileReader(Environment.getExternalStorageDirectory() + "/" + FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(reader);

            while ((line = bufferedReader.readLine()) != null) {
                jsonString += line;
            }

            menuData = gson.fromJson(jsonString, DataItems.class);
            menuData.logData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return menuData.getDataItems();
    }


    static class DataItems {
        List<DataItem> dataItems;

        public List<DataItem> getDataItems() {
            return dataItems;
        }

        public void setDataItems(List<DataItem> dataItems) {
            this.dataItems = dataItems;
        }

        public void logData() {
            String log = "Imported: \n";
            int i = 1;

            for (DataItem item : dataItems) {
                log += i++ + ". " + item.getItemName() + ", " +
                        item.getPrice() + ", " + item.getItemId() + "\n";
            }
            Log.i(TAG, log);
        }
    }
}
