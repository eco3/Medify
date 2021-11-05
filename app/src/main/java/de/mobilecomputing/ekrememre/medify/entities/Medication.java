package de.mobilecomputing.ekrememre.medify.entities;

import android.icu.util.Calendar;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Medication {
    @PrimaryKey
    private final long id;

    private final String name;
    private final String description;
    private final ArrayList<Integer> weekdays;
    private final Calendar calendar;

    public Medication(long id, String name, String description,
                      ArrayList<Integer> weekdays, Calendar calendar) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weekdays = weekdays;
        this.calendar = calendar;
    }

    public ArrayList<Calendar> getCalendars() {
        ArrayList<Calendar> calendars = new ArrayList<>();

        for(Integer weekday : weekdays) {
            Calendar tempCalendar = (Calendar) calendar.clone();
            tempCalendar.set(Calendar.DAY_OF_WEEK, weekday);

            calendars.add(tempCalendar);
        }

        return calendars;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
