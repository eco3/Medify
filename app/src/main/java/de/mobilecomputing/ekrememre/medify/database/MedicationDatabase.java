package de.mobilecomputing.ekrememre.medify.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.mobilecomputing.ekrememre.medify.entities.AlertTimestamp;
import de.mobilecomputing.ekrememre.medify.entities.Converters;
import de.mobilecomputing.ekrememre.medify.entities.Medication;

@Database(entities = {Medication.class, AlertTimestamp.class}, version = 4)
@TypeConverters({Converters.class})
public abstract class MedicationDatabase extends RoomDatabase {
    public abstract MedicationDao medicationDao();

    private static volatile MedicationDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MedicationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MedicationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MedicationDatabase.class, "medify-db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
