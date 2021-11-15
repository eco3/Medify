package de.mobilecomputing.ekrememre.medify.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import de.mobilecomputing.ekrememre.medify.entities.AlertTimestamp;
import de.mobilecomputing.ekrememre.medify.entities.Medication;
import de.mobilecomputing.ekrememre.medify.entities.MedicationWithAlertTimestamps;

@Dao
public interface MedicationDao {
    @Transaction
    @Query("SELECT * FROM medication")
    LiveData<List<MedicationWithAlertTimestamps>> getMedicationWithAlertTimestamps();

    @Transaction
    @Insert
    long insertMedication(Medication medication);

    @Transaction
    @Insert
    void insertAlertTimestamps(List<AlertTimestamp> alertTimestamps);
}
