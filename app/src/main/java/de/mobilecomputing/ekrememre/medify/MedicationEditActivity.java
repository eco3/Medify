package de.mobilecomputing.ekrememre.medify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import de.mobilecomputing.ekrememre.medify.entities.AlertTimestamp;
import de.mobilecomputing.ekrememre.medify.recyclerviews.AlertsViewAdapter;

public class MedicationEditActivity extends AppCompatActivity implements AddAlertDialogFragment.AddAlertDialogListener {
    private static final String TAG = "MedicationEditActivity";
    public static final String MEDICATION_OBJECT = "MEDICATION_OBJECT";

    private EditText mnameEditText;
    private EditText mdescriptionEditText;

    private RecyclerView malertRecyclerview;
    private AlertsViewAdapter malertsViewAdapter;

    private List<AlertTimestamp> alertTimestamps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        Intent intent = getIntent();
        int requestCode = intent.getIntExtra(MainActivity.REQUEST_CODE_EXTRA, -1);

        if (requestCode == -1) {
            throw new IllegalStateException("No RequestCode was set.");
        }

        mnameEditText = (EditText) findViewById(R.id.name_editText);
        mdescriptionEditText = (EditText) findViewById(R.id.description_editText);

        alertTimestamps = new ArrayList<>();
        malertsViewAdapter = new AlertsViewAdapter(alertTimestamps);

        malertRecyclerview = findViewById(R.id.alert_recyclerview);
        malertRecyclerview.setHasFixedSize(true);
        malertRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        malertRecyclerview.setAdapter(malertsViewAdapter);

        onAlertSwipe().attachToRecyclerView(malertRecyclerview);
    }

    public void onAddAlert(View view) {
        DialogFragment newFragment = new AddAlertDialogFragment();
        newFragment.show(getSupportFragmentManager(), "addAlert");
    }

    @Override
    public void onDialogPositiveClick(Integer hour, Integer minute, List<Integer> weekdays) {
        alertTimestamps.add(new AlertTimestamp(hour, minute, weekdays));
        malertsViewAdapter.notifyItemInserted(alertTimestamps.size());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_medication) {
            // TODO: save medication.

//            Medication medication = new Medication(name, description, timestamps);
//            Intent intent = new Intent();
//            intent.putExtra(MEDICATION_OBJECT, medication);
//            setResult(RESULT_OK, intent);
//            finish();
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

                Snackbar.make(malertRecyclerview, R.string.alert_deletion_message, Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> {
                            alertTimestamps.add(position, deletedTimestamp);
                            malertsViewAdapter.notifyItemInserted(position);
                        }).show();
            }
        });
    }
}