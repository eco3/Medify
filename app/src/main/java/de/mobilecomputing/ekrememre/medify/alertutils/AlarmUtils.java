package de.mobilecomputing.ekrememre.medify.alertutils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import de.mobilecomputing.ekrememre.medify.R;
import de.mobilecomputing.ekrememre.medify.entities.Medication;

public class AlarmUtils {
    private static final String TAG = "AlarmUtils";

    public static final String CHANNEL_ID = "MEDIFY_CHANNEL";
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;

    public static PendingIntent createPendingIntent(Context context, Medication medication, long timestamp) {
        Intent intent = new Intent(context, MedicationAlertReceiver.class);
        int uniqueID = Math.abs((int)timestamp);
        int flag = PendingIntent.FLAG_IMMUTABLE;

        Log.d(TAG, "createPendingIntent: timestamp: " +  timestamp + " - uniqueID: " + uniqueID);

        if (medication == null) {
            // If we are canceling an alarm.
            flag = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
        } else if(medication.medicationId > 0)  {
            // If we are updating an alarm.
            flag = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
            intent.putExtra(MedicationAlertReceiver.EXTRA_NOTIFICATION_ID, uniqueID);
            intent.putExtra(MedicationAlertReceiver.EXTRA_NOTIFICATION, getNotification(context, medication, timestamp));
        } else {
            // If we are creating an alarm.
            intent.putExtra(MedicationAlertReceiver.EXTRA_NOTIFICATION_ID, uniqueID);
            intent.putExtra(MedicationAlertReceiver.EXTRA_NOTIFICATION, getNotification(context, medication, timestamp));
        }

        return PendingIntent.getBroadcast(context, uniqueID, intent, flag);
    }

    private static Notification getNotification (Context context, Medication medication, long timestamp) {
        // TODO: REMOVE
            Calendar tmpCalendar = Calendar.getInstance();
            tmpCalendar.setTimeInMillis(timestamp);
            String tmpString = " - " + String.format("%02d:%02d", tmpCalendar.get(Calendar.HOUR_OF_DAY), tmpCalendar.get(Calendar.MINUTE));
        // TODO: REMOVE

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_pill)
            .setContentTitle(context.getString(R.string.reminder_text, medication.getName()))
            .setContentText(medication.getDescription() + tmpString)
            .setAutoCancel(true)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER);

        return builder.build();
    }
}