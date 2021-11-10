package de.mobilecomputing.ekrememre.medify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

import de.mobilecomputing.ekrememre.medify.entities.Medication;

public class MedicationEditActivity extends AppCompatActivity {
    private static final String TAG = "MedicationEditActivity";
    public static final String MEDICATION_OBJECT = "MEDICATION_OBJECT";

    public EditText mnameEditText;
    public EditText mdescriptionEditText;
    public EditText mtimeEditText;

    public CheckBox mmondayCheckbox;
    public CheckBox mtuesdasCheckbox;
    public CheckBox mwednesdayCheckbox;
    public CheckBox mthursdayCheckbox;
    public CheckBox mfridayCheckbox;
    public CheckBox msaturdayCheckbox;
    public CheckBox msundayCheckbox;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_edit);

        Intent intent = getIntent();
        int requestCode = intent.getIntExtra(MainActivity.REQUEST_CODE_EXTRA, -1);

        if (requestCode == -1) {
            throw new IllegalStateException("No RequestCode was set.");
        }

        mnameEditText = (EditText) findViewById(R.id.name_editText);
        mdescriptionEditText = (EditText) findViewById(R.id.description_editText);
        mtimeEditText = (EditText) findViewById(R.id.time_editText);

        mmondayCheckbox = (CheckBox) findViewById(R.id.monday_checkBox);
        mtuesdasCheckbox = (CheckBox) findViewById(R.id.tuesday_checkBox);
        mwednesdayCheckbox = (CheckBox) findViewById(R.id.wednesday_checkBox);
        mthursdayCheckbox = (CheckBox) findViewById(R.id.thursday_checkBox);
        mfridayCheckbox = (CheckBox) findViewById(R.id.friday_checkBox);
        msaturdayCheckbox = (CheckBox) findViewById(R.id.saturday_checkBox);
        msundayCheckbox = (CheckBox) findViewById(R.id.sunday_checkBox);

        if (requestCode == MainActivity.ADD_MEDICATION_REQUEST_CODE) {
            int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);

            mtimeEditText.setText(String.format("%02d:%02d", currentHour, currentMinute));
        }

        mtimeEditText.setOnClickListener(v -> {
            int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(this,
                    (timePicker, selectedHour, selectedMinute) -> mtimeEditText.setText(String.format("%02d:%02d", selectedHour, selectedMinute)),
                    currentHour, currentMinute, true);

            mTimePicker.show();
        });
    }

    public void onSaveMedication(View view) {
        String name = mnameEditText.getText().toString();
        String description = mdescriptionEditText.getText().toString();
        ArrayList<Integer> weekdays = getWeekdays();
        ArrayList<Long> timestamps = new ArrayList<>();

        for (Integer weekday : weekdays) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(mtimeEditText.getText().toString().substring(0,2)));
            calendar.set(Calendar.MINUTE, Integer.parseInt(mtimeEditText.getText().toString().substring(3,5)));
            calendar.set(Calendar.DAY_OF_WEEK, weekday);

            timestamps.add(calendar.getTimeInMillis());
        }

//        Medication medication = new Medication(name, description, timestamps);

//        Intent intent = new Intent();
//        intent.putExtra(MEDICATION_OBJECT, medication);
//
//        setResult(RESULT_OK, intent);
//        finish();
    }


    private ArrayList<Integer> getWeekdays(){
        ArrayList<Integer> weekdays = new ArrayList<>();

        if (mmondayCheckbox.isChecked()) {
            weekdays.add(Calendar.MONDAY);
        }
        if (mtuesdasCheckbox.isChecked()) {
            weekdays.add(Calendar.TUESDAY);
        }
        if (mwednesdayCheckbox.isChecked()) {
            weekdays.add(Calendar.WEDNESDAY);
        }
        if (mthursdayCheckbox.isChecked()) {
            weekdays.add(Calendar.THURSDAY);
        }
        if (mfridayCheckbox.isChecked()) {
            weekdays.add(Calendar.FRIDAY);
        }
        if (msaturdayCheckbox.isChecked()) {
            weekdays.add(Calendar.SATURDAY);
        }
        if (msundayCheckbox.isChecked()) {
            weekdays.add(Calendar.SUNDAY);
        }

        return weekdays;
    }
}