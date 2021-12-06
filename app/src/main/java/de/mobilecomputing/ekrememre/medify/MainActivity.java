package de.mobilecomputing.ekrememre.medify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import de.mobilecomputing.ekrememre.medify.recyclerviews.MedicationsViewAdapter;
import de.mobilecomputing.ekrememre.medify.viewmodels.MedicationViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String MEDICATION_ID_EXTRA = "MEDICATION_ID_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView medicationRecyclerView = findViewById(R.id.medication_recycler_view);
        MedicationsViewAdapter medicationsViewAdapter = new MedicationsViewAdapter();
        MedicationViewModel medicationViewModel = new ViewModelProvider(this).get(MedicationViewModel.class);

        medicationRecyclerView.setHasFixedSize(true);
        medicationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        medicationRecyclerView.setAdapter(medicationsViewAdapter);

        medicationViewModel.getAllMedications().observe(this, medicationsViewAdapter::updateData);
    }

    public void onClickAddMedication(View view) {
        Intent intent = new Intent(this, MedicationEditActivity.class);
        startActivity(intent);
    }
}