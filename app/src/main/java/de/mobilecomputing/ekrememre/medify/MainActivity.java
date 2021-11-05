package de.mobilecomputing.ekrememre.medify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;

import de.mobilecomputing.ekrememre.medify.database.MedicationDatabase;

public class MainActivity extends AppCompatActivity {

    public MedicationDatabase medicationDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicationDatabase = Room.databaseBuilder(getApplicationContext(),
                MedicationDatabase.class, "medify-db").build();

    }
}