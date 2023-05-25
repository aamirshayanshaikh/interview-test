package com.smallworld;


import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonUtil {

    public static Transaction[]  processJson()  {
        Gson gson = new Gson();
        FileReader reader = null;
        try {
            reader = new FileReader("transactions.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (reader != null) {
            return gson.fromJson(reader, Transaction[].class);
        }

        return new Transaction[0];
    }
}
