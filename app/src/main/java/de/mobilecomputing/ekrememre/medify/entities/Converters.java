package de.mobilecomputing.ekrememre.medify.entities;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<Long> toArrayList(String arrayListString) {
        Type listType = new TypeToken<ArrayList<Long>>() {}.getType();

        return new Gson().fromJson(arrayListString, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Long> arrayList) {
        Gson gson = new Gson();

        return gson.toJson(arrayList);
    }
}
