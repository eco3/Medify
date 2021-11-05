package de.mobilecomputing.ekrememre.medify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;

import de.mobilecomputing.ekrememre.medify.database.MedicationDatabase;

public class MainActivity extends AppCompatActivity {
    public static final String REQUEST_CODE_EXTRA = "REQUEST_CODE";
    public static final int ADD_MEDICATION_REQUEST_CODE = 0;
    public static final int EDIT_MEDICATION_REQUEST_CODE = 1;

    public MedicationDatabase medicationDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicationDatabase = Room.databaseBuilder(getApplicationContext(),
                MedicationDatabase.class, "medify-db").build();
    }

    public void onClickAddMedication(View view) {
        Intent intent = new Intent(this, MedicationEditActivity.class);
        intent.putExtra(REQUEST_CODE_EXTRA, ADD_MEDICATION_REQUEST_CODE);

        //noinspection deprecation
        startActivityForResult(intent, ADD_MEDICATION_REQUEST_CODE);
    }
}