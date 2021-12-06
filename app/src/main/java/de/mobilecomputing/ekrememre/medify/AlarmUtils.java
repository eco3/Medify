package de.mobilecomputing.ekrememre.medify;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;

import androidx.core.app.NotificationCompat;

import de.mobilecomputing.ekrememre.medify.entities.Medication;

public class AlarmUtils {
    public static final String CHANNEL_ID = "MEDIFY_CHANNEL";
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;

    public static PendingIntent createPendingIntent(Context context, Medication medication, long timestamp) {
        Intent intent = new Intent(context, MedicationAlertReceiver.class);

        int uniqueID = Math.abs((int)timestamp);

        intent.putExtra(MedicationAlertReceiver.EXTRA_NOTIFICATION_ID, uniqueID);
        intent.putExtra(MedicationAlertReceiver.EXTRA_NOTIFICATION, getNotification(context, medication, timestamp));

        return PendingIntent.getBroadcast(context, uniqueID, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    private static Notification getNotification (Context context, Medication medication, long timestamp) {
        // TODO: REMOVE
            Calendar tmpCalendar = Calendar.getInstance();
            tmpCalendar.setTimeInMillis(timestamp);
            String tmpString = " - " + tmpCalendar.get(Calendar.HOUR_OF_DAY) + ":" + tmpCalendar.get(Calendar.MINUTE);
        // TODO: REMOVE

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(medication.getName() + tmpString)
            .setContentText(medication.getDescription() + " (" + (int)timestamp + ") | (" + Math.abs((int)timestamp) + ")")
            .setAutoCancel(true)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
        ;

        return builder.build();
    }
}