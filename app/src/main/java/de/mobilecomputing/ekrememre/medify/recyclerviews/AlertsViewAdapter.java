package de.mobilecomputing.ekrememre.medify.recyclerviews;

import android.util.Log;
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

        public ViewHolder(View view) {
            super(view);

            alerttimeLabel = (TextView) view.findViewById(R.id.alerttimeLabel);
        }

        public TextView getAlerttimeLabel() {
            return alerttimeLabel;
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
        Log.d(TAG, "onBindViewHolder: pos " + position);
    }

    @Override
    public int getItemCount() {
        return alertTimestamps.size();
    }
}
