package de.mobilecomputing.ekrememre.medify.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

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

    public void insert(Medication medication, List<AlertTimestamp> alertTimestamps) {
        medicationRepository.insert(medication, alertTimestamps);
    }
}
