package de.mobilecomputing.ekrememre.medify.entities;

import android.icu.util.Calendar;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class MedicationWithAlertTimestamps {
    @Embedded
    private Medication medication;

    @Relation(
        parentColumn = "medicationId",
        entityColumn = "medicationParentId"
    )
    private List<AlertTimestamp> alertTimestamps;

    public MedicationWithAlertTimestamps(Medication medication, List<AlertTimestamp> alertTimestamps) {
        this.medication = medication;
        this.alertTimestamps = alertTimestamps;
    }

    public Medication getMedication() {
        return medication;
    }

    public List<AlertTimestamp> getAlertTimestamps() {
        return alertTimestamps;
    }

    public ArrayList<Calendar> getCalendars() {
        return getCalendars(alertTimestamps);
    }

    public Calendar getNextAlarm() {
        return getNextAlarm(alertTimestamps);
    }

    public static Calendar getNextAlarm(List<AlertTimestamp> alertTimestamps) {
        Calendar now = Calendar.getInstance();
        ArrayList<Calendar> alerts = getCalendars(alertTimestamps);

        for (Calendar alert : alerts) {
            if (now.before(alert)) {
                return alert;
            }
        }

        return alerts.get(0);
    }

    public static ArrayList<Calendar> getCalendars(List<AlertTimestamp> alertTimestamps) {
        ArrayList<Calendar> calendars = new ArrayList<>();

        for (AlertTimestamp alertTimestamp : alertTimestamps) {
            calendars.addAll(alertTimestamp.getCalendars());
        }

        calendars.sort((item1, item2) -> {
            if (item1.equals(item2)) {
                return 0;
            }
            if (item1.before(item2)) {
                return -1;
            } else {
                return 1;
            }
        });

        return calendars;
    }
}
