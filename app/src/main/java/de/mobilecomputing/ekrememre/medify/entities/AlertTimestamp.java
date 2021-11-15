package de.mobilecomputing.ekrememre.medify.entities;

import android.icu.util.Calendar;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "alert_timestamp")
public class AlertTimestamp {
    @PrimaryKey(autoGenerate = true)
    public long alertTimestampId;
    public long medicationParentId;

    private ArrayList<Long> timestamps;

    public AlertTimestamp(ArrayList<Long> timestamps) {
        this.timestamps = timestamps;
    }

    public AlertTimestamp(Integer hour, Integer minute, List<Integer> weekdays) {
        timestamps = new ArrayList<>();

        for (Integer weekday : weekdays) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.DAY_OF_WEEK, weekday);

            timestamps.add(calendar.getTimeInMillis());
        }
    }

    public ArrayList<Long> getTimestamps() {
        return timestamps;
    }

    public ArrayList<Calendar> getCalendars() {
        ArrayList<Calendar> calendars = new ArrayList<>();

        for (Long timestamp : timestamps) {
            Calendar tmpCalendar = Calendar.getInstance();
            Calendar calendar = Calendar.getInstance();

            tmpCalendar.setTimeInMillis(timestamp);
            calendar.set(Calendar.HOUR_OF_DAY, tmpCalendar.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, tmpCalendar.get(Calendar.MINUTE));
            calendar.set(Calendar.DAY_OF_WEEK, tmpCalendar.get(Calendar.DAY_OF_WEEK));

            calendars.add(calendar);
        }

        calendars.sort((item1, item2) -> {
            if (item1.equals(item2)) {
                return 0;
            }
            if (item1.before(item2)) {
                return -1;
            } else {
                return 1;
            }
        });

        return calendars;
    }
}
