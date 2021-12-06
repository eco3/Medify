package de.mobilecomputing.ekrememre.medify.viewmodels;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.icu.util.Calendar;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import de.mobilecomputing.ekrememre.medify.alertutils.AlarmUtils;
import de.mobilecomputing.ekrememre.medify.database.MedicationRepository;
import de.mobilecomputing.ekrememre.medify.entities.AlertTimestamp;
import de.mobilecomputing.ekrememre.medify.entities.Medication;
import de.mobilecomputing.ekrememre.medify.entities.MedicationWithAlertTimestamps;

public class MedicationViewModel extends AndroidViewModel {
    private static final String TAG = "MedicationViewModel";

    private AlarmManager alarmManager;

    private MedicationRepository medicationRepository;
    private final LiveData<List<MedicationWithAlertTimestamps>> medications;

    public MedicationViewModel(Application application) {
        super(application);
        medicationRepository = new MedicationRepository(application);
        medications = medicationRepository.getAllMedications();

        alarmManager = (AlarmManager)application.getSystemService(Context.ALARM_SERVICE);
    }

    public LiveData<List<MedicationWithAlertTimestamps>> getAllMedications() {
        return medications;
    }

    public MedicationWithAlertTimestamps getMedication(long id) throws ExecutionException, InterruptedException {
        return medicationRepository.getMedication(id);
    }

    public void insert(Medication medication, List<AlertTimestamp> alertTimestamps) {
        medicationRepository.insert(medication, alertTimestamps);

        for (Calendar calendar : MedicationWithAlertTimestamps.generateCalendars(alertTimestamps)) {
            PendingIntent pendingIntent = AlarmUtils.createPendingIntent(
                    getApplication().getApplicationContext(),
                    medication,
                    calendar.getTimeInMillis()
            );
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        }
    }

    public void update(Medication medication, List<AlertTimestamp> alertTimestamps) {
        medicationRepository.update(medication, alertTimestamps);
    }

    public void removeAlertTimestamp(AlertTimestamp alertTimestamp) {
        for (Calendar calendar : alertTimestamp.getCalendars()) {
            PendingIntent pendingIntent = AlarmUtils.createPendingIntent(
                    getApplication().getApplicationContext(),
                    null,
                    calendar.getTimeInMillis()
            );
            alarmManager.cancel(pendingIntent);
        }

        medicationRepository.removeAlertTimestamp(alertTimestamp);
    }

    public void insertAlertTimestamp(AlertTimestamp alertTimestamp) {
        medicationRepository.insertAlertTimestamp(alertTimestamp);
    }
}
