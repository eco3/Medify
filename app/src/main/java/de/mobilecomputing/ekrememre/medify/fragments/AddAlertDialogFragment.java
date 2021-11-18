package de.mobilecomputing.ekrememre.medify.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import de.mobilecomputing.ekrememre.medify.R;

public class AddAlertDialogFragment extends DialogFragment {
    private static final String TAG = "AddAlertDialogFragment";

    public interface AddAlertDialogListener {
        void onDialogPositiveClick(Integer hour, Integer minute, List<Integer> weekdays);
    }

    private AddAlertDialogListener listener;

    private TimePicker mtimePicker;
    private CheckBox mmondayCheckbox;
    private CheckBox mtuesdasCheckbox;
    private CheckBox mwednesdayCheckbox;
    private CheckBox mthursdayCheckbox;
    private CheckBox mfridayCheckbox;
    private CheckBox msaturdayCheckbox;
    private CheckBox msundayCheckbox;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alerttime_dialog, null);

        mtimePicker = (TimePicker) view.findViewById(R.id.timepicker);
        mmondayCheckbox = (CheckBox) view.findViewById(R.id.monday_checkBox);
        mtuesdasCheckbox = (CheckBox) view.findViewById(R.id.tuesday_checkBox);
        mwednesdayCheckbox = (CheckBox) view.findViewById(R.id.wednesday_checkBox);
        mthursdayCheckbox = (CheckBox) view.findViewById(R.id.thursday_checkBox);
        mfridayCheckbox = (CheckBox) view.findViewById(R.id.friday_checkBox);
        msaturdayCheckbox = (CheckBox) view.findViewById(R.id.saturday_checkBox);
        msundayCheckbox = (CheckBox) view.findViewById(R.id.sunday_checkBox);

        mtimePicker.setIs24HourView(true);
        setWeekdayCheckbox();

        builder.setView(view)
            .setPositiveButton(R.string.save, (dialog, id) -> {
                if (getWeekdays().isEmpty()) {
                    // TODO: create feedback, if no weekdays were checked.
                    // Snackbar.make(view, R.string.weekday_empty_warning, Snackbar.LENGTH_LONG).show();
                } else {
                    listener.onDialogPositiveClick(mtimePicker.getHour(), mtimePicker.getMinute(), getWeekdays());
                }
            })
            .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss());

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddAlertDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Class must implement AddAlertDialogListener");
        }
    }

    private List<Integer> getWeekdays(){
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

    private void setWeekdayCheckbox() {
        Calendar calendar = Calendar.getInstance();

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                mmondayCheckbox.setChecked(true);
                break;
            case Calendar.TUESDAY:
                mtuesdasCheckbox.setChecked(true);
                break;
            case Calendar.WEDNESDAY:
                mwednesdayCheckbox.setChecked(true);
                break;
            case Calendar.THURSDAY:
                mthursdayCheckbox.setChecked(true);
                break;
            case Calendar.FRIDAY:
                mfridayCheckbox.setChecked(true);
                break;
            case Calendar.SATURDAY:
                msaturdayCheckbox.setChecked(true);
                break;
            case Calendar.SUNDAY:
                msundayCheckbox.setChecked(true);
                break;
        }
    }
}
