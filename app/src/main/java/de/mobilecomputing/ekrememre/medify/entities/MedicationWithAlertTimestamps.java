package de.mobilecomputing.ekrememre.medify.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MedicationWithAlertTimestamps {
    @Embedded
    public Medication medication;

    @Relation(
        parentColumn = "medicationId",
        entityColumn = "medicationParentId"
    )
    public List<AlertTimestamp> alertTimestamps;

    public MedicationWithAlertTimestamps() {
    }
}
