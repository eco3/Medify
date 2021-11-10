package de.mobilecomputing.ekrememre.medify.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medication")
public class Medication implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public long medicationId;

    private final String name;
    private final String description;

    public Medication(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getMedicationId() {
        return medicationId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    private Medication(Parcel in) {
        medicationId = in.readLong();
        name = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(medicationId);
        out.writeString(name);
        out.writeString(description);
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
