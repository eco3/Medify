package de.mobilecomputing.ekrememre.medify.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import de.mobilecomputing.ekrememre.medify.entities.AlertTimestamp;
import de.mobilecomputing.ekrememre.medify.entities.Medication;
import de.mobilecomputing.ekrememre.medify.entities.MedicationWithAlertTimestamps;

public class MedicationRepository {
    private MedicationDao medicationDao;
    private LiveData<List<MedicationWithAlertTimestamps>> medications;

    public MedicationRepository(Application application) {
        MedicationDatabase db = MedicationDatabase.getDatabase(application);
        medicationDao = db.medicationDao();
        medications = medicationDao.getMedicationsWithAlertTimestamps();
    }

    public LiveData<List<MedicationWithAlertTimestamps>> getAllMedications() {
        return medications;
    }

    public MedicationWithAlertTimestamps getMedication(long id) throws ExecutionException, InterruptedException {
        Future<MedicationWithAlertTimestamps> med = MedicationDatabase.databaseWriteExecutor.submit(
                () -> medicationDao.getMedicationWithAlertTimestamps(id)
        );

        return med.get();
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

    public void update(Medication medication, List<AlertTimestamp> alertTimestamps) {
        MedicationDatabase.databaseWriteExecutor.execute(() -> {
            medicationDao.updateMedication(medication);

            for (AlertTimestamp alertTimestamp : alertTimestamps) {
                alertTimestamp.medicationParentId = medication.medicationId;
            }
            medicationDao.insertAlertTimestamps(alertTimestamps);
        });
    }

    public void removeAlertTimestamp(AlertTimestamp alertTimestamp) {
        MedicationDatabase.databaseWriteExecutor.execute(() -> medicationDao.deleteAlertTimestamp(alertTimestamp));
    }

    public void insertAlertTimestamp(AlertTimestamp alertTimestamp) {
        MedicationDatabase.databaseWriteExecutor.execute(() -> medicationDao.insertAlertTimestamp(alertTimestamp));
    }
}
