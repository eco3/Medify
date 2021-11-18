package de.mobilecomputing.ekrememre.medify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import de.mobilecomputing.ekrememre.medify.entities.AlertTimestamp;
import de.mobilecomputing.ekrememre.medify.entities.Medication;
import de.mobilecomputing.ekrememre.medify.fragments.AddAlertDialogFragment;
import de.mobilecomputing.ekrememre.medify.recyclerviews.AlertsViewAdapter;
import de.mobilecomputing.ekrememre.medify.viewmodels.MedicationViewModel;

public class MedicationEditActivity extends AppCompatActivity implements AddAlertDialogFragment.AddAlertDialogListener {
    private static final String TAG = "MedicationEditActivity";

    private EditText mnameEditText;
    private EditText mdescriptionEditText;

    private AlertsViewAdapter malertsViewAdapter;

    private List<AlertTimestamp> alertTimestamps;
    private MedicationViewModel medicationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        mnameEditText = (EditText) findViewById(R.id.name_editText);
        mdescriptionEditText = (EditText) findViewById(R.id.description_editText);
        medicationViewModel = new ViewModelProvider(this).get(MedicationViewModel.class);

        Intent intent = getIntent();
        long medication_id = intent.getLongExtra(MainActivity.MEDICATION_ID_EXTRA, -1);

        if (medication_id == -1) {
            alertTimestamps = new ArrayList<>();
        } else {
            Log.d(TAG, "onCreate: " + medication_id);
            alertTimestamps = new ArrayList<>();
        }

        malertsViewAdapter = new AlertsViewAdapter(alertTimestamps);
        RecyclerView alertRecyclerView = findViewById(R.id.alert_recyclerview);

        alertRecyclerView.setHasFixedSize(true);
        alertRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        alertRecyclerView.setAdapter(malertsViewAdapter);

        onAlertSwipe().attachToRecyclerView(alertRecyclerView);
    }

    public void onAddAlert(View view) {
        DialogFragment newFragment = new AddAlertDialogFragment();
        newFragment.show(getSupportFragmentManager(), "addAlert");
    }

    @Override
    public void onDialogPositiveClick(Integer hour, Integer minute, List<Integer> weekdays) {
        // TODO: Sort this list
        alertTimestamps.add(new AlertTimestamp(hour, minute, weekdays));
        malertsViewAdapter.notifyItemInserted(alertTimestamps.size());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_medication) {
            if (mnameEditText.getText().toString().equals("")) {
                Snackbar.make(findViewById(R.id.medication_constraint), R.string.medication_name_empty_warning, Snackbar.LENGTH_LONG).show();
                return false;
            }
            if (alertTimestamps.isEmpty()) {
                Snackbar.make(findViewById(R.id.medication_constraint), R.string.alerttimestamps_empty_warning, Snackbar.LENGTH_LONG).show();
                return false;
            }

            Medication medication = new Medication(
                    mnameEditText.getText().toString(),
                    mdescriptionEditText.getText().toString()
            );

            medicationViewModel.insert(medication, alertTimestamps);

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.medication_menu, menu);
        return true;
    }

    private ItemTouchHelper onAlertSwipe() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                AlertTimestamp deletedTimestamp = alertTimestamps.get(position);

                alertTimestamps.remove(position);
                malertsViewAdapter.notifyItemRemoved(position);

                Snackbar.make(findViewById(R.id.medication_constraint), R.string.alert_deletion_message, Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> {
                            alertTimestamps.add(position, deletedTimestamp);
                            malertsViewAdapter.notifyItemInserted(position);
                        }).show();
            }
        });
    }
}