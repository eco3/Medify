package de.mobilecomputing.ekrememre.medify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.EditText;

public class MedicationEditActivity extends AppCompatActivity {

    public EditText etimeEditText;

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

        etimeEditText = (EditText) findViewById(R.id.time_editText);

        if (requestCode == MainActivity.ADD_MEDICATION_REQUEST_CODE) {
            int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);

            etimeEditText.setText(String.format("%02d:%02d", currentHour, currentMinute));
        }

        etimeEditText.setOnClickListener(v -> {
            int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(this,
                    (timePicker, selectedHour, selectedMinute) -> etimeEditText.setText(String.format("%02d:%02d", selectedHour, selectedMinute)),
                    currentHour, currentMinute, true);

            mTimePicker.show();
        });
    }
}