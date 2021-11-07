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
    private final Calendar timestamp;

    public Medication(long id, String name, String description,
                      ArrayList<Integer> weekdays, Calendar timestamp) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weekdays = weekdays;
        this.timestamp = timestamp;
    }

    public ArrayList<Calendar> getTimestamps() {
        ArrayList<Calendar> timestamps = new ArrayList<>();

        for(Integer weekday : weekdays) {
            Calendar tempTimestamp = (Calendar) timestamp.clone();
            tempTimestamp.set(Calendar.DAY_OF_WEEK, weekday);

            timestamps.add(tempTimestamp);
        }

        return timestamps;
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

    public ArrayList<Integer> getWeekdays() {
        return weekdays;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }
}
