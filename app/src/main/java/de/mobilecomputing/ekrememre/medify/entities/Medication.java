package de.mobilecomputing.ekrememre.medify.entities;

import android.icu.util.Calendar;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Medication implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    private final String name;
    private final String description;
    private final ArrayList<Integer> weekdays;
    private final Calendar timestamp;

    public Medication(String name, String description, ArrayList<Integer> weekdays, Calendar timestamp) {
        this.name = name;
        this.description = description;
        this.weekdays = weekdays;
        this.timestamp = timestamp;
    }

    public ArrayList<Calendar> getTimestamps() {
        ArrayList<Calendar> timestamps = new ArrayList<>();

        for (Integer weekday : weekdays) {
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

    private Medication(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        weekdays = in.readArrayList(null);
        timestamp = (Calendar) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(name);
        out.writeString(description);
        out.writeList(weekdays);
        out.writeSerializable(timestamp);
    }

    public static final Parcelable.Creator<Medication> CREATOR
            = new Parcelable.Creator<Medication>() {
        public Medication createFromParcel(Parcel in) {
            return new Medication(in);
        }

        public Medication[] newArray(int size) {
            return new Medication[size];
        }
    };
}
