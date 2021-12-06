package de.mobilecomputing.ekrememre.medify.alertutils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import de.mobilecomputing.ekrememre.medify.R;

public class MedicationAlertReceiver extends BroadcastReceiver {
    private static final String TAG = "MedicationAlertReceiver";

    public static String EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID";
    public static String EXTRA_NOTIFICATION = "EXTRA_NOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(EXTRA_NOTIFICATION);
        int notificationID = intent.getIntExtra(EXTRA_NOTIFICATION_ID, -1);

        assert notificationManager != null;
        assert notification != null;
        assert notificationID != -1;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                AlarmUtils.NOTIFICATION_CHANNEL_ID,
                context.getResources().getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_HIGH
            );

            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(notificationID, notification);
    }
}