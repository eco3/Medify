package de.mobilecomputing.ekrememre.medify.entities;

import android.icu.util.Calendar;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "medication")
public class Medication implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    private final String name;
    private final String description;
    private final ArrayList<Long> timestamps;

    public Medication(String name, String description, ArrayList<Long> timestamps) {
        this.name = name;
        this.description = description;
        this.timestamps = timestamps;
    }

    public ArrayList<Calendar> getCalendarTimestamps() {
        ArrayList<Calendar> calendarTimestamps = new ArrayList<>();

        for (Long timestamp : timestamps) {
            Calendar calendarTimestamp = Calendar.getInstance();
            calendarTimestamp.setTimeInMillis(timestamp);
            calendarTimestamps.add(calendarTimestamp);
        }

        return calendarTimestamps;
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

    public ArrayList<Long> getTimestamps() {
        return timestamps;
    }

    private Medication(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        timestamps = in.readArrayList(null);
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
        out.writeList(timestamps);
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
