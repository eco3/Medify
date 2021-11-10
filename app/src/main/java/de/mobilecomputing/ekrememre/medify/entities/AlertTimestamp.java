package de.mobilecomputing.ekrememre.medify.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "alert_timestamp")
public class AlertTimestamp implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public long alertTimestampId;
    public long medicationParentId;

    public ArrayList<Long> timestamps;

    public AlertTimestamp() {
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
