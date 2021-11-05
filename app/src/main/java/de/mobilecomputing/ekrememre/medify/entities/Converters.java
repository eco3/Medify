package de.mobilecomputing.ekrememre.medify.entities;

import android.icu.util.Calendar;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<Integer> toArrayList(String arrayListString) {
        Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();

        return new Gson().fromJson(arrayListString, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Integer> arrayList) {
        Gson gson = new Gson();

        return gson.toJson(arrayList);
    }

    @TypeConverter
    public static String fromCalendar(Calendar calendar) {
        Gson gson = new Gson();

        return gson.toJson(calendar);
    }

    @TypeConverter
    public static Calendar toCalendar(String calendarString) {
        Type calendarType = new TypeToken<Calendar>() {}.getType();

        return new Gson().fromJson(calendarString, calendarType);
    }
}
