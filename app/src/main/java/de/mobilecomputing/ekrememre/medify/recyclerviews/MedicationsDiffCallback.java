package de.mobilecomputing.ekrememre.medify.recyclerviews;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import de.mobilecomputing.ekrememre.medify.entities.MedicationWithAlertTimestamps;

public class MedicationsDiffCallback extends DiffUtil.Callback {
    private final List<MedicationWithAlertTimestamps> oldList;
    private final List<MedicationWithAlertTimestamps> newList;

    public MedicationsDiffCallback(List<MedicationWithAlertTimestamps> oldList, List<MedicationWithAlertTimestamps> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getMedication().medicationId == newList.get(newItemPosition).getMedication().medicationId;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        MedicationWithAlertTimestamps oldItem = oldList.get(oldItemPosition);
        MedicationWithAlertTimestamps newItem = newList.get(newItemPosition);

        boolean id = oldItem.getMedication().medicationId == newItem.getMedication().medicationId;
        boolean name = oldItem.getMedication().getName().equals(newItem.getMedication().getName());
        boolean description = oldItem.getMedication().getDescription().equals(newItem.getMedication().getDescription());
        boolean timestamps = oldItem.getAlertTimestamps().size() == newItem.getAlertTimestamps().size();

        boolean timestampsIds = true;
        boolean medicationParentIds = true;
        boolean alerts = true;

        if (timestamps) {
            for (int i = 0; i < oldList.get(oldItemPosition).getAlertTimestamps().size(); i++) {
                if (oldItem.getAlertTimestamps().get(i).alertTimestampId != newItem.getAlertTimestamps().get(i).alertTimestampId) {
                    timestampsIds = false;
                    break;
                }

                if (oldItem.getAlertTimestamps().get(i).medicationParentId != newItem.getAlertTimestamps().get(i).medicationParentId) {
                    medicationParentIds = false;
                    break;
                }

                alerts = oldItem.getAlertTimestamps().get(i).getTimestamps().size() == newItem.getAlertTimestamps().get(i).getTimestamps().size();

                if (alerts) {
                    for (int j = 0; j < oldItem.getAlertTimestamps().get(i).getTimestamps().size(); j++) {
                        if (!oldItem.getAlertTimestamps().get(i).getTimestamps().get(j).equals(newItem.getAlertTimestamps().get(i).getTimestamps().get(j))) {
                            alerts = false;
                            break;
                        }
                    }
                }
            }
        }

        return id && name && description && timestamps && timestampsIds && medicationParentIds && alerts;
    }
}
