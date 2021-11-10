package de.mobilecomputing.ekrememre.medify.database;

import android.app.Application;
import android.os.AsyncTask;

import de.mobilecomputing.ekrememre.medify.entities.AlertTimestamp;
import de.mobilecomputing.ekrememre.medify.entities.MedicationWithAlertTimestamps;

public class MedicationRepository {
    private MedicationDao medicationDao;

    public MedicationRepository(Application application) {
        MedicationDatabase medicationDatabase = MedicationDatabase.getDatabase(application);
        medicationDao = medicationDatabase.medicationDao();
    }

    public void insert(MedicationWithAlertTimestamps medicationWithAlertTimestamps) {
        new insertAsync(medicationDao);
    }

    private static class insertAsync extends AsyncTask<MedicationWithAlertTimestamps, Void, Void> {
        private MedicationDao medicationDaoAsync;

        insertAsync(MedicationDao medicationDao) {
            medicationDaoAsync = medicationDao;
        }

        @Override
        protected Void doInBackground(MedicationWithAlertTimestamps... medicationWithAlertTimestamps) {
            long medicationId = medicationDaoAsync.insertMedication(medicationWithAlertTimestamps[0].medication);

            for (AlertTimestamp alertTimestamp : medicationWithAlertTimestamps[0].alertTimestamps) {
                alertTimestamp.medicationParentId = medicationId;
            }

            medicationDaoAsync.insertAlertTimestamps(medicationWithAlertTimestamps[0].alertTimestamps);

            return null;
        }
    }
}
