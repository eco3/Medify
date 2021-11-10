package de.mobilecomputing.ekrememre.medify.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class MedicationWithAlertTimestamps implements Parcelable {
    @Embedded
    public Medication medication;

    @Relation(
        parentColumn = "medicationId",
        entityColumn = "medicationParentId"
    )
    public List<AlertTimestamp> alertTimestamps;

    public MedicationWithAlertTimestamps() {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(medication, flags);
        out.writeList(alertTimestamps);
    }

    public static final Parcelable.Creator<MedicationWithAlertTimestamps> CREATOR
            = new Parcelable.Creator<MedicationWithAlertTimestamps>() {
        public MedicationWithAlertTimestamps createFromParcel(Parcel in) {
            return new MedicationWithAlertTimestamps(in);
        }

        public MedicationWithAlertTimestamps[] newArray(int size) {
            return new MedicationWithAlertTimestamps[size];
        }
    };

    private MedicationWithAlertTimestamps(Parcel in) {
        medication = in.readParcelable(Medication.class.getClassLoader());
        alertTimestamps = in.readArrayList(null);
    }
}
