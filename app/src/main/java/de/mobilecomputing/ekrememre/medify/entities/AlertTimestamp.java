package de.mobilecomputing.ekrememre.medify.entities;

import android.icu.util.Calendar;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "alert_timestamp")
public class AlertTimestamp implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public long alertTimestampId;
    public long medicationParentId;

    public ArrayList<Long> timestamps;

    public AlertTimestamp() {
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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(alertTimestampId);
        out.writeLong(medicationParentId);
        out.writeList(timestamps);
    }

    public static final Parcelable.Creator<AlertTimestamp> CREATOR
            = new Parcelable.Creator<AlertTimestamp>() {
        public AlertTimestamp createFromParcel(Parcel in) {
            return new AlertTimestamp(in);
        }

        public AlertTimestamp[] newArray(int size) {
            return new AlertTimestamp[size];
        }
    };

    private AlertTimestamp(Parcel in) {
        alertTimestampId = in.readLong();
        medicationParentId = in.readLong();
        timestamps = in.readArrayList(null);
    }
}
