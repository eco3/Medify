package de.mobilecomputing.ekrememre.medify.recyclerviews;

import android.annotation.SuppressLint;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.mobilecomputing.ekrememre.medify.R;
import de.mobilecomputing.ekrememre.medify.entities.AlertTimestamp;

public class AlertsViewAdapter extends RecyclerView.Adapter<AlertsViewAdapter.ViewHolder> {
    private static final String TAG = "AlertsViewAdapter";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
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

            cardView = (CardView) view.findViewById(R.id.alerttime_cardview);
            alerttimeLabel = (TextView) view.findViewById(R.id.alerttime_label);
            mondayLabel = (TextView) view.findViewById(R.id.monday_label);
            tuesdayLabel = (TextView) view.findViewById(R.id.tuesday_label);
            wedsdayLabel = (TextView) view.findViewById(R.id.wednesday_label);
            thursdayLabel = (TextView) view.findViewById(R.id.thursday_label);
            fridayLabel = (TextView) view.findViewById(R.id.friday_label);
            saturdayLabel = (TextView) view.findViewById(R.id.saturday_label);
            sundayLabel = (TextView) view.findViewById(R.id.sunday_label);
        }

        @SuppressLint("DefaultLocale")
        public void bind(List<Long> timestamps) {
            int hour = 0;
            int minute = 0;

            for (long timestamp : timestamps) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timestamp);

                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                    case Calendar.MONDAY:
                        mondayLabel.getBackground().setTint(this.itemView.getContext().getColor(R.color.weekday_active));
                        break;
                    case Calendar.TUESDAY:
                        tuesdayLabel.getBackground().setTint(this.itemView.getContext().getColor(R.color.weekday_active));
                        break;
                    case Calendar.WEDNESDAY:
                        wedsdayLabel.getBackground().setTint(this.itemView.getContext().getColor(R.color.weekday_active));
                        break;
                    case Calendar.THURSDAY:
                        thursdayLabel.getBackground().setTint(this.itemView.getContext().getColor(R.color.weekday_active));
                        break;
                    case Calendar.FRIDAY:
                        fridayLabel.getBackground().setTint(this.itemView.getContext().getColor(R.color.weekday_active));
                        break;
                    case Calendar.SATURDAY:
                        saturdayLabel.getBackground().setTint(this.itemView.getContext().getColor(R.color.weekday_active));
                        break;
                    case Calendar.SUNDAY:
                        sundayLabel.getBackground().setTint(this.itemView.getContext().getColor(R.color.weekday_active));
                        break;
                }
            }

            alerttimeLabel.setText(String.format("%02d:%02d", hour, minute));
        }
    }

    private final List<AlertTimestamp> alertTimestamps;

    public AlertsViewAdapter(List<AlertTimestamp> alertTimestamps){
        this.alertTimestamps = alertTimestamps;
    }

    @NonNull
    @Override
    public AlertsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.alerttime_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertsViewAdapter.ViewHolder holder, int position) {
        if (this.alertTimestamps.get(position).getTimestamps().isEmpty()) {
            throw new IllegalStateException("Timestamps member of AlertTimestamp cannot be empty.");
        }

        holder.bind(this.alertTimestamps.get(position).getTimestamps());
    }

    @Override
    public int getItemCount() {
        return alertTimestamps.size();
    }
}
