package de.mobilecomputing.ekrememre.medify.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MedicationWithAlertTimestamps {
    @Embedded
    private Medication medication;

    @Relation(
        parentColumn = "medicationId",
        entityColumn = "medicationParentId"
    )
    private List<AlertTimestamp> alertTimestamps;

    public MedicationWithAlertTimestamps(Medication medication, List<AlertTimestamp> alertTimestamps) {
        this.medication = medication;
        this.alertTimestamps = alertTimestamps;
    }

    public Medication getMedication() {
        return medication;
    }

    public List<AlertTimestamp> getAlertTimestamps() {
        return alertTimestamps;
    }
}
