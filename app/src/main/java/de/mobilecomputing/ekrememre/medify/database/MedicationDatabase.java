package de.mobilecomputing.ekrememre.medify.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import de.mobilecomputing.ekrememre.medify.entities.Converters;
import de.mobilecomputing.ekrememre.medify.entities.Medication;

@Database(entities = {Medication.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MedicationDatabase extends RoomDatabase {
    public abstract MedicationDao medicationDao();
}
