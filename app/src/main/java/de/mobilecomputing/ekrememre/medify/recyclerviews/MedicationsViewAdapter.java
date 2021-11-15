package de.mobilecomputing.ekrememre.medify.recyclerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.mobilecomputing.ekrememre.medify.R;
import de.mobilecomputing.ekrememre.medify.entities.Medication;
import de.mobilecomputing.ekrememre.medify.entities.MedicationWithAlertTimestamps;

public class MedicationsViewAdapter extends RecyclerView.Adapter<MedicationsViewAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameLabel;

        public ViewHolder(View view) {
            super(view);

            nameLabel = (TextView) view.findViewById(R.id.name_label);
        }

        public void bind(MedicationWithAlertTimestamps medication) {
            nameLabel.setText(medication.getMedication().getName());
        }
    }

    private List<MedicationWithAlertTimestamps> medications;

    public MedicationsViewAdapter() {
        this.medications = new ArrayList<>();
    }

    @NonNull
    @Override
    public MedicationsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.medication_item, viewGroup, false);

        return new MedicationsViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationsViewAdapter.ViewHolder holder, int position) {
        holder.bind(this.medications.get(position));
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    public void updateUserList(final List<MedicationWithAlertTimestamps> medications) {
        this.medications.clear();
        this.medications = medications;
        notifyDataSetChanged();
    }
}
