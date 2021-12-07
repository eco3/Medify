package de.mobilecomputing.ekrememre.medify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import de.mobilecomputing.ekrememre.medify.entities.MedicationWithAlertTimestamps;
import de.mobilecomputing.ekrememre.medify.recyclerviews.MedicationsViewAdapter;
import de.mobilecomputing.ekrememre.medify.viewmodels.MedicationViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String MEDICATION_ID_EXTRA = "MEDICATION_ID_EXTRA";

    private MedicationsViewAdapter medicationsViewAdapter;
    private MedicationViewModel medicationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicationsViewAdapter = new MedicationsViewAdapter();
        medicationViewModel = new ViewModelProvider(this).get(MedicationViewModel.class);
        RecyclerView medicationRecyclerView = findViewById(R.id.medication_recycler_view);

        medicationRecyclerView.setHasFixedSize(true);
        medicationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        medicationRecyclerView.setAdapter(medicationsViewAdapter);

        medicationViewModel.getAllMedications().observe(this, medicationsViewAdapter::updateData);

        onMedicationSwipe().attachToRecyclerView(medicationRecyclerView);
    }

    public void onClickAddMedication(View view) {
        Intent intent = new Intent(this, MedicationEditActivity.class);
        startActivity(intent);
    }

    private ItemTouchHelper onMedicationSwipe() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                MedicationWithAlertTimestamps medicationToRemove = medicationsViewAdapter.getMedications().get(position);

                AlertDialog alertDialog = new AlertDialog.Builder(viewHolder.itemView.getContext())
                    .setPositiveButton(R.string.ok, (dialog, id) -> {
                        medicationViewModel.remove(medicationToRemove);
                        medicationsViewAdapter.notifyItemRemoved(position);
                    })
                    .setNegativeButton(R.string.cancel, (dialog, id) -> medicationsViewAdapter.notifyItemChanged(position))
                    .setMessage(R.string.remove_medication)
                    .setTitle(R.string.remove_medication_title)
                    .create();

                alertDialog.show();
            }
        });
    }
}