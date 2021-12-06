package de.mobilecomputing.ekrememre.medify.recyclerviews;

import android.content.Intent;
import android.icu.util.Calendar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.mobilecomputing.ekrememre.medify.MainActivity;
import de.mobilecomputing.ekrememre.medify.MedicationEditActivity;
import de.mobilecomputing.ekrememre.medify.R;
import de.mobilecomputing.ekrememre.medify.entities.MedicationWithAlertTimestamps;

public class MedicationsViewAdapter extends RecyclerView.Adapter<MedicationsViewAdapter.ViewHolder> {
    private static final String TAG = "MedicationsViewAdapter";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final TextView nameLabel;
        private final TextView nextAlertLabel;
        private final TextView weekdayLabel;

        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.medication_cardview);
            nameLabel = (TextView) view.findViewById(R.id.name_label);
            nextAlertLabel = (TextView) view.findViewById(R.id.next_alarm_label);
            weekdayLabel = (TextView) view.findViewById(R.id.weekday_label);
        }

        public void bind(MedicationWithAlertTimestamps medication) {
            Calendar nextAlert = medication.getNextAlarm();
            int hour = nextAlert.get(Calendar.HOUR_OF_DAY);
            int minute = nextAlert.get(Calendar.MINUTE);

            switch (nextAlert.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                    weekdayLabel.setText(R.string.weekday_monday);
                    break;
                case Calendar.TUESDAY:
                    weekdayLabel.setText(R.string.weekday_tuesday);
                    break;
                case Calendar.WEDNESDAY:
                    weekdayLabel.setText(R.string.weekday_wednesday);
                    break;
                case Calendar.THURSDAY:
                    weekdayLabel.setText(R.string.weekday_thursday);
                    break;
                case Calendar.FRIDAY:
                    weekdayLabel.setText(R.string.weekday_friday);
                    break;
                case Calendar.SATURDAY:
                    weekdayLabel.setText(R.string.weekday_saturday);
                    break;
                case Calendar.SUNDAY:
                    weekdayLabel.setText(R.string.weekday_sunday);
                    break;
            }

            nameLabel.setText(medication.getMedication().getName());
            nextAlertLabel.setText(String.format("%02d:%02d", hour, minute));
        }
    }

    private List<MedicationWithAlertTimestamps> medications;

    public MedicationsViewAdapter() {
        this.medications = new ArrayList<>();
    }

    @NonNull
    @Override
    public MedicationsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.medication_item, viewGroup, false);

        return new MedicationsViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationsViewAdapter.ViewHolder holder, int position) {
        holder.bind(this.medications.get(position));

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.cardView.getContext(), MedicationEditActivity.class);
            intent.putExtra(MainActivity.MEDICATION_ID_EXTRA, medications.get(position).getMedication().medicationId);

            holder.cardView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    public void updateData(final List<MedicationWithAlertTimestamps> newMedications) {
        MedicationsDiffCallback medicationsDiffCallback = new MedicationsDiffCallback(this.medications, newMedications);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(medicationsDiffCallback);
        this.medications.clear();
        this.medications.addAll(newMedications);
        diffResult.dispatchUpdatesTo(this);
    }
}
