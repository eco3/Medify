package de.mobilecomputing.ekrememre.medify.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import de.mobilecomputing.ekrememre.medify.database.MedicationRepository;
import de.mobilecomputing.ekrememre.medify.entities.AlertTimestamp;
import de.mobilecomputing.ekrememre.medify.entities.Medication;
import de.mobilecomputing.ekrememre.medify.entities.MedicationWithAlertTimestamps;

public class MedicationViewModel extends AndroidViewModel {
    private MedicationRepository medicationRepository;

    private final LiveData<List<MedicationWithAlertTimestamps>> medications;

    public MedicationViewModel(Application application) {
        super(application);
        medicationRepository = new MedicationRepository(application);
        medications = medicationRepository.getAllMedications();
    }

    public LiveData<List<MedicationWithAlertTimestamps>> getAllMedications() {
        return medications;
    }

    public MedicationWithAlertTimestamps getMedication(long id) throws ExecutionException, InterruptedException {
        return medicationRepository.getMedication(id);
    }

    public void insert(Medication medication, List<AlertTimestamp> alertTimestamps) {
        medicationRepository.insert(medication, alertTimestamps);
    }

    public void update(Medication medication, List<AlertTimestamp> alertTimestamps) {
        medicationRepository.update(medication, alertTimestamps);
    }

    public void removeAlertTimestamp(AlertTimestamp alertTimestamp) {
        medicationRepository.removeAlertTimestamp(alertTimestamp);
    }

    public void insertAlertTimestamp(AlertTimestamp alertTimestamp) {
        medicationRepository.insertAlertTimestamp(alertTimestamp);
    }
}
