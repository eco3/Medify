package de.mobilecomputing.ekrememre.medify.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.mobilecomputing.ekrememre.medify.entities.AlertTimestamp;
import de.mobilecomputing.ekrememre.medify.entities.Medication;
import de.mobilecomputing.ekrememre.medify.entities.MedicationWithAlertTimestamps;

public class MedicationRepository {
    private MedicationDao medicationDao;
    private LiveData<List<MedicationWithAlertTimestamps>> medications;

    public MedicationRepository(Application application) {
        MedicationDatabase db = MedicationDatabase.getDatabase(application);
        medicationDao = db.medicationDao();
        medications = medicationDao.getMedicationWithAlertTimestamps();
    }

    public LiveData<List<MedicationWithAlertTimestamps>> getAllMedications() {
        return medications;
    }

    public void insert(Medication medication, List<AlertTimestamp> alertTimestamps) {
        MedicationDatabase.databaseWriteExecutor.execute(() -> {
            long id = medicationDao.insertMedication(medication);

            for (AlertTimestamp alertTimestamp : alertTimestamps) {
                alertTimestamp.medicationParentId = id;
            }
            medicationDao.insertAlertTimestamps(alertTimestamps);
        });
    }
}
