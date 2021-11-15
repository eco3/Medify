package de.mobilecomputing.ekrememre.medify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import de.mobilecomputing.ekrememre.medify.database.MedicationDatabase;
import de.mobilecomputing.ekrememre.medify.entities.Medication;
import de.mobilecomputing.ekrememre.medify.recyclerviews.AlertsViewAdapter;
import de.mobilecomputing.ekrememre.medify.recyclerviews.MedicationsViewAdapter;
import de.mobilecomputing.ekrememre.medify.viewmodels.MedicationViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String REQUEST_CODE_EXTRA = "REQUEST_CODE";
    public static final int ADD_MEDICATION_REQUEST_CODE = 0;
    public static final int EDIT_MEDICATION_REQUEST_CODE = 1;

    private MedicationViewModel medicationViewModel;

    private RecyclerView mmedicationRecyclerview;
    private MedicationsViewAdapter mmedicationsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicationViewModel = new ViewModelProvider(this).get(MedicationViewModel.class);

        mmedicationsViewAdapter = new MedicationsViewAdapter();

        mmedicationRecyclerview = findViewById(R.id.medication_recycler_view);
        mmedicationRecyclerview.setHasFixedSize(true);
        mmedicationRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mmedicationRecyclerview.setAdapter(mmedicationsViewAdapter);

        medicationViewModel.getAllMedications().observe(this, medications -> {
            Log.d(TAG, "number of medications: " + medications.size());

            mmedicationsViewAdapter.updateUserList(medications);
        });
    }

    public void onClickAddMedication(View view) {
        Intent intent = new Intent(this, MedicationEditActivity.class);
        intent.putExtra(REQUEST_CODE_EXTRA, ADD_MEDICATION_REQUEST_CODE);

        //noinspection deprecation
        startActivityForResult(intent, ADD_MEDICATION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_MEDICATION_REQUEST_CODE) {
                Log.d(TAG, "onActivityResult: ");
            }
        }
    }
}