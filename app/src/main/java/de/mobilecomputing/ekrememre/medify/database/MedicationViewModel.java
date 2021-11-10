package de.mobilecomputing.ekrememre.medify.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import de.mobilecomputing.ekrememre.medify.entities.MedicationWithAlertTimestamps;

public class MedicationViewModel extends AndroidViewModel {
    private final MedicationRepository medicationRepository;

    public MedicationViewModel(@NonNull Application application) {
        super(application);
        medicationRepository = new MedicationRepository(application);
    }

    public void insertMedicationWithAlertTimestamps(MedicationWithAlertTimestamps medicationWithAlertTimestamps) {
        medicationRepository.insert(medicationWithAlertTimestamps);
    }
}
