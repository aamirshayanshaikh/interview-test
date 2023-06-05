package com.smallworld;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonUtil {

    public static List<Transaction> processJson()  {
        Gson gson = new Gson();
        FileReader reader = null;
        try {
            reader = new FileReader("transactions.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (reader != null) {
            Type listType = new TypeToken<ArrayList<Transaction>>(){}.getType();
            List<Transaction> transactionList = new Gson().fromJson(reader, listType);
            return transactionList;
        }

        return Collections.emptyList();
    }
}
