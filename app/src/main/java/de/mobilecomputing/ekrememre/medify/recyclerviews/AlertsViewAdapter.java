package de.mobilecomputing.ekrememre.medify.recyclerviews;

import android.annotation.SuppressLint;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.mobilecomputing.ekrememre.medify.R;
import de.mobilecomputing.ekrememre.medify.entities.AlertTimestamp;

public class AlertsViewAdapter extends RecyclerView.Adapter<AlertsViewAdapter.ViewHolder> {
    private static final String TAG = "AlertsViewAdapter";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView alerttimeLabel;
        private final TextView mondayLabel;
        private final TextView tuesdayLabel;
        private final TextView wedsdayLabel;
        private final TextView thursdayLabel;
        private final TextView fridayLabel;
        private final TextView saturdayLabel;
        private final TextView sundayLabel;

        public ViewHolder(View view) {
            super(view);

            alerttimeLabel = (TextView) view.findViewById(R.id.alerttime_label);
            mondayLabel = (TextView) view.findViewById(R.id.monday_label);
            tuesdayLabel = (TextView) view.findViewById(R.id.tuesday_label);
            wedsdayLabel = (TextView) view.findViewById(R.id.wednesday_label);
            thursdayLabel = (TextView) view.findViewById(R.id.thursday_label);
            fridayLabel = (TextView) view.findViewById(R.id.friday_label);
            saturdayLabel = (TextView) view.findViewById(R.id.saturday_label);
            sundayLabel = (TextView) view.findViewById(R.id.sunday_label);
        }

        public TextView getAlerttimeLabel() {
            return alerttimeLabel;
        }
    }

    private final List<AlertTimestamp> alertTimestamps;
    private int weekday_inactive_color;

    public AlertsViewAdapter(List<AlertTimestamp> alertTimestamps){
        this.alertTimestamps = alertTimestamps;
    }

    @NonNull
    @Override
    public AlertsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.alerttime_item, viewGroup, false);

        weekday_inactive_color = v.getContext().getColor(R.color.weekday_active);

        return new ViewHolder(v);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull AlertsViewAdapter.ViewHolder holder, int position) {
        if (this.alertTimestamps.get(position).timestamps.isEmpty()) {
            throw new IllegalStateException("Timestamps member of AlertTimestamp cannot be empty.");
        }

        int hour = 0;
        int minute = 0;

        for (long timestamp : this.alertTimestamps.get(position).timestamps) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);

            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                    holder.mondayLabel.getBackground().setTint(weekday_inactive_color);
                    break;
                case Calendar.TUESDAY:
                    holder.tuesdayLabel.getBackground().setTint(weekday_inactive_color);
                    break;
                case Calendar.WEDNESDAY:
                    holder.wedsdayLabel.getBackground().setTint(weekday_inactive_color);
                    break;
                case Calendar.THURSDAY:
                    holder.thursdayLabel.getBackground().setTint(weekday_inactive_color);
                    break;
                case Calendar.FRIDAY:
                    holder.fridayLabel.getBackground().setTint(weekday_inactive_color);
                    break;
                case Calendar.SATURDAY:
                    holder.saturdayLabel.getBackground().setTint(weekday_inactive_color);
                    break;
                case Calendar.SUNDAY:
                    holder.sundayLabel.getBackground().setTint(weekday_inactive_color);
                    break;
            }
        }

        holder.alerttimeLabel.setText(String.format("%02d:%02d", hour, minute));
    }

    @Override
    public int getItemCount() {
        return alertTimestamps.size();
    }
}
